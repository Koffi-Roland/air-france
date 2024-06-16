
Feature: Login

 As a human I should be able to login
 
  Scenario: login simul server in html should raise 401 and redirect to login page
    Given I target simul server
    And I provide no credential
    When I invoke me endpoint with application/html
    Then I am rejected
    Then I am redirected to login page
    
  Scenario: login simul server in html should raise 401 with no content
    Given I target simul server
    And I provide no credential
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am rejected
    Then I receive an empty response body
    
  Scenario: login backend server should raise a 401 with a SS4H error message
    Given I target backend server
    And I provide no credential
    When I invoke me endpoint with application/json
    Then I am rejected
    Then I receive a SS4H error message

  Scenario: login with simul server
    Given I target simul server
    And I log in as admin
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am logged with id admin
    And I have a habile session cookie set to LOGGED

  Scenario: login with backend server and habile headers
    Given I target backend server
    And I provide credential for admin
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am logged with id admin
    
  Scenario: login with simul server and testing location rewritting
    Given I target simul server
    And I log in as admin
    When I invoke redirect-me endpoint with application/json; charset=UTF-8
    Then I am logged with id admin
    And I have a habile session cookie set to LOGGED

   Scenario: anonymous login with backend server and habile headers
    Given I target backend server
    And I provide anonymous authentication
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am logged with id anonymous

    