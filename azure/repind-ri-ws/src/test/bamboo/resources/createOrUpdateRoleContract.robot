*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
CreateOrUpdateRoleContractService-v1
    [Arguments]    ${ENV}    ${BODY}
    [Documentation]    https://ws-qvi-rct.airfrance.fr/passenger/marketing/001567v01/RC2
    Create Soap client    ${WSDL}/CreateOrUpdateRoleContract-v1.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rct.airfrance.fr/passenger/marketing/001567v01/RC2
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001567v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>w00799265</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/001567v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateOrUpdateRoleContractService-v1/createRoleContract</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">d6bd3aac-a69b-4ae4-a5d7-5dc2c587c2b9</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>w00799265</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-26T09:18:22.051+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">d6bd3aac-a69b-4ae4-a5d7-5dc2c587c2b9</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createRoleContract    ${req_msg}
    sleep    2s    #prevent sax parse exception
    ${response} =    Get Last Received
    [Return]    ${response}

CreateOrUpdateRoleContractService-v1-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/CreateOrUpdateRoleContract-v1.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rct.airfrance.fr/passenger/marketing/001567v01/RC2
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001567v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>w00799265</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/001567v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateOrUpdateRoleContractService-v1/createRoleContract</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">d6bd3aac-a69b-4ae4-a5d7-5dc2c587c2b9</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>w00799265</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-26T09:18:22.051+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">d6bd3aac-a69b-4ae4-a5d7-5dc2c587c2b9</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    createRoleContract    ${req_msg}
    sleep    2s    #prevent sax parse exception
    Log    ${response}
    #Michael afr645119
    ${responseDetail} =    Get From List    ${response}    3
    ${responseDetaiMessage} =    Get From List    ${responseDetail}    0
    ${responseError} =    Get From List    ${responseDetaiMessage}    0
    ${responseItems} =    Get From List    ${responseError}    1
    [Return]    ${responseItems}

CreateOrUpdateRoleContractService-v1-afr
    [Arguments]    ${ENV}    ${BODY}
    [Documentation]    Michael : http://afr645119:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    ...    Xavier : http://afr635599:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    Create Soap client    ${WSDL}/CreateOrUpdateRoleContract-v1.0.0.wsdl.xml
    Set Location    http://afr726879:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createRoleContract    ${req_msg}
    sleep    2s    #prevent sax parse exception
    ${response} =    Get Last Received
    #Michael afr645119
    [Return]    ${response}

CreateOrUpdateRoleContractService-v1-afrExpectFault
    [Arguments]    ${ENV}    ${BODY}
    [Documentation]    Michael : http://afr645119:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    ...    Xavier : http://afr635599:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    Create Soap client    ${WSDL}/CreateOrUpdateRoleContract-v1.0.0.wsdl.xml
    Set Location    http://afr645119:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/001567v01
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    createRoleContract    ${req_msg}
    sleep    2s    #prevent sax parse exception
    Log    ${response}
    #Michael afr645119
    ${nbresponse} =    Get Length    ${response}
    ${lastresponse} =    Evaluate    ${nbresponse}-1
    ${responseDetail} =    Get From List    ${response}    ${lastresponse}
    ${responseDetaiMessage} =    Get From List    ${responseDetail}    0
    ${responseError} =    Get From List    ${responseDetaiMessage}    0
    ${responseItems} =    Get From List    ${responseError}    1
    [Return]    ${responseItems}
