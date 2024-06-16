*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1222
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
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
Verify ws CreateOrUpdateAnIndividualV8 invalid parameter for inexistent type
    #Type EEE doesn t exist in DB
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>EEE</type><preferenceDatas><preferenceData><key>KLMHouseWish4</key><value>d</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_932

Verify ws CreateOrUpdateAnIndividualV8 invalid parameter for GPC type
    #KLMHouseWish4 doesn t exist for GPC
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas><preferenceData><key>KLMHouseWish4</key><value>d</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_932    #Invalid parameter: Unknown preference data key: KLMHouseWish4

Verify ws CreateOrUpdateAnIndividualV8 create and provide preferences KLM House wish
    #create wishhouse1
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas><preferenceData><key>KLMHouseWish1</key><value>vvv</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #provide
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    KLMHouseWish1

Verify ws CreateOrUpdateAnIndividualV8 update 1 preferences KLM Housewish and create 2 others
    #update wishhouse1 and create wishhouse2 wishhouse3
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas><preferenceData><key>KLMHouseWish1</key><value>vvv</value></preferenceData><preferenceData><key>KLMHouseWish2</key><value>xxx</value></preferenceData><preferenceData><key>KLMHouseWish3</key><value>vdddd</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    KLMHouseWish1
    Should Contain    ${responseDecoded}    KLMHouseWish2
    Should Contain    ${responseDecoded}    KLMHouseWish3

Verify ws CreateOrUpdateAnIndividualV8 create and delete 3 preferencesData KLM Housewish
    #create wishhouses
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas><preferenceData><key>KLMHouseWish1</key><value>vvv</value></preferenceData><preferenceData><key>KLMHouseWish2</key><value>xxx</value></preferenceData><preferenceData><key>KLMHouseWish3</key><value>vdddd</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #delete wishouses
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Should Not Contain    ${responseDecoded}    KLMHouseWish1
    Should Not Contain    ${responseDecoded}    KLMHouseWish2
    Should Not Contain    ${responseDecoded}    KLMHouseWish3

Verify ws CreateOrUpdateAnIndividualV8 create and delete GPC preference bloc
    #create wishhouses
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas><preferenceData><key>KLMHouseWish1</key><value>vvv</value></preferenceData><preferenceData><key>KLMHouseWish2</key><value>xxx</value></preferenceData><preferenceData><key>KLMHouseWish3</key><value>vdddd</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${preference}=    Get Elements    ${XML_object}    xpath=.//preferenceResponse/preference[type="GPC"]
    Log Many
    #delete wishouses
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><loggedGin>${GLOBALGIN}</loggedGin><ipAddress>11111111</ipAddress><applicationCode>MAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>GPC</type><preferenceDatas></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Should Not Contain    ${responseDecoded}    KLMHouseWish1
    Should Not Contain    ${responseDecoded}    KLMHouseWish2
    Should Not Contain    ${responseDecoded}    KLMHouseWish3

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    #enroll v2 - create individual
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>CBS</firstName><lastName>NoAnswer</lastName><emailIdentifier>cbs@robotframework.com</emailIdentifier><password>Robot2017</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>ROBOTFRAMEWORK</signature><ipAddress>localhost</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${resultGIN} =    EnrollMyAccountCustomerService-v2-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Add a FB contract to individual
    ${body} =    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${GLOBALGIN}</gin><actionCode>C</actionCode><contractRequest><contractData><key>QUALIFYINGSEGMENTS</key><value>2</value></contractData><contractData><key>TIERLEVEL</key><value>A</value></contractData><contractData><key>MEMBERTYPE</key><value>T</value></contractData><contractData><key>MILESBALANCE</key><value>123</value></contractData><contractData><key>QUALIFYINGMILES</key><value>1234</value></contractData><contract><contractNumber>999924800220</contractNumber><contractType>C</contractType><productType>FP</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${response} =    CreateOrUpdateRoleContractService-v1    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//contractNumber
    Element Text Should Be    ${response}    999924800220    xpath=.//contractNumber
    #Add FB contract and DOB
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>test@robotframework.com</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier><birthDate>1994-11-16T12:00:00</birthDate></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>FR</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}

After
    Clean database after

Clean database after
    Clean Database By Gin    ${DB_CONNECT_STRING}    ${GLOBALGIN}
