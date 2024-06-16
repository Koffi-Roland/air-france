*** Settings ***
Suite Setup       Before
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
Resource          %{SIC_RESOURCES}/mergeIndividuals.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/provideIndividualDataGP.robot
Resource          %{SIC_RESOURCES}/commonAssert.robot

*** Variables ***
${ENV}            rct
${ENV_MS}         cae
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}
${ADR_RUE_1}      123 BOULEVARD GAMBETTA
${ADR_RUE_2}      123 AVENUE FOCH
${ADR_CP_1}       06000
${ADR_CP_2}       75000
${ADR_VILLE_1}    NICE
${ADR_VILLE_2}    PARIS

*** Test Cases ***
Email GIN (A) into GIN (B) after merge
    #Create an individual A with an EMAIL code D (direct) status V
    ${email} =    Convert To String    testa@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an EMAIL code D (direct) status V
    ${email} =    Convert To String    testb@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantEmail}=    Set Variable    ${result.json()["individualSource"]["emails"][0]["email"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"EMAILS","identifiants":["${identifiantEmail}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    testa@robotframework.com    xpath=.//email/email

Email GIN (A) into GIN (B), that have two email(DV, DI), after merge
    #Create an individual A with an EMAIL code D (direct) status V
    ${email} =    Convert To String    testa@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an EMAIL code D (direct) status V
    ${email} =    Convert To String    testb@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Add invalid email
    ${email} =    Convert To String    testb.invalid@robotframework.com
    ${status}=    Convert To String    I
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><identifier>${resultGINB}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantEmail}=    Set Variable    ${result.json()["individualSource"]["emails"][0]["email"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"EMAILS","identifiants":["${identifiantEmail}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Test number email    ${resultGINA}    1
    ${XML_object}=    Parse XML    ${response}
    ${countUsage} =    Get Element Count    ${XML_object}    xpath=.//email/email
    Should Be Equal    '${countUsage}'    '2'
    ${emails}=    Get Elements Texts    ${XML_object}    xpath=.//email/email
    ${emailIV} =    Convert To String    ${emails}[0]
    Should Be Equal As Strings    ${emailIV}    testb.invalid@robotframework.com
    ${emailDV} =    Convert To String    ${emails}[1]
    Should Be Equal As Strings    ${emailDV}    testa@robotframework.com

Telecoms GIN (A) into GIN (B) after merge
    #Create an individual A with an telecom code D (direct) status V
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${terminal}=    Convert To String    M
    ${countryCode}=    Convert To String    +33
    ${numero}=    Convert To String    0606060606
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminal}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${numero}</phoneNumber></telecom></telecomRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an telecom code D (direct) status V
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${terminal}=    Convert To String    M
    ${countryCode}=    Convert To String    +33
    ${numero}=    Convert To String    0607070707
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminal}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${numero}</phoneNumber></telecom></telecomRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantTelecom}=    Set Variable    ${result.json()["individualSource"]["telecoms"][0]["telecom"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"TELECOMS","identifiants":["${identifiantTelecom}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>TELECOM</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    0606060606    xpath=.//phoneNumber

Telecoms GIN (A) into GIN (B), that have two telecoms(DVM, DVF), after merge
    #Create an individual A with an telecom code D (direct) status V
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${terminal}=    Convert To String    M
    ${countryCode}=    Convert To String    +33
    ${numero}=    Convert To String    0606060606
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminal}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${numero}</phoneNumber></telecom></telecomRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with two telecoms(DVM, DVF)
    ${terminalFax}=    Convert To String    F
    ${firstNumero}=    Convert To String    0607070707
    ${secondNumero}=    Convert To String    0607070708
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminal}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${firstNumero}</phoneNumber></telecom></telecomRequest><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminalFax}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${secondNumero}</phoneNumber></telecom></telecomRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantTelecom}=    Set Variable    ${result.json()["individualSource"]["telecoms"][0]["telecom"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"TELECOMS","identifiants":["${identifiantTelecom}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>TELECOM</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Test number phone number    ${resultGINA}    1
    ${countUsage} =    Get Element Count    ${XML_object}    xpath=.//phoneNumber
    Should Be Equal    '${countUsage}'    '2'
    ${phoneNumbers}=    Get Elements Texts    ${XML_object}    xpath=.//phoneNumber
    ${phoneNumberDVM} =    Convert To String    ${phoneNumbers}[0]
    Should Be Equal As Strings    ${phoneNumberDVM}    ${numero}

Adresses GIN (A) into GIN (B) after merge
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual A with an address    with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet

Use Informations of GIN (B) after merge
    #Create an individual A
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address, telecom and email
    ${email} =    Convert To String    testb@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${terminal}=    Convert To String    M
    ${countryCode}=    Convert To String    +33
    ${numero}=    Convert To String    0607070707
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>${code}</mediumCode><mediumStatus>${status}</mediumStatus><terminalType>${terminal}</terminalType><countryCode>${countryCode}</countryCode><phoneNumber>${numero}</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualTarget"]["addresses"][0]["address"]["identifiant"]}
    ${identifiantTelecom}=    Set Variable    ${result.json()["individualTarget"]["telecoms"][0]["telecom"]["identifiant"]}
    ${identifiantEmail}=    Set Variable    ${result.json()["individualTarget"]["emails"][0]["email"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]},{"type":"EMAILS","identifiants":["${identifiantEmail}"]},{"type":"TELECOMS","identifiants":["${identifiantTelecom}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><scopeToProvide>TELECOM</scopeToProvide><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    testb@robotframework.com    xpath=.//email/email
    Element Text Should Be    ${response}    0607070707    xpath=.//phoneNumber
    Element Text Should Be    ${response}    ${ADR_RUE_2}    xpath=.//numberAndStreet

ISI M Address GIN (A) into GIN (B) after merge should still ISI M DV
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    M    xpath=.//addressRoleCode

ISI C Addresses GIN (A) into GIN (B) after merge should still ISI C
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    P
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    C    xpath=.//addressRoleCode

No addresses GIN (A) into GIN (B) with ISI M DV address
    #Create an individual A with no address
    ${CODE_APPLI}    Convert To String    ISI
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address with usage ISI M
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualTarget"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_2}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    M    xpath=.//addressRoleCode

No addresses GIN (A) into GIN (B) with ISI C DV address
    #Create an individual A with no address
    ${CODE_APPLI}    Convert To String    ISI
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address    with usage ISI M
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualTarget"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_2}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    C    xpath=.//addressRoleCode

ISI M Address identique GIN (A) into GIN (B) BDC M Address identique after merge shoud have 1 add with 2 usage
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address    with usage ISI M
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    ${XML_object}=    Parse XML    ${response}
    ${countUsage} =    Get Element Count    ${XML_object}    xpath=.//usageAddress
    Should Be Equal    '${countUsage}'    '2'

ISI M Address not identique GIN (A) into GIN (B) BDC M Address not identique after merge shoud have 1 add with 2 usage
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address    with usage ISI M
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    ${XML_object}=    Parse XML    ${response}
    ${countUsage} =    Get Element Count    ${XML_object}    xpath=.//postalAddressResponse
    Should Be Equal    '${countUsage}'    '2'

ISI C Address different GIN (A) into GIN (B) with ISI M after merge should have source ISI C address
    #Create an individual A with an address with usage ISI C
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    C    xpath=.//addressRoleCode

ISI C same Address GIN (A) into GIN (B) ISI M same Address after merge should have same address with ISI M
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address    with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    M    xpath=.//addressRoleCode

ISI M same Address GIN (A) into GIN (B)ISI C same Address after merge should have same address with ISI M
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with an address    with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    C
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    M    xpath=.//addressRoleCode

RPD different address GIN (A) into GIN (B) ISI M / RPD different Address after merge should have source RPD address
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    RPD
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    RPD
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    0
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Create an individual B with a different address with usage RPD 0
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${resultGINB}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    RPD    xpath=.//applicationCode
    Element Text Should Be    ${response}    0    xpath=.//addressRoleCode

RPD Address GIN (A) into GIN (B) with one RPD b DV (different from the source) after merge should have source RPD address
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    RPD
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    RPD
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    0
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    # Create an individual B with a different address with usage RPD 1
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_ROLE_CODE}    Convert To String    1
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${resultGINB}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    #${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    #Element Text Should Be    ${response}    true    xpath=.//success
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Element Text Should Be    ${response}    RPD    xpath=.//applicationCode
    Element Text Should Be    ${response}    0    xpath=.//addressRoleCode

RPD Address GIN (A) into GIN (B) with one ISI M DV (same as the source) after merge should have two usage (RPD/ISI) on address
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    RPD
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    RPD
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    0
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    # Create an individual B with same address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    #Element Text Should Be    ${response}    RPD    xpath=.//applicationCode
    #Element Text Should Be    ${response}    0    xpath=.//addressRoleCode
    ${XML_object}=    Parse XML    ${response}
    ${appCodes}=    Get Elements Texts    ${XML_object}    xpath=.//applicationCode
    Should Contain X Times    ${appCodes}    RPD    1
    Should Contain X Times    ${appCodes}    ISI    1
    ${count} =    Get Element Count    ${XML_object}    xpath=.//applicationCode
    Should Be True    ${count}==2

Two adresses (ISI, BDC) GIN (A) into GIN (B) after merge
    #Create an individual A with an address with usage ISI M
    ${CODE_APPLI}    Convert To String    ISI
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_1}
    ${ADR_CP}    Convert To String    ${ADR_CP_1}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_1}
    ${USAGE_CODE}    Convert To String    ISI
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINA} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    # Update Postal Address D V with usage
    ${CODE_APPLI}    Convert To String    BDC
    ${ADR_MEDIUM}    Convert To String    D
    ${ADR_STATUS}    Convert To String    V
    ${ADR_RUE}    Convert To String    ${ADR_RUE_2}
    ${ADR_CP}    Convert To String    ${ADR_CP_2}
    ${ADR_VILLE}    Convert To String    ${ADR_VILLE_2}
    ${USAGE_CODE}    Convert To String    BDC
    ${USAGE_NUMBER}    Convert To String    01
    ${USAGE_ROLE_CODE}    Convert To String    M
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>${ADR_MEDIUM}</mediumCode><mediumStatus>${ADR_STATUS}</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>${ADR_RUE}</numberAndStreet><city>${ADR_VILLE}</city><zipCode>${ADR_CP}</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>${USAGE_CODE}</applicationCode><usageNumber>${USAGE_NUMBER}</usageNumber><addressRoleCode>${USAGE_ROLE_CODE}</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${resultGINA}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Text Should Be    ${response}    true    xpath=.//success
    #Create an individual B with no address
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>${CODE_APPLI}</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGINB} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Provide Information and identifiant
    ${result} =    ProvideMergeInformations-v1    ${resultGINA}    ${resultGINB}
    ${identifiantAddress}=    Set Variable    ${result.json()["individualSource"]["addresses"][0]["address"]["identifiant"]}
    ${identifiantAddressBdc}=    Set Variable    ${result.json()["individualSource"]["addressesNotMergeable"][0]["address"]["identifiant"]}
    ${body}=    Set Variable    [{"type":"ADDRESSES","identifiants":["${identifiantAddress}", "${identifiantAddressBdc}"]}]
    #Merge individuals
    ${result} =    MergeIndividuals-v1    ${resultGINA}    ${resultGINB}    ${body}
    #Provide to ensure all data are fine
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGINB}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${resultGINB}    xpath=.//identifier
    Element Text Should Be    ${response}    ${ADR_RUE_1}    xpath=.//numberAndStreet
    Test number adresse    ${resultGINA}    2
    Test number adresse    ${resultGINB}    1

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
