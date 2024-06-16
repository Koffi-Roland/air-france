define dateUpdate='&1 &2'

whenever sqlerror exit failure;

set echo on;
set serveroutput on;
set heading off;
set pagesize 0;
set line 130;


DELETE FROM UPLOAD where LOADING_DATE<to_date('&dateUpdate','DD/MM/YYYY HH24:MI:SS')-30;
COMMIT;
   
exit;