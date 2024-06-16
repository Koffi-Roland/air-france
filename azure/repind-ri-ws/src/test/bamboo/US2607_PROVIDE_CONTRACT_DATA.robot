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
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/provideContractData.robot

*** Variables ***
${ENV}            rc2
${ENV_MS}         ute1
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}

*** Test Cases ***
Provide All data with good data
    #Setup for test
    ${email} =    Convert to String    gincontract.test@robotframework.com
    #Create account data for both delegate and delegator
    ${contractBody} =    Convert To String    <ns4:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/data-v3/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><civility>MR</civility><firstName>test</firstName><lastName>test</lastName><emailIdentifier>${email}</emailIdentifier><languageCode>FR</languageCode><website>BB</website><pointOfSale>BB</pointOfSale><requestor><channel>QVI</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns4:EnrollMyAccountCustomerRequestElement>
    ${gin} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${contractBody}
    #Check presence of the contract data with the gin
    ${result} =    ProvideContractData-v1-by-gin    ${ENV_MS}    ${gin}    200
    ${resultSize} =    Get Length    ${result.json()}
    Should Be Equal As Integers    ${resultSize}    1
    #Check presence of the contract data with the cin
    Log    ${result.json()[0]["contractNumber"]}
    ${result} =    ProvideContractData-v1-by-cin    ${ENV_MS}    ${result.json()[0]["contractNumber"]}    200
    ${resultSize} =    Get Length    ${result.json()}
    Should Be Equal As Integers    ${resultSize}    1

Provide All Data Error Case
    #Not found error case
    ${fakeGin} =    Convert to String    000000000000
    ${fakeCin} =    Convert to String    999999ZZ
    ${result1} =    ProvideContractData-v1-by-gin    ${ENV_MS}    ${fakeGin}    404
    Should Contain    ${result1.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result1.json()["restError"]["description"]}    Contract data not found for this gin
    ${result2} =    ProvideContractData-v1-by-cin    ${ENV_MS}    ${fakeCin}    404
    Should Contain    ${result2.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result2.json()["restError"]["description"]}    Contract data not found for this gin
    #Forbidden error case
    ${badGin} =    Convert to String    0000000000000
    ${badCin} =    Convert to String    0000000
    ${result3} =    ProvideContractData-v1-by-gin    ${ENV_MS}    ${badGin}    403
    Should Contain    ${result3.json()["restError"]["code"]}    business.error.005
    Should Contain    ${result3.json()["restError"]["description"]}    Gin parameter should have 12 digit or less
    ${result4} =    ProvideContractData-v1-by-cin    ${ENV_MS}    ${badCin}    403
    Should Contain    ${result4.json()["restError"]["code"]}    business.error.004
    Should Contain    ${result4.json()["restError"]["description"]}    Cin parameter is incorrect

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}