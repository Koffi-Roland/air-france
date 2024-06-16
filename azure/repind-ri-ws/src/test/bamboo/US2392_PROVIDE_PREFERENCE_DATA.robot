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
Library           RequestsLibrary
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/providePreferenceData.robot

*** Variables ***
${ENV}            rct
${ENV_MS}         ute1
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}

*** Test Cases ***
Provide all preferences data with good GIN
    #Create an individual with not much data
    ${email} =    Convert To String    test@robotframework.com
    ${bodyCreation} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreation}
    #Add the preferences for this individual
    ${bodyUpdatePreferences} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>civility</key><value>MRS</value></preferenceData><preferenceData><key>email</key><value>LO@REPIND.COM</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${updatePreferences} =    CreateUpdateIndividualService-v8    ${ENV}    ${bodyUpdatePreferences}
    #Add the comm pref for this individual
    ${bodyUpdateCommPrefs} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>N</communicationGroupeType><communicationType>AF</communicationType><optIn>Y</optIn><dateOfConsent>2022-12-06T00:00:00Z</dateOfConsent><media><media1>E</media1></media><marketLanguage><market>FR</market><language>FR</language><optIn>Y</optIn><dateOfConsent>2022-12-06T00:00:00Z</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${updateCommPrefs} =    CreateUpdateIndividualService-v8    ${ENV}    ${bodyUpdateCommPrefs}
    #ProvidePreferenceData ALL with this gin
    ${result} =    ProvidePreferenceData-v1-All    ${ENV_MS}    ${resultGIN}    200
    ${media1} =    Convert To String    E
    Should Contain    ${result.json()["communicationPreferencesResponse"]["communicationPreferences"][0]["media1"]}    ${media1}

Provide preferences data with good GIN
    #Create an individual with not much data
    ${email} =    Convert To String    test@robotframework.com
    ${bodyCreation} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>TEST</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreation}
    #Add the preferences for this individual
    ${bodyUpdatePreferences} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>TEST</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>email</key><value>LO@REPIND.COM</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${updatePreferences} =    CreateUpdateIndividualService-v8    ${ENV}    ${bodyUpdatePreferences}
    #ProvidePreferenceData PREF with this gin
    ${result} =    ProvidePreferenceData-v1-Preferences    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =    Get Length    ${result.json()["preferences"]}
    ${emailValue} =    Convert To String    LO@REPIND.COM
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()["preferences"][0]["preferenceData"][0]["value"]}    ${emailValue}

Provide communication preferences data with good GIN
    #Create an individual with not much data
    ${email} =    Convert To String    test@robotframework.com
    ${bodyCreation} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>TEST</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreation}
    #Add the comm pref for this individual
    ${bodyUpdateCommPrefs} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>TEST</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>N</communicationGroupeType><communicationType>AF</communicationType><optIn>Y</optIn><dateOfConsent>2022-12-06T00:00:00Z</dateOfConsent><media><media1>E</media1></media><marketLanguage><market>FR</market><language>FR</language><optIn>Y</optIn><dateOfConsent>2022-12-06T00:00:00Z</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest></afk0:CreateUpdateIndividualRequestElement>
    ${updateCommPrefs} =    CreateUpdateIndividualService-v8    ${ENV}    ${bodyUpdateCommPrefs}
    #ProvidePreferenceData COM_PREF with this gin
    ${result} =    ProvidePreferenceData-v1-CommPreferences    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =    Get Length    ${result.json()["communicationPreferences"]}
    ${market} =    Convert To String    FR
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()["communicationPreferences"][0]["marketLanguages"][0]["market"]}    ${market}

Provide all preferences data with bad GIN
    ${gin} =    Convert To String    11000101746
    ${result} =    ProvidePreferenceData-v1-All    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.004
    Should Contain    ${result.json()["restError"]["description"]}    No Communication preferences or Preferences found for this gin

Provide preferences data with bad GIN
    ${gin} =    Convert To String    11000101746
    ${result} =    ProvidePreferenceData-v1-Preferences    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.002
    Should Contain    ${result.json()["restError"]["description"]}    Preferences not found for this gin

Provide communication preferences data with bad GIN
    ${gin} =    Convert To String    11000101746
    ${result} =    ProvidePreferenceData-v1-CommPreferences    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    Communication preferences not found for this gin

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
