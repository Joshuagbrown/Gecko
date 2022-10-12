Feature: log in user feature

  Scenario:
    Given User logged in with "admin" and "123456789"
    When User register vehicle with make "Honda", year "2015",model "fit", charger type "type 2"
    Then User has the vehicle in the its acctount with make "Honda", year "2015",Model "fit", charger type "type 2"