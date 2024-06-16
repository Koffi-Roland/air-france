package com.afklm.spring.security.habile.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EndpointResourceSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourcesSteps.class);

    @Autowired
    @Qualifier("simulRestTemplate")
    private RestTemplate simulRestTemplate;
    
    @Autowired
    SessionInterceptor sessionInterceptor;

    private ResponseEntity<String> response;
    
    @When("^I update csrf token to (.*)")
    public void csrf_token(String cookieValue) {
        sessionInterceptor.updateXsrfToken(cookieValue);
    }

    @When("^I remove csrf token")
    public void block_csrf_token() {
        sessionInterceptor.blockXsrfToken();
    }

    @When("^I invoke api(.*) endpoint$")
    public void i_invoke_api_user_endpoint(String endpoint) {
        i_invoke_endpoint("/api" + endpoint, HttpMethod.GET);
    }

    @When("I invoke api{string} endpoint for {string} method")
    public void i_invoke_api_user_endpoint_with_httpMethod(String endpoint, String httpMethod) {
        i_invoke_endpoint("/api" + endpoint, HttpMethod.valueOf(httpMethod));
    }

    @When("^I invoke actuator(.*) endpoint$")
    public void i_invoke_actuator_endpoint(String endpoint) {
        i_invoke_endpoint("/actuator" + endpoint, HttpMethod.GET);
    }

    private void i_invoke_endpoint(String endpoint, HttpMethod httpMethod) {
        LOGGER.info("Invoking endpoint '{}'", endpoint);
        response = simulRestTemplate.exchange(endpoint, httpMethod, null, String.class);
        LOGGER.info("Got response {}", response);
    }

    @Then("^access is (.*)$")
    public void check_access(String status) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(status.toUpperCase()));
    }

    @Before
    public void cleanup() {
        response = null;
    }
}
