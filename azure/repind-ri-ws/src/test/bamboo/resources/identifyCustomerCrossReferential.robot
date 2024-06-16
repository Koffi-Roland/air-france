*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
IdentifyCustomerCrossReferential-v1
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/IdentifyCustomerCrossReferential-v1.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001345v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001345v01
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/request-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90001345</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/001345v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/IdentifyCustomerCrossReferentialService-v1/search</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">e2fd03b1-7cd4-494f-a411-4f47b925fc8c</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90001345</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-07-25T12:56:59.622+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">e2fd03b1-7cd4-494f-a411-4f47b925fc8c</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    search    ${req_msg}
    sleep    2s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

IdentifyCustomerCrossReferential-v1-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/IdentifyCustomerCrossReferential-v1.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001345v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001345v01
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/request-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90001345</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/001345v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/IdentifyCustomerCrossReferentialService-v1/search</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">e2fd03b1-7cd4-494f-a411-4f47b925fc8c</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90001345</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-07-25T12:56:59.622+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">e2fd03b1-7cd4-494f-a411-4f47b925fc8c</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    search    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}
