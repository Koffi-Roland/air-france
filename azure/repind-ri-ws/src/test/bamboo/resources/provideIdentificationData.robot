*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        kh8r5cjj4rbtycm38ypaxmyk
${API_SECRET}     i4FhhDCBYi

*** Keywords ***
ProvideIdentificationData-v1-All
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000378/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideIdentificationData-v1-Identification
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000378/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/identification    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideIdentificationData-v1-Account
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000378/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/account    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideIdentificationData-v1-Delegation
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000378/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/delegation    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}