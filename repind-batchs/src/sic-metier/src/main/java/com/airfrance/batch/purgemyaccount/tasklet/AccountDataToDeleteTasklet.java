package com.airfrance.batch.purgemyaccount.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class AccountDataToDeleteTasklet implements Tasklet{
    private final JdbcTemplate jdbcTemplate;
    private final Integer limitSelection;

    public AccountDataToDeleteTasklet(JdbcTemplate jdbcTemplate, Integer limitSelection) {
        this.jdbcTemplate = jdbcTemplate;
        this.limitSelection = limitSelection;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // drop tables
        jdbcTemplate.execute("""
                BEGIN
                    FOR i IN (SELECT NULL FROM USER_OBJECTS WHERE OBJECT_TYPE = 'TABLE' AND OBJECT_NAME = 'DQ_ACCOUNT_DATA_TO_DELETE')
                        LOOP
                            EXECUTE IMMEDIATE 'DROP TABLE DQ_ACCOUNT_DATA_TO_DELETE purge';
                    END LOOP;
                END;
            """);

        // Create Table DQ_ACCOUNT_DATA_TO_DELETE
        String sql = """
            BEGIN
                EXECUTE IMMEDIATE
                '
                create table DQ_ACCOUNT_DATA_TO_DELETE as
                    select * from ( select ac.ID, ac.ACCOUNT_IDENTIFIER, ac.SGIN from  SIC2.ACCOUNT_DATA ac
                    where ac.STATUS in (''D'', ''E'') order by DDATE_CREATION asc )
                    where rownum <= """ + limitSelection + """
                ';
                END;
        """;

        jdbcTemplate.execute(sql);

        log.info("[+] Tmp table DQ_ACCOUNT_DATA_TO_DELETE is created.");


        return RepeatStatus.FINISHED;
    }
}
