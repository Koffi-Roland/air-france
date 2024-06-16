*** Settings ***
Resource          ../env/variables.robot

*** Keywords ***
provideIndividualReferenceTable-v2
    [Arguments]    ${ENV}    ${BODY}
    Log    ws : ProvideIndividualReferenceTable-v2 env : ${ENV}
    Create Soap client    ${WSDL}/ProvideIndividualReferenceTable-v2.0.0_1.wsdl.xml
    ${location} =    Run Keyword If    '${ENV}' == 'rc2'    Convert To String    https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001588v02
    ...    ELSE    Convert To String    https://ws-qvi-${ENV}.airfrance.fr/passenger/marketing/001588v02
    Set Location    ${location}
    ${req_msg}=    Create Raw Soap Message    <?xml version="1.0" encoding="UTF-8"?><soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:afk0="http://www.af-klm.com/services/passenger/data-v2/xsd"><soapenv:Header><wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" soapenv:mustUnderstand="1"><wsse:UsernameToken xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" wsu:Id="UsernameToken-8"><wsse:Username>W90001588</wsse:Username><wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">pswpsw123</wsse:Password></wsse:UsernameToken></wsse:Security><To xmlns="http://www.w3.org/2005/08/addressing">https://ws-qvi-rc2.airfrance.fr/passenger/marketing/001588v02</To><Action xmlns="http://www.w3.org/2005/08/addressing">http://www.af-klm.com/services/passenger/ProvideIndividualReferenceTableService-v2/provideIndividualReferenceTable</Action><ReplyTo xmlns="http://www.w3.org/2005/08/addressing"><Address>http://www.w3.org/2005/08/addressing/anonymous</Address></ReplyTo><MessageID xmlns="http://www.w3.org/2005/08/addressing">8f8c5780-1856-4825-a269-4cb9d2cf1b66</MessageID><trackingMessageHeader xmlns="http://www.af-klm.com/soa/xsd/MessageHeader-V1_0"><consumerRef><userID>t761279</userID><partyID>AF</partyID><consumerID>W90001588</consumerID><consumerLocation>QVI</consumerLocation><consumerType>A</consumerType><consumerTime>2018-04-10T09:17:56.058+00:00</consumerTime></consumerRef></trackingMessageHeader><RelatesTo xmlns="http://www.w3.org/2005/08/addressing" RelationshipType="InitiatedBy">8f8c5780-1856-4825-a269-4cb9d2cf1b66</RelatesTo></soapenv:Header><soapenv:Body>${BODY}</soapenv:Body></soapenv:Envelope>
    Call Soap Method    provideIndividualReferenceTable    ${req_msg}
    ${response} =    Get Last Received
    [Return]    ${response}
