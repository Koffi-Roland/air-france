*** Settings ***
Resource          variables.robot

*** Keywords ***
CleanDatabase
    [Arguments]    ${DB_CONNECT_STRING}
    [Documentation]    to change env set global Variable here
    #Set Global Variable    ${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RC2}
    #Set Global Variable    ${ENV}    rc2
    #Suppression des données en cascade à partir de la signature de creation de l'individu
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    ${rowCount}    Row Count    select SGIN FROM sic2.individus_all where SSIGNATURE_CREATION = 'ROBOTFRAMEWORK' AND DDATE_CREATION > to_date('20180801', 'yyyymmdd')
    Log    ${rowCount}
    Run Keyword If    ${rowCount} > 0    Execute Sql String    call DELETE_CASCADE('individus_all','SSIGNATURE_CREATION = ''ROBOTFRAMEWORK'' AND DDATE_CREATION > to_date(''20180801'', ''yyyymmdd'') \ ')
    disconnect from database

CleanComPref
    [Arguments]    ${DB_CONNECT_STRING}
    #Suppression des données en cascade à partir de la signature de creation de l'individu
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    ${rowCount}    Row Count    select SGIN FROM sic2.COMMUNICATION_PREFERENCES where CREATION_SIGNATURE = 'ROBOTFRAMEWORK' AND CREATION_DATE > to_date('20180101', 'yyyymmdd')
    Log    ${rowCount}
    Run Keyword If    ${rowCount} > 0    Execute Sql String    call DELETE_CASCADE('COMMUNICATION_PREFERENCES','CREATION_SIGNATURE = ''ROBOTFRAMEWORK'' AND CREATION_DATE > to_date(''20180101'', ''yyyymmdd'') \ ')
    disconnect from database

CleanAlert
    [Arguments]    ${DB_CONNECT_STRING}
    #Suppression des données en cascade à partir de la signature de creation de l'individu
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    ${rowCount}    Row Count    select SGIN FROM sic2.ALERT where CREATION_SIGNATURE = 'ROBOTFRAMEWORK' AND CREATION_DATE > to_date('20180101', 'yyyymmdd')
    Log    ${rowCount}
    Run Keyword If    ${rowCount} > 0    Execute Sql String    call DELETE_CASCADE('ALERT','CREATION_SIGNATURE = ''ROBOTFRAMEWORK'' AND CREATION_DATE > to_date(''20180101'', ''yyyymmdd'') \ ')
    disconnect from database

Clean Database By Gin
    [Arguments]    ${DB_CONNECT_STRING}    ${gin}
    #Suppression des données en cascade à partir de la signature de creation de l'individu
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Execute Sql String    call DELETE_CASCADE('individus_all','SGIN = ''${gin}''')
    disconnect from database

SelectOne
    [Arguments]    ${DB_CONNECT_STRING}    ${QUERY}
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    @{queryResults}    Query    ${QUERY}
    ${result} =    Get From List    @{queryResults}    0
    disconnect from database
    [Return]    ${result}

Update Envvar
    [Arguments]    ${DB_CONNECT_STRING}    ${KEY}    ${VALUE}
    #update envvar
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Execute Sql String    UPDATE SIC2.ENV_VAR SET ENVVALUE = '${VALUE}' WHERE ENVKEY = '${KEY}'
    disconnect from database

SelectMultiple
    [Arguments]    ${DB_CONNECT_STRING}    ${QUERY}
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    @{queryResults}    Query    ${QUERY}
    disconnect from database
    [Return]    @{queryResults}

ExecuteScriptSQL
    [Arguments]    ${DB_CONNECT_STRING}    ${PATH}
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Execute Sql Script    ${PATH}
    disconnect from database
	
ExecuteRequestSQL
    [Arguments]    ${DB_CONNECT_STRING}    ${QUERY}
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    @{queryResult}    Query    ${QUERY}
    disconnect from database
	[Return]    @{queryResult}

Create Index
    [Arguments]    ${DB_CONNECT_STRING}    ${KEY}    ${VALUE}
    #CREATE INDEX "SIC2"."I_IND_ROBOTFRAMEWORK" ON "SIC2"."INDIVIDUS_ALL" ("SSIGNATURE_CREATION", "DDATE_CREATION") PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS NOLOGGING STORAGE(INITIAL 450887680 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1    BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)    TABLESPACE "SIC_IND" ;
    connect to database using custom params    cx_Oracle    '${DB_CONNECT_STRING}'
    Execute Sql String    UPDATE SIC2.ENV_VAR SET ENVVALUE = '${VALUE}' WHERE ENVKEY = '${KEY}'
    disconnect from database
