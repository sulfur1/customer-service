Feature: Insert customer using POST API and verify using GET API

  Scenario Outline: Validate post customer api response body works correctly
    Given I hit the url of post customer api endpoint
    When I pass the request body of customer name <Name> and surname <Surname> and email <Email> and telegramId <TelegramId>
    Then I receive the response code as 201
    Examples:
      | Name       | Surname   | Email              | TelegramId |
      | Username22 | Surname22 | java22@example.com | @0000022   |
