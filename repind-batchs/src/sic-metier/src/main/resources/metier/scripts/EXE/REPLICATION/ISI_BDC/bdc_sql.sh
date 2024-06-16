#!/bin/bash

LINE_SIZE=599
DATA_PATH=${1?"directory path must be set"}
DATA_FILE_NAME=${2?"data filename must be set"}
LOG_FILE_NAME=${3?"log filename must be set"}
execute_sql(){
	local SQL=${1?"SQL query must be set"}
	echo "
	set serveroutput off;
	set heading off;
	set pagesize 0;
	set line $LINE_SIZE;
	set heading off;
	set feedback off;
	set tab off;
	SET LINESIZE $LINE_SIZE;
	$SQL
	exit;" > tmp_sql_query.sql
   sqlplus -S `secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC` @tmp_sql_query.sql
   rm tmp_sql_query.sql
}
count(){
	wc -l $DATA_PATH/$DATA_FILE_NAME | cut -d " " -f 1
}
execute_sql_to_file(){
	local SQL=${1?"SQL query must be set"}
	execute_sql "
	set termout OFF;
	spool $DATA_PATH/$DATA_FILE_NAME append;
	$SQL"
}

current_date=$(date '+%Y%m%d%H%M%S' )
echo "DEBUT REPLICATION: $current_date" | tee $DATA_PATH/$LOG_FILE_NAME
echo "Lecture de la date de debut de replication" | tee -a $DATA_PATH/$LOG_FILE_NAME
date_replication=$(execute_sql "SELECT TO_CHAR(DATE_BDC, 'YYYYMMDDHH24MISS') FROM DATES_REPLICATIONS;")
echo "date de debut de periode repliquee: $date_replication" | tee -a $DATA_PATH/$LOG_FILE_NAME
echo "date de fin de periode repliquee: $current_date" | tee -a $DATA_PATH/$LOG_FILE_NAME
printf "%-"$LINE_SIZE"s\n" "REPLI_REP$current_date" > $DATA_PATH/$DATA_FILE_NAME
printf "%-"$LINE_SIZE"s\n" "REPLI_REP$current_date" >> $DATA_PATH/$DATA_FILE_NAME

echo "cursorGroupeInfos: "$(date '+%s')
execute_sql_to_file "
SELECT
    RPAD('XI' || RPAD('BDC', 3, ' ') || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0') || LPAD(NVL(I.IVERSION, 0), 12, '0') ||
    RPAD(' ', 10, ' ') || RPAD(NVL(I.SNOM, ' '), 35, ' ') || RPAD(NVL(I.SSEXE, ' '), 1, ' ') ||
    RPAD(NVL(I.SIDENTIFIANT_PERSONNEL, ' '), 16, ' ') ||
    DECODE(I.DDATE_NAISSANCE, NULL, RPAD(' ', 8, ' '), TO_CHAR(I.DDATE_NAISSANCE, 'DDMMYYYY')) ||
    RPAD(NVL(I.SNATIONALITE, ' '), 2, ' ') || RPAD(NVL(I.SAUTRE_NATIONALITE, ' '), 2, ' ') ||
    RPAD(NVL(I.SSECOND_PRENOM, ' '), 25, ' ') || RPAD(NVL(I.SPRENOM, ' '), 25, ' ') || RPAD(NVL(I.SNON_FUSIONNABLE, ' '), 1, ' ') ||
    RPAD(NVL(I.SSTATUT_INDIVIDU, ' '), 1, ' ') || RPAD(NVL(I.SFRAUDEUR_CARTE_BANCAIRE, ' '), 1, ' ') ||
    RPAD(NVL(I.STIER_UTILISE_COMME_PIEGE, ' '), 1, ' ') || RPAD(NVL(I.SCIVILITE, ' '), 4, ' ') ||
    RPAD(NVL(I.SALIAS, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRENOM, ' '), 25, ' ') ||
    RPAD(NVL(I.SALIAS_NOM1, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRE1, ' '), 25, ' ') || RPAD(NVL(I.SALIAS_CIV1, ' '), 4, ' ') ||
    RPAD(NVL(I.SALIAS_NOM2, NVL(I.SALIAS_NOM1, ' ')), 35, ' ') || RPAD(NVL(I.SALIAS_PRE2, NVL(I.SALIAS_PRE1, ' ')), 25, ' ') || RPAD(NVL(I.SALIAS_CIV2, NVL(I.SALIAS_CIV1, ' ')), 4, ' ') ||
    RPAD(NVL(I.SCODE_TITRE, ' '), 3, ' ') || RPAD(NVL(C.SLIBELLE, ' '), 15, ' ') || RPAD(' ', 15, ' ') || 'M' || -- NVL(C.SLIBELLE_EN, ' ') a la place du dernier ' '
    RPAD(NVL(I.SSITE_MODIFICATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_MODIFICATION, ' '), 16, ' ') ||
    DECODE(I.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS')) ||
    LPAD(NVL(P.SRIN, ' '), 16, '0') || RPAD(NVL(P.SMAILING_AUTORISE, ' '), 1, ' ') || RPAD(NVL(P.SSOLVABILITE, ' '), 1, ' ') ||
    RPAD(NVL(P.SCODE_PROFESSIONNEL, ' '), 2, ' ') || RPAD(NVL(D.SLIBELLE_DOMAINE, ' '), 40, ' ') ||
    RPAD(NVL(P.SCODE_MARITALE, ' '), 1, ' ') || RPAD(NVL(P.SCODE_LANGUE, ' '), 2, ' ') || RPAD(NVL(P.SCODE_FONCTION, ' '), 2, ' ') ||
    RPAD(NVL(F.SLIBELLE_FONCTION, ' '), 30, ' ') || LPAD(NVL(P.INB_ENFANTS, 0), 2, '0') || RPAD(NVL(P.SSEGMENT, ' '), 10, ' ') ||
    RPAD(NVL(P.SETUDIANT, ' '), 1, ' ') || 'C' ||
    RPAD(NVL(I.SSITE_CREATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_CREATION, ' '), 16, ' ') ||
    DECODE(I.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_CREATION, 'DDMMYYYYHH24MISS')), 599, ' ') as RESULTAT
FROM
    INDIVIDUS I, REF_CODE_TITRE C, PROFILS P, DOM_PRO D, FCT_PRO F
WHERE
    I.SCODE_TITRE = C.SCODE (+) AND I.SGIN = P.SGIN (+) AND
    P.SCODE_PROFESSIONNEL = D.SCODE_PROFESSIONNEL (+) AND
    P.SCODE_FONCTION = F.SCODE_FONCTION (+) AND
    I.SGIN in
    (
        SELECT
            A.SGIN
        FROM
            USAGE_CLIENTS A,USAGE_CLIENTS B
        WHERE
            A.SGIN = B.SGIN AND
            A.SCODE = 'BDC' AND
            B.SCODE = 'ISI' AND
            ((A.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS') AND A.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS'))
             OR
             (B.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS') AND B.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS')))
    );"

echo "cursorIndividusInfos: "$(date '+%s')
execute_sql_to_file "
SELECT
    RPAD('MI' || RPAD('BDC', 3, ' ') || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0') || LPAD(NVL(I.IVERSION, 0), 12, '0') ||
    RPAD(' ', 10, ' ') || RPAD(NVL(I.SNOM, ' '), 35, ' ') || RPAD(NVL(I.SSEXE, ' '), 1, ' ') ||
    RPAD(NVL(I.SIDENTIFIANT_PERSONNEL, ' '), 16, ' ') || DECODE(I.DDATE_NAISSANCE, NULL, RPAD(' ', 8, ' '), TO_CHAR(I.DDATE_NAISSANCE, 'DDMMYYYY')) ||
    RPAD(NVL(I.SNATIONALITE, ' '), 2, ' ') || RPAD(NVL(I.SAUTRE_NATIONALITE, ' '), 2, ' ') ||
    RPAD(NVL(I.SSECOND_PRENOM, ' '), 25, ' ') || RPAD(NVL(I.SPRENOM, ' '), 25, ' ') || RPAD(NVL(I.SNON_FUSIONNABLE, ' '), 1, ' ') ||
    RPAD(NVL(I.SSTATUT_INDIVIDU, ' '), 1, ' ') || RPAD(NVL(I.SFRAUDEUR_CARTE_BANCAIRE, ' '), 1, ' ') ||
    RPAD(NVL(I.STIER_UTILISE_COMME_PIEGE, ' '), 1, ' ') || RPAD(NVL(I.SCIVILITE, ' '), 4, ' ') ||
    RPAD(NVL(I.SALIAS, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRENOM, ' '), 25, ' ') ||
    RPAD(NVL(I.SALIAS_NOM1, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRE1, ' '), 25, ' ') || RPAD(NVL(I.SALIAS_CIV1, ' '), 4, ' ') ||
    RPAD(NVL(I.SALIAS_NOM2, NVL(I.SALIAS_NOM1, ' ')), 35, ' ') || RPAD(NVL(I.SALIAS_PRE2, NVL(I.SALIAS_PRE1, ' ')), 25, ' ') || RPAD(NVL(I.SALIAS_CIV2, NVL(I.SALIAS_CIV1, ' ')), 4, ' ') ||
    RPAD(NVL(I.SCODE_TITRE, ' '), 3, ' ') || RPAD(NVL(C.SLIBELLE, ' '), 15, ' ') || RPAD(' ', 15, ' ') || 'M' || -- NVL(C.SLIBELLE_EN, ' ') a la place du dernier ' '
    RPAD(NVL(I.SSITE_MODIFICATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_MODIFICATION, ' '), 16, ' ') ||
    DECODE(I.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS')) ||
    LPAD(NVL(P.SRIN, ' '), 16, '0') || RPAD(NVL(P.SMAILING_AUTORISE, ' '), 1, ' ') || RPAD(NVL(P.SSOLVABILITE, ' '), 1, ' ') ||
    RPAD(NVL(P.SCODE_PROFESSIONNEL, ' '), 2, ' ') || RPAD(NVL(D.SLIBELLE_DOMAINE, ' '), 40, ' ') ||
    RPAD(NVL(P.SCODE_MARITALE, ' '), 1, ' ') || RPAD(NVL(P.SCODE_LANGUE, ' '), 2, ' ') || RPAD(NVL(P.SCODE_FONCTION, ' '), 2, ' ') ||
    RPAD(NVL(F.SLIBELLE_FONCTION, ' '), 30, ' ') || LPAD(NVL(P.INB_ENFANTS, 0), 2, '0') || RPAD(NVL(P.SSEGMENT, ' '), 10, ' ') ||
    RPAD(NVL(P.SETUDIANT, ' '), 1, ' ') || 'C' ||
    RPAD(NVL(I.SSITE_CREATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_CREATION, ' '), 16, ' ') ||
    DECODE(I.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_CREATION, 'DDMMYYYYHH24MISS')), 599, ' ') as RESULTAT
FROM
    INDIVIDUS I, REF_CODE_TITRE C, PROFILS P, DOM_PRO D, FCT_PRO F, USAGE_CLIENTS U
WHERE
    I.SCODE_TITRE = C.SCODE (+) AND I.SGIN = P.SGIN (+)
    AND P.SCODE_PROFESSIONNEL = D.SCODE_PROFESSIONNEL (+)
    AND P.SCODE_FONCTION = F.SCODE_FONCTION (+)
    AND I.SGIN = U.SGIN
    AND U.SCODE = 'BDC'
    AND I.DDATE_MODIFICATION IS NOT NULL
    AND I.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS')
    AND I.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS');"

echo "cursorSCLInfos: "$(date '+%s')
execute_sql_to_file "
SELECT
    RPAD('PI' || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0'), 599, ' ') as RESULTAT
FROM
    INDIVIDUS I
WHERE
    NOT EXISTS (SELECT SGIN FROM ROLE_CONTRATS WHERE ROLE_CONTRATS.SGIN = I.SGIN)
    AND
    (
        NOT EXISTS (SELECT I.SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN)
        OR
        (
            EXISTS(SELECT SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN AND SCODE='SCL')
            AND
            EXISTS (SELECT SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN GROUP BY SGIN HAVING COUNT(SCODE)=1)
        )
    )
    AND I.DDATE_MODIFICATION IS NOT NULL
    AND I.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS') AND I.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS');"
echo "cursorAdressesPostalesInfos: "$(date '+%s')

compteur_before=$(count)
execute_sql_to_file "
SELECT
    RPAD(DECODE(A.SSIGNATURE_MODIFICATION, NULL, 'CA', 'MA') ||
    LPAD(TRIM(NVL(A.SGIN, '0')), 12, '0') || RPAD('BDC', 3, ' ') ||
    DECODE(A.SSIGNATURE_MODIFICATION, NULL,
        'C' || RPAD(NVL(A.SSITE_CREATION, ' '), 10, ' ') || RPAD(NVL(A.SSIGNATURE_CREATION, ' '), 16, ' ') ||
            DECODE(A.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(A.DDATE_CREATION, 'DDMMYYYYHH24MISS')),
         'M' || RPAD(NVL(A.SSITE_MODIFICATION, ' '), 10, ' ') || RPAD(NVL(A.SSIGNATURE_MODIFICATION, ' '), 16, ' ') ||
            DECODE(A.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(A.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS'))) ||
    LPAD(TRIM(NVL(A.SAIN, '0')), 16, '0') || LPAD(NVL(A.IVERSION, 0), 12, '0') || LPAD(NVL(U.INUM, 0), 2, '0') ||
    RPAD(NVL(A.SCODE_MEDIUM, ' '), 1, ' ') || RPAD(NVL(A.SFORCAGE, ' '), 1, ' ') || RPAD(NVL(A.SSTATUT_MEDIUM, ' '), 1, ' ') ||
    RPAD(NVL(A.SRAISON_SOCIALE, ' '), 42, ' ') || RPAD(NVL(A.SVILLE, ' '), 32, ' ') || RPAD(NVL(A.SCOMPLEMENT_ADRESSE, ' '), 42, ' ') ||
    RPAD(NVL(A.SNO_ET_RUE, ' '), 42, ' ') || RPAD(NVL(A.SLOCALITE, ' '), 42, ' ') || RPAD(NVL(A.SCODE_POSTAL, ' '), 10, ' ') ||
    RPAD(NVL(A.SCODE_PAYS, ' '), 2, ' ') || RPAD(NVL(A.SCODE_PROVINCE, ' '), 2, ' ') || RPAD(NVL(U.SROLE1, ' '), 1, ' ') ||
    RPAD(NVL(U.SROLE2, ' '), 1, ' ') || RPAD(NVL(U.SROLE3, ' '), 1, ' ') || RPAD(NVL(U.SROLE4, ' '), 1, ' ') ||
    RPAD(NVL(U.SROLE5, ' '), 1, ' '), 599, ' ') as RESULTAT
FROM
     USAGE_MEDIUMS U, ADR_POST A
WHERE
    U.SAIN_ADR = A.SAIN
    AND U.SCODE_APPLICATION = 'BDC'
    AND A.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS') 
	AND A.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS') 
    AND A.SSTATUT_MEDIUM <> 'H'
    AND A.SSTATUT_MEDIUM <> 'X';"
compteur_after=$(count)
compteur=$(( compteur_after - compteur_before ))
	
echo "cursorTelecomsInfos: "$(date '+%s')
telecoms=$(execute_sql "
SELECT
    LPAD(TRIM(NVL(T.SGIN, '0')), 12, '0') as GIN, RPAD(NVL(T.SSTATUT_MEDIUM, ' '), 1, ' ') || RPAD(NVL(T.SCODE_MEDIUM, ' '), 1, ' ') || RPAD(NVL(T.STERMINAL, ' '), 1, ' ') ||
    RPAD(NVL(T.SINDICATIF, ' '), 4, ' ') || RPAD(NVL(T.SCODE_REGION, ' '), 4, ' ') || RPAD(NVL(T.SNUMERO, ' '), 15, ' ') ||
    DECODE(T.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(T.DDATE_CREATION, 'DDMMYYYYHH24MISS')) ||
    DECODE(T.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(T.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS')) as RESULTAT
FROM
    TELECOMS T
WHERE
    T.SGIN IN
    (
        SELECT
            T.SGIN
        FROM
            TELECOMS T
        WHERE
            (
                (T.DDATE_CREATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS') AND T.DDATE_CREATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS'))
                OR
                (
                    T.DDATE_MODIFICATION IS NOT NULL
                    AND T.DDATE_MODIFICATION >= TO_DATE('$date_replication', 'YYYYMMDDHH24MISS')
                    AND T.DDATE_MODIFICATION < TO_DATE('$current_date', 'YYYYMMDDHH24MISS')
                )
            )
            AND T.SSTATUT_MEDIUM <> 'H'
    )
    AND T.SSTATUT_MEDIUM <> 'H'
ORDER BY
    T.SGIN;")
	
lineCounter=0;
while read -r line; do
	[ -z "$line" ] && continue
	isGin=$((lineCounter % 2));
	((lineCounter++))
	if (( $isGin == 0 )); then
		gin=$(echo ${line:0:12})
		continue
	fi
	if [ "$oldgin" = "$gin" ]; then
		ligneTelecom="$ligneTelecom""$line"
	else
		if [ -n "$oldgin" ]; then
			printf "%-"$LINE_SIZE"s\n" "$ligneTelecom" >> $DATA_PATH/$DATA_FILE_NAME
		fi
		ligneTelecom="MT""$gin""$line"
		oldgin="$gin"
	fi
	((compteur++))
done <<< "$telecoms"

if [ -n "$oldgin" ]; then
	printf "%-"$LINE_SIZE"s\n" "$ligneTelecom" >> $DATA_PATH/$DATA_FILE_NAME
fi

end_date=$(date '+%Y%m%d%H%M%S' )
printf "%-"$LINE_SIZE"s\n" "REPLI_REP""$end_date""$compteur" >> $DATA_PATH/$DATA_FILE_NAME
execute_sql "UPDATE DATES_REPLICATIONS SET DATE_BDC = TO_DATE('$current_date', 'YYYYMMDDHH24MISS');"
echo "Date de replication mise a jour dans la base a $current_date" | tee -a $DATA_PATH/$LOG_FILE_NAME
echo "FIN REPLICATION: $end_date" | tee -a $DATA_PATH/$LOG_FILE_NAME