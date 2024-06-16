*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        xdz9rrzc8hgd2g39btskqqzs
${API_SECRET}     hxesO4t5Af

*** Keywords ***
ProvideContactData-v1-All
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000347/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideContactData-v1-Emails
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000347/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/emails    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideContactData-v1-Telecoms
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000347/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/telecoms    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideContactData-v1-Addresses
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-${ENV}.airfranceklm.com/passenger/R000347/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}/addresses    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}
