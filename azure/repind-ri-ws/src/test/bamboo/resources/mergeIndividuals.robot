*** Settings ***
Library           ../scripts/mashery.py
Resource          ../env/variables.robot

*** Variables ***
${API_KEY}        nhmrtnkvbj5za2ajfhn8cz47
${API_SECRET}     uH8Q6SpgjQ

*** Keywords ***
ProvideMergeInformations-v1
    [Arguments]    ${GINA}    ${GINB}
	${sig}=    mashery.retrieve_mashery_token	${API_KEY}	${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000131/V01    verify=True
    ${result}=    Get On Session    Swagger    /merge/${GINA}/${GINB}	params=api_key=${API_KEY}&sig=${sig}    headers=&{Headers}
    Should Be Equal As Strings    ${result.status_code}    200
    Log    ${result}
    [Return]    ${result}
	
MergeIndividuals-v1
    [Arguments]    ${GINA}    ${GINB}	${BODY}
	${sig}=    mashery.retrieve_mashery_token	${API_KEY}	${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000131/V01    verify=True
    ${result}=    Post On Session    Swagger    /merge/${GINA}/${GINB}	params=api_key=${API_KEY}&sig=${sig}	headers=&{Headers}	data=${BODY}
    Should Be Equal As Strings    ${result.status_code}    200
    Log    ${result}
    [Return]    ${result}
