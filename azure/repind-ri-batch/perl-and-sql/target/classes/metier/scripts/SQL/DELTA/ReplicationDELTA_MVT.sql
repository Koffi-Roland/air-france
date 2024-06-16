set echo off
set linesize 89 
set pages 0
set serveroutput off
set term off
set feedback on  

spool /app/REPIND/data/REPLICATION/DELTA/afdlelite_mvt

SELECT    RPAD(r.SCODE_COMPAGNIE,3,' ')||
                LPAD(LTRIM(r.SNUMERO_CONTRAT,'0'),12,' ')||
                RPAD(DECODE(r.STIER,'R',1,'B',0,2),1)||
                CHR('0')||
                CASE 
                    WHEN r.DDATE_CREATION >= (SELECT DDATE  from DATE_REPLIC where STYPE = 'DELTA') THEN 'A'
                    WHEN ( r.DDATE_MODIFICATION >= (SELECT DDATE  from DATE_REPLIC where STYPE = 'DELTA') AND
                                (R.SETAT NOT IN ('C','P') OR R.DFIN_VALIDITE < sysdate ))
                                 THEN 'D'          
                    ELSE 'M'
                END || 
           	CHR('0')||
                LPAD(LENGTH(i.SNOM)+LENGTH(i.SPRENOM)+1,2,' ')||
                RPAD(REGEXP_REPLACE(i.SNOM,'/',' ')||'/'||REGEXP_REPLACE(i.SPRENOM,'/',' '),68)
        FROM 
            ROLE_CONTRATS r, INDIVIDUS i 
        WHERE (r.SGIN = i.SGIN) 
        AND i.SSTATUT_INDIVIDU IN ('V','P')
        AND r.STIER IN ('M', 'C', 'R', 'B') 
        AND ((i.DDATE_MODIFICATION >= (SELECT DDATE  from DATE_REPLIC where STYPE = 'DELTA') 
              AND (SALIAS_NOM1 IS NOT NULL OR SALIAS_NOM2 IS NOT NULL)) 
             OR r.DDATE_MODIFICATION >= (SELECT DDATE  from DATE_REPLIC WHERE STYPE= 'DELTA'));

spool off;
exit;
/
