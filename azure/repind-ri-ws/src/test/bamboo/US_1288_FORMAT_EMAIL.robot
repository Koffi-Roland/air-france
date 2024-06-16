*** Settings ***
Suite Setup       Before
Force Tags        REPIND-1288
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot
Resource          %{SIC_RESOURCES}/searchIndividualByMulticriteria.robot
Resource          %{SIC_RESOURCES}/provideInfoOnAProspect.robot
Resource          %{SIC_RESOURCES}/provideGinForUserIdService.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
Verify Enroll V2 V3 Process - Check the email format
    # control sur l email
    @{EMAIL}=    Create List    SIC@ROBOTFRAMEWORK.FR    sic2@robotframework.fr    Sic3@RobotFramework.fr    sic4@RobotframeworK.fr    sic5 @ Robotframework.com    # List of email combination
    @{EMAILEXPECTED}=    Create List    sic@robotframework.fr    sic2@robotframework.fr    sic3@robotframework.fr    sic4@robotframework.fr    sic5@robotframework.com    # List of email combination
    # Loop on email List
    FOR    ${INDEX}    IN RANGE    0    4
        Log    ${INDEX}
        ${emailLocal} =    Get From List    ${EMAIL}    ${INDEX}
        ${emailExpectedLocal} =    Get From List    ${EMAILEXPECTED}    ${INDEX}
        Log    Call Enroll V2 process
        Check Enroll process v2    ${ENV}    ${emailLocal}    ${emailExpectedLocal}
        CleanDatabase    ${DB_CONNECT_STRING}
        Log    Call Enroll V3 process
        sleep    5s
        Check Enroll process v3    ${ENV}    ${emailLocal}    ${emailExpectedLocal}
    END

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}

Check Existing Email and Data
    [Arguments]    ${response}    ${email}    # Must pass the response from provide and the email inserted
    [Documentation]    Check email from the response and if found, verify data by calling Check data of email
    Element Should Exist    ${response}    xpath=.//ProvideIndividualInformationResponseElement
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
    Should Be True    ${is_email_found}    \    #email not found in the response

Check Enroll process v3
    [Arguments]    ${ENV}    ${email}    ${emailExpected}
    [Documentation]    Check enroll MYA process
    Log    email : -${email}- emaiexpected : -${emailExpected}-
    #CAll Enroll WS
    ${pwd}=    Convert To String    Robot2018
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV3</firstName><lastName>TESTENROLLV3</lastName><emailIdentifier>${email}</emailIdentifier><password>${pwd}</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v3    ${ENV}    ${body}
    Log    ${response}
    #Get GIN
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    #Assert Enroll
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email
    Element Text Should Be    ${response}    ${emailExpected}    xpath=.//email
    #Call CreateUpdateIndividual to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    #Provide email
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Existing Email and Data    ${response}    ${emailExpected}
    #call searchWS
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><populationTargeted>A</populationTargeted><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><contact><email>${email}</email></contact></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2    ${ENV}    ${body}
    Log    ${response}
    Element Text Should Be    ${response}    ${emailExpected}    xpath=.//email/email
    #ProvideGinForUserIdService-v1 with email
    ${bodyProvide} =    Convert To String    <afk0:ProvideGinForUserIdRequestElement><IdentifierType>EM</IdentifierType><CustomerIdentifier>${email}</CustomerIdentifier></afk0:ProvideGinForUserIdRequestElement>
    ${response} =    ProvideGinForUserIdService-v1-ReturnGin    ${ENV}    ${bodyProvide}
    Should Contain    ${response}    ${resultGIN}
    #ProvideGinForUserIdService-v1 with emailExpected
    ${bodyProvide} =    Convert To String    <afk0:ProvideGinForUserIdRequestElement><IdentifierType>EM</IdentifierType><CustomerIdentifier>${emailExpected}</CustomerIdentifier></afk0:ProvideGinForUserIdRequestElement>
    ${response} =    ProvideGinForUserIdService-v1-ReturnGin    ${ENV}    ${bodyProvide}
    Should Contain    ${response}    ${resultGIN}
    ############################################
    Log    Update V7 Provide V6
    sleep    5s
    #Call CreateUpdateIndividual 7 to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v7    ${ENV}    ${body}
    #Provide v6 email
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v6    ${ENV}    ${bodyProvide}
    Check Existing Email and Data    ${response}    ${emailExpected}
    Log    Update V6 Provide V5
    sleep    5s
    #Call CreateUpdateIndividual 6 to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${responseItems} =    CreateUpdateIndividualService-v6    ${ENV}    ${body}
    #Provide v5 email
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><ipAddress>10.10.10.10</ipAddress><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v5    ${ENV}    ${body}
    Check Existing Email and Data    ${response}    ${emailExpected}

Check Enroll process v2
    [Arguments]    ${ENV}    ${email}    ${emailExpected}
    [Documentation]    Check enroll MYA process
    Log    email : -${email}- emaiexpected : -${emailExpected}-
    #CAll Enroll WS
    ${pwd}=    Convert To String    Robot2018
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV2</firstName><lastName>TESTENROLLV2</lastName><emailIdentifier>${email}</emailIdentifier><password>${pwd}</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>0.0.0.0</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${body}    #Get GIN
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    #Assert Enroll
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email
    Element Text Should Be    ${response}    ${emailExpected}    xpath=.//email
    #Call CreateUpdateIndividual to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    #Provide email
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${bodyProvide}
    Check Existing Email and Data    ${response}    ${emailExpected}
    #call searchWS with email
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><populationTargeted>A</populationTargeted><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><contact><email>${email}</email></contact></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2    ${ENV}    ${body}
    Log    ${response}
    Element Text Should Be    ${response}    ${emailExpected}    xpath=.//email/email
    #ProvideGinForUserIdService-v1 with email
    ${bodyProvide} =    Convert To String    <afk0:ProvideGinForUserIdRequestElement><IdentifierType>EM</IdentifierType><CustomerIdentifier>${email}</CustomerIdentifier></afk0:ProvideGinForUserIdRequestElement>
    ${response} =    ProvideGinForUserIdService-v1-ReturnGin    ${ENV}    ${bodyProvide}
    Should Contain    ${response}    ${resultGIN}
    #ProvideGinForUserIdService-v1 with emailExpected
    ${bodyProvide} =    Convert To String    <afk0:ProvideGinForUserIdRequestElement><IdentifierType>EM</IdentifierType><CustomerIdentifier>${emailExpected}</CustomerIdentifier></afk0:ProvideGinForUserIdRequestElement>
    ${response} =    ProvideGinForUserIdService-v1-ReturnGin    ${ENV}    ${bodyProvide}
    Should Contain    ${response}    ${resultGIN}
    ############################################
    Log    Update V7 Provide V6
    sleep    5s
    #Call CreateUpdateIndividual 7 to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v7    ${ENV}    ${body}
    #Provide v6 email
    ${bodyProvide} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v6    ${ENV}    ${bodyProvide}
    Check Existing Email and Data    ${response}    ${emailExpected}
    Log    Update V6 Provide V5
    sleep    5s
    #Call CreateUpdateIndividual 6 to Change email
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${responseItems} =    CreateUpdateIndividualService-v6    ${ENV}    ${body}
    #Provide v5 email
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><ipAddress>10.10.10.10</ipAddress><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v5    ${ENV}    ${body}
    Check Existing Email and Data    ${response}    ${emailExpected}
