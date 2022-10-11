Feature:  LoginController

  Background:



  Scenario: Logging in as valid user
    Given User clicks My Details button
    And I am on the login screen
    When I login with username "admin" and password "123456789"
    And User clicks My Details button
    Then I am logged in success

  Scenario Outline: Logging in as invalid user
    Given User clicks My Details button
    And I am on the login screen
    When I login with username "<username>" and password "<password>"
    Then I am not logged in

    Examples:
      | username | password         |
      | admin    | notauserpassward |
      | admain    | 123456789       |
      | admain    | admain          |

  Scenario: (AT_14) Viewing user details
    Given User logged in with with username "admin" and password "123456789" and in my detail page
    When User clicks My Details button
    Then User can see the my details page


  Scenario:  (AT_) User Log Out
    Given User logged in with with username "admin" and password "123456789" and in my detail page
    When User clicks log out button
    And User clicks My Details button
    Then I am on the login screen

  Scenario: (AT_) User want to change the name
    Given User logged in with with username "admin" and password "123456789" and in my detail page
    When User click edit button
    And User input "Admin" on name space and input "3 ilam road" on the address space
    And User click confirm edit button
    Then User Name have changed into "Admin" and address has changed into "3 ilam road"

    Scenario: User want to see the help page
      Given User launch the the app
      When User click the help button
      Then User can see the help page and related button


