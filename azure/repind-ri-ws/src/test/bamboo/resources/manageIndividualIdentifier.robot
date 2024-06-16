*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        8neap453n59kp4jasmhryqpu
${API_SECRET}     3e1krxXA1p

*** Keywords ***
ManageIndividualIdentifier-GetByEmail-v1
    [Arguments]    ${EMAIL}     ${EXPECTED_STATUS_CODE}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000298/V01   verify=True
    ${result}=    Get On Session    Swagger    /email/${EMAIL}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=${EXPECTED_STATUS_CODE}
    Should Be Equal As Strings    ${result.status_code}    ${EXPECTED_STATUS_CODE}
    Log    ${result}
    [Return]    ${result}

ManageIndividualIdentifier-GetByContract-v1
    [Arguments]    ${CIN}   ${EXPECTED_STATUS_CODE}
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000298/V01   verify=True
    ${result}=    Get On Session    Swagger    /contract/${CIN}    params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}   expected_status=${EXPECTED_STATUS_CODE}
    Should Be Equal As Strings    ${result.status_code}    ${EXPECTED_STATUS_CODE}
    Log    ${result}
    [Return]    ${result}
