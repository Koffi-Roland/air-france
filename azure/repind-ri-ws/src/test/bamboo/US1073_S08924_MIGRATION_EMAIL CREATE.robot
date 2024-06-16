*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1073
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           String
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
Verify creation of individual and email D P
    [Tags]    REPINDXR-132
    #Create an individual with an EMAIL code D (direct) status V
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    Clean database after    #Delete individual to be created once more
    #Create an individual with an email code P (Business) status V
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    P
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    Clean database after    #Delete individual to be created once more
    #Create an individual with email code P (Business) status V and an EMAIL code D (direct) status V
    ${emailP} =    GenerateRandomValidEmail
    ${statusP}=    Convert To String    V
    ${codeP}=    Convert To String    P
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><emailRequest><email><mediumStatus>${statusP}</mediumStatus><mediumCode>${codeP}</mediumCode><email>${emailP}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    Check Data Email Valid    ${response}    ${emailP}    ${statusP}    ${codeP}
    Clean database after    #Delete individual to be created once more

Verify creation of email D Pfor existing individual
    [Tags]    REPINDXR-133
    #Initialise individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    #Add an EMAIL code D (direct) status V to a GIN
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    #Add an EMAIL code P (business) status V to a GIN
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    P
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    #Add an EMAIL code D (direct) status V and an EMAIL code P (business) status V to a GIN
    ${emailP} =    GenerateRandomValidEmail
    ${statusP}=    Convert To String    V
    ${codeP}=    Convert To String    P
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><emailRequest><email><mediumStatus>${statusP}</mediumStatus><mediumCode>${codeP}</mediumCode><email>${emailP}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    Check Data Email Valid    ${response}    ${emailP}    ${statusP}    ${codeP}
    Clean database after    #Delete individual to be created once more

Verify to create a new valid email in remplacement of the same deleted
    [Tags]    REPINDXR-134
    #Initialise individual with address status X
    ${emailP} =    GenerateRandomValidEmail
    ${statusP}=    Convert To String    X
    ${codeP}=    Convert To String    P
    ${email} =    GenerateRandomValidEmail
    ${status}=    Convert To String    X
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><emailRequest><email><mediumStatus>${statusP}</mediumStatus><mediumCode>${codeP}</mediumCode><email>${emailP}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    #Add an EMAIL code D (direct) status V to a GIN
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${email}    ${status}    ${code}
    #Add an EMAIL code P (business) status V to a GIN
    ${status}=    Convert To String    V
    ${code}=    Convert To String    P
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${emailP}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Data Email Valid    ${response}    ${emailP}    ${status}    ${code}

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
    Element Should Exist    ${response}    xpath=.//ProvideIndividualInformationResponseElement
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Should contain    ${responseDecoded}    ${email}
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
    END
    Should Be True    ${is_email_found}    #email not found in the response
    ${statusFound}=    Get Element Text    ${email_from_response}    xpath=.//mediumStatus
    Should Be Equal As Strings    ${statusFound}    ${status}    #Check status
    ${codeFound}=    Get Element Text    ${email_from_response}    xpath=.//mediumCode
    Should Be Equal As Strings    ${codeFound}    ${code}    #Check medium code
