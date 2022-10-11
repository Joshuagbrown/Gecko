Feature:  Manipulate the stations

  Background:




  Scenario: User want to save journey
    Given User logged in with "admin" and "123456789"
    When The user save the start point "3 ilam road" and end point "499 greers road" and save the journey
    Then the user have save journey with the start "3 ilam road" and end point "499 greers road"

  Scenario:

