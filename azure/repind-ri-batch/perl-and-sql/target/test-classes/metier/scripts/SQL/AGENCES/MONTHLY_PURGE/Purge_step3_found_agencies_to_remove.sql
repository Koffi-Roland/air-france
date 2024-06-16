define datePurge='&1'
define oneGin='&2'
define numOfRow='&3'
define oneIata='&4'

whenever sqlerror exit failure;


DECLARE

-- get closed agencies older than 1 year
TYPE TcursorAgences IS REF CURSOR;
cursorAgences TcursorAgences;

-- get infos on firms attached to the angency
CURSOR cursorFirme(gin VARCHAR2) IS  select gp.SGIN_GEREE, z.SZC1, z.SZC2, z.SZC3, z.SZC4, z.sZC5, gp.LIEN_ZC_FIRME, pm.DDATE_FERMETURE
    from gestion_pm gp, pm_zone pm, zone_decoup zd, zone_comm z
    where gp.SGIN_GERANTE=gin
    and TRUNC(gp.DDATE_DEB_LIEN) <= TRUNC( TO_DATE('&datePurge','ddmmyyyy') ) 
    and ( gp.DDATE_FIN_LIEN is null or TRUNC(gp.DDATE_FIN_LIEN) >= TRUNC( TO_DATE('&datePurge','ddmmyyyy') ) )
    and pm.SGIN=gp.SGIN_GEREE and zd.IGIN=pm.IGIN_ZONE and z.IGIN=zd.IGIN and zd.STYPE='ZC';    


-- get infos on valid contracts attached to the agency
CURSOR cursorRoleFirme(gin VARCHAR2) IS SELECT DISTINCT(br.SNUMERO_CONTRAT), rf.STYPE, rf.SSOUS_TYPE, rf.DDEBUT_VALIDITE, rf.DFIN_VALIDITE 
                       from BUSINESS_ROLE br, ROLE_FIRME rf
                       where br.ICLE_ROLE=rf.ICLE_ROLE 
                       and br.SGIN_PM=gin                        
                        and TRUNC(rf.DDEBUT_VALIDITE) <= TRUNC(TO_DATE('&datePurge','ddmmyyyy')) 
                       and ( rf.DFIN_VALIDITE is null or TRUNC(rf.DFIN_VALIDITE) >= TRUNC(TO_DATE('&datePurge','ddmmyyyy')) );

-- get infos in role_agence to known if the contract is a sequoia and its validity date
CURSOR cursorRoleCtSequoia(gin VARCHAR2) IS SELECT DISTINCT(br.SNUMERO_CONTRAT), ra.ICLE_ROLE, ra.STYPE, ra.SSOUS_TYPE, ra.DDEBUT_VALIDITE, ra.DFIN_VALIDITE 
                       from BUSINESS_ROLE br, ROLE_AGENCE ra
                       where br.ICLE_ROLE=ra.ICLE_ROLE 
                       and br.SGIN_PM=gin                        
                        and br.stype = 'S'
                       and TRUNC(ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -36 )) < TRUNC(ra.dfin_validite)
                       and TRUNC(TO_DATE('&datePurge','ddmmyyyy')) >= TRUNC(ra.dfin_validite);

ICLE_SEQ NUMBER(10);
GIN VARCHAR2(12);
NUMERO VARCHAR2(20);
ZoneC1 VARCHAR(3);
NOM VARCHAR(45);
ZC VARCHAR2(18);
MONTANT NUMBER(12);
TYPEMONNAIE VARCHAR2(3);

compteur INTEGER := 0;
i INTEGER := 0;

Agency_GIN AGENCE.SGIN%TYPE;
Agency_NUM_IATA_MERE AGENCE.SNUMERO_IATA_MERE%TYPE;
Agency_STATUS PERS_MORALE.SSTATUT%TYPE;
Agency_TYPE AGENCE.STYPE%TYPE;
Agency_NOM PERS_MORALE.SNOM%TYPE;
Agency_DATE_DEB AGENCE.DDATE_DEB%TYPE;
Agency_DATE_RADIATION AGENCE.DDATE_RADIATION%TYPE;
Agency_DATE_FIN AGENCE.DDATE_FIN%TYPE;

BEGIN
IF (&oneGin != -1) THEN
    OPEN cursorAgences FOR SELECT a.SGIN, p.SSTATUT, a.STYPE, p.SNOM,a.DDATE_DEB, a.DDATE_RADIATION, a.DDATE_FIN, a.SNUMERO_IATA_MERE
            from AGENCE a, PERS_MORALE p                     
            where a.SGIN=p.SGIN and a.SGIN='&oneGin'
            and (( p.SSTATUT='X' and TRUNC(ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -12 )) >= TRUNC(a.DDATE_FIN)));
ELSIF (&oneIata != -1) THEN
    OPEN cursorAgences FOR SELECT a.SGIN, p.SSTATUT, a.STYPE, p.SNOM,a.DDATE_DEB, a.DDATE_RADIATION, a.DDATE_FIN, a.SNUMERO_IATA_MERE
            from AGENCE a, PERS_MORALE p, NUMERO_IDENT ni                  
            where a.SGIN=p.SGIN  and ni.SGIN=a.SGIN and ni.SNUMERO='&oneIata'
            and (( p.SSTATUT='X' and TRUNC(ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -12 )) >= TRUNC(a.DDATE_FIN)));
ELSIF (&numOfRow != -1) THEN
    OPEN cursorAgences FOR SELECT a.SGIN, p.SSTATUT, a.STYPE, p.SNOM,a.DDATE_DEB, a.DDATE_RADIATION, a.DDATE_FIN, a.SNUMERO_IATA_MERE
            from AGENCE a, PERS_MORALE p                     
            where a.SGIN=p.SGIN and rowNum<'&numOfRow'
            and (( p.SSTATUT='X' and TRUNC(ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -12 )) >= TRUNC(a.DDATE_FIN)));
ELSE 
    OPEN cursorAgences FOR SELECT a.SGIN, p.SSTATUT, a.STYPE, p.SNOM,a.DDATE_DEB, a.DDATE_RADIATION, a.DDATE_FIN, a.SNUMERO_IATA_MERE
            from AGENCE a, PERS_MORALE p                     
            where a.SGIN=p.SGIN
            and (( p.SSTATUT='X' and TRUNC(ADD_MONTHS(TO_DATE('&datePurge','ddmmyyyy'), -12 )) >= TRUNC(a.DDATE_FIN)));
END IF;

LOOP
    FETCH cursorAgences into Agency_GIN, Agency_STATUS, Agency_TYPE, Agency_NOM, Agency_DATE_DEB, Agency_DATE_RADIATION, Agency_DATE_FIN, Agency_NUM_IATA_MERE  ;
  
    -- sequence number
	BEGIN
		SELECT MAX(ICLE) INTO ICLE_SEQ FROM PURGE_AGENCES;
		EXCEPTION      
		  WHEN TOO_MANY_ROWS THEN
			DBMS_OUTPUT.put_line('TOO MANY ROWS 0');
	END;
			
	IF( ICLE_SEQ IS NULL ) THEN
		ICLE_SEQ := 0;
	END IF;
    
    i := 0;
    compteur := compteur + 1;
    
    DBMS_OUTPUT.put_line('Do agency ' || Agency_GIN);
    
    -- agency
    INSERT INTO PURGE_AGENCES(ICLE, GIN, NUM_AGENCE_MERE,STATUT, TYPE_AGENCE, RAISON_SOCIALE, DATE_OUVERTURE, DATE_RADIATION, DATE_FERMETURE)
    VALUES (ICLE_SEQ+1, Agency_GIN, Agency_NUM_IATA_MERE, Agency_STATUS, Agency_TYPE, Agency_NOM, Agency_DATE_DEB, Agency_DATE_RADIATION, Agency_DATE_FIN);
  
    -- id number  
    BEGIN
      SELECT DISTINCT(SNUMERO) INTO NUMERO from NUMERO_IDENT where SGIN=Agency_GIN
      and ikey in ( SELECT MAX(IKEY) FROM NUMERO_IDENT where SGIN=Agency_GIN);          
    EXCEPTION      
      WHEN NO_DATA_FOUND THEN
        NUMERO := '';    
      WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.put_line('TOO MANY ROWS 1');
    END;

    -- active ZONE_COMM
    BEGIN
      SELECT DISTINCT(zc.SZC1) INTO ZoneC1 from ZONE_COMM zc, PM_ZONE pz
      where zc.IGIN=pz.IGIN_ZONE and pz.SGIN=Agency_GIN and ( pz.DDATE_FERMETURE is null or pz.DDATE_FERMETURE >= TRUNC(SYSDATE));      
    EXCEPTION     
      WHEN NO_DATA_FOUND THEN
        ZoneC1 := '';
      WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.put_line('TOO MANY ROWS 2');
    END;

    -- SYNONYME
    BEGIN
      SELECT SNOM INTO NOM from SYNONYME where SGIN=Agency_GIN and SSTATUT='V' and STYPE='U';
    EXCEPTION      
      WHEN NO_DATA_FOUND THEN
        NOM := '';
      WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.put_line('TOO MANY ROWS 3');
    END;

    -- CHIFFRE
    BEGIN
      SELECT NMONTANT, SMONNAIE INTO MONTANT, TYPEMONNAIE from CHIFFRE where SGIN=Agency_GIN and STYPE='CA';
    EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        MONTANT := '';
        TYPEMONNAIE := '';
      WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.put_line('TOO MANY ROWS 4');
    END;

    -- UPDATE
    UPDATE PURGE_AGENCES set NUM_AGENCE=NUMERO, ZC1=ZoneC1, NOM_USUEL=NOM, RESULTAT=MONTANT, MONNAIE=TYPEMONNAIE where ICLE=ICLE_SEQ+1; 

    -- FIRME : gin_firme, zc_firme, lien_zc_firme
	FOR Firme IN cursorFirme(Agency_GIN) LOOP
		ZC := (Firme.SZC1||Firme.SZC2||Firme.SZC3||Firme.SZC4||Firme.SZC5);
		IF ( i >= 1 ) THEN
			SELECT MAX(ICLE) INTO ICLE_SEQ FROM PURGE_AGENCES;		
			INSERT INTO PURGE_AGENCES(ICLE, GIN, STATUT, ZC1, TYPE_AGENCE, GIN_FIRME, ZC_FIRME, LIEN_ZC_FIRME, DATE_FIN_FIRME )
			VALUES ( ICLE_SEQ+1, Agency_GIN, Agency_STATUS, ZoneC1, Agency_TYPE, Firme.SGIN_GEREE, ZC, Firme.LIEN_ZC_FIRME, Firme.DDATE_FERMETURE );
		ELSE
			UPDATE PURGE_AGENCES set GIN_FIRME=Firme.SGIN_GEREE, ZC_FIRME=ZC, LIEN_ZC_FIRME=Firme.LIEN_ZC_FIRME, DATE_FIN_FIRME=Firme.DDATE_FERMETURE where ICLE=ICLE_SEQ+1;
		END IF;
		i := i+1;
    END LOOP;    

    -- ROLE_FIRME : contract
	BEGIN
		i := 0;
		FOR RoleFirme IN cursorRoleFirme(Agency_GIN) LOOP
			IF ( i >= 1 ) THEN
				SELECT MAX(ICLE) INTO ICLE_SEQ FROM PURGE_AGENCES;
				INSERT INTO PURGE_AGENCES(ICLE, GIN, STATUT, ZC1, TYPE_AGENCE, TYPE_CONTRAT, SOUS_TYPE_CONTRAT, DEBUT_VALIDITE, FIN_VALIDITE )
				VALUES ( ICLE_SEQ+1, Agency_GIN, Agency_STATUS, ZoneC1, Agency_TYPE, RoleFirme.STYPE, RoleFirme.SSOUS_TYPE, RoleFirme.DDEBUT_VALIDITE, RoleFirme.DFIN_VALIDITE );   
			ELSE
				UPDATE PURGE_AGENCES set TYPE_CONTRAT=RoleFirme.STYPE, SOUS_TYPE_CONTRAT=RoleFirme.SSOUS_TYPE, 
					DEBUT_VALIDITE=RoleFirme.DDEBUT_VALIDITE, FIN_VALIDITE=RoleFirme.DFIN_VALIDITE
					where ICLE=ICLE_SEQ+1;
			END IF;
			i := i + 1;
		END LOOP;
		EXCEPTION 
			WHEN TOO_MANY_ROWS THEN
				DBMS_OUTPUT.put_line('TOO MANY ROWS 6');
	END;
	
    -- ROLE_AGENCE : if the is an sequoia contract, we tag the agentcy to purge only if the validity date is older than 3 years.
    FOR RoleSequoia IN cursorRoleCtSequoia(Agency_GIN) LOOP
      UPDATE PURGE_AGENCES set APURGER='N' where ICLE=ICLE_SEQ+1;
    END LOOP;

    IF(compteur >= 100) THEN
      compteur :=0;
        DBMS_OUTPUT.put_line('COMMIT each 100 modifications');
      COMMIT;
    END IF;

  EXIT WHEN cursorAgences%NOTFOUND;
  END LOOP;
    
  -- update ZC_FIRME_ACTIVE
  UPDATE PURGE_AGENCES set ZC_FIRME_ACTIF='O' where GIN_FIRME is not null and ( DATE_FIN_FIRME is null OR TRUNC(DATE_FIN_FIRME) >= TRUNC( TO_DATE('&datePurge','ddmmyyyy') ));

    COMMIT;

END;
/

exit;