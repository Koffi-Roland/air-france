package com.afklm.batch.cleanupcreditcards.config;

import com.afklm.batch.cleanupcreditcards.BatchCleanupCreditCards;
import com.afklm.batch.cleanupcreditcards.processor.CleanupCreditCardsProcessor;
import com.afklm.batch.cleanupcreditcards.service.CleanupCreditCardsService;
import com.afklm.batch.cleanupcreditcards.writer.CleanupCreditCardsWriter;
import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;

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
import org.springframework.beans.factory.annotation.Value;
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

@ComponentScan(basePackages = {	"com.afklm.batch.cleanupcreditcards"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class CleanupCreditCardsConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("transactionManagerRepindPP")
    private PlatformTransactionManager transactionManagerRepindPP;

    @Autowired
    @Qualifier("dataSourceRepindPP")
    DataSource dataSource;
    private static final int CHUNK_SIZE = 100;
    public static final int STEP_MAX_THREAD_NUMBER = 50;
    public static final int LIMIT_SELECTION = 400000;

    @Bean
    public Job cleanupCreditCardsJob(Step cardProcessingStep) {
        return jobBuilderFactory.get("cleanupCreditCardsJob")
                .incrementer(new RunIdIncrementer())
                .start(cardProcessingStep)
                .build();
    }

    @Bean
    public Step cleanupCreditCardsStep(ItemProcessor<PaymentDetails, PaymentDetails> creditCardsProcessor,
                                   ItemWriter<PaymentDetails> creditCardsWriter) {
        return stepBuilderFactory.get("cleanupCreditCardsStep")
                .<PaymentDetails, PaymentDetails>chunk(CHUNK_SIZE)
                .reader(creditCardsReader(null))
                .processor(creditCardsProcessor)
                .writer(creditCardsWriter)
                .transactionManager(transactionManagerRepindPP)
                .taskExecutor(taskExecutor())
                .build();

    }

    @Bean
    @StepScope
    public ItemReader<PaymentDetails> creditCardsReader(@Value("#{jobParameters['offset']}") String offset) {
        log.info("The paymentPreferences being processed in this launch have rownum ranging from "+ offset + " and " + (Integer.parseInt(offset)+LIMIT_SELECTION));

        JdbcPagingItemReader<PaymentDetails> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(PaymentDetails.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("(SELECT PD.*, ROW_NUMBER() OVER (ORDER BY PAYMENTID DESC) AS RN FROM PAYMENTDETAILS PD) WHERE RN  BETWEEN :startPos AND :endPos");
        queryProvider.setSortKeys(Collections.singletonMap("PAYMENTID", Order.DESCENDING));

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("startPos", offset);
        parameterValues.put("endPos", (Integer.parseInt(offset)+LIMIT_SELECTION));

        reader.setQueryProvider(queryProvider);
        reader.setParameterValues(parameterValues);

        return reader;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-");
        return executor;
    }
    @Bean
    public BatchCleanupCreditCards batchCleanupCreditCards() {
        return new BatchCleanupCreditCards();
    }

    @Bean
    public CleanupCreditCardsService cleanupCreditCardsService() {
        return new CleanupCreditCardsService();
    }

    @Bean
    public PaymentPreferencesDS paymentPreferencesDS() {
        return new PaymentPreferencesDS();
    }

    @Bean
    public ItemProcessor<PaymentDetails, PaymentDetails> creditCardsProcessor() {
        return new CleanupCreditCardsProcessor();
    }

    @Bean
    public ItemWriter<PaymentDetails> creditCardsWriter() {
        return new CleanupCreditCardsWriter();
    }

}
