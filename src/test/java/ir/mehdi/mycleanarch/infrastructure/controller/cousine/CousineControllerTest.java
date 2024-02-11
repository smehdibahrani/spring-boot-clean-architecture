package ir.mehdi.mycleanarch.infrastructure.controller.cousine;


import ir.mehdi.mycleanarch.domain.models.Cousin;
import ir.mehdi.mycleanarch.domain.models.Identity;
import ir.mehdi.mycleanarch.domain.models.Store;
import ir.mehdi.mycleanarch.infrastructure.controller.common.BaseControllerTest;
import ir.mehdi.mycleanarch.infrastructure.controllers.cousine.CousinController;
import ir.mehdi.mycleanarch.infrastructure.entities.TestCoreEntityGenerator;
import ir.mehdi.mycleanarch.infrastructure.exceptions.NotFoundException;
import ir.mehdi.mycleanarch.usecases.UseCaseExecutorImpl;
import ir.mehdi.mycleanarch.usecases.cousine.GetAllCousinsUseCase;
import ir.mehdi.mycleanarch.usecases.cousine.GetStoresByCousineUseCase;
import ir.mehdi.mycleanarch.usecases.cousine.SearchCousineByNameUseCase;
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

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CousinController.class, secure = false)
public class CousineControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {
            "ir.mehdi.mycleanarch.infrastructure.controllers.cousine",
            "ir.mehdi.mycleanarch.infrastructure.exceptions"})
    static class Config {
    }

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @MockBean
    private GetStoresByCousineUseCase getStoresByCousineUseCase;

    @MockBean
    private GetAllCousinsUseCase getAllCousinesUseCase;

    @MockBean
    private SearchCousineByNameUseCase searchCousineByNameUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void getStoreByCousineIdReturnsNotFound() throws Exception {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetStoresByCousineUseCase.InputValues input = new GetStoresByCousineUseCase.InputValues(id);

        // and
        doThrow(new NotFoundException("Error"))
                .when(getStoresByCousineUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/cousin/" + id.getNumber() + "/stores");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void getStoresByCousineIdReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = store.getCousin().getId();
        GetStoresByCousineUseCase.InputValues input = new GetStoresByCousineUseCase.InputValues(id);
        GetStoresByCousineUseCase.OutputValues output =
                new GetStoresByCousineUseCase.OutputValues(singletonList(store));

        // and
        doReturn(output)
                .when(getStoresByCousineUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/cousin/" + id.getNumber() + "/stores");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousinId", is(id.getNumber().intValue())));
    }

    @Test
    public void getAllCousinesReturnsOk() throws Exception {
        // given
        List<Cousin> cousines = TestCoreEntityGenerator.randomCousines();
        Cousin firstCousine = cousines.get(0);

        GetAllCousinsUseCase.InputValues input = new GetAllCousinsUseCase.InputValues();
        GetAllCousinsUseCase.OutputValues output = new GetAllCousinsUseCase.OutputValues(cousines);

        // and
        doReturn(output)
                .when(getAllCousinesUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/cousin");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }

    @Test
    public void searchCousineByNameReturnsOk() throws Exception {
        // given
        List<Cousin> cousines = TestCoreEntityGenerator.randomCousines();
        Cousin firstCousine = cousines.get(0);
        SearchCousineByNameUseCase.InputValues input = new SearchCousineByNameUseCase.InputValues("abc");
        SearchCousineByNameUseCase.OutputValues output = new SearchCousineByNameUseCase.OutputValues(cousines);

        // and
        doReturn(output)
                .when(searchCousineByNameUseCase)
                .execute(input);
        // when
        final RequestBuilder payload = asyncGetRequest("/cousin/search/abc");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }
}