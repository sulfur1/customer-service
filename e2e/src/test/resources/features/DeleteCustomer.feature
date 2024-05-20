Feature: delete customer using DELETE API

  Scenario Outline: validate delete customer api status code works correctly
    Given I hit the url of delete customer api endpoint
    When I pass the url of delete customer in the request with <CustomerId>
    Then I receive the response code as 204
    Examples:
      | CustomerId |
      | 37         |

