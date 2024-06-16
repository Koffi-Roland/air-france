package com.airfrance.batch.config;

import com.airfrance.batch.compref.migklsub.enums.ModeEnum;
import com.airfrance.batch.compref.migklsub.listener.InitStepExecutionListener;
import com.airfrance.batch.compref.migklsub.logger.MigrationKLSubscriptionsLogger;
import com.airfrance.batch.compref.migklsub.mapper.MigrationKLSubLineMapper;
import com.airfrance.batch.compref.migklsub.mapper.MigrationKLSubMapper;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static com.airfrance.batch.compref.migklsub.enums.ComPrefFieldEnum.*;
import static com.airfrance.batch.compref.migklsub.enums.EmailFieldEnum.EMAIL_ADDRESS;
import static com.airfrance.batch.compref.migklsub.enums.IndividuFieldEnum.*;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.*;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.ADHOC_SUBSCRIPTION_CODE;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.UPDATE_DATE;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.UPDATE_SOURCE;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.STATUS;
import static com.airfrance.batch.compref.migklsub.enums.RoleContratsFieldEnum.CIN;
import static com.airfrance.batch.compref.migklsub.enums.RoleContratsFieldEnum.GIN;

@Configuration
@EnableBatchProcessing
@Import(JpaSicConfigTest.class)
public class MigrationKLSubConfigTest {

    public final String CSV_FILE_NAME = "SFMC_Feed_20200720.csv";

    public final int STEP_MAX_THREAD_NUMBER = 10;
    public final int CHUNK_NUMBER = 100;
    public final int MAX_PARTITION_NUMBER = 50;
    public final int GRID_SIZE = 10;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public PlatformTransactionManager transactionManagerRepind;

    @Autowired
    public EntityManagerFactory entityManagerFactoryRepind;

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(){
        return new JobLauncherTestUtils();
    }

    @Bean
    public Map<String , FieldSet> mapContext(){
        return new HashMap<>();
    }

    @Bean
    @StepScope
    public Partitioner partitioner(@Value("#{jobParameters['fileName']}") String fileName, @Value("#{jobParameters['inputPath']}") String inputPath) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        Resource[] resources = null;
        try {
            resources = new PathMatchingResourcePatternResolver().getResources("classpath:"+inputPath+fileName.substring(0 , fileName.length() - 4 )+"-*.CSV");
        } catch (IOException e) {
            e.printStackTrace();
        }
        partitioner.setResources(resources);
        partitioner.partition(MAX_PARTITION_NUMBER);
        return partitioner;
    }

    @Bean
    @StepScope
    public MigrationKLSubscriptionsLogger migrationKLSubscriptionsLogger(@Value("#{jobParameters['fileName']}") String iCsvInput , @Value("#{jobParameters['outputPath']}") String iOutputPath){
        return new MigrationKLSubscriptionsLogger(iCsvInput , iOutputPath , tokens());
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Individu> individuItemReader(@Qualifier("lineMapper") LineMapper<Individu> iLineMapper , @Value("#{stepExecutionContext['fileName']}") String filename) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<Individu>()
                .name("individuItemReader")
                .delimited()
                .names(tokens())
                .resource(new UrlResource(filename))
                .lineMapper(iLineMapper)
                .build();
    }

    @Bean
    public String[] tokens(){
        return new String[]{"SF_Key", EMAIL_ADDRESS.getValue(), GIN.getValue(), CIN.getValue(), FIRST_NAME.getValue(), SURNAME.getValue(), CIVILITY.getValue(), BIRTHDATE.getValue(), COUNTRY_CODE.getValue(), LANGUAGE_CODE.getValue(), SUBSCRIPTION_TYPE.getValue(), STATUS.getValue(), SIGNUP_SOURCE.getValue(), SIGNUP_DATE.getValue(), UPDATE_SOURCE.getValue(), UPDATE_DATE.getValue(), ADHOC_SUBSCRIPTION_CODE.getValue()};
    }

    @Bean
    public LineMapper<Individu> lineMapper(@Qualifier("migrationKLSubMapper") MigrationKLSubMapper iMapper){
        DefaultLineMapper<Individu> lineMapper = new MigrationKLSubLineMapper();
        lineMapper.setLineTokenizer(tokenizer());
        lineMapper.setFieldSetMapper(iMapper);
        lineMapper.afterPropertiesSet();
        return lineMapper;
    }

    @Bean
    public MigrationKLSubMapper migrationKLSubMapper(){
        return new MigrationKLSubMapper(ModeEnum.INIT);
    }

    @Bean
    public DelimitedLineTokenizer tokenizer(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens());
        tokenizer.setDelimiter(",");
        return tokenizer;
    }

    @Bean
    public AsyncItemWriter<CommunicationPreferences> asyncJpaItemWriter(){
        AsyncItemWriter<CommunicationPreferences> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(jpaItemWriter());
        return asyncItemWriter;
    }

    @Bean
    public ItemWriter<CommunicationPreferences> jpaItemWriter() {
        return new JpaItemWriterBuilder<CommunicationPreferences>()
                .entityManagerFactory(entityManagerFactoryRepind)
                .build();
    }

    @Bean
    public AsyncItemProcessor<Individu,CommunicationPreferences> asyncCompositeIndividuComPrefItemProcessor(@Qualifier("compositeIndividuComPrefItemProcessor") ItemProcessor<Individu , CommunicationPreferences> iProcessor){
        AsyncItemProcessor<Individu, CommunicationPreferences> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(iProcessor);
        asyncItemProcessor.setTaskExecutor(asyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CHUNK_NUMBER);
        executor.setMaxPoolSize(CHUNK_NUMBER);
        executor.setQueueCapacity(CHUNK_NUMBER * STEP_MAX_THREAD_NUMBER);
        executor.afterPropertiesSet();
        executor.setThreadNamePrefix("MultiThreaded Processor -");
        return executor;
    }

    @Bean
    public TaskExecutor stepExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(MAX_PARTITION_NUMBER);
        executor.afterPropertiesSet();
        executor.setThreadNamePrefix("MultiThreaded Step -");
        return executor;
    }

    @Bean
    public Step initStep(@Qualifier("partitioner") Partitioner iPartitioner , @Qualifier("initSlaveStep") Step initSlaveStep){
        return stepBuilderFactory.get("initStep")
                .partitioner("stepPartitioner", iPartitioner)
                .step(initSlaveStep)
                .taskExecutor(stepExecutor())
                .gridSize(GRID_SIZE)
                .build();
    }

    @Bean
    public Step initSlaveStep(@Qualifier("individuItemReader") ItemReader<Individu> iReader, @Qualifier("asyncCompositeIndividuComPrefItemProcessor") AsyncItemProcessor<Individu , CommunicationPreferences> iProcessor , @Qualifier("initStepExecutionListener") InitStepExecutionListener initStepExecutionListener) {
        return stepBuilderFactory.get("initSlaveStep")
                .<Individu, Future<CommunicationPreferences>>chunk(CHUNK_NUMBER)
                .reader(iReader)
                .processor(iProcessor)
                .writer(asyncJpaItemWriter())
                .transactionManager(transactionManagerRepind)
                .listener(initStepExecutionListener)
                .build();
    }

    @Bean
    protected Job initBatchJob(@Qualifier("initStep") Step initStep) {
        return jobBuilderFactory.get("initBatchJob").start(initStep).build();
    }
}
