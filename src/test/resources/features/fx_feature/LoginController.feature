Feature:  LoginController

  Background:



  Scenario: Logging in as valid user
    Given I am on the login screen
    When I login with username "admin" and password "123456789"
    Then I am logged in success

  Scenario: Logging in as invalid user
    Given I am on the login screen
    When I login with username "admin" and password "notauserpassward"
    Then I am not logged in

  Scenario: (AT_14) Viewing user details
    Given I am on the login screen
    And  I login with username "admin" and password "123456789"
    When User clicks My Details button
    Then User can see the my details page