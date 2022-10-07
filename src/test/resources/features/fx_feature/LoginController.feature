Feature:  LoginController


  Scenario: Logging in as valid user
    Given I am on the login screen
    When I login with username "pwl" and password "123456789"
    Then I am logged in success

