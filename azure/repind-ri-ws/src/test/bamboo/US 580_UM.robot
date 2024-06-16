*** Settings ***
Test Setup        Before
Test Teardown     After
Force Tags        REPIND-1022
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

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}
${GLOBALGIN2}     ${EMPTY}
${GLOBALGINUA}    ${EMPTY}
${GLOBALGINUM}    ${EMPTY}

*** Test Cases ***
Verify that an Persons Accompanying (UA) can be managed by RI WS
    [Tags]    REPINDXR-48    REPIND-580
    #Create an individual UA with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    #Provide v7 - retrieve UA
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGIN}    xpath=.//identifier
    #Provide v6 - can't retrieve a type K
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v6-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001
    #Provide v5 - can't retrieve a type K
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v5-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001
    #Create an individual UA with WS8 and V status without BirthDate
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}

Verify that an Unaccompanied Minors (UM) can be managed by RI WS
    [Tags]    REPINDXR-49    REPIND-580
    #Create an individual UM with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUM}    ${resultGIN}
    #Provide v7 - retrieve UM
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGIN}    xpath=.//identifier
    #Provide v6 - can't retrieve a UM type K
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v6-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001
    #Provide v5 - can't retrieve a UM type K
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v5-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001
    #Create an individual UM with WS8 and V status without BirthDate
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${result} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}

Verify that an UM can be created with complementary information
    [Tags]    REPINDXR-50    REPIND-580
    #Create an individual UM with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUM}    ${resultGIN}
    #Add complementary info Expect fault
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUM}</gin><delegationAction>A</delegationAction><delegationType>UM</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_708    #No FB or MyA related to the gin: '${GLOBALGIN}'
    #Add a FB contract to individual
    ${body} =    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${GLOBALGIN}</gin><actionCode>C</actionCode><contractRequest><contractData><key>QUALIFYINGSEGMENTS</key><value>2</value></contractData><contractData><key>TIERLEVEL</key><value>A</value></contractData><contractData><key>MEMBERTYPE</key><value>T</value></contractData><contractData><key>MILESBALANCE</key><value>123</value></contractData><contractData><key>QUALIFYINGMILES</key><value>1234</value></contractData><contract><contractNumber>R${GLOBALGIN}</contractNumber><contractType>C</contractType><productType>FP</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${response} =    CreateOrUpdateRoleContractService-v1    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//contractNumber
    Element Text Should Be    ${response}    R${GLOBALGIN}    xpath=.//contractNumber
    #Add complementary info
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUM}</gin><delegationAction>A</delegationAction><delegationType>UM</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide v7 - retrieve UM
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGINUM}</identificationNumber><option>GIN</option><scopeToProvide>DELEGATION</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//delegationDataResponse
    #Delete delegation
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUM}</gin><delegationAction>D</delegationAction><delegationType>UM</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide v7 - get Delegation
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>DELEGATION</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//delegationDataResponse

Verify that an UA can be created with complementary information
    [Tags]    REPINDXR-51    REPIND-580
    #Create an individual UA with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGINUA}    ${resultGIN}
    #Add complementary info Expect fault
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN2}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUA}</gin><delegationAction>A</delegationAction><delegationType>UM</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_708    #No FB or MyA related to the gin: '${GLOBALGIN}'
    #Add a FB contract to individual
    ${body} =    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${GLOBALGIN2}</gin><actionCode>C</actionCode><contractRequest><contractData><key>QUALIFYINGSEGMENTS</key><value>2</value></contractData><contractData><key>TIERLEVEL</key><value>A</value></contractData><contractData><key>MEMBERTYPE</key><value>T</value></contractData><contractData><key>MILESBALANCE</key><value>123</value></contractData><contractData><key>QUALIFYINGMILES</key><value>1234</value></contractData><contract><contractNumber>R${GLOBALGIN2}</contractNumber><contractType>C</contractType><productType>FP</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${response} =    CreateOrUpdateRoleContractService-v1    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//contractNumber
    Element Text Should Be    ${response}    R${GLOBALGIN2}    xpath=.//contractNumber
    #Add complementary info
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN2}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUA}</gin><delegationAction>A</delegationAction><delegationType>UA</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide v7 - retrieve UA
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGINUA}</identificationNumber><option>GIN</option><scopeToProvide>DELEGATION</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//delegationDataResponse
    #Delete delegation
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN2}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${GLOBALGINUA}</gin><delegationAction>D</delegationAction><delegationType>UA</delegationType></delegationData><complementaryInformation><type>IND</type><complementaryInformationDatas><complementaryInformationData><key>dateOfBirth</key><value>01/01/2001</value></complementaryInformationData></complementaryInformationDatas></complementaryInformation></delegate></accountDelegationDataRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide v7 - get Delegation
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN2}</identificationNumber><option>GIN</option><scopeToProvide>DELEGATION</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//delegationDataResponse

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    #Create an individual with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>test@robotframework.com</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>1994-11-16T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>FLYINGBLUE</firstNameSC></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>FR</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    Log    ${ENV}
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Create an individual with WS8 and V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>test@robotframework.com</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>1994-11-16T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>FLYINGBLUE</firstNameSC></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>FR</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN2}    ${resultGIN}

After
    Clean Database By Gin    ${DB_CONNECT_STRING}    ${GLOBALGIN}
