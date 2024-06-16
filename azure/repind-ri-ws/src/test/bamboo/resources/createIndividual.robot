*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
CreateUpdateIndividualService-v8
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v8 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v08
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v08
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v08</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v8/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">728fd0a9-50e2-4996-a516-522a01ac973e</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-25T14:57:35.389+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">728fd0a9-50e2-4996-a516-522a01ac973e</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    [Return]    ${response}

CreateUpdateIndividualService-v8-ReturnGin
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v8-ReturnGin env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v08
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v08
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v08</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v8/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">728fd0a9-50e2-4996-a516-522a01ac973e</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-25T14:57:35.389+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">728fd0a9-50e2-4996-a516-522a01ac973e</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

CreateUpdateIndividualService-v8-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v8-ExpectFault env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v08
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v08
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v08</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v8/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">728fd0a9-50e2-4996-a516-522a01ac973e</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-25T14:57:35.389+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">728fd0a9-50e2-4996-a516-522a01ac973e</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}

CreateUpdateIndividualService-v8-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v8 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    Set Location    http://AFR667992:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/000442v08
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    ${response} =    Get Last Received
    Element Text Should Be    ${response}    true    xpath=.//success
    [Return]    ${response}

CreateUpdateIndividualService-v8-consumerID
    [Arguments]    ${ENV}    ${BODY}    ${CONSUMERID}
    Log    ws : CreateOrUpdateAnIndividual-v8 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v08
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v08
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v08</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v8/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">728fd0a9-50e2-4996-a516-522a01ac973e</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AF</partyID><consumerID>${CONSUMERID}</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-01-25T14:57:35.389+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">728fd0a9-50e2-4996-a516-522a01ac973e</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    Element Text Should Be    ${response}    true    xpath=.//success
    [Return]    ${response}

CreateUpdateIndividualService-v8-ReturnGin-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v8-afr
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v8.0.0.wsdl.xml
    Set Location    http://AFR667992:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/000442v08
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v8/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    ${response} =    Get Last Received
    Should Contain    ${response}    <success>true</success>
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

CreateUpdateIndividualService-v7-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v7 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v7.0.0.wsdl.xml
    Set Location    http://AFR645119:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/000442v07
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    ${response} =    Get Last Received
    Element Text Should Be    ${response}    true    xpath=.//success
    [Return]    ${response}

CreateUpdateIndividualService-v6-afr
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v6 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v6.0.0.wsdl.xml
    Set Location    http://AFR645119:8080/CreateOrUpdateIndividualWS/ws/passenger/marketing/000442v06
    ${req_msg}=    Create Raw Soap Message    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v6/xsd"><soapenv:Header/><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    ${response} =    Get Last Received
    Element Text Should Be    ${response}    true    xpath=.//success
    [Return]    ${response}

CreateUpdateIndividualService-v7
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v7 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v07
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v7/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">1b517df6-3c21-4571-adf1-40f2491c201d</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-15T12:23:23.725+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">1b517df6-3c21-4571-adf1-40f2491c201d</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    [Return]    ${response}

CreateUpdateIndividualService-v7-ReturnGin
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v7-ReturnGin env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v07
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v7/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">1b517df6-3c21-4571-adf1-40f2491c201d</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-15T12:23:23.725+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">1b517df6-3c21-4571-adf1-40f2491c201d</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    ${XML_object}=    Parse XML    ${response}
    ${resultGIN}=    Get Element Text    ${XML_object}    xpath=.//gin
    [Return]    ${resultGIN}

CreateUpdateIndividualService-v6
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v6 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v6.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v06
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v06
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v6/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v06</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v6/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">7913fe82-9f8c-4ceb-b398-b4ae7c7e14ad</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-15T13:04:47.841+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">7913fe82-9f8c-4ceb-b398-b4ae7c7e14ad</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    ${response} =    Get Last Received
    [Return]    ${response}

CreateUpdateIndividualService-v7-ExpectFault
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : CreateOrUpdateAnIndividual-v7 env : ${ENV}
    Create Soap client    ${WSDL}/CreateOrUpdateAnIndividual-v7.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000442v07
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000442v07
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v7/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000442</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000442v07</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v7/createIndividual</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">1b517df6-3c21-4571-adf1-40f2491c201d</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000442</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-15T12:23:23.725+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">1b517df6-3c21-4571-adf1-40f2491c201d</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    ${response} =    Call Soap Method Expecting Fault    createIndividual    ${req_msg}
    sleep    3s    #prevent sax parse exception
    [Return]    ${response}
