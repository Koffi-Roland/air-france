
Feature: Cross session

 As a human I should be able to login with a given userId and if a session cross is detected the backend sessions should be updated
 


  Scenario: login with backend server and switching habile headers
    Given I target backend server
    And I provide credential for admin
    When I invoke me endpoint with application/json; charset=UTF-8
    Then I am logged with id admin
    And I switch credential for user
    And I am logged with id user


 