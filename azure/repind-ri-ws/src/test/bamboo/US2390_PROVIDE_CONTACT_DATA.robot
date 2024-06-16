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
Resource          %{SIC_RESOURCES}/provideContactData.robot

*** Variables ***
${ENV}            rct
${ENV_MS}         ute1
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}

*** Test Cases ***
Provide all contact data with good GIN
    #Create an individual with all contact infos
    ${email} =    Convert To String    test@robotframework.com
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>33</countryCode><phoneNumber>0662558899</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>78 BD JEAN JAURES</numberAndStreet><city>CLICHY</city><zipCode>92110</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #ProvideContactData ALL with this gin
    ${result} =    ProvideContactData-v1-All    ${ENV_MS}    ${resultGIN}    200
    ${internationalPhoneNumber} =    Convert To String    +33662558899
    Should Contain    ${result.json()["telecomResponse"][0]["telecomNormalization"]["internationalPhoneNumber"]}    ${internationalPhoneNumber}

Provide emails data with good GIN
    #Create an individual with all contact infos
    ${email} =    Convert To String    test@robotframework.com
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>33</countryCode><phoneNumber>0662558899</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>78 BD JEAN JAURES</numberAndStreet><city>CLICHY</city><zipCode>92110</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #ProvideContactData EMAILS with this gin
    ${result} =    ProvideContactData-v1-Emails    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =    Get Length    ${result.json()}
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()[0]["email"]["email"]}    ${email}

Provide telecoms data with good GIN
    #Create an individual with all contact infos
    ${email} =    Convert To String    test@robotframework.com
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>33</countryCode><phoneNumber>0662558899</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>78 BD JEAN JAURES</numberAndStreet><city>CLICHY</city><zipCode>92110</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #ProvideContactData TELECOMS with this gin
    ${result} =    ProvideContactData-v1-Telecoms    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =    Get Length    ${result.json()}
    ${phoneNumber} =    Convert To String    0662558899
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()[0]["telecom"]["phoneNumber"]}    ${phoneNumber}

Provide postal addresses data with good GIN
    #Create an individual with all contact infos
    ${email} =    Convert To String    test@robotframework.com
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><matricule>M402925</matricule><managingCompany>AF</managingCompany><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>33</countryCode><phoneNumber>0662558899</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>78 BD JEAN JAURES</numberAndStreet><city>CLICHY</city><zipCode>92110</zipCode><countryCode>FR</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #ProvideContactData POSTAL ADDR with this gin
    ${result} =    ProvideContactData-v1-Addresses    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =    Get Length    ${result.json()}
    ${numberAndStreet} =    Convert To String    78 BD JEAN JAURES
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()[0]["postalAddressContent"]["numberAndStreet"]}    ${numberAndStreet}

Provide all contact data with bad GIN
    ${gin} =    Convert To String    1100010174
    ${result} =    ProvideContactData-v1-All    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.004
    Should Contain    ${result.json()["restError"]["description"]}    No contact data found for this gin

Provide emails data with bad GIN
    ${gin} =    Convert To String    1100010174
    ${result} =    ProvideContactData-v1-Emails    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    Emails data not found for this gin

Provide telecoms data with bad GIN
    ${gin} =    Convert To String    1100010174
    ${result} =    ProvideContactData-v1-Telecoms    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.003
    Should Contain    ${result.json()["restError"]["description"]}    Telecoms data not found for this gin

Provide postal addresses data with bad GIN
    ${gin} =    Convert To String    1100010174
    ${result} =    ProvideContactData-v1-Addresses    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.002
    Should Contain    ${result.json()["restError"]["description"]}    Postal Addresses data not found for this gin

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
