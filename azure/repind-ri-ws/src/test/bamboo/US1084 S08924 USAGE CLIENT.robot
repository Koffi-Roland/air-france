*** Settings ***
Suite Setup       Before
Suite Teardown    After
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           String
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${password}       Abcd123+
${accountId}      0
${gin}            0
${email}          0

*** Test Cases ***
Verify usage client exit after create/update individual
    [Tags]    REPIND-1084
    Create User    ISI
    Update User    BDC
    @{usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    2
    ${result} =    Get From List    ${usages}    0
    Should Contain Any    ${result}    ISI    BDC
    ${result} =    Get From List    ${usages}    1
    Should Contain Any    ${result}    ISI    BDC

Verify usage client is not created after gender update
    [Tags]    REPIND-1084
    Create User    ISI
    Update User Gender
    ${usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    2    #Evol mig 8924 2 usages attendus dans ce cas

Verify default usage for individu creation is RPD
    [Tags]    REPIND-1084
    Create User Default
    ${usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    0    # evol mig 8924 plus d usage dans ce cas

Verify no usage client created for prospect
    [Tags]    REPIND-1084
    Create Prospect
    ${usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    0

Verify no usage client created for traveler
    [Tags]    REPIND-1084
    Create Traveler
    ${usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    0

Verify usage client is updated after birthdate update
    [Tags]    REPIND-1084
    Create User    ISI
    Update User Birthdate    #with BDC usage
    ${usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    2
    ${result} =    Get From List    ${usages}    0
    Should Contain Any    ${result}    ISI    BDC
    ${result} =    Get From List    ${usages}    1
    Should Contain Any    ${result}    BDC    ISI

Verify usage client is RPD \ for enrollMyAccount
    [Tags]    REPIND-1084
    Enroll User
    @{usages} =    SelectMultiple    ${DB_CONNECT_STRING}    SELECT SCODE FROM USAGE_CLIENTS WHERE SGIN = '${gin}'
    Log    ${usages}
    Length Should Be    ${usages}    1
    ${result} =    Get From List    ${usages}    0
    Should Contain Any    ${result}    RPD

*** Keywords ***
After
    Clean database after

Clean database after
    Clean Database By Gin    ${DB_CONNECT_STRING}    ${gin}

Create User
    [Arguments]    ${code}
    ${random} =    Generate Random String    6    [LETTERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><token>WSSiC2</token><applicationCode>${code}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>todfhjsdf</numberAndStreet><city>${random}</city><zipCode>31000</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><birthDate>2018-03-19T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>Ethanol</lastNameSC><firstNameSC>POTATIUM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${soapBody}
    Set Global Variable    ${gin}

Create User Default
    ${random} =    Generate Random String    6    [LETTERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>todfhjsdf</numberAndStreet><city>${random}</city><zipCode>31000</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><birthDate>2018-03-19T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>Ethanol</lastNameSC><firstNameSC>POTATIUM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${soapBody}
    Set Global Variable    ${gin}

Update User
    [Arguments]    ${code}
    ${random} =    Generate Random String    6    [LETTERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><token>WSSiC2</token><applicationCode>${code}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier><lastNameSC>TAPIZZ</lastNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}

Update User Gender
    ${random} =    Generate Random String    6    [LETTERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><token>WSSiC2</token><applicationCode>BDC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier><gender>M</gender></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}

Update User Birthdate
    ${random} =    Generate Random String    6    [LETTERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><token>WSSiC2</token><applicationCode>BDC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier><birthDate>1981-09-09T12:00:00</birthDate></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}

Create Prospect
    ${random} =    Generate Random String    6    [LETTERS]
    ${email}=    Convert To String    test${random}@airfrance.fr
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>W</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><individualRequest><individualInformations><birthDate>1982-09-09T12:00:00</birthDate><civility>MR</civility><lastNamePseudonym>${random}</lastNamePseudonym><lastNameSC>${random}</lastNameSC><firstNameSC>${random}</firstNameSC></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>N</communicationGroupeType><communicationType>AF</communicationType><optIn>Y</optIn><dateOfConsent>2015-10-15T12:00:00</dateOfConsent><subscriptionChannel>B2CHomepage</subscriptionChannel><marketLanguage><market>FR</market><language>FR</language><optIn>Y</optIn><dateOfConsent>2014-09-12T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${soapBody}
    Set Global Variable    ${gin}

Create Traveler
    ${random} =    Generate Random String    6    [LETTERS]
    ${numero} =    Generate Random String    6    [NUMBERS]
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>T</process><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>33</countryCode><phoneNumber>0622666200</phoneNumber></telecom></telecomRequest><individualRequest><individualInformations><gender>M</gender><status>V</status><civility>MR</civility><lastNameSC>${random}</lastNameSC><firstNameSC>${random}</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${soapBody}
    Set Global Variable    ${gin}

Enroll User
    ${random} =    Generate Random String    15    [LETTERS]
    ${email}=    Convert To String    test${random}@airfrance.fr
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>${random}</firstName><lastName>${random}</lastName><emailIdentifier>${email}</emailIdentifier><password>${password}</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>test</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${result} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${soapBody}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    Set Global Variable    ${gin}

Before
    CleanDatabase    ${DB_CONNECT_STRING}
