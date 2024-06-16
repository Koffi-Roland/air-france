*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        96smn8p7qp59nzcd8esy89rb
${API_SECRET}     YyfX7WOAOL

*** Keywords ***
ManageExternalIdentifier-v1-Provide
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    [Documentation]    Provide all the external identifier related to the gin passed in parameter
    [Tags]    provide
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000447/V01    verify=True
    ${result}=    Get On Session    Swagger    /${gin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ManageExternalIdentifier-v1-Delete-with-Gin
    [Arguments]    ${ENV}    ${identifier}    ${type}    ${gin}    ${status_code}
    [Documentation]    Delete all the external identifier who have the gin, the identifier and the type passed in parameter
    [Tags]    delete
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000447/V01    verify=True
    ${result}=    Delete On Session    Swagger    /    params=identifier=${identifier}&type=${type}&gin=${gin}&api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ManageExternalIdentifier-v1-Delete-without-Gin
    [Arguments]    ${ENV}    ${identifier}    ${type}    ${status_code}
    [Documentation]    Delete all the external identifier who have the the identifier and the type passed in parameter
    [Tags]    delete
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000447/V01    verify=True
    ${result}=    Delete On Session    Swagger    /    params=identifier=${identifier}&type=${type}&api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}