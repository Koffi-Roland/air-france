*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        gnm6ssyszyrsbuen5vcb6ysq
${API_SECRET}     JuMaK2WULn

*** Keywords ***
ProvideContractData-v1-by-gin
    [Arguments]    ${ENV}    ${gin}    ${status_code}
    [Documentation]    Provide all the contract related to the gin passed in parameter
    [Tags]    provide
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000491/V01    verify=True
    ${result}=    Get On Session    Swagger    /GIN/${gin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}

ProvideContractData-v1-by-cin
    [Arguments]    ${ENV}    ${cin}    ${status_code}
    [Documentation]    Provide all the contract related to individual of the contract with the cin in parameter
    [Tags]    provide
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000491/V01    verify=True
    ${result}=    Get On Session    Swagger    /CIN/${cin}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${status_code}
    Should Be Equal As Strings    ${result.status_code}    ${status_code}
    Log    ${result}
    [Return]    ${result}