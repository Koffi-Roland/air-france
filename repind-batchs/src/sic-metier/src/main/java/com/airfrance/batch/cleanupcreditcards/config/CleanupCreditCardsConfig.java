package com.airfrance.batch.cleanupcreditcards.config;

import com.afklm.repindpp.paymentpreference.entity.PaymentDetails;
import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.airfrance.batch.cleanupcreditcards.BatchCleanupCreditCards;
import com.airfrance.batch.cleanupcreditcards.processor.CleanupCreditCardsProcessor;
import com.airfrance.batch.cleanupcreditcards.service.CleanupCreditCardsService;
import com.airfrance.batch.cleanupcreditcards.writer.CleanupCreditCardsWriter;
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
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;

@ComponentScan(basePackages = {	"com.airfrance.batch.cleanupcreditcards"})
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
    public static final int STEP_THREAD_NUMBER = 40;
    public static final int STEP_MAX_THREAD_NUMBER = 100;

    @Bean
    public Job cleanupCreditCardsJob(@Qualifier("cleanupCreditCardsStep") Step cardProcessingStep) {
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
                .reader(creditCardsReader())
                .processor(creditCardsProcessor)
                .writer(creditCardsWriter)
                .transactionManager(transactionManagerRepindPP)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<PaymentDetails> creditCardsReader() {
        JdbcPagingItemReader<PaymentDetails> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(100000);
        reader.setRowMapper(new BeanPropertyRowMapper<>(PaymentDetails.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause(" * ");
        queryProvider.setFromClause(" PAYMENTDETAILS pd ");
        queryProvider.setWhereClause(" UPPER(pd.PAYMENTGROUP) IN ('CC', 'DC') ");
        queryProvider.setSortKeys(Collections.singletonMap("PAYMENTID", Order.DESCENDING));

        reader.setQueryProvider(queryProvider);

        return reader;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-cleanup-credit-cards");
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
