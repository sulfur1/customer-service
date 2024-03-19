package com.iprody08.e2e.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReviewStepDefinitions {
    @Given("I have completed course in my customer")
    public void i_have_completed_course_in_my_customer() {
        System.out.println("Completed course.");
    }

    @When("I click the send feedback button")
    public void i_click_the_send_feedback_button() {
        System.out.println("Button");
    }

    @Then("I should post a review")
    public void i_should_post_a_review() {
        System.out.println("Completed post");
    }
}
