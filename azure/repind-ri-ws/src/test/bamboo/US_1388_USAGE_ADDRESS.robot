*** Settings ***
Documentation     an AdrPost has always an usage
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1388
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/commonAssert.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      0
${ADR_RUE_1}      123 BOULEVARD GAMBETTA
${ADR_RUE_2}      123 AVENUE FOCH
${ADR_CP_1}       06000
${ADR_CP_2}       75000
${ADR_VILLE_1}    NICE
${ADR_VILLE_2}    PARIS

*** Test Cases ***
WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI without usage
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V without usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V without usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI with usage
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for BDC without usage
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V without usage
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V without usage
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for BDC with usage
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V with usage
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI puis BDC with usage and different city
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V with usage énd different city
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI puis BDC with usage
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V with usage énd same data adress
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI pass status X without complementary
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address to status X
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    X
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    0

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI pass status X with complementary
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address P V with usage complementary
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2
    # Update Postal Address Mailing to status X
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    X
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1

WS CreateOrUpdateAnIndividualV8 create individual and update postal address for ISI D AND P VALID
    # Initialize an individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    1
    # Update Postal Address mailind P V non attempt already usage exist
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    # Verify number adress
    Test number adresse    ${GLOBALGIN}    2

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
    Log Variables
