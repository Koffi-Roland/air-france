
Feature: Logout

 As a human I should be able to logout
 

  Scenario: logout with simul server should raise a 401
    Given I target simul server
    And I log in as admin
    And I invoke me endpoint with application/json; charset=UTF-8
    And I logout
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am rejected
    Then I receive an empty response body

   Scenario: login with simul server after logout
    Given I target simul server
    And I log in as admin
    And I invoke me endpoint with application/json; charset=UTF-8
    And I logout
    And I log in as user
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am logged with id user
