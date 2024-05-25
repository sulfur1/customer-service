Feature: Get customer by ID

  Scenario Outline: Retrieve an existing customer by Id
    Given I hit the url of get customers api endpoint
    When I pass the customer id <id> in the request
    Then I receive the response code as 200
    And I verify the response body contains the customer name <name> and surname <surname> and email <email> and telegramId <telegramId>

    Examples:
      | id | name       | surname   | email              | telegramId |
      | 37 | Username21 | Surname21 | java21@example.com | @0000021   |
