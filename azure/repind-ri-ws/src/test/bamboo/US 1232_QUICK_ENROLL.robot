*** Settings ***
Suite Setup       Before
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
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
EnrollV3 - Check that a GIN is returned with no password in input
    #A temporary password must be created
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV3</firstName><lastName>TESTENROLLV3</lastName><emailIdentifier>TESTENROLLV3@AF.COM</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v3    ${ENV}    ${body}
    Log    ${response}
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email

EnrollV3 - Check that a GIN is returned with password in input
    #Password filled must be used
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV3PW</firstName><lastName>TESTENROLLV3PW</lastName><emailIdentifier>TESTENROLLV3PW@AF.COM</emailIdentifier><password>Abcd123456</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v3    ${ENV}    ${body}
    Log    ${response}
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email

EnrollV2 - Check that a Error ocured when no password is provided
    #A temporary password must be created
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV2</firstName><lastName>TESTENROLLV2</lastName><emailIdentifier>TESTENROLLV2@AF.COM</emailIdentifier><password></password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>0.0.0.0</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2-ExpectFault    ${ENV}    ${body}
    Log    ${response}
    Should be Equal    ${response.detail.BusinessErrorElement.errorCode}    ERR_932

EnrollV2 - Check that a GIN is returned with password in input
    #Password filled must be used
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>TESTENROLLV2PW</firstName><lastName>TESTENROLLV2PW</lastName><emailIdentifier>TESTENROLLV2PW@AF.COM</emailIdentifier><password>Abcd123456</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>0.0.0.0</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${body}
    Log    ${response}
    Element Should Exist    ${response}    xpath=.//gin
    Element Should Exist    ${response}    xpath=.//accountID
    Element Should Exist    ${response}    xpath=.//email

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
