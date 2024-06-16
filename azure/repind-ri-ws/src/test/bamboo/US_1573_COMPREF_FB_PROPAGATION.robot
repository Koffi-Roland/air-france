*** Settings ***
Documentation     Change all Language on FB Comprefs in one request
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1573
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
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/createOrUpdateComPrefBasedOnPermission.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}

*** Test Cases ***
Update All FB Comprefs in one request - CAPI
    #Enroll Individual
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTFBPROPAGATIONCAPI</firstName><lastName>TESTFBPROPAGATIONCAPI</lastName><emailIdentifier>TESTFBPROPAGATIONCAPI@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${result}=    EnrollMyAccountCustomerService-v3    ${ENV}    ${soapBody}
    Log    ${result}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #Update Postal Address FR
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    #Create Comprefs with Permission
    ${soapBody}=    Convert To String    <afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement><gin>${gin}</gin><permissionRequest><permission><permissionID>10000001</permissionID><permissionAnswer>true</permissionAnswer><market>FR</market><language>FR</language></permission></permissionRequest><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement>
    ${response}=    createOrUpdateComPrefBasedOnPermission-v1    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    #Check Comprefs created
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
        ${count}=    Get Length    ${listMarketLanguage}
    END
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='FRFR'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5
    #Update all FB Comprefs
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><updateCommunicationPrefMode>U</updateCommunicationPrefMode><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>F</domain><communicationGroupeType>N</communicationGroupeType><communicationType>FB_ESS</communicationType><optIn>Y</optIn><marketLanguage><market>DE</market><language>DE</language><optIn>Y</optIn></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8-consumerID    ${ENV}    ${soapBody}    w21375138
    Element Text Should Be    ${response}    true    xpath=.//success
    #Check Comprefs updated
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
        ${count}=    Get Length    ${listMarketLanguage}
    END
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='DEDE'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5

Update All FB Comprefs in one request - CBS
    #Enroll Individual
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTFBPROPAGATIONCBS</firstName><lastName>TESTFBPROPAGATIONCBS</lastName><emailIdentifier>TESTFBPROPAGATIONCBS@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${result}=    EnrollMyAccountCustomerService-v3    ${ENV}    ${soapBody}
    Log    ${result}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #Update Postal Address FR
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    #Create Comprefs with Permission
    ${soapBody}=    Convert To String    <afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement><gin>${gin}</gin><permissionRequest><permission><permissionID>10000001</permissionID><permissionAnswer>true</permissionAnswer><market>FR</market><language>FR</language></permission></permissionRequest><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement>
    ${response}=    createOrUpdateComPrefBasedOnPermission-v1    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    #Check Comprefs created
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
        ${count}=    Get Length    ${listMarketLanguage}
    END
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='FRFR'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5
    #Update all FB Comprefs
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><updateCommunicationPrefMode>U</updateCommunicationPrefMode><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>F</domain><communicationGroupeType>N</communicationGroupeType><communicationType>FB_ESS</communicationType><optIn>Y</optIn><marketLanguage><market>DE</market><language>DE</language><optIn>Y</optIn></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8-consumerID    ${ENV}    ${soapBody}    w06536507
    Element Text Should Be    ${response}    true    xpath=.//success
    #Check Comprefs updated
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
        ${count}=    Get Length    ${listMarketLanguage}
    END
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='DEDE'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5

Update All FB Comprefs in one request - Unauthorized consumer DALLAS
    #Enroll Individual
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTFBPROPAGATIONDALLAS</firstName><lastName>TESTFBPROPAGATIONDALLAS</lastName><emailIdentifier>TESTFBPROPAGATIONDALLAS@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${result}=    EnrollMyAccountCustomerService-v3    ${ENV}    ${soapBody}
    Log    ${result}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #Update Postal Address FR
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    #Create Comprefs with Permission
    ${soapBody}=    Convert To String    <afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement><gin>${gin}</gin><permissionRequest><permission><permissionID>10000001</permissionID><permissionAnswer>true</permissionAnswer><market>FR</market><language>FR</language></permission></permissionRequest><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement>
    ${response}=    createOrUpdateComPrefBasedOnPermission-v1    ${ENV}    ${soapBody}
    Element Text Should Be    ${response}    true    xpath=.//success
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    #Check Comprefs created
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
        ${count}=    Get Length    ${listMarketLanguage}
    END
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='FRFR'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5
    #Update all FB Comprefs
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><updateCommunicationPrefMode>U</updateCommunicationPrefMode><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${gin}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>F</domain><communicationGroupeType>N</communicationGroupeType><communicationType>FB_ESS</communicationType><optIn>Y</optIn><marketLanguage><market>DE</market><language>DE</language><optIn>Y</optIn></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response}=    CreateUpdateIndividualService-v8-consumerID    ${ENV}    ${soapBody}    w04766060
    Element Text Should Be    ${response}    true    xpath=.//success
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    Your application is not authorized to perform this update on Flying Blue Communication Preferences
    #Check Comprefs updated
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    @{listMarketLanguage}    Create List
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        Continue For Loop If    '${domain}'!='F'
        ${marketLanguage}    Get Element    ${ComPref}    .//communicationPreferences/marketLanguage
        Run Keyword If    '${domain}'=='F'    Append To List    ${listMarketLanguage}    ${marketLanguage}
    END
    ${count}=    Get Length    ${listMarketLanguage}
    Should be Equal as Integers    ${count}    5
    ${countML}=    Convert To Integer    0
    FOR    ${marketLanguageItem}    IN    @{listMarketLanguage}
        ${market}    Get Element Text    ${marketLanguageItem}    .//market
        ${language}    Get Element Text    ${marketLanguageItem}    .//language
        ${countML}=    Set Variable If    '${market}${language}'=='FRFR'    ${countML+1}
    END
    Should be Equal as Integers    ${countML}    5

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    CleanComPref    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1573_createPermission.sql

After
    Clean database    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1573_deletePermission.sql
    CleanComPref    ${DB_CONNECT_STRING}

Test number adresse
    [Arguments]    ${GLOBALGIN}    ${expectedNumber}
    #Provide GLOBALGIN SCOPE ADR POST
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    # Verifier number adresse is expectedNumber
    ${XML_object}=    Parse XML    ${response}
    ${postalAddressCount}=    Get Element Count    ${XML_object}    xpath=.//postalAddressResponse
    Should Be True    ${postalAddressCount} == ${expectedNumber}
    [Return]    ${GLOBALGIN}
