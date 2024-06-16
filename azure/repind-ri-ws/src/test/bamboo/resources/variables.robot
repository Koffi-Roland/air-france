*** Variables ***
${DB_CONNECT_STRING_DEV}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lh-dsic01-db.france.airfrance.fr)(PORT=1525))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIC)))
${DB_CONNECT_STRING_RC2}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=tst-siclrc2-db.france.airfrance.fr)(PORT=1530))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SICL)))
${DB_CONNECT_STRING_RCT}    sic2/sic2@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=lh-csic01-db.france.airfrance.fr)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=SIC)))
${SIGNATURE}      ROBOTFRAMEWORK    # Signature des tests
${GIN_NOT_FOUND}    888888888888
