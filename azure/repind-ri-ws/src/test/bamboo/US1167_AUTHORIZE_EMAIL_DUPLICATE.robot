*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1167
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/commonUtils.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}
${EMAIL}          ${EMPTY}

*** Test Cases ***
Verify creation of individual and same email
    #Create an individual with an EMAIL code D (direct) status V
    ${email} =    Convert To String    test@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Should Not Be Empty    ${resultGIN}    GIN is not created
    # Provide the individual
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${resultGIN}
    #Create an individual with an email code P (Business) status V and same email
    ${email} =    Convert To String    test@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    P
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Should Not Be Empty    ${resultGIN}    GIN is not created
    # Provide the individual
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${resultGIN}

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    Clean database after

Clean database after
    Clean Database By Gin    ${DB_CONNECT_STRING}    ${GLOBALGIN}

Check Data Email Valid
    [Arguments]    ${response}    ${email}    ${status}=V    ${code}=D    # Must pass the response from provide and the email inserted
    [Documentation]    Check email from the response and if it's found and verify data of it:
    ...    - Status
    ...    - Code
    Should Contain    ${response}    ProvideIndividualInformationResponseElement
    Should Contain    ${response}    ${email}
    ${XML_object}=    Parse XML    ${response}
    ${emails}=    Get Elements    ${XML_object}    xpath=.//emailResponse/email
    ${length_emails}=    Get Length    ${emails}    #initialize to be used out of the loop
    ${index}=    Convert To Number    0    #initialize to be used out of the loop
    ${email_from_response}=    Convert To String    null    #initialize to be used out of the loop
    ${is_email_found}=    Set Variable    ${False}    #email found in response
    FOR    ${index}    IN RANGE    0    ${length_emails}
    ${email_from_response}=    Get From List    ${emails}    ${index}
    ${text_email}=    Get Element Text    ${email_from_response}    xpath=.//email
    ${is_email_found}=    Set Variable If    '${text_email}' == '${email}'    ${True}    ${False}    #Email found, exit the loop
    Exit For Loop If    '${text_email}' == '${email}'    #Email found, exit the loop
    Should Be True    ${is_email_found}    #email not found in the response
    ${statusFound}=    Get Element Text    ${email_from_response}    xpath=.//mediumStatus
    Should Be Equal As Strings    ${statusFound}    ${status}    #Check status
    ${codeFound}=    Get Element Text    ${email_from_response}    xpath=.//mediumCode
    Should Be Equal As Strings    ${codeFound}    ${code}    #Check medium code
