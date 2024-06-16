*** Settings ***
Documentation     Retrieve GIN by email or FB contract identifier
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-2308
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           RequestsLibrary
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/updateMyAccountCustomerConnectionData.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/manageIndividualIdentifier.robot

*** Variables ***
${ENV}            rc2
${ENV_MS}         cae
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}
${GLOBALGIN2}     ${EMPTY}
${GLOBALGINUA}    ${EMPTY}
${GLOBALGINUM}    ${EMPTY}

*** Test Cases ***
Get GIN by email identifier
    #Password filled must be used
    ${EMAIL} =    Convert To String    testenrollv2pw@af.com
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV2PW</firstName><lastName>TESTENROLLV2PW</lastName><emailIdentifier>${EMAIL}</emailIdentifier><password>Abcd123456</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>0.0.0.0</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${body}
    Log    ${response}
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    ${result} =    ManageIndividualIdentifier-GetByEmail-v1    ${EMAIL}    200
    ${ginFound}=    Set Variable    ${result.json()["gin"]}
    Should Be Equal As Strings    ${ginFound}    ${resultGIN}

Error - Get GIN by email - GIN not Found
    ${EMAIL} =    Convert To String    testenrollnotfoundv2@pwaf.com
    ${result} =    ManageIndividualIdentifier-GetByEmail-v1    ${EMAIL}    404
    ${errorCode}=    Set Variable    ${result.json()["restError"]["code"]}
    Should Be Equal As Strings    ${errorCode}    001

Error - Get GIN by email - Email already used by flying blue members
    ${EMAIL} =    Convert To String    cooldown@gmail.com
    ${result} =    ManageIndividualIdentifier-GetByEmail-v1    ${EMAIL}    400
    ${errorCode}=    Set Variable    ${result.json()["restError"]["code"]}
    Should Be Equal As Strings    ${errorCode}    382

Error - Get GIN by email - Invalid email
    ${EMAIL} =    Convert To String    testenrollv2pwaf.com
    ${result} =    ManageIndividualIdentifier-GetByEmail-v1    ${EMAIL}    412
    ${errorCode}=    Set Variable    ${result.json()["restError"]["code"]}
    Should Be Equal As Strings    ${errorCode}    business.412_001

Get GIN by contract FB identifier
    #Create an individual UM with WS8 and V status
    ${FB_CONTRACT} =    Convert To String    999924800220
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><process>K</process><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Add a FB contract to individual
    ${body} =    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${GLOBALGIN}</gin><actionCode>C</actionCode><contractRequest><contractData><key>QUALIFYINGSEGMENTS</key><value>2</value></contractData><contractData><key>TIERLEVEL</key><value>A</value></contractData><contractData><key>MEMBERTYPE</key><value>T</value></contractData><contractData><key>MILESBALANCE</key><value>123</value></contractData><contractData><key>QUALIFYINGMILES</key><value>1234</value></contractData><contract><contractNumber>999924800220</contractNumber><contractType>C</contractType><productType>FP</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${response} =    CreateOrUpdateRoleContractService-v1    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//contractNumber
    Element Text Should Be    ${response}    ${FB_CONTRACT}    xpath=.//contractNumber
    ${result} =    ManageIndividualIdentifier-GetByContract-v1    ${FB_CONTRACT}    200
    ${ginFound}=    Set Variable    ${result.json()["gin"]}
    Should Be Equal As Strings    ${ginFound}    ${resultGIN}

Error - Get GIN by contract - Invalid CIN
    ${FB_CONTRACT} =    Convert To String    222326999924800220
    ${result} =    ManageIndividualIdentifier-GetByContract-v1    ${FB_CONTRACT}    412
    ${errorCode}=    Set Variable    ${result.json()["restError"]["code"]}
    Should Be Equal As Strings    ${errorCode}    business.412_002

Error - Get GIN by contract - GIN not found
    ${FB_CONTRACT} =    Convert To String    999924800221
    ${result} =    ManageIndividualIdentifier-GetByContract-v1    ${FB_CONTRACT}    404
    ${errorCode}=    Set Variable    ${result.json()["restError"]["code"]}
    Should Be Equal As Strings    ${errorCode}    001

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
