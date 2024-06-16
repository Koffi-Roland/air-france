package com.airfrance.batch.cleanTravelDoc.config;

import com.airfrance.batch.cleanTravelDoc.BatchCleanTravelDoc;
import com.airfrance.batch.cleanTravelDoc.model.CleanTravelDocModel;
import com.airfrance.batch.cleanTravelDoc.processor.CleanTravelDocProcessor;
import com.airfrance.batch.cleanTravelDoc.writer.CleanTravelDocWriter;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

@ComponentScan(basePackages = {"com.airfrance.batch.cleanTravelDoc"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class CleanTravelDocConfig {

    /**
     * Job builder factory -inject by spring
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    /**
     * Step builder factory -inject by spring
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    /**
     * Data source used for connection
     */
    @Autowired
    @Qualifier("dataSourceRepind")
    private DataSource dataSource;
    /**
     * Transaction manager - inject by spring
     */
    @Autowired
    @Qualifier("transactionManagerRepind")
    private PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 100;

    /**
     * clean travel doc batch job
     *
     * @param cleanTravelDocStep          batch clean expired preference
     * @param cleanPassportCharactersStep batch clean passport special characters
     * @return cleanTravelDoc job builder
     */
    @Bean
    public Job cleanTravelDocJob(
            @Qualifier("cleanTravelDocStep") Step cleanTravelDocStep,
            @Qualifier("cleanPassportCharactersStep") Step cleanPassportCharactersStep
    ) {
        return jobBuilderFactory.get("cleanTravelDocJob")
                .incrementer(new RunIdIncrementer())
                .start(cleanTravelDocStep)
                .next(cleanPassportCharactersStep)
                .build();
    }

    /**
     * Batch step for clean travel doc
     *
     * @param cleanTravelDocModelItemReader clean travel doc data reader
     * @param cleanTravelDocProcessor       clean travel doc processor
     * @param cleanTravelDocWriter          clean travel doc writer
     * @return individual step
     */
    @Bean
    public Step cleanTravelDocStep(ItemReader<CleanTravelDocModel> cleanTravelDocModelItemReader,
                                   ItemProcessor<CleanTravelDocModel, CleanTravelDocModel> cleanTravelDocProcessor,
                                   ItemWriter<CleanTravelDocModel> cleanTravelDocWriter) {
        return stepBuilderFactory.get("cleanTravelDocStep")
                .<CleanTravelDocModel, CleanTravelDocModel>chunk(CHUNK_SIZE)
                .reader(cleanTravelDocModelItemReader)
                .processor(cleanTravelDocProcessor)
                .writer(cleanTravelDocWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * Batch step for clean passport characters
     *
     * @param cleanPassportCharactersReader clean passport characters reader
     * @param cleanTravelDocProcessor       clean travel doc processor
     * @param cleanTravelDocWriter          clean travel doc writer
     * @return individual step
     */
    @Bean
    public Step cleanPassportCharactersStep(ItemReader<CleanTravelDocModel> cleanPassportCharactersReader,
                                            ItemProcessor<CleanTravelDocModel, CleanTravelDocModel> cleanTravelDocProcessor,
                                            ItemWriter<CleanTravelDocModel> cleanTravelDocWriter) {
        return stepBuilderFactory.get("cleanPassportCharactersStep")
                .<CleanTravelDocModel, CleanTravelDocModel>chunk(CHUNK_SIZE)
                .reader(cleanPassportCharactersReader)
                .processor(cleanTravelDocProcessor)
                .writer(cleanTravelDocWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    /**
     * Individual reader
     *
     * @return Clean travel doc reader
     */
    @Bean
    @StepScope
    public ItemReader<CleanTravelDocModel> cleanTravelDocModelItemReader() {
        log.info("Parsing the table PREFERENCE and PREFERENCE_DATA to get the PREFERENCES of type TDC who has a expiry date");

        JdbcPagingItemReader<CleanTravelDocModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(CleanTravelDocModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT P.PREFERENCE_ID as preferenceId");
        queryProvider.setFromClause("FROM PREFERENCE P, PREFERENCE_DATA PD");
        queryProvider.setWhereClause(
                "WHERE P.PREFERENCE_ID = PD.PREFERENCE_ID"
                        + " AND P.STYPE = 'TDC' AND PD.SKEY = 'expiryDate'"
                        + " AND TO_DATE(PD.SVALUE, 'dd/MM/yyyy') < SYSDATE");
        queryProvider.setSortKeys(Collections.singletonMap("preferenceId", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);

        log.info("Parsing cleanTravelDocModelItemReader finished successfully");
        return reader;
    }

    /**
     * Individual reader
     *
     * @return Clean passport character reader
     */
    @Bean
    @StepScope
    public ItemReader<CleanTravelDocModel> cleanPassportCharactersReader() {
        log.info("Parsing the table PREFERENCE and PREFERENCE_DATA to get the PREFERENCES of type TDC and subtype PA or CI that has non alpha numerical character in it");

        JdbcPagingItemReader<CleanTravelDocModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(CleanTravelDocModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT P.PREFERENCE_ID as preferenceId");
        queryProvider.setFromClause("FROM PREFERENCE P, PREFERENCE_DATA PD");
        queryProvider.setWhereClause(
                "WHERE P.PREFERENCE_ID = PD.PREFERENCE_ID"
                        + " AND P.STYPE = 'TDC'"
                        // GET PREFERENCE_ID of type TDC that has non alpha_numerical characters in their preference_data associated
                        + " AND PD.PREFERENCE_ID IN (SELECT P.PREFERENCE_ID FROM PREFERENCE P, PREFERENCE_DATA PD WHERE P.PREFERENCE_ID = PD.PREFERENCE_ID AND P.STYPE = 'TDC' AND PD.SKEY = 'number' AND NOT REGEXP_LIKE (PD.SVALUE,'^[0-9a-zA-Z]+$'))"
                        + " AND PD.SKEY = 'type'"
                        + " AND PD.SVALUE IN ('PA','CI')");
        queryProvider.setSortKeys(Collections.singletonMap("preferenceId", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);

        log.info("Parsing cleanPassportCharactersReader finished successfully");
        return reader;
    }

    /**
     * Instantiates  and inject batch cleanTravelDoc bean in spring context to be managed (IoC)
     *
     * @return Clean travel doc
     */
    @Bean
    public BatchCleanTravelDoc batchCleanTravelDoc() {
        return new BatchCleanTravelDoc();
    }


    /**
     * Instantiates  and inject cleanTravelDocProcessor bean in spring context to be managed (IoC)
     *
     * @return clean travel doc processor
     */
    @Bean
    public ItemProcessor<CleanTravelDocModel, CleanTravelDocModel> cleanTravelDocProcessor() {
        return new CleanTravelDocProcessor();
    }

    /**
     * Instantiates  and inject cleanTravelDocWriter bean in spring context to be managed (IoC)
     *
     * @return clean travel doc writer
     */
    @Bean
    public ItemWriter<CleanTravelDocModel> cleanTravelDocWriter() {
        return new CleanTravelDocWriter();
    }
}
