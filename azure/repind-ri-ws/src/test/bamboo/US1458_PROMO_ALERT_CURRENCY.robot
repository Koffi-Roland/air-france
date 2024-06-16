*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1485
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

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
Verify ws CreateOrUpdateAnIndividualV8 create update and provide a promo alert for an individual
    #Initialise an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Create an Alert optin y
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>A</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>P</communicationGroupeType><communicationType>KL</communicationType><optIn>Y</optIn><dateOfConsent>2016-08-23T12:00:00</dateOfConsent><subscriptionChannel>B2C Home page</subscriptionChannel><marketLanguage><market>FR</market><language>FR</language><optIn>Y</optIn><dateOfConsent>2016-09-01T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest><alertRequest><alert><type>P</type><optIn>Y</optIn><alertData><key>ORIGIN</key><value>CDG</value></alertData><alertData><key>ORIGIN_TYPE</key><value>A</value></alertData><alertData><key>DESTINATION</key><value>NCE</value></alertData><alertData><key>DESTINATION_TYPE</key><value>C</value></alertData><alertData><key>START_DATE</key><value>03102018</value></alertData><alertData><key>CURRENCY</key><value>EUR</value></alertData><alertData><key>END_DATE</key><value>31102021</value></alertData><alertData><key>CABIN</key><value>C</value></alertData><alertData><key>ORIGIN_ENR</key><value>Ori</value></alertData></alert></alertRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #provide alert
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    CURRENCY
    Should Contain    ${responseDecoded}    alertId
    ${XML_object}=    Parse XML    ${response}
    ${resultalertId}=    Get Element Text    ${XML_object}    xpath=.//alertId
    #update alert data - update optin N
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>A</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>P</communicationGroupeType><communicationType>KL</communicationType><optIn>Y</optIn><dateOfConsent>2016-08-23T12:00:00</dateOfConsent><subscriptionChannel>B2C Home page</subscriptionChannel><marketLanguage><market>FR</market><language>FR</language><optIn>Y</optIn><dateOfConsent>2016-09-01T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest><alertRequest><alert><type>P</type><alertId>${resultalertId}</alertId><optIn>N</optIn><alertData><key>ORIGIN</key><value>CDG</value></alertData><alertData><key>ORIGIN_TYPE</key><value>A</value></alertData><alertData><key>DESTINATION</key><value>NCE</value></alertData><alertData><key>DESTINATION_TYPE</key><value>C</value></alertData><alertData><key>START_DATE</key><value>03102018</value></alertData><alertData><key>CURRENCY</key><value>EUR</value></alertData><alertData><key>END_DATE</key><value>31102021</value></alertData><alertData><key>CABIN</key><value>C</value></alertData><alertData><key>ORIGIN_ENR</key><value>Ori</value></alertData></alert></alertRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #provide no alert response
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//alertResponse

Verify ws CreateOrUpdateAnIndividualV7 create update and provide a promo alert for an individual
    #Initialise an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Create an Alert optin y
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>A</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>P</communicationGroupeType><communicationType>KL</communicationType><optIn>Y</optIn><dateOfConsent>2016-08-23T12:00:00</dateOfConsent><subscriptionChannel>B2C Home page</subscriptionChannel><marketLanguage><market>EN</market><language>EN</language><optIn>Y</optIn><dateOfConsent>2018-09-01T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest><alertRequest><alert><type>P</type><optIn>Y</optIn><alertData><key>ORIGIN</key><value>CDG</value></alertData><alertData><key>ORIGIN_TYPE</key><value>A</value></alertData><alertData><key>DESTINATION</key><value>NCE</value></alertData><alertData><key>DESTINATION_TYPE</key><value>C</value></alertData><alertData><key>START_DATE</key><value>03102016</value></alertData><alertData><key>CURRENCY</key><value>EUR</value></alertData><alertData><key>END_DATE</key><value>31102016</value></alertData><alertData><key>CABIN</key><value>C</value></alertData><alertData><key>ORIGIN_ENR</key><value>Ori</value></alertData></alert></alertRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #provide alert
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>TLD</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    CURRENCY
    Should Contain    ${responseDecoded}    alertId
    ${XML_object}=    Parse XML    ${response}
    ${resultalertId}=    Get Element Text    ${XML_object}    xpath=.//alertId
    #update alert data - update optin N
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>A</process><requestor><channel>B2C</channel><context>B2C_HOME_PAGE</context><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest><comunicationPreferencesRequest><communicationPreferences><domain>S</domain><communicationGroupeType>P</communicationGroupeType><communicationType>KL</communicationType><optIn>Y</optIn><dateOfConsent>2016-08-23T12:00:00</dateOfConsent><subscriptionChannel>B2C Home page</subscriptionChannel><marketLanguage><market>EN</market><language>EN</language><optIn>Y</optIn><dateOfConsent>2018-09-01T12:00:00</dateOfConsent></marketLanguage></communicationPreferences></comunicationPreferencesRequest><alertRequest><alert><type>P</type><alertId>${resultalertId}</alertId><optIn>N</optIn><alertData><key>ORIGIN</key><value>CDG</value></alertData><alertData><key>ORIGIN_TYPE</key><value>A</value></alertData><alertData><key>DESTINATION</key><value>NCE</value></alertData><alertData><key>DESTINATION_TYPE</key><value>C</value></alertData><alertData><key>START_DATE</key><value>03102016</value></alertData><alertData><key>CURRENCY</key><value>EUR</value></alertData><alertData><key>END_DATE</key><value>31102016</value></alertData><alertData><key>CABIN</key><value>C</value></alertData><alertData><key>ORIGIN_ENR</key><value>Ori</value></alertData></alert></alertRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #provide no alert response
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><matricule>m093564</matricule><context>MYA</context><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//alertResponse

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    CleanAlert    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
    CleanAlert    ${DB_CONNECT_STRING}
    Log Variables
