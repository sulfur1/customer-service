package com.iprody08.e2e.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReviewStepDefinitions {
    @Given("I have completed course in my customer")
    public void iHaveCompletedCourseInMyCustomer() {
        System.out.println("Completed course.");
    }

    @When("I click the send feedback button")
    public void iClickTheSendFeedbackButton() {
        System.out.println("Button");
    }

    @Then("I should post a review")
    public void iShouldPostAReview() {
        System.out.println("Completed post");
    }
}
