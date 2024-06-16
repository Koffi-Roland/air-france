package com.airfrance.batch.purgemyaccount.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class ViewAccountPurgeTasklet implements Tasklet{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ViewAccountPurgeTasklet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // drop tables
        jdbcTemplate.execute("""
                BEGIN
                    FOR i IN (SELECT NULL FROM USER_OBJECTS WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'TMP_MYA_TO_PURGE')
                        LOOP
                            EXECUTE IMMEDIATE 'DROP TABLE TMP_MYA_TO_PURGE purge';
                    END LOOP;
                    FOR i IN (SELECT NULL FROM USER_OBJECTS WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'TMP_MYA_PURGE_AD')
                        LOOP
                            EXECUTE IMMEDIATE 'DROP TABLE TMP_MYA_PURGE_AD purge';
                    END LOOP;
                    FOR i IN (SELECT NULL FROM USER_OBJECTS WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'TMP_MYA_PURGE_RC')
                        LOOP
                            EXECUTE IMMEDIATE 'DROP TABLE TMP_MYA_PURGE_RC purge';
                    END LOOP;
                END;
            """);

        // Create Table TMP_MYA_PURGE_AD
        jdbcTemplate.execute("""
            BEGIN
                EXECUTE IMMEDIATE '
               CREATE TABLE SIC2.TMP_MYA_PURGE_AD AS
                select *  from SIC2.ACCOUNT_DATA ad
                where ad.FB_IDENTIFIER is null and ad.STATUS in (''V'', ''E'')
                and (
                    (ad.LAST_SOCIAL_NETWORK_LOGON_DATE is null and ad.LAST_CONNECTION_DATE < sysdate - 730  )
                    or   (  ad.LAST_CONNECTION_DATE is null and ad.LAST_SOCIAL_NETWORK_LOGON_DATE < sysdate - 730 )
                    or   (  ad.LAST_CONNECTION_DATE < sysdate - 730 and ad.LAST_SOCIAL_NETWORK_LOGON_DATE < sysdate - 730)
                 )
                order by ad.DDATE_CREATION asc';
            END;
        """);
        log.info("[+] Tmp table TMP_MYA_PURGE_AD is created.");

        // Create Table TMP_MYA_PURGE_RC to filter those not MYA PURS
        jdbcTemplate.execute("""
            BEGIN
                EXECUTE IMMEDIATE'
                create table SIC2.TMP_MYA_PURGE_RC as
                  SELECT * from SIC2.TMP_MYA_PURGE_AD ad
                  where not exists (select 1 from SIC2.role_contrats rc where ad.SGIN = rc.SGIN  and rc.STYPE_CONTRAT = ''FP'' )';
            END;
            """);
        log.info("[+] Tmp table TMP_MYA_PURGE_RC is created.");

        // Create Table TMP_MYA_TO_PURGE to filter if EMAIL shared
        jdbcTemplate.execute("""
            BEGIN
                EXECUTE IMMEDIATE'
               create table SIC2.TMP_MYA_TO_PURGE as
            SELECT * from SIC2.TMP_MYA_PURGE_RC ad
            where not exists ( select 1 from EMAILS em where ad.EMAIL_IDENTIFIER = em.SEMAIL AND ad.SGIN != em.SGIN)';
            END;
        """);
        log.info("[+] Tmp table TMP_MYA_TO_PURGE is created.");



        return RepeatStatus.FINISHED;
    }
}