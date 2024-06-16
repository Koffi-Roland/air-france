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
Resource          %{SIC_RESOURCES}/searchGinByEmail.robot

*** Variables ***
${ENV}            rc2
${ENV_MS}         cae
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
Search gin by Email with single success response
    #Create an individual with an EMAIL code D (direct) status V
    ${email} =    Convert To String    test@robotframework.com
    ${status}=    Convert To String    V
    ${code}=    Convert To String    D
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>${status}</mediumStatus><mediumCode>${code}</mediumCode><email>${email}</email><emailOptin>T</emailOptin></email></emailRequest><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    #Search gin by Email
    ${result} =    SearchGinByEmailService-v1    ${ENV_MS}    ${email}
    ${resultSize} =    Get Length    ${result.json()["gins"]}
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()["gins"][0]}    ${resultGIN}

Search gin by Email with bad email
    #Search gin by Email with bad email
    ${email} =    Convert To String    test
    ${result} =    SearchGinByEmailService-v1-ExpectFault    ${ENV_MS}    ${email}    412
    Should Contain    ${result.json()["restError"]["description"]}    Invalid value for the 'email' parameter, email must be valid

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
