/*
 - Step 0 = initialization phase
     - if this sql script is called it will first drop the table DQ_MGE_SAME_NOMPRE_CANDIDATES if it exist.
     - will scan all RI DB and create a pool of NOMPRENOM candidates
     - will add a new column 'is_checked' to keep track of NOMPRENOM that we already detected their duplicates.
 */
BEGIN
    FOR i IN (SELECT NULL FROM USER_OBJECTS WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'DQ_MGE_SAME_NOMPRE_CANDIDATES')
        LOOP
            EXECUTE IMMEDIATE 'DROP TABLE DQ_MGE_SAME_NOMPRE_CANDIDATES';
    END LOOP;

    EXECUTE IMMEDIATE '  CREATE TABLE DQ_MGE_SAME_NOMPRE_CANDIDATES AS
                select SNOM || SPRENOM as NOMPRENOM, count(*) as nb
                from SIC2.INDIVIDUS_ALL
                where SNOM is not null and SPRENOM is not null and SSTATUT_INDIVIDU in (''V'', ''P'')
                group by SNOM || SPRENOM
                having count(*) > 1
                order by NOMPRENOM ';

    EXECUTE IMMEDIATE  'ALTER TABLE DQ_MGE_SAME_NOMPRE_CANDIDATES ADD is_checked NUMBER(1) DEFAULT 0 CHECK (is_checked IN (0, 1)) ';

END;