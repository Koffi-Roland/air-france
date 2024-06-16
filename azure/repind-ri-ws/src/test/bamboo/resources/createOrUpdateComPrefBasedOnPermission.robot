*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
createOrUpdateComPrefBasedOnPermission-v1
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateComPrefBasedOnPermission-v1 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateComPrefBasedOnPermission-v1.0.0_2.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001950v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001950v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90001950</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc3.airfrance.fr/passenger/marketing/001950v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateOrUpdateComPrefBasedOnPermission-v1/createOrUpdateComPrefBasedOnPermission</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">026414d7-ffde-4c7a-8088-d5f4e648d9bc</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90001950</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-03-14T16:01:41.361+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">026414d7-ffde-4c7a-8088-d5f4e648d9bc</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createOrUpdateComPrefBasedOnPermission    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    #<afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement><gin>111111111111</gin><permissionRequest><permission><permissionID>123</permissionID><permissionAnswer>true</permissionAnswer><market>AZ</market><language>AE</language></permission></permissionRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROB</signature></requestor></afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement>
    [Return]    ${response}

createOrUpdateComPrefBasedOnPermission-v1-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateComPrefBasedOnPermission-v1 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateComPrefBasedOnPermission-v1.0.0_2.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001950v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001950v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90001950</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc3.airfrance.fr/passenger/marketing/001950v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateOrUpdateComPrefBasedOnPermission-v1/createOrUpdateComPrefBasedOnPermission</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">026414d7-ffde-4c7a-8088-d5f4e648d9bc</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90001950</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-03-14T16:01:41.361+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">026414d7-ffde-4c7a-8088-d5f4e648d9bc</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    createOrUpdateComPrefBasedOnPermission    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${responseDetail} =    Get From List    ${response}    3
    ${responseDetaiMessage} =    Get From List    ${responseDetail}    0
    ${responseError} =    Get From List    ${responseDetaiMessage}    0
    ${responseItems} =    Get From List    ${responseError}    1
    Log Many    ${response}
    [Return]    ${response}

createOrUpdateComPrefBasedOnPermission-v1-afr
    [Arguments]    ${ENV}    ${BODY}
    Log Many    ${BODY}
    Create Soap client    ${WSDL}/CreateOrUpdateComPrefBasedOnPermission-v1.0.0_2.wsdl.xml
    #Create Soap client    http://soarepo.airfrance.fr/repository/jcr/view/wsdl/www.af-klm.com/services/passenger/CreateOrUpdateComPrefBasedOnPermission-v1/wsdl/CreateOrUpdateComPrefBasedOnPermission-v1.0.0_2.wsdl
    Set Location    http://AFR726879:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001950v01
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createOrUpdateComPrefBasedOnPermission    ${req_msg}
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}
