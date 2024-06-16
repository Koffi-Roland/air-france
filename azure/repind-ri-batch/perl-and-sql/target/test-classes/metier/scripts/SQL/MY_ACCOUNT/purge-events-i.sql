/*============================================================*/
/* Purge de la table TRIGGER_CHANGE						      */
/*============================================================*/

WHENEVER SQLERROR EXIT 2
SET DEFINE ON
SET TIMING ON
SET TIME ON
SET SERVEROUTPUT ON
SET LINESIZE 200
SET TRIMS ON
SET ECHO ON
SET TERM OFF
SET VERIFY OFF

DEFINE TABLE_TO_PURGE = "TRIGGER_CHANGE_INDIVIDUS"
DEFINE TMP_TABLE = "TMP_TRIGGER_CHANGE_INDIVIDUS"
DEFINE TMP_INSERT_TABLE = "TMP_TRIGGER_CHANGE_IND_INSERT"

SPOOL &1/purge-table-TRIGGER_CHANGE_INDIVIDUS.out

ALTER SESSION SET DB_FILE_MULTIBLOCK_READ_COUNT = 128;
ALTER SESSION SET SORT_AREA_SIZE = 120000000;
ALTER SESSION SET WORKAREA_SIZE_POLICY = MANUAL;
ALTER SESSION ENABLE PARALLEL DDL;
ALTER SESSION ENABLE PARALLEL DML;
ALTER SESSION SET "_SORT_MULTIBLOCK_READ_COUNT" = 128;

/*============================================================*/
/*============================================================*/
/* Suppression des enregistrements trait�s de la table        */
/* TRIGGER_CHANGE (avec CHANGE_STATUS = "P"					  */
/*============================================================*/
/*============================================================*/


/*============================================================*/
/* Trigger qui sauvegarde les records qui entrent     		  */
/*============================================================*/

CREATE OR REPLACE TRIGGER TRG_TRIGGER_CHANGE_INDIVIDUS 
BEFORE INSERT ON &TABLE_TO_PURGE 
FOR EACH ROW 
BEGIN 
INSERT INTO &TMP_INSERT_TABLE VALUES (:NEW.ID, :NEW.SGIN, :NEW.CHANGE_TABLE, 
:NEW.CHANGE_TABLE_ID, :NEW.CHANGE_TYPE, :NEW.CHANGE_TIME, :NEW.CHANGE_ERROR, 
:NEW.SSIGNATURE_MODIFICATION, :NEW.SSITE_MODIFICATION, :NEW.DDATE_CHANGE, 
:NEW.CHANGE_STATUS, :NEW.CHANGE_BEFORE, :NEW.CHANGE_AFTER); 
END; 
/

/*============================================================*/
/* Recopie dans tmp des records qui restent     			  */
/*============================================================*/
WHENEVER SQLERROR CONTINUE
DROP TABLE &TMP_TABLE;
WHENEVER SQLERROR EXIT 2

CREATE TABLE &TMP_TABLE AS SELECT * FROM &TABLE_TO_PURGE WHERE CHANGE_STATUS IS NULL OR CHANGE_STATUS = 'N';

/*============================================================*/
/* Truncate table initiale avant rechargement avec les deux   */
/* tables tmp 					     			  			  */
/*============================================================*/

TRUNCATE TABLE &TABLE_TO_PURGE;

DROP TRIGGER TRG_TRIGGER_CHANGE_INDIVIDUS;  

INSERT INTO &TABLE_TO_PURGE SELECT * FROM &TMP_TABLE; 
COMMIT;
 
INSERT INTO &TABLE_TO_PURGE SELECT * FROM &TMP_INSERT_TABLE t WHERE t.id NOT IN (SELECT id FROM &TABLE_TO_PURGE); 
TRUNCATE TABLE &TMP_INSERT_TABLE; 
COMMIT; 

/*============================================================*/
/* Fin de Suppression reouverture des MAJ par drop trigger    */ 
/*============================================================*/
DROP TABLE &TMP_TABLE;


SPOOL OFF
EXIT 0