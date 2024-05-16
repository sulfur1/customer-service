Feature: Customer Service

  Scenario: Add a new customer
    Given a new customer with name "Kirill" and surname "Kozlov"
      And customer endpoint "api/customer/" with http method POST available
    When the user add the customer
    Then the customer add to database successful
      And response code is 201