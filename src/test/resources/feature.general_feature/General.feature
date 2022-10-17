Feature:  General feature of the app


  Scenario: (AT_20) User want to see the help page
    Given User launch the the app
    When User click the help button
    Then User can see the help page and related button

  Scenario:
    Given User click the signup button in log in page
    When User input "pwl" in user name and input "123456789" as passward
    And input name as "someone"
    And input address as "3 ilam road" and signed up
    Then the user is signed up with name "someone" and address "3 ilam road"
