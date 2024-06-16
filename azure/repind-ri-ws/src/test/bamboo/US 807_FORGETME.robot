*** Settings ***
Suite Setup       Before
Suite Teardown    After
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
${IPDADRESS}      LOCALHOST
${SIGNATURE}      ROBOTFRAMEWORK
${SITE}           QVI
${SNOM}           ROBOTFRAMEWORK
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
CreateOrUpdateAnIndividualWS STATUS
    [Tags]    REPINDXR-34    REPIND-807    TEST
    #Can't create an individual with F status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2017-12-04T12:00:00</birthDate><status>F</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>ROBOTFMK</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_932
    #Create an individual with V status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2017-12-04T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMKZABZZ</lastNameSC><firstNameSC>ROBOTFMKZAZZZ</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #Try to update this individual with F Status
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier><status>F</status></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_932

CreateOrUpdateAnIndividualWS FORGET_CONFIRM
    [Tags]    REPINDXR-36    REPIND-807    TEST
    #Update an individual with F status context
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><context>FORGET_CONFIRM</context><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${GLOBALGIN}</identifier></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success

ProvideIndividualDataWS 7
    [Tags]    REPINDXR-29    REPIND-807    TEST
    #Provide v7 - can't retrieve a status F
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001

ProvideIndividualDataWS 6
    [Tags]    REPINDXR-30    REPIND-807    TEST
    #Provide v6 - can't retrieve a status F
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v6-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001

ProvideIndividualDataWS 5
    [Tags]    REPINDXR-31    REPIND-807    TEST
    #Provide v5 - can't retrieve a status F
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v5-ExpectNotFound    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_001

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    Clean database    ${DB_CONNECT_STRING}
