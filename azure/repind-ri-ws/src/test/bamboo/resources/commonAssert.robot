*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
Test number adresse
    [Arguments]    ${GLOBALGIN}    ${expectedNumber}
    #Provide GLOBALGIN SCOPE ADR POST
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>POSTAL ADDRESS</scopeToProvide><requestor><channel>B2C</channel><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    # Verifier number adresse is expectedNumber
    ${XML_object}=    Parse XML    ${response}
    ${postalAddressCount}=    Get Element Count    ${XML_object}    xpath=.//postalAddressResponse
    Should Be True    ${postalAddressCount} == ${expectedNumber}
    [Return]    ${GLOBALGIN}

Test number email
    [Arguments]    ${GLOBALGIN}    ${expectedNumber}
    #Provide GLOBALGIN SCOPE EMAIL
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>EMAIL</scopeToProvide><requestor><channel>B2C</channel><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    # Verify that count of email is equal to expectedNumber
    ${XML_object}=    Parse XML    ${response}
    ${emailCount}=    Get Element Count    ${XML_object}    xpath=.//email/email
    Should Be True    ${emailCount} == ${expectedNumber}
    [Return]    ${GLOBALGIN}

Test number phone number
    [Arguments]    ${GLOBALGIN}    ${expectedNumber}
    #Provide GLOBALGIN SCOPE PHONE_NUMBER
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${GLOBALGIN}</identificationNumber><option>GIN</option><scopeToProvide>TELECOM</scopeToProvide><requestor><channel>B2C</channel><applicationCode>HAC</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    # Verify that count of phone number is equal to expectedNumber
    ${XML_object}=    Parse XML    ${response}
    ${count}=    Get Element Count    ${XML_object}    xpath=.//phoneNumber
    Should Be True    ${count} == ${expectedNumber}
    [Return]    ${GLOBALGIN}
