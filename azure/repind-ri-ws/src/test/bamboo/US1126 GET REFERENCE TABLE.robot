*** Settings ***
Force Tags        REPIND-1126    #Suite Setup    Before    #Suite Teardown    After
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           String
Resource          %{SIC_RESOURCES}/../env/variables.robot
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/commonUtils.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualReferenceTable.robot

*** Variables ***
${ENV}            rc2
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}

*** Test Cases ***
Verify the Provide on REF_COMPREF table
    ${refTableName} =    Convert To String    REF_COMPREF
    ${refTableNameId} =    Convert To String    REF_COMPREF_ID
    ${refTableQuery} =    Convert To String    VIEW_REF_COMPREF
    ${refTableQueryFilter} =    Convert To String    DOMAIN = 'P'
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    2
    @{keyExpected} =    Create List    refComPrefId    domain    groupType    type    description    mandatoryOptin    market    defaultLanguage1    fieldA    media    REF_COMPREF
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_PERMISSIONS_QUESTION table
    ${refTableName} =    Convert To String    REF_PERMISSIONS_QUESTION
    ${refTableNameId} =    Convert To String    REF_PERMISSIONS_QUESTION_ID
    ${refTableQuery} =    Convert To String    REF_PERMISSIONS_QUESTION
    ${refTableQueryFilter} =    Convert To String    SNAME IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    3
    @{keyExpected} =    Create List    permissionQuestionId    name    questionFr    questionEn    REF_PERMISSIONS_QUESTION
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_PERMISSIONS table
    ${refTableName} =    Convert To String    REF_PERMISSIONS
    ${refTableNameId} =    Convert To String    REF_PERMISSIONS_QUESTION_ID
    ${refTableQuery} =    Convert To String    REF_PERMISSIONS_QUESTION where REF_PERMISSIONS_QUESTION_ID in ( select distinct a.REF_PERMISSIONS_QUESTION_ID from sic2.REF_PERMISSIONS a inner join sic2.REF_PERMISSIONS_QUESTION b on b.REF_PERMISSIONS_QUESTION_ID = a.REF_PERMISSIONS_QUESTION_ID )
    ${refTableQueryFilter} =    Convert To String    1 = 1
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    3
    @{keyExpected} =    Create List    permissionQuestionId    domain    groupType    type    name    questionFr    questionEn    REF_PERMISSIONS_QUESTION
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}
    #REF_PERMISSIONS return 2 blocs \ PERMISSIONS and REFCOMPREF

Verify the Provide on REF_COMPREF_DOMAIN table
    ${refTableName} =    Convert To String    REF_COMPREF_DOMAIN
    ${refTableNameId} =    Convert To String    SCODE_DOMAIN
    ${refTableQuery} =    Convert To String    REF_COMPREF_DOMAIN
    ${refTableQueryFilter} =    Convert To String    SCODE_DOMAIN IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    4
    @{keyExpected} =    Create List    codeDomain    libelleFr    libelleEn    REF_COMPREF_DOMAIN
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_COMPREF_TYPE table
    ${refTableName} =    Convert To String    REF_COMPREF_TYPE
    ${refTableNameId} =    Convert To String    SCODE_TYPE
    ${refTableQuery} =    Convert To String    REF_COMPREF_TYPE
    ${refTableQueryFilter} =    Convert To String    SCODE_TYPE IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    19
    @{keyExpected} =    Create List    codeType    libelleTypeFR    libelleTypeEN    REF_COMPREF_TYPE
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_COMPREF_GTYPE table
    ${refTableName} =    Convert To String    REF_COMPREF_GTYPE
    ${refTableNameId} =    Convert To String    SCODE_GTYPE
    ${refTableQuery} =    Convert To String    REF_COMPREF_GTYPE
    ${refTableQueryFilter} =    Convert To String    SCODE_GTYPE IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    6
    @{keyExpected} =    Create List    codeGType    libelleGTypeFR    libelleGTypeEN    REF_COMPREF_GTYPE
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_COMPREF_MEDIA table
    ${refTableName} =    Convert To String    REF_COMPREF_MEDIA
    ${refTableNameId} =    Convert To String    SCODE_MEDIA
    ${refTableQuery} =    Convert To String    REF_COMPREF_MEDIA
    ${refTableQueryFilter} =    Convert To String    SCODE_MEDIA IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    2    # was 3
    @{keyExpected} =    Create List    codeMedia    libelleMediaFR    libelleMediaEN    REF_COMPREF_MEDIA
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on REF_COMPREF_COUNTRY_MARKET table
    ${refTableName} =    Convert To String    REF_COMPREF_COUNTRY_MARKET
    ${refTableNameId} =    Convert To String    CODE_PAYS
    ${refTableQuery} =    Convert To String    REF_COMPREF_COUNTRY_MARKET
    ${refTableQueryFilter} =    Convert To String    MARKET IS NOT NULL
    ${refTableWSFilter} =    Convert To String    <domain>P</domain>
    ${maxResult} =    Convert To String    198
    @{keyExpected} =    Create List    codePays    market    REF_COMPREF_COUNTRY_MARKET
    # Test using data defined
    Check provide    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}

Verify the Provide on complex table
    #Classic Provide REF_PERMISSIONS_QUESTION
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>REF_PERMISSIONS_QUESTION</tableName><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${nbSubRefTable}=    Get Element Count    ${XML_object}    xpath=.//subRefTable
    ${reponseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Be True    ${nbSubRefTable} == 1
    Should Contain    ${reponseDecoded}    REF_PERMISSIONS_QUESTION
    Should Not Contain    ${reponseDecoded}    REF_COMPREF
    #Classic Provide REF_PERMISSIONS
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>REF_PERMISSIONS</tableName><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${nbSubRefTable}=    Get Element Count    ${XML_object}    xpath=.//subRefTable
    ${reponseDecoded}=    Decode Bytes to String    ${response}    UTF-8
    Should Contain    ${reponseDecoded}    REF_PERMISSIONS_QUESTION
    Should Contain    ${reponseDecoded}    REF_COMPREF
    Should Be True    ${nbSubRefTable} == 2
    #Classic Provide REF_PERMISSIONS Check that the number of Compref is good
    ${query} =    Convert To String    select distinct REF_PERMISSIONS_QUESTION_ID from SIC2.REF_PERMISSIONS where rownum = 1 order by REF_PERMISSIONS_QUESTION_ID
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${query} =    Convert To String    select count(*) from SIC2.REF_PERMISSIONS where REF_PERMISSIONS_QUESTION_ID = ${resultQuery}
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${totalCompref}=    Convert To String    ${resultQuery}
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>REF_PERMISSIONS</tableName><pagination><index>1</index><maxResults>1</maxResults></pagination><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${nbCompref}=    Get Element Count    ${XML_object}    xpath=.//subRefTable[2]/row
    Should Be True    ${nbCompref} == ${totalCompref}
    #Classic Provide REF_PERMISSIONS Check that the permissionId is good for every Compref
    ${query} =    Convert To String    select distinct REF_PERMISSIONS_QUESTION_ID from SIC2.REF_PERMISSIONS where rownum = 1 order by REF_PERMISSIONS_QUESTION_ID
    ${permissionId}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>REF_PERMISSIONS</tableName><pagination><index>1</index><maxResults>1</maxResults></pagination><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${listCompref}=    Get Elements    ${XML_object}    xpath=.//subRefTable[2]/row
    FOR    ${compref}    IN    @{listCompref}
        ${listValue}    Get Elements Texts    ${compref}    xpath=.//value
        ${comprefPermissionsId}=    Convert To String    ${listValue}[0]
    END
    Should Be True    ${comprefPermissionsId} == ${permissionId}

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}

After
    Clean database    ${DB_CONNECT_STRING}

Check provide
    [Arguments]    ${refTableName}    ${refTableNameId}    ${refTableQuery}    ${refTableQueryFilter}    ${refTableWSFilter}    ${maxResult}    ${keyExpected}
    #Provide : test limit
    ${query} =    Convert To String    select count(*) from sic2.${refTableQuery}
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${totalResultsFound}=    Convert To String    ${resultQuery}
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>${refTableName}</tableName><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    Element Should Exist    ${response}    xpath=.//totalResults
    Element Text Should Be    ${response}    ${totalResultsFound}    xpath=.//totalResults
    #Verify WS don t return more than 200 rows
    ${nbRows}=    Get Element Count    ${XML_object}    xpath=.//row
    Run Keyword If    ${totalResultsFound} > 199    Should Be True    ${nbRows} == 200
    Log    Test limit OK
    #Provide with Pagination test MaxResult
    ${query} =    Convert To String    select count(*) from sic2.${refTableQuery}
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${totalResultsFound}=    Convert To String    ${resultQuery}
    ${pagination}=    Convert To String    <pagination><index>1</index><maxResults>${maxResult}</maxResults></pagination>
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>${refTableName}</tableName>${pagination}<requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${totalResults}=    Get Element Text    ${XML_object}    xpath=.//totalResults
    #Verify maxRows ( with pagination context )
    Should Contain    ${totalResultsFound}    ${totalResults}
    ${nbRows}=    Run Keyword If    '${refTableName}' == 'REF_PERMISSIONS'    Get Element Count    ${XML_object}    xpath=.//subRefTable[1]/row
    ...    ELSE    Get Element Count    ${XML_object}    xpath=.//row
    #Verify nb rows returned = maxResultExpected
    Should Be True    ${nbRows} == ${maxResult}
    Log    Test pagination OK
    #Provide Check Order Id - only for REF_COMPREF or REF_PERMISSIONS QUESTIONS
    ${query} =    Convert To String    select ${refTableNameId} from SIC2.${refTableName} where rownum = 1 order by ${refTableNameId} desc
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    # if REF_COMPREF or REF_PERMISSIONS QUESTIONS then use the result of the query as integer else the query can return only a String
    ${totalResultsFound}=    Run Keyword If    '${refTableName}' == 'REF_COMPREF' or '${refTableName}' == 'REF_PERMISSIONS_QUESTION'    Convert To String    ${resultQuery}
    ...    ELSE    Convert To String    100
    ${lastRefComPrefId}=    Convert To String    ${resultQuery}
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>${refTableName}</tableName><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    ${output}=    Convert To String    ${response}
    #If Total > 199 then id will not be found else it will be found...
    Run Keyword If    ${totalResultsFound} > 199    Should Not Contain    ${output}=    <value>${lastRefComPrefId}</value>
    ...    ELSE    Run Keyword If    '${refTableName}' == 'REF_COMPREF' or '${refTableName}' == 'REF_PERMISSIONS_QUESTION'    Should Contain    ${output}=    <value>${lastRefComPrefId}</value>
    Log    Test order OK
    #Provide Check filter
    ${query} =    Run Keyword If    '${refTableName}' == 'REF_PERMISSIONS'    Convert To String    select count(*) from sic2.${refTableQuery} and \ ${refTableQueryFilter}
    ...    ELSE    Convert To String    select count(*) from sic2.${refTableQuery} where ${refTableQueryFilter}
    ${resultQuery}=    SelectOne    ${DB_CONNECT_STRING}    ${query}
    ${totalResultsFound}=    Convert To String    ${resultQuery}
    #call WS with a Domain P filter
    ${body} =    Convert To String    <afk0:ProvideIndividualReferenceTableRequestElement><tableName>${refTableName}</tableName>${refTableWSFilter}<requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualReferenceTableRequestElement>
    ${response} =    provideIndividualReferenceTable-v2    ${ENV}    ${body}
    Element Should Exist    ${response}    xpath=.//totalResults
    Element Text Should Be    ${response}    ${totalResultsFound}    xpath=.//totalResults
    ${responseDecoded}=    Decode Bytes To String    ${response}    UTF-8
    Log    Test filter OK
    #Provide Check all keys expected
    FOR    ${key}    IN    @{keyExpected}
        Should Contain    ${responseDecoded}    ${key}
    END
    # Finally...
    Pass Execution    Test ${refTableName} OK
