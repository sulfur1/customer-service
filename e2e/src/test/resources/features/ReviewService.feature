Feature: Review Service
  Scenario: Post a review
    Given I have completed course in my customer
    When I click the send feedback button
    Then I should post a review