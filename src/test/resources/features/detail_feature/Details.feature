Feature: Details page

  Scenario: (AT_14) Viewing user details
    Given User is logged in with username "test" and password "123456789"
    When User clicks My Details button
    Then User can see the my details page