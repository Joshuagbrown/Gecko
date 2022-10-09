Feature:  LoginController

  Background:



  Scenario: Logging in as valid user
    Given I am on the login screen
    When I login with username "admin" and password "123456789"
    Then I am logged in success

