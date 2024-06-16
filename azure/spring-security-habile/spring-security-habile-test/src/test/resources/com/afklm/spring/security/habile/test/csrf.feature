
Feature: csrf

 As a human I should be able to login
 
  Scenario: Authorization for admin and /api/update on POST call
    Given I target simul server
    And I log in as admin
    When I invoke api"/update" endpoint for "POST" method
    Then access is forbidden
    When I invoke api/admin endpoint
    Then access is ok
    When I invoke api"/update" endpoint for "POST" method
    Then access is ok
    When I update csrf token to dummyCsrf
    When I invoke api"/update" endpoint for "POST" method
    Then access is forbidden
    When I invoke api"/update" endpoint for "POST" method
    Then access is ok
    When I remove csrf token
    When I invoke api"/update" endpoint for "POST" method
    Then access is forbidden