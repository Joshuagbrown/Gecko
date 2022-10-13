Feature:

  Background:

#    Scenario: User view the charging Station Information
#
#      Given User is on the data page
#      When User click the second station on the station table which name is
#      Then the user can see the information in the text area of the main screen

  Scenario: User filter the station

    Given User is on the data page
    When User filter the station by the station name "MERIDIAN" and choose 24 hour operative and no charging cost
    Then User has no vehicle on the table as no vehicle meet the situation.