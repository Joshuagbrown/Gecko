Feature: log in user feature

#  Scenario:
#    Given User logged in with "admin" and "123456789"
#    When User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN input charger type "type 2"
#    Then User has the vehicle in the its acctount with make "ARCIMOTO"  , year "2019" , model "EVERGREEN" input charger type "type 2"


#    Scenario: User change the vehicl year and model and save.
#      Given User logged in with "admin" and "123456789"
#      And User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN input charger type "type 2"
#      When User change the register vehicle year to "2020" , model "something"
#      Then User has the vehicle in the its acctount with make "ARCIMOTO"  , year "2020" , model "something" input charger type "type 2"


  Scenario: User delete the vehicle
    Given User logged in with "admin" and "123456789"
    And User register vehicle by choose make ARCIMOTO  , year "2019" , model EVERGREEN input charger type "type 2"
    When user selected the vehicle and delete it
    Then user has no vehicle in table.


  Scenario: User edit the charging station information
    Given User logged in with "admin" and "123456789"
    When User go to the data page
    And select the first station
    And click edit button
    And the station info by edit the name to "SOMETHING"
    And edit address to "3 ilam road, christchurch"
    And number of carpark to "3"
    And time limit to "240"
    And save the changes
    Then User have the charging station with those information

    Scenario: User eidt the charger information of the station
      Given User logged in with "admin" and "123456789"
      And User go to the data page
      And select the first station
      And click edit button
      And click the view charger
      When User edit the charger wattage to "10"
      And plug type to CHAdeMo
      And click save change
      And User click the return Button
      And User click the save button in station
      Then the user have the charger type changed in that station


  Scenario: User add the charger information of the station
    Given User logged in with "admin" and "123456789"
    And User go to the data page
    And select the first station
    And click edit button
    And click the view charger
    And User click the add charger button
    When User edit the charger wattage to "10"
    And plug type to CHAdeMo
    And click save change
    And User click the return Button
    And User click the save button in station
    Then User have the new charger type changed in that station


    Scenario: User delete the fist charger at the selected station
      Given User logged in with "admin" and "123456789"
      And User go to the data page
      And select the first station
      And click edit button
      And click the view charger
      And User click the add charger button
      And User edit the charger wattage to "10"
      And plug type to CHAdeMo
      And click save change
      And User click the return Button
      And User click the save button in station
      When User selected the first charger and delete it
      Then User will only have the previous added charger info in that station


  Scenario: User delete the first station
    Given User logged in with "admin" and "123456789"
    And User go to the data page
    And select the first station
    When user delete the charging station
    Then the first charging station has been deleted and replace by other one.







