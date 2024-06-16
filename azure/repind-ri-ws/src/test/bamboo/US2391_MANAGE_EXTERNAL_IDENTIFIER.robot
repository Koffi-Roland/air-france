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
Resource          %{SIC_RESOURCES}/manageExternalIdentifier.robot

*** Variables ***
${ENV}            rct
${ENV_MS}         ute1
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}

*** Test Cases ***
Provide etxernal identifier with good GIN
    #Create the individual and his external identifier
    ${bodyCreationIndividual} =    Convert to String    <ns5:CreateUpdateIndividualRequestElement xmlns:ns5="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns6="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><status>V</status><process>E</process><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><externalIdentifierRequest><externalIdentifier><identifier>123</identifier><type>APP_ID</type></externalIdentifier></externalIdentifierRequest></ns5:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreationIndividual}
    #Check the provided external identifier
    ${result} =    ManageExternalIdentifier-v1-Provide    ${ENV_MS}    ${gin}    200
    ${resultSize} =    Get Length    ${result.json()["externalIdentifierList"]}
    Should Be Equal As Integers    ${resultSize}    1
    Should Contain    ${result.json()["externalIdentifierList"][0]["type"]}    APP_ID
    Should Be Equal As Integers    ${result.json()["externalIdentifierList"][0]["identifier"]}    123

Provide etxernal identifier with non existant GIN
    #Setup for test
    ${gin} =    Convert to String    aaaaaaaaaaaa
    #Check the provided external identifier
    ${result} =    ManageExternalIdentifier-v1-Provide    ${ENV_MS}    ${gin}    404
    ${resultSize} =    Get Length    ${result.json()}
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    External Identifier not found

Provide etxernal identifier with too long GIN
    #Setup for test
    ${gin} =    Convert to String    123456789431456798123456789446547645645646
    #Check the provided external identifier
    ${result} =    ManageExternalIdentifier-v1-Provide    ${ENV_MS}    ${gin}    400
    ${resultSize} =    Get Length    ${result.json()}
    Should Contain    ${result.json()["restError"]["code"]}    business.error.002
    Should Contain    ${result.json()["restError"]["description"]}    Gin parameter should have 12 digit or less

Delete external identifier with good parameter without gin
    ${type} =    Convert to String    APP_ID
    ${identifier} =    Convert to String    123456789123479123456456456ad4z56a4z56d4a6zd4a56
    ${bodyCreationIndividual} =    Convert to String    <ns5:CreateUpdateIndividualRequestElement xmlns:ns5="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns6="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><status>V</status><process>E</process><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><externalIdentifierRequest><externalIdentifier><identifier>${identifier}</identifier><type>${type}</type></externalIdentifier></externalIdentifierRequest></ns5:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreationIndividual}
    #Check the provided external identifier
    ${result} =    ManageExternalIdentifier-v1-Delete-without-Gin    ${ENV_MS}    ${identifier}    ${type}    200
    Should Contain    ${result.json()}    Deletion completed
    #Check if the external identifier still exist in database
    ${result} =    ManageExternalIdentifier-v1-Provide    ${ENV_MS}    ${gin}    404
    ${resultSize} =    Get Length    ${result.json()}
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    External Identifier not found

Delete external identifier with good parameter with gin
    #Setup for test
    ${type} =    Convert to String    APP_ID
    ${identifier} =    Convert to String    az13da5z64d56az798d9a8z4daz56daz1daz16d79azdazdaz1d13az21d3azd8466
    ${bodyCreationIndividual} =    Convert to String    <ns5:CreateUpdateIndividualRequestElement xmlns:ns5="http://www.af-klm.com/services/passenger/data-v8/xsd" xmlns:ns2="http://www.af-klm.com/services/common/SystemFault-v1/xsd" xmlns:ns3="http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd" xmlns:ns4="http://www.af-klm.com/services/passenger/response-v8/xsd" xmlns:ns6="http://www.af-klm.com/services/passenger/SicOsirisType-v1/xsd" xmlns:ns7="http://www.af-klm.com/services/passenger/SicIndividuType-v3/xsd" xmlns:ns8="http://www.af-klm.com/services/passenger/request-v8/xsd" xmlns:ns9="http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd"><status>V</status><process>E</process><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><externalIdentifierRequest><externalIdentifier><identifier>${identifier}</identifier><type>${type}</type></externalIdentifier></externalIdentifierRequest></ns5:CreateUpdateIndividualRequestElement>
    ${gin} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${bodyCreationIndividual}
    #Check the provided external identifier
    ${result}=    ManageExternalIdentifier-v1-Delete-with-Gin    ${ENV_MS}    ${identifier}    ${type}    ${gin}    200
    Should Contain    ${result.json()}    Deletion completed
    #Check if the external identifier still exist in database
    ${result} =    ManageExternalIdentifier-v1-Provide    ${ENV_MS}    ${gin}    404
    ${resultSize} =    Get Length    ${result.json()}
    Should Contain    ${result.json()["restError"]["code"]}    business.error.001
    Should Contain    ${result.json()["restError"]["description"]}    External Identifier not found

Delete external identifier without type
    #Setup for test
    ${identifier} =    Convert to String    123
    #Create the Request with the missing parameter, I don't put it in function to not be able to called this bad request from an other file
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000447/V01    verify=True
    ${result}=    Delete On Session    Swagger    /    params=identifier=${identifier}&api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=BAD_REQUEST
    #Check the error
    Should Contain    ${result.json()["restError"]["code"]}    business.400
    Should Contain    ${result.json()["restError"]["description"]}    Required request parameter 'type' for method parameter type String is not present

Delete external identifier wrong type
    #Setup for test
    ${identifier} =    Convert to String    123
    ${type} =    Convert to String    5656556556887897445645689787
    #Check the provided external identifier
    ${result} =    ManageExternalIdentifier-v1-Delete-without-Gin    ${ENV_MS}    ${identifier}    ${type}    400
    Should Contain    ${result.json()["restError"]["code"]}    business.error.005
    Should Contain    ${result.json()["restError"]["description"]}    Type is wrong

Delete external identifier without identifier
    #Setup for test
    ${type} =    Convert to String    APP_ID
    #Create Individual and his external identifier for test
    #Create the Request with the missing parameter, I don't put it in function to not be able to called this bad request from an other file
    ${sig}=    mashery.retrieve_mashery_token    ${API_KEY}    ${API_SECRET}
    &{Headers}=    Create Dictionary    Accept=application/json    Content-Type=application/json    api_key=${API_KEY}
    Create Session    alias=Swagger    url=https://apiams-ute1.airfranceklm.com/passenger/R000447/V01    verify=True
    ${result}=    Delete On Session    Swagger    /    params=type=${type}&api_key=${API_KEY}&sig=${sig}    headers=&{Headers}    expected_status=BAD_REQUEST
    #Check the error
    Should Contain    ${result.json()["restError"]["code"]}    business.400
    Should Contain    ${result.json()["restError"]["description"]}    Required request parameter 'identifier' for method parameter type String is not present

Delete external identifier with too long gin
    #Setup for test
    ${gin} =    Convert to String    123456789431456798123456789446547645645646
    ${identifier} =    Convert to String    123
    ${type} =    Convert to String    APP_ID
    #Check the provided external identifier
    ${result} =     ManageExternalIdentifier-v1-Delete-with-Gin    ${ENV_MS}    ${identifier}    ${type}    ${gin}    400
    Should Contain    ${result.json()["restError"]["code"]}    business.error.002
    Should Contain    ${result.json()["restError"]["description"]}    Gin parameter should have 12 digit or less

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    CleanDatabase    ${DB_CONNECT_STRING}