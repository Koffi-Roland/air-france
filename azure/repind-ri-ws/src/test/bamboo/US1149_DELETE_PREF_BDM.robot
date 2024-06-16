*** Settings ***
Force Tags        REPIND-1149    #Suite Setup    Before    #Suite Teardown    After
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/commonUtils.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}
${EMAIL}          ${EMPTY}

*** Test Cases ***
Verify deletion in BDM
    #Create an individual with com pref - expect fault
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>2014-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOTFMK</lastNameSC><firstNameSC>UA</firstNameSC></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>civility</key><value>MRS</value></preferenceData><preferenceData><key>email</key><value>test2@klm.com</value></preferenceData><preferenceData><key>firstName</key><value>PHIL</value></preferenceData><preferenceData><key>lastName</key><value>DONAHUE</value></preferenceData><preferenceData><key>dateOfBirth</key><value>22/04/1978</value></preferenceData><preferenceData><key>fFPProgram</key><value>SU</value></preferenceData><preferenceData><key>fFPNumber</key><value>987654321</value></preferenceData></preferenceDatas></preference></preferenceRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    Should be Equal    ${response.detail.BusinessErrorBlocElement.businessError.errorCode}    ERROR_932    #Invalid parameter: Unable to create an individual with preferences and marketing data
    #Initialise a valid individual
    ${body} =    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><token>WSSiC2</token><applicationCode>B2B</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><birthDate>1974-01-08T12:00:00</birthDate><status>V</status><civility>MR</civility><lastNameSC>ROBOT</lastNameSC><firstNameSC>BDM</firstNameSC></individualInformations></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #update TCC pref data
    ${body} =    Convert To String    <xsd:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>MAC</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>civility</key><value>MRS</value></preferenceData><preferenceData><key>email</key><value>test2@klm.com</value></preferenceData><preferenceData><key>firstName</key><value>PHIL</value></preferenceData><preferenceData><key>lastName</key><value>DONAHUE</value></preferenceData><preferenceData><key>dateOfBirth</key><value>22/04/1978</value></preferenceData></preferenceDatas></preference></preferenceRequest></xsd:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    #update TCC pref data
    ${body} =    Convert To String    <xsd:CreateUpdateIndividualRequestElement><requestor><channel>AGV</channel><applicationCode>WSSiC2</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>civility</key><value>MRS</value></preferenceData><preferenceData><key>email</key><value>test2@klm.com</value></preferenceData><preferenceData><key>firstName</key><value>PHIL</value></preferenceData><preferenceData><key>lastName</key><value>DONAHUE</value></preferenceData><preferenceData><key>dateOfBirth</key><value>22/04/1978</value></preferenceData><preferenceData><key>fFPProgram</key><value>SU</value></preferenceData><preferenceData><key>fFPNumber</key><value>987654321</value></preferenceData></preferenceDatas></preference></preferenceRequest></xsd:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    #Provide v7 - retrieve GIN
    Log    GIN is : ${GLOBALGIN}
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//identifier
    Element Text Should Be    ${response}    ${GLOBALGIN}    xpath=.//identifier

Verify bug
    #enroll v2 - create individual
    ${email} =    GenerateRandomValidEmail
    ${body} =    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>ROBOT</firstName><lastName>BDM</lastName><emailIdentifier>${email}</emailIdentifier><password>Robot2017</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>FR</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>ROBOTFRAMEWORK</signature><ipAddress>localhost</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${resultGIN} =    EnrollMyAccountCustomerService-v2-ReturnGin    ${ENV}    ${body}
    Set global variable    ${GLOBALGIN}    ${resultGIN}
    #update TCC pref data
    ${body} =    Convert To String    <xsd:CreateUpdateIndividualRequestElement><requestor><channel>B2C</channel><applicationCode>MAC</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><individualRequest><individualInformations><identifier>${resultGIN}</identifier></individualInformations></individualRequest><preferenceRequest><preference><type>TCC</type><preferenceDatas><preferenceData><key>civility</key><value>MRS</value></preferenceData><preferenceData><key>email</key><value>test2@klm.com</value></preferenceData><preferenceData><key>firstName</key><value>PHIL</value></preferenceData><preferenceData><key>lastName</key><value>DONAHUE</value></preferenceData><preferenceData><key>dateOfBirth</key><value>22/04/1978</value></preferenceData></preferenceDatas></preference></preferenceRequest></xsd:CreateUpdateIndividualRequestElement>
    ${resultGIN} =    CreateUpdateIndividualService-v8-ExpectFault    ${ENV}    ${body}
    #CreateUpdateIndividualService-v8-ExpectFault
    #CreateUpdateIndividualService-v8-ReturnGin

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    Clean database after

Clean database after
    Clean Database By Gin    ${DB_CONNECT_STRING}    ${GLOBALGIN}
