
Feature: Authorization

 As a human I should be able to see only authorized endpoints
 

  Scenario: Authorization for admin and /api/admin
    Given I target simul server
    And I log in as admin
    When I invoke api/admin endpoint
    Then access is ok

  Scenario: Authorization for admin and /api/user
    Given I target simul server
    And I log in as admin
    When I invoke api/user endpoint
    Then access is ok

  Scenario: Authorization for user and /api/admin
    Given I target simul server
    And I log in as user
    When I invoke api/admin endpoint
    Then access is forbidden

  Scenario: Authorization for user and /api/user
    Given I target simul server
    And I log in as user
    When I invoke api/user endpoint
    Then access is ok

  Scenario: Authorization for guest and /api/admin
    Given I target simul server
    And I log in as guest
    When I invoke api/admin endpoint
    Then access is forbidden

  Scenario: Authorization for guest and /api/user
    Given I target simul server
    And I log in as guest
    When I invoke api/user endpoint
    Then access is forbidden

  Scenario: Authorization for user and /actuator/health
    Given I target simul server
    And I log in as user
    When I invoke actuator/health endpoint
    Then access is forbidden

  Scenario: Authorization for admin and /actuator/health
    Given I target simul server
    And I log in as admin
    When I invoke actuator/health endpoint
    Then access is ok

  Scenario: Authorization for admin and /api/user-only/hello
    Given I target simul server
    And I log in as admin
    When I invoke api/user-only/hello endpoint
    Then access is forbidden

  Scenario: Authorization for user and /api/user-only/hello
    Given I target simul server
    And I log in as user
    When I invoke api/user-only/hello endpoint
    Then access is ok

  Scenario: Authorization for admin and /api/user-only/hello on OPTIONS call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/hello" endpoint for "OPTIONS" method
    Then access is forbidden

  Scenario: Authorization for admin and /api/user-only/byebye on GET call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/byebye" endpoint for "GET" method
    Then access is ok

  Scenario: Authorization for user and /api/user-only/byebye on GET call
    Given I target simul server
    And I log in as user
    When I invoke api"/user-only/byebye" endpoint for "GET" method
    Then access is forbidden

###############################################################
# Scenarios to check new "authorities" configuration property
###############################################################

  Scenario: Authorization for user with ASK_LESS permission and /api/user-only/howareyou on HEAD call
    Given I target simul server
    And I log in as user
    When I invoke api"/user-only/howareyou" endpoint for "HEAD" method
    Then access is ok

  Scenario: Authorization for admin with P_XXXXX_ADMIN role and /api/user-only/howareyou on HEAD call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/howareyou" endpoint for "HEAD" method
    Then access is ok

  Scenario: Authorization for user with ASK permission and /api/user-only/howareyou on GET call
    Given I target simul server
    And I log in as user
    When I invoke api"/user-only/howareyou" endpoint for "GET" method
    Then access is ok

  Scenario: Authorization for admin with ASK permission and /api/user-only/howareyou on GET call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/howareyou" endpoint for "GET" method
    Then access is ok

  Scenario: Authorization for user without proper permission and /api/user-only/howareyou on POST call
    Given I target simul server
    And I log in as user
    When I invoke api"/user-only/howareyou" endpoint for "POST" method
    Then access is forbidden

  Scenario: Authorization for admin with ASK_MORE permission and /api/user-only/howareyou on POST call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/howareyou" endpoint for "POST" method
# Access if forbidden on first attempt since there is no XSRF token header sent nor XSRF cookie to compare with
    Then access is forbidden
    When I invoke api"/user-only/howareyou" endpoint for "POST" method
# On second attempt, after proper XSRF stuff have been put in place, access is granted
    Then access is ok

  Scenario: Authorization for user with ASK_LESS permission and /api/user-only/howareyou on OPTIONS call
    Given I target simul server
    And I log in as user
    When I invoke api"/user-only/howareyou" endpoint for "OPTIONS" method
    Then access is ok

  Scenario: Authorization for admin with ASK_MORE permission and /api/user-only/howareyou on OPTIONS call
    Given I target simul server
    And I log in as admin
    When I invoke api"/user-only/howareyou" endpoint for "OPTIONS" method
    Then access is ok
