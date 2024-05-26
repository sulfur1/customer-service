package com.iprody08.e2e.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class CustomerStepDefinitions {
    private static final String BASE_URL = "https://localhost";
    private static final String API_PATH = "/api";
    private static final String CUSTOMERS_PATH = API_PATH + "/customers";
    private static final String SLASH = "/";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String TELEGRAM_ID = "telegramId";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private RequestSpecification httpRequest;
    private Response response;
    private JSONObject requestParams;

    @Given("I hit the url of get customers api endpoint")
    public void hitTheUrlOfGetCustomersApiEndpoint() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @When("I pass the url of customers in the request")
    public void passTheUrlOfCustomersInTheRequest() {
        httpRequest = given();
        response = httpRequest.get(CUSTOMERS_PATH);
    }

    @Then("I receive the response code as {int}")
    public void receiveTheResponseCodeAs(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("I verify that the email of the first customer is {}")
    public void verifyThatTheEmailOfTheFirstCustomerIs(String expectedEmail) {
        String actualEmail = response.jsonPath().getString("email[0]");
        assertEquals(expectedEmail, actualEmail);
    }

    @Given("I hit the url of post customer api endpoint")
    public void hitTheUrlOfPostCustomerApiEndpoint() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
        httpRequest = given();
        requestParams = new JSONObject();
    }

    @When("I pass the request body of customer name {} and surname {} and email {} and telegramId {}")
    public void passTheRequestBodyOfCustomerNameCustomerName(
            String name, String surname, String email, String telegramId) {
        requestParams.put(NAME, name);
        requestParams.put(SURNAME, surname);
        requestParams.put("countryId", 1);
        requestParams.put(EMAIL, email);
        requestParams.put(TELEGRAM_ID, telegramId);

        httpRequest.header(CONTENT_TYPE, APPLICATION_JSON);
        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.post(CUSTOMERS_PATH);
    }

    @When("I pass the customer id {int} in the request")
    public void passTheCustomerIdInTheRequest(Integer id) {
        httpRequest = given();
        response = httpRequest.get(CUSTOMERS_PATH + SLASH + id);
    }

    @And("I verify the response body contains the customer name {} and surname {} and email {} and telegramId {}")
    public void verifyTheResponseBodyContainsTheCustomerNameAndSurnameAndEmailAndTelegramId(
            String name, String surname, String email, String telegramId) {
        String responseName = response.jsonPath().getString(NAME);
        String responseSurname = response.jsonPath().getString(SURNAME);
        String responseEmail = response.jsonPath().getString(EMAIL);
        String responseTelegramId = response.jsonPath().getString(TELEGRAM_ID);

        assertEquals(name, responseName);
        assertEquals(surname, responseSurname);
        assertEquals(email, responseEmail);
        assertEquals(telegramId, responseTelegramId);
    }

    @Given("I hit the url of put customer api endpoint")
    public void iHitTheUrlOfPutProductApiEndpoint() {
        RestAssured.baseURI = BASE_URL + CUSTOMERS_PATH;
        RestAssured.useRelaxedHTTPSValidation();
        requestParams = new JSONObject();
    }

    @When("I pass the request body with CustomerId {}, name {}, surname {}, email {}, and telegramId {}")
    public void iPassTheRequestBodyWithCustomerIdAndDetails(
            String customerId, String name, String surname, String email, String telegramId) {
        httpRequest = given();

        requestParams.put(NAME, name);
        requestParams.put(SURNAME, surname);
        requestParams.put(EMAIL, email);
        requestParams.put(TELEGRAM_ID, telegramId);

        httpRequest.header(CONTENT_TYPE, APPLICATION_JSON);
        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.put(SLASH + customerId);
    }

    @Given("I hit the url of delete customer api endpoint")
    public void iHitTheUrlOfDeleteCustomerApiEndpoint() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @When("I pass the url of delete customer in the request with {}")
    public void iPassTheUrlOfDeleteCustomerInTheRequestWith(String customerId) {
        response = given().delete(CUSTOMERS_PATH + SLASH + customerId);
    }
}
