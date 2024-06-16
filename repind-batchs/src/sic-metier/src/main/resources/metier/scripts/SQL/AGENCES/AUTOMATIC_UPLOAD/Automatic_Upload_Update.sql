define dateUpdate='&1 &2'
define myFile='&3'
define status='&4'

whenever sqlerror exit failure;

set echo on;
set serveroutput on;
set heading off;
set pagesize 0;
set line 130;


UPDATE UPLOAD SET BATCH_DATE=to_date('&dateUpdate','DD/MM/YYYY HH24:MI:SS'), STATUS_CODE='&status' where FILE_NAME='&myFile';
COMMIT;
   
exit;