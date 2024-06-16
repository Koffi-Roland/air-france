*** Settings ***
Suite Setup       Before
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}
${DB_CONNECT_STRING_DEV}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lh-dsic01-db.france.airfrance.fr)(PORT=1525))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIC)))
${DB_CONNECT_STRING_RC2}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=tst-siclrc2-db.france.airfrance.fr)(PORT=1530))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SICL)))
${DB_CONNECT_STRING_RCT}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lh-csic01-db.france.airfrance.fr)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIC)))
${IPDADRESS}      LOCALHOST
${SIGNATURE}      ROBOTFRAMEWORK
${SITE}           QVI
${SNOM}           ROBOTFRAMEWORK
@{GLOBALGIN}

*** Test Cases ***
Enrollment RBP v2
    [Tags]    REPINDXR-11    REPIND-942    REPIND-1053
    #enroll v2 avec Robust Password
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>ENROLL ROBUST</firstName><lastName>${SIGNATURE}</lastName><emailIdentifier>test11@robotframework.com</emailIdentifier><password>Robot2017</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>ROBOTFRAMEWORK</signature><ipAddress>localhost</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${resultGIN} =    EnrollMyAccountCustomerService-v2-ReturnGin    ${ENV}    ${body}
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Set Suite variable    ${GIN11}    ${resultGIN}
    disconnect from database

Enrollment NRBP v2
    [Tags]    REPINDXR-19    REPIND-942    REPIND-1053
    #enroll v2 with no Robust Password
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>ENROLL NOROBUST</firstName><lastName>${SIGNATURE}</lastName><emailIdentifier>test111@robotframework.com</emailIdentifier><password>123456</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>ROBOTFRAMEWORK</signature><ipAddress>localhost</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorElement.errorCode}    ERR_932

Clean Enrollements
    [Tags]    REPIND-942
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Execute Sql String    call DELETE_CASCADE('individus_all','SSIGNATURE_CREATION = ''ROBOTFRAMEWORK''')
    disconnect from database

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    @{queryResults}=    SelectMultiple    ${DB_CONNECT_STRING}    select * from sic2.ENV_VAR where envkey like 'ROBUST_PASSWORD%'
