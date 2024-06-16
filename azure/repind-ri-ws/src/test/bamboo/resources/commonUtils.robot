*** Settings ***
Library           String

*** Keywords ***
GenerateRandomValidEmail
    [Documentation]    Generate a random valid email
    #Generate a random email valid
    ${random} =    Generate Random String    15    [LETTERS][NUMBERS]
    ${email} =    Convert To Lowercase    test-${random}@airfrance.fr
    [Return]    ${email}

GenerateInputFromCsv
    [Arguments]    ${CSVFILE}    ${LINENUMBER}    ${XMLDELIMITER}    ${COLUMNS}
    [Documentation]    Generate input xml from a csv file
    ${lineNumber}=    Convert To Integer    ${LINENUMBER}
    #Read CSV file
    @{dict}=    Read Csv File To Associative    ${CSV}/${CSVFILE}.csv    delimiter=;
    #Error if line > size
    ${size} =    Get Length    ${dict}
    Run Keyword And Return If    ${LINENUMBER} > ${size}    Fail    Line doesn't exist
    #Build xml
    ${inputXML} =    Convert To String    <afk0:${XMLDELIMITER}>
    : FOR    ${key}    IN    @{COLUMNS}
    \    ${element}=    Set Variable    &{dict[${lineNumber}]}[${key}]
    \    ${length}=    GetLength    ${element}
    \    ${inputXML} =    Run Keyword If    ${length} != 0 and ${key.find("<")} != 0    Catenate    ${inputXML}    <${key}>${element}</${key}>
    \    ...    ELSE IF    ${key.find("<")} == 0    Catenate    ${inputXML}    ${key}
    \    ...    ELSE    Catenate    ${inputXML}    /*/
    ${inputXML} =    Catenate    ${inputXML}    <afk0:${XMLDELIMITER}>
    Log    XML : ${inputXML.replace(" ", "").replace("/*/", "")}
    #<afk0:ProvideIndividualInformationRequestElement><identificationNumber>${resultGIN}</identificationNumber><option>GIN</option><requestor><channel>B2C</channel><site>QVI</site><signature>ROBOTFRAMEWORK</signature></requestor></afk0:ProvideIndividualInformationRequestElement>
    [Return]    ${inputXML.replace(" ", "").replace("/*/", "")}
