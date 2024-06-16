*** Settings ***
Suite Setup       Before
Suite Teardown    After
Force Tags        REPIND-1068
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
@{P1}             01    FR    FR
@{P2}             02    NL    NL
@{P3}             03    US    EN
@{P4}             04    GB    EN
@{P5}             05    DE    DE
@{P6}             06    GB    FR
@{P7}             07    DE    FR
@{P8}             08    ES    ES
@{P9}             09    BR    EN
@{P10}            10    IT    IT
@{P11}            11    JP    JA
@{P12}            12    CA    EN
@{P13}            13    NO    EN
@{P14}            14    US    FR
@{P15}            15    NL    FR
@{P16}            16    NL    EN
@{P17}            17    SE    EN
@{P18}            18    ES    FR
@{P19}            19    CA    FR
@{nbComPrefExpectedList}    14    13    11    12    6    10    11    9    5    3    4    8    2    3    1    0    0
...               0    0
@{nbComPrefCreatedExpectedList}    8    8    8    8    5    8    8    8    4    3    4    8    2    3    1    0    0
...               0    0
@{nbComPrefStatusKOList}    1    1    1    1    0    1    1    1    0    0    0    1    0    0    0    0    0
...               0    0

*** Test Cases ***
Verify the number of compref created by group WS scenario 01
    #Set group configuration
    ${productId}    Convert To String    ${P1}[0]
    ${countryCode} =    Convert To String    ${P1}[1]
    ${spokenLanguage}=    Convert To String    ${P1}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[0]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[0]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[0]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=FRFR    FNFB_ESS=FRFR    FNFB_NEWS=FRFR    FNFB_PART=FRFR    FNFB_PROG=FRFR    FNTO=FRFR    PSRECO=NoneNone    SNAF=FRFR    PSRECO=NoneNone    SNAF=FRFR    FNFB_CC=FRFR    FNTO=FRFR    SNAF=FRFR    FNFB_NEWS=FRFR    FNRO=FRFR
    ...    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 02
    #Set group configuration
    ${productId}    Convert To String    ${P2}[0]
    ${countryCode} =    Convert To String    ${P2}[1]
    ${spokenLanguage}=    Convert To String    ${P2}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[1]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[1]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[1]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=NLNL    FNFB_ESS=NLNL    FNFB_NEWS=NLNL    FNFB_PART=NLNL    FNFB_PROG=NLNL    FNTO=NLNL    PSRECO=NoneNone    SNAF=NLNL    PSRECO=NoneNone    SNAF=NLNL    FNFB_CC=NLNL    FNTO=NLNL    SNAF=NLNL    FNRO=NLNL    PNAF_PERS=NoneNone
    ...    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 03
    #Set group configuration
    ${productId}    Convert To String    ${P3}[0]
    ${countryCode} =    Convert To String    ${P3}[1]
    ${spokenLanguage}=    Convert To String    ${P3}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[2]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[2]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[2]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=USEN    FNFB_ESS=USEN    FNFB_NEWS=USEN    FNFB_PART=USEN    FNFB_PROG=USEN    FNTO=USEN    PSRECO=NoneNone    SNAF=USEN    PSRECO=NoneNone    SNAF=USEN    FNFB_NEWS=USEN    FNRO=USEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 04
    #Set group configuration
    ${productId}    Convert To String    ${P4}[0]
    ${countryCode} =    Convert To String    ${P4}[1]
    ${spokenLanguage}=    Convert To String    ${P4}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[3]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[3]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[3]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=GBEN    FNFB_ESS=GBEN    FNFB_NEWS=GBEN    FNFB_PART=GBEN    FNFB_PROG=GBEN    FNTO=GBEN    PSRECO=NoneNone    SNAF=GBEN    FNFB_CC=GBEN    FNTO=GBEN    SNAF=GBEN    FNFB_NEWS=GBEN    FNRO=GBEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 05
    #Set group configuration
    ${productId}    Convert To String    ${P5}[0]
    ${countryCode} =    Convert To String    ${P5}[1]
    ${spokenLanguage}=    Convert To String    ${P5}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[4]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[4]    #5
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[4]    #0
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=DEDE    FNTO=DEDE    SNAF=DEDE    FNFB_NEWS=DEDE    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 06
    #Set group configuration
    ${productId}    Convert To String    ${P6}[0]
    ${countryCode} =    Convert To String    ${P6}[1]
    ${spokenLanguage}=    Convert To String    ${P6}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[5]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[5]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[5]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=GBEN    FNFB_ESS=GBEN    FNFB_NEWS=GBEN    FNFB_PART=GBEN    FNFB_PROG=GBEN    FNTO=GBEN    PSRECO=NoneNone    SNAF=GBEN    PSRECO=NoneNone    SNAF=GBEN    FNRO=GBEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 07
    #Set group configuration
    ${productId}    Convert To String    ${P7}[0]
    ${countryCode} =    Convert To String    ${P7}[1]
    ${spokenLanguage}=    Convert To String    ${P7}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[6]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[6]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[6]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=DEFR    FNFB_ESS=DEFR    FNFB_NEWS=DEFR    FNFB_PART=DEFR    FNFB_PROG=DEFR    FNTO=DEFR    PSRECO=NoneNone    SNAF=DEDE    FNFB_CC=DEFR    FNTO=DEFR    SNAF=DEDE    FNRO=DEFR    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 08
    #Set group configuration
    ${productId}    Convert To String    ${P8}[0]
    ${countryCode} =    Convert To String    ${P8}[1]
    ${spokenLanguage}=    Convert To String    ${P8}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[7]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[7]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[7]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=ESES    FNFB_ESS=ESES    FNFB_NEWS=ESES    FNFB_PART=ESES    FNFB_PROG=ESES    FNTO=ESES    PSRECO=NoneNone    SNAF=ESES    FNFB_NEWS=ESES    PNAF_PERS=NoneNone    PTTEL=NoneNone    FNRO=ESES
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 09
    #Set group configuration
    ${productId}    Convert To String    ${P9}[0]
    ${countryCode} =    Convert To String    ${P9}[1]
    ${spokenLanguage}=    Convert To String    ${P9}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[8]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[8]    #4
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[8]    #0
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SNAF=BRPT    FNFB_CC=BREN    FNTO=BREN    SNAF=BRPT    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 10
    #Set group configuration
    ${productId}    Convert To String    ${P10}[0]
    ${countryCode} =    Convert To String    ${P10}[1]
    ${spokenLanguage}=    Convert To String    ${P10}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[9]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[9]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[9]
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SNAF=ITIT    FNFB_NEWS=ITIT    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 11
    #Set group configuration
    ${productId}    Convert To String    ${P11}[0]
    ${countryCode} =    Convert To String    ${P11}[1]
    ${spokenLanguage}=    Convert To String    ${P11}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[10]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[10]    #4
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[10]    #0
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=JPJA    FNTO=JPJA    SNAF=JPJA    FNFB_NEWS=JPJA    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 12
    #Set group configuration
    ${productId}    Convert To String    ${P12}[0]
    ${countryCode} =    Convert To String    ${P12}[1]
    ${spokenLanguage}=    Convert To String    ${P12}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[11]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[11]    #8
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[11]    #3
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=CAEN    FNFB_ESS=CAEN    FNFB_NEWS=CAEN    FNFB_PART=CAEN    FNFB_PROG=CAEN    FNTO=CAEN    PSRECO=NoneNone    SNAF=CAEN    FNRO=CAEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 13
    #Set group configuration
    ${productId}    Convert To String    ${P13}[0]
    ${countryCode} =    Convert To String    ${P13}[1]
    ${spokenLanguage}=    Convert To String    ${P13}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[12]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[12]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[12]
    ${expectMarketLanquage} =    Create Dictionary    PSRECO=NoneNone    SNAF=NOEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 14
    #Set group configuration
    ${productId}    Convert To String    ${P14}[0]
    ${countryCode} =    Convert To String    ${P14}[1]
    ${spokenLanguage}=    Convert To String    ${P14}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[13]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[13]    #3
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[13]    #0
    ${expectMarketLanquage} =    Create Dictionary    FNFB_CC=USEN    FNTO=USEN    SNAF=USEN    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 15
    #Set group configuration
    ${productId}    Convert To String    ${P15}[0]
    ${countryCode} =    Convert To String    ${P15}[1]
    ${spokenLanguage}=    Convert To String    ${P15}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[14]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[14]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[14]
    ${expectMarketLanquage} =    Create Dictionary    FNFB_NEWS=NLNL    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 16
    #Set group configuration
    ${productId}    Convert To String    ${P16}[0]
    ${countryCode} =    Convert To String    ${P16}[1]
    ${spokenLanguage}=    Convert To String    ${P16}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[15]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[15]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[15]
    ${expectMarketLanquage} =    Create Dictionary    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 17
    #Set group configuration
    ${productId}    Convert To String    ${P17}[0]
    ${countryCode} =    Convert To String    ${P17}[1]
    ${spokenLanguage}=    Convert To String    ${P17}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[16]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[16]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[16]
    ${expectMarketLanquage} =    Create Dictionary    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 18
    #Set group configuration
    ${productId}    Convert To String    ${P18}[0]
    ${countryCode} =    Convert To String    ${P18}[1]
    ${spokenLanguage}=    Convert To String    ${P18}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[17]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[17]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[17]
    ${expectMarketLanquage} =    Create Dictionary    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify the number of compref created by group WS scenario 19
    #Set group configuration
    ${productId}    Convert To String    ${P19}[0]
    ${countryCode} =    Convert To String    ${P19}[1]
    ${spokenLanguage}=    Convert To String    ${P19}[2]
    ${nbComPrefExpected}=    Convert To Integer    ${nbComPrefExpectedList}[18]
    ${nbComPrefCreatedExpected}=    Convert To Integer    ${nbComPrefCreatedExpectedList}[18]
    ${nbComPrefStatusKO}=    Convert To Integer    ${nbComPrefStatusKOList}[18]
    ${expectMarketLanquage} =    Create Dictionary    PNAF_PERS=NoneNone    PTTEL=NoneNone
    # Call Test keyword
    Test Group    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefStatusKO}    ${expectMarketLanquage}

Verify error message relating on context
    ${productId}    Convert To String    ${P3}[0]
    ${countryCode} =    Convert To String    ${P3}[1]
    ${spokenLanguage}=    Convert To String    ${P3}[2]
    ${gin}=    Create User    ${countryCode}    ${spokenLanguage}
    #call permission WS with bad context and create action
    ${soapBody}=    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${gin}</gin><actionCode>C</actionCode><contractRequest><contract><contractNumber>R${gin}</contractNumber><contractType>C</contractType><productType>${productId}</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><context>UPDATE_COMPREF</context><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${result}=    CreateOrUpdateRoleContractService-v1-ExpectFault    ${ENV}    ${soapBody}
    Should Contain    ${result}    Invalid parameter: Context not valid
    #call permission WS with good context but update action
    ${soapBody}=    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${gin}</gin><actionCode>U</actionCode><contractRequest><contract><contractNumber>R${gin}</contractNumber><contractType>C</contractType><productType>${productId}</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><context>CREATE_COMPREF</context><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${result}=    CreateOrUpdateRoleContractService-v1-ExpectFault    ${ENV}    ${soapBody}
    Should Contain    ${result}    Invalid parameter: Context not allowed

Verify role contrat with NAT N
    ${productId}    Convert To String    FP
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${emailOptin} =    Convert To String    N
    ${expectMarketLanquage} =    Create Dictionary    FNFB_PARTN=FRFR    FNTON=FRFR    FNFB_PROGN=FRFR    FNFB_CCN=FRFR    FNFB_ESSY=FRFR    FNFB_NEWSY=FRFR    PTTELN=NoneNone    FNFB_EXCLN=FRFR    FNFB_NEWSN=FRFR    FNFB_EXCLY=FRFR    FMFB_EXCLY=FRFR    PNAF_PERSN=NoneNone    PTTELY=NoneNone    FNRON=FRFR    FPARON=FRFR
    ...    FNFB_PARTY=FRFR    FNSBY=FRFR    FNFB_PROGY=FRFR    FNFB_CCY=FRFR    FNFB_CCY=FRFR    FNSBN=FRFR
    #test group by NAT
    Test Group by NAT    ${productId}    ${countryCode}    ${spokenLanguage}    ${expectMarketLanquage}    ${emailOptin}

Verify role contrat with NAT A
    ${productId}    Convert To String    FP
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${emailOptin} =    Convert To String    A
    ${expectMarketLanquage} =    Create Dictionary    FNFB_PARTN=FRFR    FNTON=FRFR    FNFB_PROGY=FRFR    FNFB_CCY=FRFR    FNFB_ESSY=FRFR    FNFB_NEWSY=FRFR    PTTELN=NoneNone    FNFB EXCLY=FRFR    FNFB_EXCLN=FRFR    PNAF_PERSN=NoneNone    PTTELY=NoneNone    FNROY=FRFR    FMFB_EXCLN=FRFR    FPAROY=FRFR    FNFB_PARTY=FRFR
    ...    FNSBY=FRFR    FNFB_EXCLY=FRFR
    #test group by NAT
    Test Group by NAT    ${productId}    ${countryCode}    ${spokenLanguage}    ${expectMarketLanquage}    ${emailOptin}

Verify role contrat with NAT T
    ${productId}    Convert To String    FP
    ${countryCode} =    Convert To String    FR
    ${spokenLanguage}=    Convert To String    FR
    ${emailOptin} =    Convert To String    T
    ${expectMarketLanquage} =    Create Dictionary    FNFB_PARTY=FRFR    FNTON=FRFR    FNFB_PROGY=FRFR    FNFB_CCY=FRFR    FNFB_ESSY=FRFR    FNFB_NEWSY=FRFR    PTTELN=NoneNone    FNFB EXCLY=FRFR    FNFB_EXCLN=FRFR    FMFB_EXCLN=FRFR    PNAF_PERSN=NoneNone    PTTELY=NoneNone    FNROY=FRFR    FPAROY=FRFR    FNSBY=FRFR
    ...    FNFB_EXCLY=FRFR
    #test group by NAT
    Test Group by NAT    ${productId}    ${countryCode}    ${spokenLanguage}    ${expectMarketLanquage}    ${emailOptin}

*** Keywords ***
Before
    CleanDatabase    ${DB_CONNECT_STRING}
    CleanComPref    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1068_createGroupe.sql
    ${ref_group_quest_id} =    SelectOne    ${DB_CONNECT_STRING}    select REF_COMPREF_GROUP_INFO_ID from REF_COMPREF_GROUP_INFO where SSIGNATURE_MODIFICATION = 'ROBOTFRAMEWORK' and REF_COMPREF_GROUP_INFO_ID = '20000004' and rownum < 2
    Should Be Equal    ${ref_group_quest_id}(integer)    20000004(integer)

After
    Clean database    ${DB_CONNECT_STRING}
    ExecuteScriptSQL    ${DB_CONNECT_STRING}    ${SQL}/US1068_deleteGroupe.sql
    CleanComPref    ${DB_CONNECT_STRING}

Create User
    [Arguments]    ${countryCode}    ${spokenLanguage}
    #enroll individual
    ${random} =    Generate Random String    5    [LETTERS][NUMBERS]
    ${EMAIL}=    GenerateRandomValidEmail
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>${random}</firstName><lastName>${random}</lastName><emailIdentifier>${email}</emailIdentifier><password>${password}</password><languageCode>${spokenLanguage}</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>test</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${result} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${soapBody}
    ${XML_object}=    Parse XML    ${result}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #update his postal adress to set country code
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${EMAIL}</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>${countryCode}</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier><birthDate>1994-11-16T12:00:00</birthDate></individualInformations><individualProfil><emailOptin>T</emailOptin><languageCode>${spokenLanguage}</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${result} =    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    [Return]    ${gin}

Test Group
    [Arguments]    ${productId}    ${countryCode}    ${spokenLanguage}    ${nbComPrefExpected}    ${nbComPrefCreatedExpected}    ${nbComPrefKO}    ${expectMarketLanquage}
    #create User with country code and spoken langage parameter
    ${gin}=    Create User    ${countryCode}    ${spokenLanguage}
    #call permission WS
    ${soapBody}=    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${gin}</gin><actionCode>C</actionCode><contractRequest><contract><contractNumber>R${gin}</contractNumber><contractType>C</contractType><productType>${productId}</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><context>CREATE_COMPREF</context><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${result}=    CreateOrUpdateRoleContractService-v1    ${ENV}    ${soapBody}
    #Extract code number
    ${XML_object}=    Parse XML    ${result}
    ${count} =    Get Element Count    ${XML_object}    xpath=.//communicationpreferences
    Should Be Equal As Integers    ${count}    ${nbComPrefCreatedExpected}
    ${countWarning} =    Get Element Count    ${XML_object}    xpath=.//warning
    Should Be Equal    ${countWarning}    ${nbComPrefKO}
    #Provide v7 - retrieve Com Pref
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    Log    ${response}
    ${XML_object}=    Parse XML    ${response}
    ${countComPref} =    Get Element Count    ${XML_object}    xpath=.//communicationPreferences
    Should Be True    ${countComPref}>=${nbComPrefCreatedExpected}
    #Test MarketLanguage
    ${countPreferenceResponse} =    Get Element Count    ${XML_object}    xpath=.//communicationPreferencesResponse
    @{comPrefResponse} =    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        ${groupe}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationGroupeType
        ${type}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationType
        ${countMarket}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${countLanguage}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${market}    Run Keyword If    ${countMarket} > 0    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${language}    Run Keyword If    ${countLanguage} > 0    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${marketLanguage} =    Convert To String    ${market}${language}
        ${expectmarketLanguageValue} =    Get From Dictionary    ${expectMarketLanquage}    ${domain}${groupe}${type}
        Run Keyword If    '${domain}${groupe}${type}' != 'SNAF'    Should Be Equal    '${expectmarketLanguageValue}'    '${marketLanguage}'
        Run Keyword If    '${domain}${groupe}${type}' == 'SNAF'    Should Be True    ('${marketLanguage}' == 'FRFR' or '${marketLanguage}' == 'NLNL' or '${marketLanguage}' =='USEN' or '${marketLanguage}' == 'GBEN' \ or '${marketLanguage}' == 'DEDE' or '${marketLanguage}' == 'ESES' or '${marketLanguage}' == 'BRPT' \ or '${marketLanguage}' == 'ITIT' or '${marketLanguage}' == 'CAEN' \ or '${marketLanguage}' == 'JPJA' \ or '${marketLanguage}' == 'NOEN')
        Log    "---------------------------------------------------------------------------------------------------------------------------------------------------------"
    END
    Pass Execution    MarketLanguage are correct

Test Group by NAT
    [Arguments]    ${productId}    ${countryCode}    ${spokenLanguage}    ${expectMarketLanquage}    ${emailOptin}
    #enroll individual
    ${random} =    Generate Random String    5    [LETTERS][NUMBERS]
    ${EMAIL}=    GenerateRandomValidEmail
    ${soapBody}=    Convert To String    <afk0:EnrollMyAccountCustomerRequestElement><civility>MR</civility><firstName>${random}</firstName><lastName>${random}</lastName><emailIdentifier>${email}</emailIdentifier><password>${password}</password><languageCode>${spokenLanguage}</languageCode><website>AF</website><pointOfSale>AF</pointOfSale><optIn>false</optIn><directFBEnroll>false</directFBEnroll><signatureLight><site>QVI</site><signature>${SIGNATURE}</signature><ipAddress>test</ipAddress></signatureLight></afk0:EnrollMyAccountCustomerRequestElement>
    ${response} =    EnrollMyAccountCustomerService-v2    ${ENV}    ${soapBody}
    ${XML_object}=    Parse XML    ${response}
    ${gin}=    Get Element Text    ${XML_object}    xpath=.//gin
    #update his postal adress to set country code and emailOptin profil
    ${soapBody}=    Convert To String    <afk0:CreateUpdateIndividualRequestElement><requestor><channel>BAF</channel><context>FB_ENROLMENT</context><token>WSSiC2</token><applicationCode>ISI</applicationCode><site>QVI</site><signature>${SIGNATURE}</signature></requestor><emailRequest><email><mediumStatus>V</mediumStatus><mediumCode>D</mediumCode><email>${EMAIL}</email><emailOptin>T</emailOptin></email></emailRequest><telecomRequest><telecom><mediumCode>D</mediumCode><mediumStatus>V</mediumStatus><terminalType>M</terminalType><countryCode>+33</countryCode><phoneNumber>606060606</phoneNumber></telecom></telecomRequest><postalAddressRequest><postalAddressProperties><indicAdrNorm>true</indicAdrNorm><mediumCode>D</mediumCode><mediumStatus>I</mediumStatus></postalAddressProperties><postalAddressContent><numberAndStreet>NOT COLLECTED</numberAndStreet><additionalInformation>NOT COLLECTED</additionalInformation><district>NOT COLLECTED</district><city>NOT COLLECTED</city><zipCode>99999</zipCode><stateCode>AA</stateCode><countryCode>${countryCode}</countryCode></postalAddressContent><usageAddress><applicationCode>ISI</applicationCode><usageNumber>01</usageNumber><addressRoleCode>M</addressRoleCode></usageAddress></postalAddressRequest><individualRequest><individualInformations><identifier>${gin}</identifier><birthDate>1994-11-16T12:00:00</birthDate></individualInformations><individualProfil><emailOptin>${emailOptin}</emailOptin><languageCode>${spokenLanguage}</languageCode></individualProfil></individualRequest></afk0:CreateUpdateIndividualRequestElement>
    ${response} =    CreateUpdateIndividualService-v8    ${ENV}    ${soapBody}
    #call permission WS without context and create action - Expect com pref created by NAT
    ${soapBody}=    Convert To String    <afk0:CreateUpdateRoleContractRequestElement><gin>${gin}</gin><actionCode>C</actionCode><contractRequest><contractData><key>QUALIFYINGSEGMENTS</key><value>2</value></contractData><contractData><key>TIERLEVEL</key><value>A</value></contractData><contractData><key>MEMBERTYPE</key><value>T</value></contractData><contractData><key>MILESBALANCE</key><value>123</value></contractData><contractData><key>QUALIFYINGMILES</key><value>1234</value></contractData><contract><contractNumber>R${gin}</contractNumber><contractType>C</contractType><productType>${productId}</productType><contractStatus>A</contractStatus></contract></contractRequest><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:CreateUpdateRoleContractRequestElement>
    ${response}=    CreateOrUpdateRoleContractService-v1    ${ENV}    ${soapBody}
    Element Should Exist    ${response}    xpath=.//success
    Element Text Should Be    ${response}    true    xpath=.//success
    #Provide v7 - retrieve Com Pref
    ${body} =    Convert To String    <afk0:ProvideIndividualInformationRequestElement><identificationNumber>${gin}</identificationNumber><option>GIN</option><scopeToProvide>ALL</scopeToProvide><requestor><channel>B2C</channel><site>QVI</site><signature>${SIGNATURE}</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    ${response} =    ProvideIndividualDataService-v7    ${ENV}    ${body}
    ${XML_object}=    Parse XML    ${response}
    ${countComPref} =    Get Element Count    ${XML_object}    xpath=.//communicationPreferences
    Should Be Equal    '${countComPref}'    '9'    # or 9 or 8 ?
    #test com pref created
    @{comPrefResponse} =    Get Elements    ${XML_object}    xpath=.//communicationPreferencesResponse
    FOR    ${ComPref}    IN    @{comPrefResponse}
        ${domain}    Get Element Text    ${ComPref}    .//communicationPreferences/domain
        ${groupe}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationGroupeType
        ${type}    Get Element Text    ${ComPref}    .//communicationPreferences/communicationType
        ${optin}    Get Element Text    ${ComPref}    .//communicationPreferences/optIn
        ${countMarket}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${countLanguage}=    Get Element Count    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${market}    Run Keyword If    ${countMarket} > 0    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/market
        ${language}    Run Keyword If    ${countLanguage} > 0    Get Element Text    ${ComPref}    .//communicationPreferences/marketLanguage/language
        ${marketLanguage} =    Convert To String    ${market}${language}
        ${expectmarketLanguageValue} =    Get From Dictionary    ${expectMarketLanquage}    ${domain}${groupe}${type}${optin}
        Should Be Equal    '${expectmarketLanguageValue}'    '${marketLanguage}'
    END
    Pass Execution    Com Pref created by NAT are correct
