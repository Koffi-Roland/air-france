*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1490
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
Resource          %{SIC_RESOURCES}/searchIndividualByMulticriteria.robot
Resource          %{SIC_RESOURCES}/identifyCustomerCrossReferential.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${IPDADRESS}      LOCALHOST
${SIGNATURE}      ROBOTFRAMEWORK
${SITE}           QVI
${SNOM}           ROBOTFRAMEWORK
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
CreateOrUpdateAnIndividualWS STATUS H
    #Can't create an individual with H status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2017-12-04T12:00:00</birthDate><status>H</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>ROBOTFMK</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.detail.BusinessErrorBlocElement.businessError.errorDetail}    ConstraintViolationException

Update an individual with the statut H
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>H</process><requestor><channel>BAF</channel><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success

Provide do not return Individual H
    #Create Individual V
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>CREATEINDV</firstName><lastName>CREATEINDV</lastName><emailIdentifier>CREATEINDV@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${GIN} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${body}
    #Provide returns Individual I
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${GIN}    xpath=.//identifier
    #Update Individual V
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>H</process><requestor><channel>BAF</channel><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide do not retreive Individual H
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001

Search do not return Individual H
    #Create Individual V
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>CREATEINDVSEARCH</firstName><lastName>CREATEINDVSEARCH</lastName><emailIdentifier>CREATEINDVSEARCH@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${GIN} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${body}
    #Search returns Individual I
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><populationTargeted>A</populationTargeted><processType>M</processType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><identity><lastName>CREATEINDVSEARCH</lastName><firstName>CREATEINDVSEARCH</firstName></identity><contact><email>CREATEINDVSEARCH@AF.COM</email></contact></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${GIN}
    #Update Individual V
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>H</process><requestor><channel>BAF</channel><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Search do not retreive Individual H
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><populationTargeted>A</populationTargeted><processType>M</processType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><identity><lastName>CREATEINDVSEARCH</lastName><firstName>CREATEINDVSEARCH</firstName></identity><contact><email>CREATEINDVSEARCH@AF.COM</email></contact></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERR_001

Identify do not return Individual H
    #Create Individual V
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>CREATEINDVIDENTIFY</firstName><lastName>CREATEINDVIDENTIFY</lastName><emailIdentifier>CREATEINDVIDENTIFY@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${GIN} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${body}
    #Identify returns Individual I (Provide)
    ${body} =    Convert To String    <afk0:IdentifyCustomerCrossReferentialRequestElement><provideIdentifier><customerGin>${GIN}</customerGin><identificationType>GIN</identificationType></provideIdentifier><context><typeOfSearch>I</typeOfSearch><responseType>F</responseType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></context></afk0:IdentifyCustomerCrossReferentialRequestElement>
    ${response} =    IdentifyCustomerCrossReferential-v1    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${GIN}
    #Identify returns Individual I (Search)
    ${body} =    Convert To String    <afk0:IdentifyCustomerCrossReferentialRequestElement><searchIdentifier><personsIdentity><lastName>CREATEINDVIDENTIFY</lastName><lastNameSearchType>S</lastNameSearchType><firstName>CREATEINDVIDENTIFY</firstName><firstNameSearchType>S</firstNameSearchType></personsIdentity><email><email>CREATEINDVIDENTIFY@AF.COM</email></email></searchIdentifier><context><typeOfSearch>I</typeOfSearch><responseType>F</responseType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></context></afk0:IdentifyCustomerCrossReferentialRequestElement>
    ${response} =    IdentifyCustomerCrossReferential-v1    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${GIN}
    #Update Individual V
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>H</process><requestor><channel>BAF</channel><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Identify do not return Individual I (Provide)
    ${body} =    Convert To String    <afk0:IdentifyCustomerCrossReferentialRequestElement><provideIdentifier><customerGin>${GIN}</customerGin><identificationType>GIN</identificationType></provideIdentifier><context><typeOfSearch>I</typeOfSearch><responseType>F</responseType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></context></afk0:IdentifyCustomerCrossReferentialRequestElement>
    ${response} =    IdentifyCustomerCrossReferential-v1-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.faultstring}    NOT FOUND
    #Identify do not return Individual I (Search)
    ${body} =    Convert To String    <afk0:IdentifyCustomerCrossReferentialRequestElement><searchIdentifier><personsIdentity><lastName>CREATEINDVIDENTIFY</lastName><lastNameSearchType>S</lastNameSearchType><firstName>CREATEINDVIDENTIFY</firstName><firstNameSearchType>S</firstNameSearchType></personsIdentity><email><email>CREATEINDVIDENTIFY@AF.COM</email></email></searchIdentifier><context><typeOfSearch>I</typeOfSearch><responseType>F</responseType><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></context></afk0:IdentifyCustomerCrossReferentialRequestElement>
    ${response} =    IdentifyCustomerCrossReferential-v1-ExpectFault    ${ENV}    ${body}
    Should Contain    ${response.faultstring}    001

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>test@robotframework.com</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>1994-11-16T00:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>FLYINGBLUE</firstNameSC></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>FR</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}

After
    CleanDatabase    ${DB_CONNECT_STRING}
