Feature: log in user feature

  Scenario: User register the vehicle in his account
    Given User logged in with "admin" and "123456789"
    When User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN choose charger type "Type 2 Socketed"
    Then User has the vehicle in the its acctount with make "ARCIMOTO"  , year "2019" , model "EVERGREEN", charger type "Type 2 Socketed"


    Scenario: User change the vehicle year and model and save.
      Given User logged in with "admin" and "123456789"
      And User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN choose charger type "Type 2 Socketed"
      When User change the register vehicle year to "2020" , model "something"
      Then User has the vehicle in the its acctount with make "ARCIMOTO"  , year "2020" , model "something", charger type "Type 2 Socketed"


  Scenario: User delete the vehicle
    Given User logged in with "admin" and "123456789"
    And User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN choose charger type "Type 2 Socketed"
    When user selected the vehicle and delete it
    Then user has no vehicle in table.


  Scenario: User edit the charging station information
    Given User logged in with "admin" and "123456789"
    When User go to the data page
    And select the first station
    And click edit button
    And the station info by edit the name to "SOMETHING"
    And number of carpark to "3"
    And time limit to "240"
    And save the changes
    And User go to the data page
    And select the first station
    Then User have the charging station with those information

    Scenario: User edit the charger information of the station
      Given User logged in with "admin" and "123456789"
      And User go to the data page
      And select the first station
      And click edit button
      And click the view charger
      When User edit the charger wattage to "10"
      And click save change
      And User click the return Button
      And User click the save button in station
      And User go to the data page
      And select the first station
      Then the user have the charger type changed in that station


  Scenario: User add the new charger to the station
    Given User logged in with "admin" and "123456789"
    And User go to the data page
    And select the first station
    And click edit button
    And click the view charger
    And User click the add charger button
    When User edit the charger wattage to "10"
    And plug type to Type 2 Socketed
    And click save change
    And User click the return Button
    And User click the save button in station
    And User go to the data page
    And select the first station
    Then User have the new charger type changed in that station


    Scenario: User delete the fist charger at the selected station
      Given User logged in with "admin" and "123456789"
      And User go to the data page
      And select the first station
      And click edit button
      And click the view charger
      And User click the add charger button
      When User edit the charger wattage to "10"
      And plug type to Type 2 Socketed
      And click save change
      And User click the return Button
      And User click the save button in station
      And User go to the data page
      And select the first station
      And click edit button
      And click the view charger
      When User selected the first charger and delete it
      And User click the return Button
      And User click the save button in station
      And User go to the data page
      And select the first station
      Then User will only have the previous added charger info in that station


  Scenario: User delete the first station
    Given User logged in with "admin" and "123456789"
    And User go to the data page
    And select the first station
    And click edit button
    When user delete the charging station
    And User go to the data page
    And select the first station
    Then the first charging station has been deleted and replace by other one.


    Scenario: User add a new charging station and charger
      Given User logged in with "admin" and "123456789"
      And User go to the data page
      And click edit button
      When the station info by edit the name to "SOMETHING"
      And edit address to "3 ilam road"
      And number of carpark to "5"
      And input the operator as "Some one"
      And input the owner as "Some one"
      And choose no charging cost
      And choose has tourist attraction
      And choose 24 hour operative
      And time limit to "240"
      And click continue button
      And User edit the charger wattage to "10"
      And plug type to Type 2 CCS
      And click save change
      And click the save station
      And User filter the station by the station name "SOMETHING" and choose 24 hour operative and no charging cost and has tourist attraction
      And select the first station
      Then User have the charging station with related info






