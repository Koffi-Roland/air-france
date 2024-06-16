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
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/provideIdentificationData.robot

*** Variables ***
${ENV}            rc2
${ENV_MS}         ute1
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}

*** Test Cases ***
Provide all data with good GIN
    #Setup for test
    ${email} =    Convert to String    delegate.myguy.test@robotframework.com
    ${email2} =    Convert to String    delegator.myguy.test@robotframework.com
    #Create account data for both delegate and delegator
    ${delegateAccountBodyCreation} =    Convert To String   <ns4:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/data-v3/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><civility>MR</civility><firstName>test</firstName><lastName>test</lastName><emailIdentifier>${email}</emailIdentifier><languageCode>FR</languageCode><website>BB</website><pointOfSale>BB</pointOfSale><requestor><channel>QVI</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns4:EnrollMyAccountCustomerRequestElement>
    ${ginDelegate} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${delegateAccountBodyCreation}
    ${delegatorAccountBodyCreation} =    Convert To String   <ns4:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/data-v3/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><civility>MR</civility><firstName>test</firstName><lastName>test</lastName><emailIdentifier>${email2}</emailIdentifier><languageCode>FR</languageCode><website>BB</website><pointOfSale>BB</pointOfSale><requestor><channel>QVI</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns4:EnrollMyAccountCustomerRequestElement>
    ${ginDelegator} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${delegatorAccountBodyCreation}
    #Create the invitation to delegator
    ${delegationBodyCreation} =    Convert To String   <ns8:CreateUpdateIndividualRequestElement xmlns:ns6="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns5="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd"><requestor><channel>APPS</channel><matricule>MYA</matricule><applicationCode>MAC</applicationCode><site>AMS</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${ginDelegator}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${ginDelegate}</gin><delegationAction>I</delegationAction><delegationType>TM</delegationType></delegationData></delegate></accountDelegationDataRequest></ns8:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8  ${ENV}  ${delegationBodyCreation}
    #Accept invitation
    ${delegationBodyCreation} =    Convert To String   <ns8:CreateUpdateIndividualRequestElement xmlns:ns6="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns5="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd"><requestor><channel>APPS</channel><matricule>MYA</matricule><applicationCode>MAC</applicationCode><site>AMS</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${ginDelegate}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegator><delegationData><gin>${ginDelegator}</gin><delegationAction>A</delegationAction><delegationType>TM</delegationType></delegationData></delegator></accountDelegationDataRequest></ns8:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8  ${ENV}  ${delegationBodyCreation}
    #Check presence of all type of data for one of the gin
    ${result} =     ProvideIdentificationData-v1-All     ${ENV_MS}   ${ginDelegate}    200
    ${resultSize} =     Get Length      ${result.json()}
    Should Be Equal As Integers     ${resultSize}   3

Provide identification data with good GIN
    #Create identification data only
    ${email} =  Convert to String   test@robotframework.com
    ${bodyCreation} =   Convert to String   <ns9:CreateUpdateIndividualRequestElement xmlns:ns6="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns5="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/response-v8/xsd"><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${email}</email></email></emailRequest><individualRequest><individualInformations><gender>M</gender><birthDate>1971-01-01T00:00:00Z</birthDate><status>V</status><civility>MR</civility><lastNameSC>lastname</lastNameSC><firstNameSC>firstname</firstNameSC></individualInformations></individualRequest></ns9:CreateUpdateIndividualRequestElement>
    ${resultGIN} =  CreateUpdateIndividualService-v8-ReturnGin  ${ENV}  ${bodyCreation}
    ${result} =     ProvideIdentificationData-v1-Identification     ${ENV_MS}   ${resultGIN}    200
    Should Contain    ${result.json()["individualInformations"]["birthdate"]}    1971-01-01
    Should Contain    ${result.json()["individualInformations"]["status"]}    V
    Should Contain    ${result.json()["individualInformations"]["lastNameNormalized"]}    LASTNAME
    Should Contain    ${result.json()["individualInformations"]["firstNameNormalized"]}    FIRSTNAME
    Should Contain    ${result.json()["individualInformations"]["languageCode"]}    FR
    ${individualInformationsSize} =     Get Length      ${result.json()["individualInformations"]}
    Should Be Equal As Integers     ${individualInformationsSize}   14

Provide Account data with good GIN
    #Create account data only
    ${email} =    Convert to String    test@robotframework.com
    ${bodyCreation} =    Convert To String    <ns3:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/data-v3/xsd"><civility>MR</civility><firstName>Test</firstName><lastName>Account</lastName><emailIdentifier>${email}</emailIdentifier><languageCode>FR</languageCode><website>AF</website><pointOfSale>ABC</pointOfSale><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns3:EnrollMyAccountCustomerRequestElement>
    ${resultGIN} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${bodyCreation}
    ${result} =    ProvideIdentificationData-v1-Account    ${ENV_MS}    ${resultGIN}    200
    ${resultSize} =     Get Length     ${result.json()["identifierDatas"]}
    Should Contain    ${result.json()["identifierDatas"][1]["identifier"]}    ${email}
    Should Be Equal As Integers     ${resultSize}   2

Provide Delegation data with good GIN
    ${email} =    Convert to String    delegate.myguy2.test@robotframework.com
    ${email2} =    Convert to String    delegator.myguy2.test@robotframework.com
    #Create account data for both delegate and delegator
    ${delegateAccountBodyCreation} =    Convert To String   <ns4:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/data-v3/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><civility>MR</civility><firstName>test</firstName><lastName>test</lastName><emailIdentifier>${email}</emailIdentifier><languageCode>FR</languageCode><website>BB</website><pointOfSale>BB</pointOfSale><requestor><channel>QVI</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns4:EnrollMyAccountCustomerRequestElement>
    ${ginDelegate} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${delegateAccountBodyCreation}
    ${delegatorAccountBodyCreation} =    Convert To String   <ns4:EnrollMyAccountCustomerRequestElement xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/data-v3/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><civility>MR</civility><firstName>test</firstName><lastName>test</lastName><emailIdentifier>${email2}</emailIdentifier><languageCode>FR</languageCode><website>BB</website><pointOfSale>BB</pointOfSale><requestor><channel>QVI</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></ns4:EnrollMyAccountCustomerRequestElement>
    ${ginDelegator} =    EnrollMyAccountCustomerService-v3-ReturnGin    ${ENV}    ${delegatorAccountBodyCreation}
    #Create the invitation to delegator
    ${delegationBodyCreation} =    Convert To String   <ns8:CreateUpdateIndividualRequestElement xmlns:ns6="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns5="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd"><requestor><channel>APPS</channel><matricule>MYA</matricule><applicationCode>MAC</applicationCode><site>AMS</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${ginDelegator}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegate><delegationData><gin>${ginDelegate}</gin><delegationAction>I</delegationAction><delegationType>TM</delegationType></delegationData></delegate></accountDelegationDataRequest></ns8:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8  ${ENV}  ${delegationBodyCreation}
    #Accept invitation
    ${delegationBodyCreation} =    Convert To String   <ns8:CreateUpdateIndividualRequestElement xmlns:ns6="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd" xmlns:ns5="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd"><requestor><channel>APPS</channel><matricule>MYA</matricule><applicationCode>MAC</applicationCode><site>AMS</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${ginDelegate}</identifier></individualInformations></individualRequest><accountDelegationDataRequest><delegator><delegationData><gin>${ginDelegator}</gin><delegationAction>A</delegationAction><delegationType>TM</delegationType></delegationData></delegator></accountDelegationDataRequest></ns8:CreateUpdateIndividualRequestElement>
    CreateUpdateIndividualService-v8  ${ENV}  ${delegationBodyCreation}
    #Check the presence of the delegator when asking with the delegate's gin
    ${delegateResult} =     ProvideIdentificationData-v1-Delegation     ${ENV_MS}   ${ginDelegate}    200
    ${delegatorListSize} =   Get Length  ${delegateResult.json()["delegators"]}
    Should Be Equal As Integers     ${delegatorListSize}    1
    Should Contain      ${delegateResult.json()["delegators"][0]["delegationStatusData"]["gin"]}     ${ginDelegator}
    #Check the presence of the delegate when asking with the delegator's gin
    ${delegatorResult} =     ProvideIdentificationData-v1-Delegation     ${ENV_MS}   ${ginDelegator}    200
    ${delegateListSize} =   Get Length  ${delegatorResult.json()["delegates"]}
    Should Be Equal As Integers     ${delegateListSize}    1
    Should Contain      ${delegatorResult.json()["delegates"][0]["delegationStatusData"]["gin"]}     ${ginDelegate}

Provide all data with bad GIN
    ${gin} =    Convert To String    999999999999
    ${result} =    ProvideIdentificationData-v1-All    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.004
    Should Contain    ${result.json()["restError"]["description"]}    ProvideIdentificationData data not found for this gin

Provide Identification data with bad GIN
    ${gin} =    Convert To String    999999999999
    ${result} =    ProvideIdentificationData-v1-Identification    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.003
    Should Contain    ${result.json()["restError"]["description"]}    Identification data not found for this gin

Provide Account data with bad GIN
    ${gin} =    Convert To String    999999999999
    ${result} =    ProvideIdentificationData-v1-Account    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    Account data not found for this gin

Provide Delegation data with bad GIN
    ${gin} =    Convert To String    999999999999
    ${result} =    ProvideIdentificationData-v1-Delegation    ${ENV_MS}    ${gin}    404
    Should Contain    ${result.json()["restError"]["code"]}    business.error.002
    Should Contain    ${result.json()["restError"]["description"]}    Delegation data not found for this gin

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}
