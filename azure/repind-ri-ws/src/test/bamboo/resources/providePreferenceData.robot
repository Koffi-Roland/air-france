*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        jy3ch75ddbxnu862ekem3dfd
${API_SECRET}     WieK0fGu40

*** Keywords ***
ProvidePreferenceData-v1-All
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000380/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvidePreferenceData-v1-Preferences
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000380/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/preference    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvidePreferenceData-v1-CommPreferences
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000380/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/communicationpreference    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}
