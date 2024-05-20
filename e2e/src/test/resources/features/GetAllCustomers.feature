Feature: Get all customers from API

  Scenario: Verify the get API for the customers
    Given I hit the url of get customers api endpoint
    When I pass the url of customers in the request
    Then I receive the response code as 200

  Scenario Outline: Verify the email of the first customer is correct
    Given I hit the url of get customers api endpoint
    When I pass the url of customers in the request
    Then I verify that the email of the first customer is <FirstCustomerEmail>
    Examples:
      | FirstCustomerEmail |
      | test@example.com   |
