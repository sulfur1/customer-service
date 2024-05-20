Feature: Insert customer using POST API and verify using GET API

  Scenario Outline: Validate post customer api response body works correctly
    Given I hit the url of post customer api endpoint
    When I pass the request body of customer name <Name> and surname <Surname> and email <Email> and telegramId <TelegramId>
    Then I receive the response code as 201
    Examples:
      | Name       | Surname   | Email              | TelegramId |
      | Username22 | Surname22 | java22@example.com | @0000022   |

#  Scenario Outline: Validate get customer api retrieves the created customer correctly
#    Given I hit the url of get customers api endpoint
#    When I pass the url of customers in the request with stored id
#    Then I verify the response body contains the customer name <Name> and surname <Surname> and email <Email> and telegramId <TelegramId>
#
#    Examples:
#      | Name      | Surname   | Email              | TelegramId |
#      | Username10 | Surname10 | java10@example.com | @0000010   |
