define date='&1'
define dirLogs='&2'

whenever sqlerror exit failure;

set echo off;
set serveroutput on;
set heading off;
set pagesize 0;
set line 130;

DECLARE

-- agencies mother
CURSOR cursorIataAgences IS SELECT pa.GIN,  pa.NUM_AGENCE_MERE, pa.NUM_AGENCE  FROM PURGE_AGENCES pa, AGENCE a 
                WHERE a.SNUMERO_IATA_MERE IS NOT NULL
				AND pa.APURGER='O' 
                AND a.SNUMERO_IATA_MERE=pa.NUM_AGENCE;

compteur INTEGER := 0;

BEGIN
  compteur := compteur + 1;
          
  --links agencies mother / daughter
  FOR IataAgences IN cursorIataAgences LOOP
    UPDATE AGENCE SET SNUMERO_IATA_MERE = NULL ;
    DBMS_OUTPUT.put_line(' Gin : ' || IataAgences.GIN || ' Iata : ' || IataAgences.NUM_AGENCE || ' Iata mother  ' ||  IataAgences.NUM_AGENCE_MERE );
  
    IF(compteur >= 100) THEN
      compteur :=0;
        DBMS_OUTPUT.put_line('COMMIT each 100 modifications');
      COMMIT;
    END IF;
	
  END LOOP;

        DBMS_OUTPUT.put_line('COMMIT');
        COMMIT;
 
END;
/

exit;