DECLARE
    language_code VARCHAR2(2);
    market VARCHAR2(3);
    com_pref_id NUMBER(12,0);
    nbadrpost      NUMBER(12, 0);

    -- Retrieve all individuals with Confirmed FB Contract and with no FB_ESS compref
    CURSOR c_individuals
    IS
        (select SGIN from (select SGIN from SIC2.ROLE_CONTRATS rc where SETAT='C' AND STYPE_CONTRAT='FP'
        intersect 
        select SGIN from SIC2.communication_preferences comm where NOT EXISTS (select 1 from SIC2.communication_preferences comm2 where DOMAIN='F' and com_group_type='N' and COM_TYPE='FB_ESS' and comm2.SGIN=comm.SGIN)));
BEGIN
    FOR indiv IN c_individuals
    LOOP
        language_code := null;
        market := null;

        dbms_output.put_line(indiv.sgin);
        -- Check if one COMPREF with market language exist for this GIN
        BEGIN
            select language_code, market into language_code, market from SIC2.communication_preferences comm 
                inner join SIC2.market_language mkt on comm.com_pref_id = mkt.com_pref_id 
                where DOMAIN='F' and comm.com_group_type='N' 
                    and SGIN=indiv.SGIN and comm.COM_TYPE IN ('FB_PROG', 'FB_PART', 'FB_NEWS', 'FB_CC', 'FB_EXCL') AND ROWNUM = 1;
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                dbms_output.put_line('No Compref');
        END;
        
        IF language_code IS NULL THEN
            BEGIN
                -- Check if one Postal Address with ISI M usage exist
                SELECT
                    COUNT(scode_pays)
                INTO nbadrpost
                FROM
                         sic2.usage_mediums usg
                    INNER JOIN sic2.adr_post adr ON usg.sain_adr = adr.sain
                WHERE
                        scode_application = 'ISI'
                    AND usg.srole1 = 'M'
                    AND adr.sstatut_medium = 'V'
                    AND sgin = indiv.sgin;

                IF nbadrpost = 1 THEN
                    SELECT
                        scode_pays
                    INTO language_code
                    FROM
                             sic2.usage_mediums usg
                        INNER JOIN sic2.adr_post adr ON usg.sain_adr = adr.sain
                    WHERE
                            scode_application = 'ISI'
                        AND usg.srole1 = 'M'
                        AND adr.sstatut_medium = 'V'
                        AND sgin = indiv.sgin;
                    dbms_output.put_line('ISI M adr founded');
                END IF;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    dbms_output.put_line('No ISI M adr');
            END;
        END IF;

        -- Create the FB_ESS compref with the market language previously found
        IF language_code IS NOT NULL THEN
            IF market IS NULL THEN
                market := language_code;
            END IF;
        
            INSERT INTO sic2.communication_preferences (
                com_pref_id,
                sgin,
                com_group_type,
                com_type,
                subscribe,
                media1,
                domain,
                creation_date,
                creation_signature,
                creation_site,
                date_optin,
                modification_date,
                modification_signature,
                modification_site
            ) VALUES (
                sic2.ISEQ_COMM_PREF.nextval,
                indiv.SGIN,
                'N',
                'FB_ESS',
                'Y',
                'E',
                'F',
                CURRENT_TIMESTAMP,
                'REPIND-2360',
                'QVI',
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP,
                'REPIND-2360',
                'QVI'
            ) returning com_pref_id into com_pref_id;

            INSERT INTO sic2.market_language (
                market_language_id,
                com_pref_id,
                media1,
                creation_date,
                creation_signature,
                creation_site,
                date_optin,
                language_code,
                market,
                optin,
                modification_date,
                modification_signature,
                modification_site
            ) VALUES (
                sic2.SEQ_MARKET_LANGUAGE.nextval,
                com_pref_id,
                'E',
                CURRENT_TIMESTAMP,
                'REPIND-2360',
                'QVI',
                CURRENT_TIMESTAMP,
                language_code,
                market,
                'Y',
                CURRENT_TIMESTAMP,
                'REPIND-2360',
                'QVI'
            );
        ELSE
            dbms_output.put_line('No possibility to guess ML');
        END IF; 

    END LOOP;
    commit;
END;
/

exit;
