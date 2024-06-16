*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
ProvideIndividualDataService-v6
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v6 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v6.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v06
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v06
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v6/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v06</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v6/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">190418a8-845d-401f-95ce-8f74842a04d9</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-24T09:17:35.958+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">190418a8-845d-401f-95ce-8f74842a04d9</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

ProvideIndividualDataService-v6-ExpectNotFound
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v6 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v6.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v06
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v06
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v6/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v06</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v6/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">190418a8-845d-401f-95ce-8f74842a04d9</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-24T09:17:35.958+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">190418a8-845d-401f-95ce-8f74842a04d9</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    provideIndividualDataByIdentifier    ${req_msg}
    Log    ${response}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}

ProvideIndividualDataService-v7
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v7 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v07
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000418v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v7/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-08T13:21:24.767+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

ProvideIndividualDataService-v7-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v7 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v07
    Set Location    ${location}
    Set Location    http://AFR667992:8080/ProvideIndividualDataWS/ws/passenger/marketing000418v07
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    provideIndividualDataByIdentifier    ${req_msg}
    sleep    2s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}

ProvideIndividualDataService-v7-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v7-ExpectFault env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v07
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000418v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v7/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-08T13:21:24.767+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}

ProvideIndividualDataService-v7-ExpectNotFound
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v7 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v05
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v07
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000418v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v7/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-08T13:21:24.767+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">3e6b8faf-1f3e-49e0-9850-7e9f90d8c1cc</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}

ProvideIndividualDataService-v5-ExpectNotFound
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v5 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v5.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v05
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v05
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v5/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v05</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v5/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">d4b8abd6-b376-4660-b4b8-70ff3fa3e7c0</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-24T09:20:09.928+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">d4b8abd6-b376-4660-b4b8-70ff3fa3e7c0</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}

ProvideIndividualDataService-v5
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualData-v5 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualData-v5.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000418v05
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000418v05
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v5/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000418</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000418v05</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v5/provideIndividualDataByIdentifier</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">fe0755ff-07dc-42e3-98c9-52feee1aef3e</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000418</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-15T14:00:03.774+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">fe0755ff-07dc-42e3-98c9-52feee1aef3e</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    provideIndividualDataByIdentifier    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Log Many    ${response}
    [Return]    ${response}
