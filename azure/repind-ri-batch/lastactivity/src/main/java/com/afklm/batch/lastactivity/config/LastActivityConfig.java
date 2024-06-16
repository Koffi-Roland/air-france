package com.afklm.batch.lastactivity.config;

import com.afklm.batch.lastactivity.BatchLastActivity;
import com.afklm.batch.lastactivity.model.LastActivityModel;
import com.afklm.batch.lastactivity.processor.LastActivityProcessor;
import com.afklm.batch.lastactivity.writer.LastActivityWriter;
import com.airfrance.batch.common.config.ConfigBatch;
import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Last activity batch configuration
 */
@ComponentScan(basePackages = {"com.afklm.batch.lastactivity"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class LastActivityConfig {

    /**
     * The number of items in a chunk
     */
    private static final int CHUNK_SIZE = 100;
    /**
     * The number of items selected from database
     */
    public static final int LIMIT_SELECTION = 100000;
    /**
     *  Thread number
     */
    public static final int STEP_THREAD_NUMBER = 40;
    /**
     * Max Thread number
     */
    public static final int STEP_MAX_THREAD_NUMBER = 100;

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

    /**
     * Last activity batch job
     *
     * @param individualBatchStep                   table individual  batch step
     * @return Last activity job builder
     */
    @Bean
    public Job lastActivityJob(@Qualifier("individualBatchStep") Step individualBatchStep
                             ) {
        return jobBuilderFactory.get("lastActivityJob")
                .incrementer(new RunIdIncrementer())
                .start(individualBatchStep)
                .build();
    }

    /**
     * Individual batch step for last activity
     *
     * @param lastActivityIndividualReader individual data reader
     * @param lastActivityProcessor        last activity processor
     * @param lastActivityItemWriter       last activity writer
     * @return individual step
     */
    @Bean
    public Step individualBatchStep(ItemReader<LastActivityModel> lastActivityIndividualReader,
                                    ItemProcessor<LastActivityModel, LastActivityModel> lastActivityProcessor,
                                    ItemWriter<LastActivityModel> lastActivityItemWriter)
    {
        return stepBuilderFactory.get("individualBatchStep").<LastActivityModel, LastActivityModel>chunk(CHUNK_SIZE)
                .reader(lastActivityIndividualReader)
                .processor(lastActivityProcessor)
                .writer(lastActivityItemWriter)
                .transactionManager(platformTransactionManager)
                .taskExecutor(taskExecutor())
                .build();
    }

    /**
     * Individual reader
     *
     * @return Last activity individual reader
     */
    @Bean
    @StepScope
    public ItemReader<LastActivityModel> lastActivityIndividualReader()
    {
        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("endPos", LIMIT_SELECTION);
        JdbcPagingItemReader<LastActivityModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(LastActivityModel.class));
        queryProvider.setSelectClause("SELECT I.SGIN AS gin, I.DDATE_MODIFICATION as dateModification, I.SSIGNATURE_MODIFICATION as signatureModification, I.SSITE_MODIFICATION as siteModification, 'INDIVIDUS' AS sourceModification");
        queryProvider.setFromClause("FROM INDIVIDUS_ALL I");
        queryProvider.setWhereClause("I.DDATE_MODIFICATION IS NOT NULL AND I.SGIN NOT IN (SELECT L.SGIN FROM LAST_ACTIVITY L) AND ROWNUM < :endPos");
        queryProvider.setSortKeys(Collections.singletonMap("gin", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        reader.setParameterValues(parameterValues);
        return reader;
    }

    /**
     * Task executor (We want our threads to be managed by spring and specify number of max thread)
     *
     * @return Task executor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-last-activity-");
        return executor;
    }

    /**
     * Instantiates  and inject lastActivity bean in spring context to be managed (IoC)
     *
     * @return Last activity
     */
    @Bean
    public BatchLastActivity batchLastActivity()
    {
        return new BatchLastActivity();
    }


    /**
     * Instantiates  and inject lastActivityProcessor bean in spring context to be managed (IoC)
     *
     * @return Last activity processor
     */
    @Bean
    public ItemProcessor<LastActivityModel, LastActivityModel> lastActivityProcessor()
    {
        return new LastActivityProcessor();
    }

    /**
     * Instantiates  and inject lastActivityWriter bean in spring context to be managed (IoC)
     *
     * @return Last activity writer
     */
    @Bean
    public ItemWriter<LastActivityModel> lastActivityWriter()
    {
        return new LastActivityWriter();
    }
}
