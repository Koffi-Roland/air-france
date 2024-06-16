
Feature: Session management

 As a human I should be requested to reauthenticate after the session expires
 

  Scenario: login with simul server and waiting for session to expire
    Given I target simul server
    And I log in as admin
    And I invoke me endpoint with application/json; charset=UTF-8
    And I wait for session to expire
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am rejected
    And I receive an empty response body

  Scenario: login with simul server and check session after login logout
    Given I target simul server
    And I log in as admin
    And I invoke me endpoint with application/json; charset=UTF-8
    Then I invoke setdatainsession endpoint to store value CucumberHello
    Then I invoke getdatainsession endpoint to check result CucumberHello
    And I logout
    And I provide no credential
    Then I invoke getdatainsession endpoint to check result NoData
  