*** Settings ***
Force Tags        REPIND-1264
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           String
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/searchIndividualByMulticriteria.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}

*** Test Cases ***
Verify the social media search by type
    #PNM
    ${socialtype} =    Convert To String    PNM
    ${socialtypeSql} =    Convert To String    PNM_ID
    Test search    ${socialtype}    ${socialtypeSql}
    #GIGYA
    ${socialtype} =    Convert To String    GID
    ${socialtypeSql} =    Convert To String    GIGYA_ID
    Test search    ${socialtype}    ${socialtypeSql}
    #TWITTER
    ${socialtype} =    Convert To String    TWT
    ${socialtypeSql} =    Convert To String    TWITTER_ID
    Test search    ${socialtype}    ${socialtypeSql}
    #FACEBOOK
    ${socialtype} =    Convert To String    FB
    ${socialtypeSql} =    Convert To String    FACEBOOK_ID
    Test search    ${socialtype}    ${socialtypeSql}
    #KAKAO
    ${socialtype} =    Convert To String    KKO
    ${socialtypeSql} =    Convert To String    KAKAO_ID
    Test search    ${socialtype}    ${socialtypeSql}

Verify the social media search GIG
    #GIGYA SOCIAL NETWORK DATA
    ${query} =    Convert To String    select SOCIAL_NETWORK_ID from sic2.social_network_data where rownum < 2 and length(SOCIAL_NETWORK_ID)< 51
    ${identifier}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${query} =    Convert To String    select SGIN \ from sic2.account_data a inner join sic2.social_network_data s on s.SOCIAL_NETWORK_ID = a.SOCIAL_NETWORK_ID where s.SOCIAL_NETWORK_ID = '${identifier}'
    ${sgin}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    #call searchWS
    ${socialtype} =    Convert To String    GID
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><identification><identificationType>${socialtype}</identificationType><identificationValue>${identifier}</identificationValue></identification></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${sgin}

*** Keywords ***
Test search
    [Arguments]    ${socialtype}    ${socialtypeSql}
    #Get the GIN
    ${query} =    Convert To String    SELECT \ IDENTIFIER FROM EXTERNAL_IDENTIFIER where TYPE = '${socialtypeSql}' and rownum < 2 and length(IDENTIFIER)< 51
    ${identifier}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${query} =    Convert To String    SELECT \ SGIN FROM EXTERNAL_IDENTIFIER where IDENTIFIER = '${identifier}' and TYPE = '${socialtypeSql}' \ and rownum < 2
    ${sgin}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    #call searchWS
    ${body} =    Convert To String    <afk0:SearchIndividualByMulticriteriaRequestElement><requestor><channel>B2C</channel><applicationCode>ISI</applicationCode><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor><identification><identificationType>${socialtype}</identificationType><identificationValue>${identifier}</identificationValue></identification></afk0:SearchIndividualByMulticriteriaRequestElement>
    ${response} =    SearchIndividualByMulticriteria-v2    ${ENV}    ${body}
    ${responseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${responseDecoded}    ${sgin}
