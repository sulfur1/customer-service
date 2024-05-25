Feature: Update customer using PUT API

  Scenario Outline: validate put customer api status code works correctly
    Given I hit the url of put customer api endpoint
    When I pass the request body with CustomerId <CustomerId>, name <Name>, surname <Surname>, email <Email>, and telegramId <TelegramId>
    Then I receive the response code as 200
    Examples:
      | CustomerId | Name       | Surname   | Email              | TelegramId |
      | 37         | Username21 | Surname21 | java21@example.com | @0000021   |
