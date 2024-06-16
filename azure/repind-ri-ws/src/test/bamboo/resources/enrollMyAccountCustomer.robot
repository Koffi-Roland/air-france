*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
EnrollMyAccountCustomerService-v3
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v3.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v03
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v03
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v3/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v03</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v3/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fb204366-4831-4afa-b1bd-01a4409ca269</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-05T09:30:54.869+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fb204366-4831-4afa-b1bd-01a4409ca269</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

EnrollMyAccountCustomerService-v3-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : EnrollMyAccountCustomer-v3 env : ${ENV}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v3.0.0.wsdl.xml
    Set Location    http://AFR726879:8080/EnrollMyAccountCustomerWS/ws/passenger/marketing/000431v03
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v3/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

EnrollMyAccountCustomerService-v3-ReturnGin
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v3.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v03
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v03
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v3/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v03</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v3/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fb204366-4831-4afa-b1bd-01a4409ca269</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-05T09:30:54.869+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fb204366-4831-4afa-b1bd-01a4409ca269</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

EnrollMyAccountCustomerService-v2
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v2.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v02
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v02
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v2/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v02</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v2/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fb204366-4831-4afa-b1bd-01a4409ca269</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-05T09:30:54.869+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fb204366-4831-4afa-b1bd-01a4409ca269</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

EnrollMyAccountCustomerService-v2-ReturnGin
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v2.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v02
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v02
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v2/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v02</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v2/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fb204366-4831-4afa-b1bd-01a4409ca269</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-05T09:30:54.869+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fb204366-4831-4afa-b1bd-01a4409ca269</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

EnrollMyAccountCustomerService-v2-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v2.0.0.wsdl.xml
    Log    env=${ENV}
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v02
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v02
    Log    env=${ENV}
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v2/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v02</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v2/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fb204366-4831-4afa-b1bd-01a4409ca269</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-05T09:30:54.869+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fb204366-4831-4afa-b1bd-01a4409ca269</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response}=    Call Soap Method Expecting Fault    enrollMyAccountCustomer    ${req_msg}
    Log    ${response}
    sleep    3s    #prevent sax parse exception
    Log Many    ${response}
    [Return]    ${response}

EnrollMyAccountCustomerService-v1
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v1.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerType-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v1/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">37cfa756-85f9-4698-b5f4-061f39c65df1</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-06T15:44:15.217+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">37cfa756-85f9-4698-b5f4-061f39c65df1</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

EnrollMyAccountCustomerService-v1-ReturnGin
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v1.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v01
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerType-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v1/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">37cfa756-85f9-4698-b5f4-061f39c65df1</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-06T15:44:15.217+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">37cfa756-85f9-4698-b5f4-061f39c65df1</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

EnrollMyAccountCustomerService-v1-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Create Soap client    ${WSDL}/EnrollMyAccountCustomer-v1.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000431v01

    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000431v01
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerType-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000431</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-dev.airfrance.fr/passenger/marketing/000431v01</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/EnrollMyAccountCustomerService-v1/enrollMyAccountCustomer</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">37cfa756-85f9-4698-b5f4-061f39c65df1</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000431</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2017-12-06T15:44:15.217+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">37cfa756-85f9-4698-b5f4-061f39c65df1</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response}=    Call Soap Method Expecting Fault    enrollMyAccountCustomer    ${req_msg}
    sleep    3s    #prevent sax parse exception
    Log Many    ${response}
    [Return]    ${response}
