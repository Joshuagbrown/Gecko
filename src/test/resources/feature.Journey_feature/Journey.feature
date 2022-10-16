Feature:  Manipulate the stations

  Background:




  Scenario: User want to save journey
    Given User logged in with "admin" and "123456789"
    And User click plan Trip
    When The user save the start point "3 ilam road" and end point "499 greers road" and save the journey
    And User go to the save journey page in my detail
    Then the user have save journey with the start "3 ilam road" and end point "499 greers road"

  Scenario: User want to reload journey on the map page
    Given User logged in with "admin" and "123456789"
    And User click plan Trip
    And The user save the start point "3 ilam road" and end point "499 greers road" and save the journey
    And User remove the routing from the plan trip page
    And User go to the save journey page in my detail
    When User choose that journey and show it on the map
    Then User can see the journey reloaded on the plan trip page

  Scenario: (AT_17) User want to add one stop point on the journey
    Given User logged in with "admin" and "123456789"
    And User click plan Trip
    When The user save the start point "3 ilam road" and end point "499 greers road" and add "161 ilam road" as a stop point and save the journey
    Then the user have save journey with the start "3 ilam road" and end point "499 greers road" and "161 ilam road" as middle point

  Scenario: User want to delete journey
    Given User logged in with "admin" and "123456789"
    And User click plan Trip
    And The user save the start point "14 Suva Street, christchurch" and end point "499 greers road" and save the journey
    And User remove the routing from the plan trip page
    And The user save the start point "3 ilam road" and end point "499 greers road" and save the journey
    And User remove the routing from the plan trip page
    And User go to the save journey page in my detail
    When User delete the first selected journey
    Then the user have save journey with the start "3 ilam road" and end point "499 greers road"


