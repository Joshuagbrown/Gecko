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
    When User go to the data page and select the first station and edit the station info by