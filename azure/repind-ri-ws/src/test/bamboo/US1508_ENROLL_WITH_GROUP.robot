*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1508
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

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${IPDADRESS}      LOCALHOST
${SIGNATURE}      ROBOTFRAMEWORK
${SITE}           QVI
${SNOM}           ROBOTFRAMEWORK
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
EnrollMyAccount With Group
    #Enroll a MyAccount using Group (S N AF)
    ${body}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>ENROLLWITHGROUP</firstName><lastName>ENROLLWITHGROUP</lastName><emailIdentifier>ENROLLWITHGROUP@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${GIN}=    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${body}
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>400620936983</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    ${comPrefFound}=    Set Variable    ${null}
    Log    ${comPrefFound}
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        ${groupe}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationGroupeType
        ${type}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationType
        ${comPrefFound}=    Set Variable If    '${domain}${groupe}${type}'=='SNAF'    Y
        Run Keyword If    '${domain}${groupe}${type}'=='SNAF'    Exit For Loop
    END
    Log    ${comPrefFound}
    Should Be Equal    ${comPrefFound}    Y

Traveler - Enroll a MyAccount using Group
    #Create a Traveler with S N AF (FR FR Optout)
    ${body}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>W</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><email>testenrolltravelergroup@af.com</email></email></emailRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>N</communicationGroupeType><communicationType>AF</communicationType><optIn>N</optIn><dateOfConsent>2017-08-04T12:00:00</dateOfConsent><subscriptionChannel>B2C</subscriptionChannel><marketLanguage><market>FR</market><language>FR</language><optIn>N</optIn><dateOfConsent>2017-08-04T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${GIN}=    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Check with Provide that S N AF (FR FR Optout)
    ${body}=    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${optin}    Get Element Text    ${ComPref}    .//communicationPreferences/optIn
        Should Be Equal    ${optin}    N
    END
    #Enroll the Traveler with Group S N AF (FR FR Optin)
    ${body}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>testenrolltravelergroup</firstName><lastName>testenrolltravelergroup</lastName><emailIdentifier>testenrolltravelergroup@af.com</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${response}=    EnrollMyAccountCustomerService-v3    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//gin
    #Check with Provide that S N AF (FR FR Optout)
    ${body}=    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    @{comPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    ${comPrefFoundOptin}=    Set Variable    ${null}
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        ${groupe}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationGroupeType
        ${type}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationType
        ${optin}    Get Element Text    ${ComPref}    .//communicationPreferences/optIn
        ${comPrefFoundOptin}=    Set Variable If    '${domain}${groupe}${type}'=='SNAF'    ${optin}
        Run Keyword If    '${domain}${groupe}${type}'=='SNAF'    Exit For Loop
    END
    Log    ${comPrefFoundOptin}
    Should Be Equal    ${comPrefFoundOptin}    N

Traveler - Enroll a MyAccount using Group Multiple M/L
    ${expectedMarketLanguageOptin}=    Create Dictionary    ESES=N    FRFR=Y
    #Create a Traveler with S N AF (ES ES Optout)
    ${body}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>W</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><email>testenrolltravelergroupml@af.com</email></email></emailRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>N</communicationGroupeType><communicationType>AF</communicationType><optIn>N</optIn><dateOfConsent>2017-08-04T12:00:00</dateOfConsent><subscriptionChannel>B2C</subscriptionChannel><marketLanguage><market>ES</market><language>ES</language><optIn>N</optIn><dateOfConsent>2017-08-04T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${GIN1}=    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Check with Provide that S N AF (ES ES Optout)
    ${body}=    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN1}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    @{mlComPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferences/marketLanguage
    FOR    ${ml}    IN    @{mlComPrefResponse}
        ${market}    Get Element Text    ${ml}    xpath=.//market
        ${language}    Get Element Text    ${ml}    xpath=.//language
        ${optin}    Get Element Text    ${ml}    xpath=.//optIn
        ${expectedOptin}=    Get From Dictionary    ${expectedMarketLanguageOptin}    ${market}${language}
        Should Be Equal    ${expectedOptin}    ${optin}
    END
    #Enroll the Traveler with Group S N AF (FR FR Optin)
    ${body}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>testenrolltravelergroupml</firstName><lastName>testenrolltravelergroupml</lastName><emailIdentifier>testenrolltravelergroupml@af.com</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${response}=    EnrollMyAccountCustomerService-v3    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//gin
    #Check with Provide that S N AF (FR FR Optin and ES ES Optout)
    ${body}=    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GIN1}</identificationNumber><option>GIN</option><scopeToProvide>COMMUNICATION PREFERENCE</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response}=    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    @{mlComPrefResponse}=    Get Elements    ${XML_object}    xpath=.//communicationPreferences/marketLanguage
    FOR    ${ml}    IN    @{mlComPrefResponse}
        ${market}    Get Element Text    ${ml}    xpath=.//market
        ${language}    Get Element Text    ${ml}    xpath=.//language
        ${optin}    Get Element Text    ${ml}    xpath=.//optIn
        ${expectedOptin}=    Get From Dictionary    ${expectedMarketLanguageOptin}    ${market}${language}
        Should Be Equal    ${expectedOptin}    ${optin}
    END

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    CleanComPref    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1508_createGroupe.sql
    ${ref_group_quest_id} =    SelectOne    ${DB_CONNECT_STRING}    select REF_COMPREF_GROUP_INFO_ID from REF_COMPREF_GROUP_INFO where SSIGNATURE_MODIFICATION = 'ROBOTFRAMEWORK' and REF_COMPREF_GROUP_INFO_ID = '30000001' and rownum < 2
    Should Be Equal    ${ref_group_quest_id}(integer)    30000001(integer)

After
    Clean database    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1508_deleteGroupe.sql
    CleanComPref    ${DB_CONNECT_STRING}
