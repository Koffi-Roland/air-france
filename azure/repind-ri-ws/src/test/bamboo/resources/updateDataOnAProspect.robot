*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
UpdateDataOnAProspect-v2-DEPRECATED
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : UpdateDataOnAProspect-v2 env : ${ENV}
    Create Soap client    ${WSDL}/UpdateDataOnAProspect-v2.0.0.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/000843v02
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/000843v02
    Log    WSURL : ${location}
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v1/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90000843</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rct.airfrance.fr/passenger/marketing/000843v02</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/UpdateDataOnAProspectService-v2/updateDataOnAProspect</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">2a12e025-f2a5-48ce-b949-d0bff7f99ef8</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t379296</userID><partyID>AFKL</partyID><consumerID>W90000843</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-10-09T13:36:01.517+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">2a12e025-f2a5-48ce-b949-d0bff7f99ef8</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    updateDataOnAProspect    ${req_msg}
    ${response} =    Get Last Received
    Should Contain    ${response}    New prospect created
    #<afk0:UpdateDataOnAProspectRequestElement><prospectIdentifier>900029881401</prospectIdentifier><identifierType>GIN</identifierType><signature><site>QVI</site><signature>ROBOTFRAMEWORK</signature><ipAddress>10.20.30.40</ipAddress></signature><identificationData><lastNameSC>COUCOU</lastNameSC><firstNameSC>COUCOU</firstNameSC></identificationData></afk0:UpdateDataOnAProspectRequestElement> \
    [Return]    ${response}
