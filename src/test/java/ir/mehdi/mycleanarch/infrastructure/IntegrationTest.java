package ir.mehdi.mycleanarch.infrastructure;


import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignInRequest;
import ir.mehdi.mycleanarch.infrastructure.controllers.customer.SignUpRequest;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderRequest;
import ir.mehdi.mycleanarch.infrastructure.controllers.order.OrderRequestItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private static String USER_EMAIL = "email@email.com";
    private static String USER_PASSWORD = "password";
    private static final String USER_NAME = "name";
    private static final String USER_ADDRESS = "address";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getAllCousines() throws Exception {
        // given
        String url = createUrl("/cousin");

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoresByCousineId() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/cousin/1/stores"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void searchCousine() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/cousin/search/abc"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStores() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/store"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchStore() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/store/search/pizza"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoreById() throws Exception {
        ResponseEntity<String> response = template.getForEntity(createUrl("/store/1/"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getProductsByStoreId() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/store/1/products"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getAllProducts() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/product/"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getProductById() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/product/1"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void searchProductByNameOrDescription() throws Exception {
        // when
        ResponseEntity<String> response = template.getForEntity(createUrl("/product/search/temp"), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = BEFORE_TEST_METHOD)
    public void createCustomer() throws Exception {
        // when
        ResponseEntity<String> response =
                template.postForEntity(createUrl("/customer"), createSignUpRequest(), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = BEFORE_TEST_METHOD)
    public void authenticateCustomer() throws Exception {
        // given
        template.postForEntity(createUrl("/customer"), createSignUpRequest(), String.class);

        // when
        ResponseEntity<String> response =
                template.postForEntity(createUrl("/customer/auth"), createSignInRequest(), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Sql(scripts = "classpath:clean-up.sql", executionPhase = BEFORE_TEST_METHOD)
    public void createOrder() throws Exception {
        // given
        template.postForEntity(createUrl("/customer"), createSignUpRequest(), String.class);

        // when
        ResponseEntity<String> response =
                template.exchange(createUrl("/order"), HttpMethod.POST, createOrderRequest(), String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    // Helpers

    private SignUpRequest createSignUpRequest() {
        return new SignUpRequest(USER_NAME, USER_EMAIL, USER_ADDRESS, USER_PASSWORD);
    }

    private String createUrl(String path) throws Exception {
        return new URL(String.format("http://localhost:%s/api/v1/%s", port, path)).toString();
    }

    private HttpEntity createAuthRequest() throws Exception {
        return new HttpEntity(createAuthHeader());
    }

    private HttpHeaders createAuthHeader() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, createAuthToken());
        return headers;
    }

    private static class AuthenticationResponse {
        private String token;

        public String getToken() {
            return token;
        }
    }

    private String createAuthToken() throws Exception {
        return "Bearer " +
                template.postForEntity(createUrl("/customer/auth"), createSignInRequest(), AuthenticationResponse.class)
                        .getBody()
                        .getToken();
    }

    private SignInRequest createSignInRequest() {
        return new SignInRequest(USER_EMAIL, USER_PASSWORD);
    }

    private HttpEntity<OrderRequest> createOrderRequest() throws Exception {
        return new HttpEntity<>(createHaiShangOrder(), createAuthHeader());
    }

    private OrderRequest createHaiShangOrder() {
        Long storeId = 1L;
        return new OrderRequest(storeId, createOneShrimpTempura());
    }

    private List<OrderRequestItem> createOneShrimpTempura() {
        return Collections.singletonList(new OrderRequestItem(1L, 2));
    }
}
