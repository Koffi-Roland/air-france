package com.afklm.spring.security.habile.test;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginResourcesSteps extends SpringIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResourcesSteps.class);

    /**
     * Habile session cookie
     */
    public static final String HABILE_SESSION_COOKIE = "MOCKSMSESSION";
    @Autowired
    @Qualifier("simulRestTemplate")
    private RestTemplate simulRestTemplate;

    @Autowired
    @Qualifier("demoRestTemplate")
    private RestTemplate demoTemplate;

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    private ResponseEntity<String> userResponse; // output

    private ServerTarget serverTarget;

    private String user;

    private ResponseEntity<String> logoutResponse;

    private ResponseEntity<String> dataInSessionResponse;

    private AuthenticationMode getAuthenticationMode() {
        AuthenticationMode authenticationMode = ServerTarget.BACKEND.equals(serverTarget) ? AuthenticationMode.HABILE : AuthenticationMode.BASIC;
        return authenticationMode;
    }

    private UserResource getUser(ResponseEntity<String> response) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(response.getBody(), UserResource.class);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        LOGGER.info("***********************************************************");
        LOGGER.info("Starting scenario '{}'", scenario.getName());
        sessionInterceptor.clear();
        this.serverTarget = null;
        authenticationInterceptor.setAuthentication(null, null, null);
    }

    @Given("I access protected resource in form auth")
    public void i_access_protected_resource_in_form_auth() throws Exception {
        // Login is done by invoking /me endpoint
        LOGGER.info("Querying /me endpoint not authenticated '{}' (form auth mode)", simulRestTemplate.getUriTemplateHandler().expand("/"));
        userResponse = getRestTemplate().exchange("/me", HttpMethod.GET, null, String.class);
        LOGGER.info("Queried /me endpoint and got response '{}'", userResponse);
    }

    @Then("^I am rejected")
    public void i_am_rejected_and_redirected_to_login_page() {
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Then("^I am redirected to login page")
    public void i_am_redirected_to_login_page() {
        String uri = getRestTemplate().getUriTemplateHandler()
            .expand("/login", new HashMap<>())
            .toString()
            .replace("/", "\\/");
        assertThat(userResponse.getBody()).contains("window.location = '" + uri + "';");
    }

    @Then("^I receive an empty response body")
    public void receive_an_empty_response_body() {
        assertThat(userResponse.getBody()).isNull();
    }

    @Then("^I receive a SS4H error message")
    public void receive_a_ss4h_error_message() {
        assertThat(userResponse.getBody()).contains("SS4H-MSG-004");
    }

    @Given("I authenticate as (.*)$")
    public void i_authenticate(String username) throws Exception {
        LOGGER.info("Authenticating (form auth mode) '{}'", getRestTemplate().getUriTemplateHandler().expand("/"));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", username);
        params.add("password", username + "-password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        userResponse = getRestTemplate().postForEntity("/login", request, String.class);
        LOGGER.info("Queried authentication '{}'", userResponse);
    }

    @Given("I target (.*) server$")
    public void i_target_server(String server) {
        LOGGER.info("Using server {}", server);
        this.serverTarget = ServerTarget.valueOf(server.toUpperCase());
    }

    @Given("^I provide credential for (.*)$")
    public void i_provide_credential_for(String user) {
        LOGGER.info("Injecting  user '{}'", user);
        this.user = user;
        authenticationInterceptor.setAuthentication(getAuthenticationMode(), user, user + "-password");
    }

    @Given("^I provide anonymous authentication")
    public void i_provide_anonymous_authentication() {
        LOGGER.info("Injecting  user '{}'", user);
        authenticationInterceptor.setAnonymousAuthentication(getAuthenticationMode());
    }

    @Given("I provide no credential")
    public void i_provide_no_credential() {
        sessionInterceptor.clear();
        authenticationInterceptor.setAuthentication(null, null, null);
        user = null;
    }

    @Given("I logout")
    public void i_logout() {
        LOGGER.info("Querying logout endpoint to login {}", getRestTemplate().getUriTemplateHandler().expand("/logout"));
        logoutResponse = getRestTemplate().exchange("/logout", HttpMethod.POST, null, String.class);
        LOGGER.info("Logout step 1 {}", logoutResponse);
        assertThat(logoutResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(logoutResponse.getHeaders()).containsKey("x-logout-url");
        logoutResponse = getRestTemplate().exchange("/mock/logout", HttpMethod.GET, null, String.class);
        LOGGER.info("Logout step 2 {}", logoutResponse);
        assertThat(logoutResponse.getStatusCode()).isIn(HttpStatus.OK);
        i_provide_no_credential();
    }

    @Given("I log in")
    public void i_log_in() throws Exception {
        LOGGER.info("Querying me endpoint to login {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        userResponse = getRestTemplate().exchange("/me", HttpMethod.GET, null, String.class);
        LOGGER.info("Queried me endpoint to login {}", userResponse);
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUser(userResponse).getUsername()).isEqualTo(user);
    }

    @Given("I log in as (.*)$")
    public void i_log_in_as(String who) throws Exception {
        LOGGER.info("Querying /login as {}", who);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", who);
        params.add("password", who + "-password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        userResponse = getRestTemplate().postForEntity("/login", request, String.class);
        LOGGER.info("Queried /login '{}'", userResponse);

        // userResponse = getRestTemp late().exchange("/me", HttpMethod.GET, null, String.class);
        // LOGGER.info("Queried me endpoint to login {}", userResponse);
        // assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(getUser(userResponse).getUsername()).isEqualTo(user);
    }

    @Given("I wait for session to expire")
    public void i_wait_for_session_to_expire() throws InterruptedException {
        LOGGER.info("Waiting for '{}' ms", 11000);
        Thread.sleep(11000);
    }

    @When("I invoke me endpoint")
    public void i_invoke_me_endpoint() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/html");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        LOGGER.info("Querying me endpoint {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        userResponse = getRestTemplate().exchange("/me", HttpMethod.GET, entity, String.class);
        LOGGER.info("Queried me endpoint {}", userResponse);
    }

    @When("I invoke me endpoint with (.*)$")
    public void i_invoke_me_endpoint_with(String accept) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, accept);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        LOGGER.info("Querying me endpoint {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        userResponse = getRestTemplate().exchange("/me", HttpMethod.GET, entity, String.class);
        LOGGER.info("Queried me endpoint {}", userResponse);
    }
    
    @When("I invoke redirect-me endpoint with (.*)$")
    public void i_invoke_redirect_me_endpoint_with(String accept) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, accept);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        LOGGER.info("Querying redirect-me endpoint {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        userResponse = getRestTemplate().exchange("/redirect-me", HttpMethod.GET, entity, String.class);
        LOGGER.info("Queried redirect-me endpoint {}", userResponse);
    }

    @When("I invoke setdatainsession endpoint to store value (.*)$")
    public void i_invoke_setdatainsession_endpoint(String what) throws Exception {
        LOGGER.info("Querying echo setdatainsession {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        getRestTemplate().exchange("/data/" + what, HttpMethod.PUT, null, String.class);
    }

    @Then("I invoke getdatainsession endpoint to check result (.*)$")
    public void i_invoke_getdatainsession_endpoint(String expectedData) throws Exception {
        LOGGER.info("Querying echo getdatainsession {}", getRestTemplate().getUriTemplateHandler().expand("/"));
        dataInSessionResponse = getRestTemplate().exchange("/public/data", HttpMethod.GET, null, String.class);
        LOGGER.info("Queried echo getdatainsession {}", dataInSessionResponse);
        assertThat(dataInSessionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dataInSessionResponse.getBody()).isEqualTo(expectedData);
    }

    private RestTemplate getRestTemplate() {
        if (ServerTarget.SIMUL.equals(serverTarget)) {
            return simulRestTemplate;
        } else if (ServerTarget.BACKEND.equals(serverTarget)) {
            return demoTemplate;
        }
        return null;
    }

    @Then("^I am requested to autenth with scheme ([A-Za-z]+) and realm <(.*)> auth$")
    public void i_am_requested_to_autenth_with_basic_auth(String scheme, String realm) {
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        // assertThat(userResponse.getHeaders().get(HttpHeaders.WWW_AUTHENTICATE)).hasSize(1);
        assertThat(userResponse.getBody()).contains("uuid");

    }

    @Then("^I am logged with id (.*)$")
    public void i_am_logged_with_id(String user) throws Exception {
        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getUser(userResponse).getUsername()).isEqualTo(user);
    }

    @Then("^I switch credential for (.*)$")
    public void i_switch_credential_for_user(String user) throws Exception {
        LOGGER.info("Switching  user '{}'", user);
        this.user = user;
        authenticationInterceptor.setAuthentication(getAuthenticationMode(), user, user + "-password");
        i_invoke_me_endpoint_with("application/json; charset=UTF-8");
    }

    @Then("^I have a habile session cookie set to (.*)$")
    public void i_have_habile_session_cookie_set_to(String expectedValue) {
        HttpCookie cookieHabile = sessionInterceptor.getCookie(HABILE_SESSION_COOKIE);
        assertThat(cookieHabile).isNotNull();
        assertThat(cookieHabile.getValue()).isEqualTo(expectedValue);
    }
}
