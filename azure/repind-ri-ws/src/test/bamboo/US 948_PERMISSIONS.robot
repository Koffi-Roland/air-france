*** Settings ***
Suite Setup       Before
Suite Teardown    After
Library           DatabaseLibrary
Library           OperatingSystem
Library           Collections
Library           SudsLibrary
Library           XML
Library           BuiltIn
Library           String
Resource          %{SIC_RESOURCES}/../env/variables.robot
Resource          %{SIC_RESOURCES}/variables.robot
Resource          %{SIC_RESOURCES}/dataBase.robot
Resource          %{SIC_RESOURCES}/provideIndividualData.robot
Resource          %{SIC_RESOURCES}/enrollMyAccountCustomer.robot
Resource          %{SIC_RESOURCES}/createIndividual.robot
Resource          %{SIC_RESOURCES}/createOrUpdateRoleContract.robot
Resource          %{SIC_RESOURCES}/createOrUpdateComPrefBasedOnPermission.robot

*** Variables ***
${ENV}            rct
${DB_CONNECT_STRING}    ${DB_CONNECT_STRING_RCT}
${GLOBALGIN}      ${EMPTY}
${ACCOUNTID}      ${EMPTY}
${EMAIL}          ${EMPTY}
${password}       Abcd123+
@{P1}             10000001    FR    FR
@{P2}             10000002    FR    EN
@{P3}             10000003    DE    DE
@{P4}             10000004    NL    EN
@{P5}             10000005    US    ES
@{P6}             10000006    null    null
@{P7}             10000007    null    null
@{P8}             10000008    null    null
@{P9}             10000009    null    null
@{P10}            10000010    FR    EN
@{nbComPrefStatus0ExpectedList}    18    9    15    6    15    6    12    3    16    7    13    4    13    4    10    1    17
...               8    14    2    14    5    11    2    15    6    12    3    12    3    9    2    5    2
...               2    1    2    1    1    1    2    0    4
@{nbComPrefCreatedExpectedList}    9    5    9    4    9    5    9    2    9    4    9    3    9    4    9    1    9
...               5    9    2    9    5    9    2    9    4    9    3    9    3    9    2    4    2
...               2    1    2    1    1    1    2    0    4

*** Test Cases ***
Verify the number of compref created by permission WS scenario 01
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P3}    ${P4}    ${P5}
    @{answers}=    Create List    true    true    true    true    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    21
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[0]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[0]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FRFR    FNFB_CC=FRFR    FNFB_ESS=FRFR    FNFB_CC=FRFR    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=FRFR    FNFB_ESS=FRFR    FNFB_NEWS=FRFR    FNFB_PART=FRFR    FNFB_PROG=FRFR    PSRECO=NoneNone    SNAF=USEN
    ...    SXAF=USES    FNTO=FRFR    FNRO=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 02
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P3}    ${P4}
    @{answers}=    Create List    true    false    true    true
    ${countryCode} =    Convert To String    NL
    ${spokenLanguage}=    Convert To String    NL
    ${nbComPrefExpected}=    Convert To Integer    9
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[1]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[1]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=NLNL    FNFB_CC=NLNL    FNFB_ESS=NLNL    FNFB_CC=NLNL    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 03
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P3}    ${P5}
    @{answers}=    Create List    true    true    true    false
    ${countryCode} =    Convert To String    US
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    18
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[2]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[2]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=USEN    FNFB_CC=USEN    FNFB_ESS=USEN    FNFB_CC=USEN    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=USEN    FNFB_ESS=USEN    FNFB_NEWS=USEN    FNFB_PART=USEN    FNFB_PROG=USEN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=USEN
    ...    FNRO=USEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 04
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P3}
    @{answers}=    Create List    true    true    true
    ${countryCode} =    Convert To String    GB
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    6
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[3]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[3]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=GBEN    FNFB_CC=GBEN    FNFB_ESS=GBEN    FNFB_CC=GBEN    PSRECO=NoneNone    SNAF=DEDE    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 05
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P4}    ${P5}
    @{answers}=    Create List    true    true    false    true
    ${countryCode} =    Convert To String    DE
    ${spokenLanguage}=    Convert To String    DE
    ${nbComPrefExpected}=    Convert To Integer    18
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[4]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[4]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=DEDE    FNFB_CC=DEDE    FNFB_ESS=DEDE    PSRECO=NoneNone    SXAN=NLEN    FNFB_CC=DEDE    FNFB_ESS=DEDE    FNFB_NEWS=DEDE    FNFB_PART=DEDE    FNFB_PROG=DEDE    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=DEDE    PNAF_PERS=NoneNone
    ...    PTTEL=NoneNone    FNRO=DEDE
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 06
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P4}
    @{answers}=    Create List    false    true    true
    ${countryCode} =    Convert To String    GB
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    6
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[5]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[5]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=GBEN    FNFB_CC=GBEN    FNFB_ESS=GBEN    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SNAF=NLEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 07
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}    ${P5}
    @{answers}=    Create List    true    false    true
    ${countryCode} =    Convert To String    DE
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    15
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[6]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[6]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=DEFR    FNFB_CC=DEFR    FNFB_ESS=DEFR    FNFB_CC=DEFR    FNFB_ESS=DEFR    FNFB_NEWS=DEFR    FNFB_PART=DEFR    FNFB_PROG=DEFR    PSRECO=NoneNone    SNAF=USES    SXAF=USES    FNTO=DEFR    PNAF_PERS=NoneNone    PTTEL=NoneNone    FNRO=DEFR
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 08
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P2}
    @{answers}=    Create List    true    true
    ${countryCode} =    Convert To String    ES
    ${spokenLanguage}=    Convert To String    ES
    ${nbComPrefExpected}=    Convert To Integer    3
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[7]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[7]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=ESES    FNFB_CC=ESES    FNFB_ESS=ESES    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 09
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P3}    ${P4}    ${P5}
    @{answers}=    Create List    true    false    true    true
    ${countryCode} =    Convert To String    BR
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    19
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[8]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[8]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=BREN    FNFB_CC=BREN    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=BREN    FNFB_ESS=BREN    FNFB_NEWS=BREN    FNFB_PART=BREN    FNFB_PROG=BREN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=BREN
    ...    FNRO=BREN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 10
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P3}    ${P4}
    @{answers}=    Create List    false    true    false
    ${countryCode} =    Convert To String    IT
    ${spokenLanguage}=    Convert To String    IT
    ${nbComPrefExpected}=    Convert To Integer    7
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[9]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[9]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=ITIT    FNFB_CC=ITIT    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 11
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P3}    ${P5}
    @{answers}=    Create List    false    true    true
    ${countryCode} =    Convert To String    JP
    ${spokenLanguage}=    Convert To String    JA
    ${nbComPrefExpected}=    Convert To Integer    16
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[10]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[10]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=JPJA    FNFB_CC=JPJA    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=JPJA    FNFB_ESS=JPJA    FNFB_NEWS=JPJA    FNFB_PART=JPJA    FNFB_PROG=JPJA    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=JPJA    FNRO=JPJA    PNAF_PERS=NoneNone
    ...    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 12
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P3}
    @{answers}=    Create List    false    true
    ${countryCode} =    Convert To String    CA
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    4
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[11]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[11]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=CAEN    FNFB_CC=CAEN    PSRECO=NoneNone    SNAF=DEDE    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 13
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P4}    ${P5}
    @{answers}=    Create List    true    true    true
    ${countryCode} =    Convert To String    NO
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    16
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[12]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[12]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=NOEN    FNFB_CC=NOEN    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=NOEN    FNFB_ESS=NOEN    FNFB_NEWS=NOEN    FNFB_PART=NOEN    FNFB_PROG=NOEN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=NOEN    FNRO=NOEN    PNAF_PERS=NoneNone
    ...    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 14
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P4}
    @{answers}=    Create List    false    true
    ${countryCode} =    Convert To String    US
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    4
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[13]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[13]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=USEN    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SNAF=NLEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 15
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}    ${P5}
    @{answers}=    Create List    false    false
    ${countryCode} =    Convert To String    NL
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    13
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[14]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[14]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=NLNL    FNFB_CC=NLNL    FNFB_ESS=NLNL    FNFB_NEWS=NLNL    FNFB_PART=NLNL    FNFB_PROG=NLNL    PSRECO=NoneNone    SNAF=USES    SXAF=USES    FNTO=NLNL    FNRO=NLNL    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 16
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P1}
    @{answers}=    Create List    false
    ${countryCode} =    Convert To String    NL
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[15]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[15]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 17
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P3}    ${P4}    ${P5}
    @{answers}=    Create List    true    true    false    true
    ${countryCode} =    Convert To String    SE
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    20
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[16]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[16]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=SEEN    FNFB_ESS=SEEN    FNFB_CC=SEEN    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=SEEN    FNFB_ESS=SEEN    FNFB_NEWS=SEEN    FNFB_PART=SEEN    FNFB_PROG=SEEN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES
    ...    FNTO=SEEN    FNRO=SEEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 18
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P3}    ${P4}
    @{answers}=    Create List    false    false    false
    ${countryCode} =    Convert To String    ES
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    8
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[17]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[17]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=ESFR    FNFB_ESS=ESFR    FNFB_CC=ESFR    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 19
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P3}    ${P5}
    @{answers}=    Create List    true    true    false
    ${countryCode} =    Convert To String    CA
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    17
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[18]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[18]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=CAFR    FNFB_ESS=CAFR    FNFB_CC=CAFR    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=CAFR    FNFB_ESS=CAFR    FNFB_NEWS=CAFR    FNFB_PART=CAFR    FNFB_PROG=CAFR    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=CAFR    FNRO=CAFR
    ...    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 20
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P3}
    @{answers}=    Create List    true    false
    ${countryCode} =    Convert To String    CN
    ${spokenLanguage}=    Convert To String    ZH
    ${nbComPrefExpected}=    Convert To Integer    5
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[19]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[19]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=CNZH    FNFB_ESS=CNZH    FNFB_CC=CNZH    PSRECO=NoneNone    SNAF=DEDE    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 21
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P4}    ${P5}
    @{answers}=    Create List    false    false    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    17
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[20]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[20]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FREN    FNFB_ESS=FREN    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=FREN    FNFB_ESS=FREN    FNFB_NEWS=FREN    FNFB_PART=FREN    FNFB_PROG=FREN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=FREN    FNRO=FREN    PNAF_PERS=NoneNone
    ...    PTTEL=NoneNone
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 22
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P4}
    @{answers}=    Create List    false    true
    ${countryCode} =    Convert To String    CH
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    5
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[21]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[21]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=CHFR    FNFB_ESS=CHFR    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SNAF=NLEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 23
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}    ${P5}
    @{answers}=    Create List    true    false
    ${countryCode} =    Convert To String    IT
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    14
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[22]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[22]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=ITIT    FNFB_ESS=ITIT    FNFB_CC=ITIT    FNFB_ESS=ITIT    FNFB_NEWS=ITIT    FNFB_PART=ITIT    FNFB_PROG=ITIT    PSRECO=NoneNone    SNAF=USES    SXAF=USES    FNTO=ITIT    FNRO=ITIT    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 24
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P2}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    DK
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[23]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[23]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=DKEN    FNFB_ESS=DKEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 25
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P3}    ${P4}    ${P5}
    @{answers}=    Create List    true    false    true
    ${countryCode} =    Convert To String    IN
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    18
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[24]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[24]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=INEN    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=INEN    FNFB_ESS=INEN    FNFB_NEWS=INEN    FNFB_PART=INEN    FNFB_PROG=INEN    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=INEN    FNRO=INEN
    ...    PTTEL=NoneNone    PNAF_PERS=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 26
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P3}    ${P4}
    @{answers}=    Create List    false    true
    ${countryCode} =    Convert To String    BE
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    6
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[25]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[25]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=BEFR    PSRECO=NoneNone    SNAF=DEDE    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 27
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P3}    ${P5}
    @{answers}=    Create List    false    false
    ${countryCode} =    Convert To String    BR
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    15
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[26]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[26]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=BRFR    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=BRFR    FNFB_ESS=BRFR    FNFB_NEWS=BRFR    FNFB_PART=BRFR    FNFB_PROG=BRFR    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=BRFR    FNRO=BRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 28
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P3}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    KR
    ${spokenLanguage}=    Convert To String    KO
    ${nbComPrefExpected}=    Convert To Integer    3
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[27]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[27]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=KRKO    PSRECO=NoneNone    SNAF=DEDE    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 29
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P4}    ${P5}
    @{answers}=    Create List    true    true
    ${countryCode} =    Convert To String    AR
    ${spokenLanguage}=    Convert To String    ES
    ${nbComPrefExpected}=    Convert To Integer    15
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[28]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[28]
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SXAF=NLEN    FNFB_CC=ARES    FNFB_ESS=ARES    FNFB_NEWS=ARES    FNFB_PART=ARES    FNFB_PROG=ARES    PSRECO=NoneNone    SNAF=USEN    SXAF=USES    FNTO=ARES    PNAF_PERS=NoneNone    PTTEL=NoneNone    FNRO=ARES
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 30
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P4}
    @{answers}=    Create List    false
    ${countryCode} =    Convert To String    DE
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    3
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[29]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[29]
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SXAF=NLEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SNAF=NLEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 31
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P5}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    GP
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    12
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[30]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[30]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=GPFR    FNFB_ESS=GPFR    FNFB_NEWS=GPFR    FNFB_PART=GPFR    FNFB_PROG=GPFR    PSRECO=NoneNone    SNAF=USES    SXAF=USES    FNTO=GPFR    FNRO=GPFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 32
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P6}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    ZA
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[31]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[31]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_PROG=ZAEN    SNAF=ZAEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 33
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P4}    ${P6}
    @{answers}=    Create List    true    true
    ${countryCode} =    Convert To String    RE
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    5
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[32]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[32]
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SXAF=NLEN    FNFB_PROG=REFR    SNAF=REFR    PTTEL=NoneNone    PNAF_PERS=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 34
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P7}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    ZM
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[33]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[33]
    ${expectMarketLanquage} =    Create Dictionary    SAKQ=ZMEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SXAF=ZMEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 35
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P7}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    ZM
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[34]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[34]
    ${expectMarketLanquage} =    Create Dictionary    SAKQ=ZMEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SXAF=ZMEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 36
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P8}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    IT
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[35]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[35]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_ESS=ITIT    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 37
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P7}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    ZM
    ${spokenLanguage}=    Convert To String    null
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[36]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[36]
    ${expectMarketLanquage} =    Create Dictionary    SAKQ=ZMEN    PNAF_PERS=NoneNone    PTTEL=NoneNone    SXAF=ZMEN
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 38
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P9}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[37]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[37]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 39
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P9}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    ES
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[38]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[38]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 40
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P9}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    null
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[39]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[39]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 41
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P7}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${nbComPrefExpected}=    Convert To Integer    2
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[40]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[40]
    ${expectMarketLanquage} =    Create Dictionary    SAKQ=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone    SXAF=FRFR
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 42
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P9}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    EH
    ${spokenLanguage}=    Convert To String    EN
    ${nbComPrefExpected}=    Convert To Integer    1
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[41]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[41]
    ${expectMarketLanquage} =    Create Dictionary    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

Verify the number of compref created by permission WS scenario 43
    [Tags]    REPIND-948
    #Set permission configuration
    @{permissionId}=    Create List    ${P10}
    @{answers}=    Create List    true
    ${countryCode} =    Convert To String    IT
    ${spokenLanguage}=    Convert To String    IT
    ${nbComPrefExpected}=    Convert To Integer    4
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[42]
    ${nbComPrefStatus0Expected}=    Convert To Integer    ${nbComPrefStatus0ExpectedList}[42]
    ${expectMarketLanquage} =    Create Dictionary    SATO=FRFR    PNAF_PERS=NoneNone    PTTEL=NoneNone    SASB=FRFR    SAKQ=FRFR    SARO=KQKQ
    # Call Test keyword
    Test Permission    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    #Init DB
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US948_createPermissions.sql
    ${ref_perm_quest_id} =    SelectOne    ${DB_CONNECT_STRING}    select REF_PERMISSIONS_QUESTION_ID from SIC2.REF_PERMISSIONS where SSIGNATURE_MODIFICATION = 'ROBOTFRAMEWORK' and REF_PERMISSIONS_QUESTION_ID = '10000004' and rownum < 2
    Should Be Equal    ${ref_perm_quest_id}(integer)    10000004(integer)

After
    Clean database    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US948_deletePermissions.sql
    CleanComPref    ${DB_CONNECT_STRING}

Create User
    [Arguments]    ${countryCode}    ${spokenLanguage}
    #enroll individual
    ${random} =    Generate Random String    5    [LETTERS]
    ${EMAIL}=    Convert To String    test${random}@airfrance.fr
    ${soapBody}=    Run Keyword If    '${spokenLanguage}' == 'null'    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>${random}</firstName><lastName>${random}</lastName><emailIdentifier>${email}</emailIdentifier><password>${password}</password><languageCode>FR</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>test</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ...    ELSE    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>${random}</firstName><lastName>${random}</lastName><emailIdentifier>${email}</emailIdentifier><password>${password}</password><languageCode>${spokenLanguage}</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>test</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${result} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${soapBody}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #update his postal adress to set country code
    ${soapBody}=    Run Keyword If    '${spokenLanguage}' == 'null'    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${EMAIL}</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>${countryCode}</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier><birthDate>1994-11-16T12:00:00</birthDate></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode></languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ...    ELSE    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${EMAIL}</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>${countryCode}</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier><birthDate>1994-11-16T12:00:00</birthDate></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>${spokenLanguage}</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${result} =    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    [Return]    ${gin}

Test Permission
    [Arguments]    ${permissionId}    ${answers}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatus0Expected}    ${expectMarketLanquage}
    #create User with country code and spoken langage parameter
    ${gin}=    Create User    ${countryCode}    ${spokenLanguage}
    #calculate permissions block
    ${permissionBlock} =    Calculate PermissionBloc    ${permissionId}    ${answers}
    #call permission WS
    ${soapBody}=    Convert To String    <afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement><gin>${gin}</gin><permissionRequest>${permissionBlock}</permissionRequest><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:CreateOrUpdateComPrefBasedOnPermissionRequestElement>
    ${result}=    createOrUpdateComPrefBasedOnPermission-v1    ${ENV}    ${soapBody}
    #Extract code number
    ${XML_object}=    Parse XML    ${result}
    ${count} =    Get Element Count    ${XML_object}    xpath=.//code
    ${Codes} =    Get Elements Texts    ${XML_object}    xpath=.//code
    Should Contain X Times    ${Codes}    0    ${nbComPrefStatus0Expected}
    ${Details} =    Get Elements Texts    ${XML_object}    xpath=.//details
    Should Contain X Times    ${Details}    Communication preference created    ${nbComPrefCreatedExpected}
    #Provide v7 - retrieve Com Pref
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    ${XML_object}=    Parse XML    ${response}
    ${countComPref} =    Get Element Count    ${XML_object}    xpath=.//communicationPreferencesResponse
    Should Be True    ${countComPref}>=${nbComPrefCreatedExpected}
    #Test MarketLanguage
    #${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    #${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${countPreferenceResponse} =    Get Element Count    ${XML_object}    xpath=.//communicationPreferencesResponse
    @{comPrefResponse} =    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        ${groupe}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationGroupeType
        ${type}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationType
        ${countMarket}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${countLanguage}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${market}    Run Keyword If    ${countMarket} > 0 and ${countMarket} < 2    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${language}    Run Keyword If    ${countLanguage} > 0 and ${countLanguage} < 2    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${marketLanguage} =    Convert To String    ${market}${language}
        #Dictionary Should Contain Key    ${expectMarketLanquage}    ${domain}${groupe}${type}
        #Dictionary Should Contain Item    ${expectMarketLanquage}    ${domain}${groupe}${type}    ${marketLanguage}
        ${expectmarketLanguageValue} =    Get From Dictionary    ${expectMarketLanquage}    ${domain}${groupe}${type}
        Run Keyword If    '${domain}' != 'S'    Should Be Equal    '${expectmarketLanguageValue}'    '${marketLanguage}'
        #Run Keyword If    '${domain}${groupe}${type}' == 'SNAF' and '${marketLanguage}' != 'NoneNone'    Should Be True    ('${marketLanguage}' == 'NLEN' or '${marketLanguage}' == 'DEDE' or '${marketLanguage}' == 'USEN'or '${marketLanguage}' == 'ZAEN' or '${marketLanguage}' == 'REFR')
        Run Keyword If    '${domain}${groupe}${type}' == 'SNAF' and '${marketLanguage}' != 'NoneNone'    Should Be Equal    '${expectmarketLanguageValue}'    '${marketLanguage}'
        #Run Keyword If    '${domain}${groupe}${type}' == 'SXAF' and '${marketLanguage}' != 'NoneNone'    Should Be True    ('${marketLanguage}' == 'NLEN' or '${marketLanguage}' == 'USES')
        Run Keyword If    '${domain}${groupe}${type}' == 'SXAF' and '${marketLanguage}' != 'NoneNone'    Should Be Equal    '${expectmarketLanguageValue}'    '${marketLanguage}'
        Log    "---------------------------------------------------------------------------------------------------------------------------------------------------------"
    END
    Pass Execution    MarketLanguage are correct

Calculate PermissionBloc
    [Arguments]    ${permissionId}    ${answers}
    ${compteur}=    Convert To Integer    0
    ${permissionBlock}=    Set Variable
    FOR    ${itemPermission}    IN    @{permissionId}
        Log    ${itemPermission}
        ${permission}=    Run Keyword If    '${itemPermission}[1]' == 'null'    Convert To String    <permission><permissionID>${itemPermission}[0]</permissionID><permissionAnswer>${answers}[${compteur}]</permissionAnswer></permission>
        ...    ELSE    Convert To String    <permission><permissionID>${itemPermission}[0]</permissionID><permissionAnswer>${answers}[${compteur}]</permissionAnswer><market>${itemPermission}[1]</market><language>${itemPermission}[2]</language></permission>
        ${compteur}=    Evaluate    ${compteur}+1
        ${permissionBlock} =    Catenate    ${permissionBlock}    ${permission}
        ${permissionBlock} =    Strip String    ${permissionBlock}
    END
    [Return]    ${permissionBlock}
