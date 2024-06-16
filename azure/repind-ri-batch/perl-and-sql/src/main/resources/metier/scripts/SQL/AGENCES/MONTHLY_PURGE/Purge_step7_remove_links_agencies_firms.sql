define date='&1'
define dirLogs='&2'

whenever sqlerror exit failure;

set echo off;
set serveroutput on;
set heading off;
set pagesize 0;
set line 130;

DECLARE

-- links agencies et firms
CURSOR cursorAgvRf IS SELECT GIN, GIN_FIRME FROM purge_agences WHERE GIN_FIRME IS NOT NULL AND APURGER='O' ORDER BY(GIN);
compteur INTEGER := 0;

BEGIN
  compteur := compteur + 1;
      
  --firms
  FOR AgvRf IN cursorAgvRf LOOP
    DELETE FROM GESTION_PM where SGIN_GEREE=AgvRf.GIN_FIRME and SGIN_GERANTE=AgvRf.GIN;
    UPDATE PM_ZONE set SORIGINE=NULL WHERE SGIN=AgvRf.GIN_FIRME;
    
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