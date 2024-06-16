*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
SearchGinByEmailService-v1
    [Arguments]    ${ENV}    ${email}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json
    Create Session    alias=Swagger    url=https://search-gin-by-email-${ENV}-cdm-00035-pks.qvi-cae.af-klm.com    verify=True
    ${result}=    Get On Session    Swagger    /${email}    headers=&{Headers}
    Should Be Equal As Strings    ${result.status_code}    200
    Log    ${result}
    [Return]    ${result}

SearchGinByEmailService-v1-ExpectFault
    [Arguments]    ${ENV}    ${email}    ${status_code}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json
    Create Session    alias=Swagger    url=https://search-gin-by-email-${ENV}-cdm-00035-pks.qvi-cae.af-klm.com    verify=True
    ${result}=    Get On Session    Swagger    /${email}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}
