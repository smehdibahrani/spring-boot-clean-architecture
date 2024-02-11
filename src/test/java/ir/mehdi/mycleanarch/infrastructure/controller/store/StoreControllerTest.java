package ir.mehdi.mycleanarch.infrastructure.controller.store;


import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Product;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.infrastructure.controller.common.BaseControllerTest;
import ir.mehdi.mycleanarch.infrastructure.controllers.store.StoreController;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.UseCaseExecutorImpl;
import ir.mehdi.mycleanarch.usecases.store.GetAllStoresUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetProductsByStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.GetStoreUseCase;
import ir.mehdi.mycleanarch.usecases.store.SearchStoresByNameUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StoreController.class, secure = false)
public class StoreControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {"ir.mehdi.mycleanarch.infrastructure.controllers.store",
            "ir.mehdi.mycleanarch.infrastructure.exceptions"})
    static class Config {
    }

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @MockBean
    private GetAllStoresUseCase getAllStoresUseCase;

    @MockBean
    private SearchStoresByNameUseCase searchStoresByNameUseCase;

    @MockBean
    private GetStoreUseCase getStoreUseCase;

    @MockBean
    private GetProductsByStoreUseCase getProductsByStoreUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void getAllStoresReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetAllStoresUseCase.InputValues input = new GetAllStoresUseCase.InputValues();
        GetAllStoresUseCase.OutputValues output = new GetAllStoresUseCase.OutputValues(singletonList(store));

        // and
        doReturn(output)
                .when(getAllStoresUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/store");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousinId", is(store.getCousin().getId().getNumber().intValue())));
    }

    @Test
    public void getStoreByIdentityReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetStoreUseCase.OutputValues output = new GetStoreUseCase.OutputValues(store);
        GetStoreUseCase.InputValues input = new GetStoreUseCase.InputValues(store.getId());

        // and
        doReturn(output)
                .when(getStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/store/" + store.getId().getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$.name", is(store.getName())))
                .andExpect(jsonPath("$.address", is(store.getAddress())))
                .andExpect(jsonPath("$.cousinId", is(store.getCousin().getId().getNumber().intValue())));
    }

    @Test
    public void getAllStoresNameMatchingReturnsStores() throws Exception {
        //given
        Store store = TestCoreEntityGenerator.randomStore();
        SearchStoresByNameUseCase.InputValues input = new SearchStoresByNameUseCase.InputValues("abc");
        SearchStoresByNameUseCase.OutputValues output =
                new SearchStoresByNameUseCase.OutputValues(singletonList(store));

        // and
        doReturn(output)
                .when(searchStoresByNameUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/store/search/abc");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousinId", is(store.getCousin().getId().getNumber().intValue())));
    }

    @Test
    public void getProductsByStoreIdReturnsStoreProducts() throws Exception {
        //given
        Product product = TestCoreEntityGenerator.randomProduct();
        Identity id = product.getStore().getId();
        GetProductsByStoreUseCase.InputValues input = new GetProductsByStoreUseCase.InputValues(id);
        GetProductsByStoreUseCase.OutputValues output = new GetProductsByStoreUseCase.OutputValues(singletonList(product));

        // and
        doReturn(output)
                .when(getProductsByStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/store/" + id.getNumber() + "/products");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(product.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$[0].description", is(product.getDescription())))
                .andExpect(jsonPath("$[0].price", is(product.getPrice())))
                .andExpect(jsonPath("$[0].storeId", is(id.getNumber().intValue())));
    }

    @Test
    public void getProductsByStoreIdReturnsNotFound() throws Exception {
        //given
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductsByStoreUseCase.InputValues input = new GetProductsByStoreUseCase.InputValues(id);

        // and
        doThrow(new NotFoundException("Error"))
                .when(getProductsByStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/store/" + id.getNumber() + "/products");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }
}