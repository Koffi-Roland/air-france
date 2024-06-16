package com.afklm.batch.propagate.email.config;

import com.afklm.batch.propagate.email.processor.AccountEmailProcessor;
import com.afklm.batch.propagate.email.writer.PropagateEmailOutputTasklet;
import com.airfrance.batch.common.config.ConfigBatch;
import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;
import com.airfrance.repind.entity.individu.AccountData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@ComponentScan(basePackages = {	"com.afklm.batch.propagate.email"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class PropagateEmailConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManagerRepind;

    @Autowired
    @Qualifier("dataSourceRepind")
    DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    public static final int CHUNK_NUMBER = 100;
    public static final int STEP_MAX_THREAD_NUMBER = 50;
    public static final int LIMIT_SELECTION = 600000;

    public PropagateEmailConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean("propagateEmailJob")
    public Job propagateEmailJob() {
        return jobBuilderFactory.get("propagateEmailJob")
                .start(propagateEmailStep())
                .next(writeUpdatedAccountsStep())
                .build();
    }

    @Bean
    public Step propagateEmailStep() {
        return stepBuilderFactory.get("propagateEmailStep")
                .<AccountData, AccountData>chunk(CHUNK_NUMBER)
                .reader(accountReader(null))
                .processor(accountEmailProcessor())
                .writer(accountWriter())
                .transactionManager(transactionManagerRepind)
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    @StepScope
    public ItemReader<AccountData> accountReader(@Value("#{jobParameters['offset']}") String offset) {
        log.info("The gins being processed in this launch have IDs ranging from "+ offset + " and " + (Integer.parseInt(offset)+LIMIT_SELECTION));
        JdbcPagingItemReader<AccountData> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(AccountData.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT  AD.*");
        queryProvider.setFromClause("FROM ACCOUNT_DATA AD");
        queryProvider.setWhereClause("WHERE ID BETWEEN :fromId AND :toId AND EMAIL_IDENTIFIER IS NULL AND (AD.FB_IDENTIFIER IS NOT NULL \n" +
                "OR AD.ACCOUNT_IDENTIFIER IS NOT NULL)");
        queryProvider.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("fromId", offset);
        parameterValues.put("toId", (Integer.parseInt(offset)+LIMIT_SELECTION));

        reader.setQueryProvider(queryProvider);
        reader.setParameterValues(parameterValues);

        return reader;
    }

    @Bean
    public ItemProcessor<AccountData, AccountData> accountEmailProcessor() {
        return new AccountEmailProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<AccountData> accountWriter() {
        JdbcBatchItemWriter<AccountData> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("UPDATE ACCOUNT_DATA SET EMAIL_IDENTIFIER = :emailIdentifier, " +
                "SSITE_MODIFICATION = :siteModification, SSIGNATURE_MODIFICATION = :signatureModification, " +
                "DDATE_MODIFICATION = :dateModification WHERE SGIN = :sgin");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.afterPropertiesSet();
        return writer;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_NUMBER);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }


    @Bean
    public Step writeUpdatedAccountsStep() {
        return stepBuilderFactory.get("writeUpdatedAccountsStep")
                .tasklet(propagateEmailOutputTasklet())
                .build();
    }

    @Bean
    public PropagateEmailOutputTasklet propagateEmailOutputTasklet() {
        return new PropagateEmailOutputTasklet(jdbcTemplate());
    }
}
