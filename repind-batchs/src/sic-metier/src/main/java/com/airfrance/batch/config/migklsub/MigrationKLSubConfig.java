package com.airfrance.batch.config.migklsub;


import com.airfrance.batch.compref.migklsub.enums.ModeEnum;
import com.airfrance.batch.compref.migklsub.policy.DailySkipPolicy;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.compref.migklsub.BatchMigrationKLSubscriptions;
import com.airfrance.batch.compref.migklsub.listener.InitStepExecutionListener;
import com.airfrance.batch.compref.migklsub.logger.MigrationKLSubscriptionsLogger;
import com.airfrance.batch.compref.migklsub.mapper.MigrationKLSubLineMapper;
import com.airfrance.batch.compref.migklsub.mapper.MigrationKLSubMapper;
import com.airfrance.batch.compref.migklsub.service.MigrationKLSubService;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
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


import static com.airfrance.batch.compref.migklsub.enums.IndividuFieldEnum.*;
import static com.airfrance.batch.compref.migklsub.enums.EmailFieldEnum.EMAIL_ADDRESS;
import static com.airfrance.batch.compref.migklsub.enums.RoleContratsFieldEnum.CIN;
import static com.airfrance.batch.compref.migklsub.enums.MarketLanguageFieldEnum.*;
import static com.airfrance.batch.compref.migklsub.enums.ComPrefFieldEnum.STATUS;
import static com.airfrance.batch.compref.migklsub.enums.ComPrefFieldEnum.SUBSCRIPTION_TYPE;
import static com.airfrance.batch.compref.migklsub.enums.ComPrefFieldEnum.SIGNUP_SOURCE;

@ComponentScan(basePackages = {	"com.airfrance.batch.compref.migklsub" , "com.airfrance.batch.config.migklsub"})
@Configuration
@EnableConfigurationProperties
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class MigrationKLSubConfig {

    public final int STEP_MAX_THREAD_NUMBER = 10;
    public final int CHUNK_NUMBER = 40;
    public final int MAX_PARTITION_NUMBER = 50;
    public final int GRID_SIZE = 10;

    @Autowired
    public EntityManagerFactory entityManagerFactoryRepind;

    @Autowired
    public PlatformTransactionManager transactionManagerRepind;

    @Autowired
    public StepBuilderFactory stepBuilderFactorySic;

    @Autowired
    public JobBuilderFactory jobBuilderFactorySic;

    @Bean
    public Map<String , FieldSet> mapContext(){
        return new HashMap<>();
    }

    ///////////////////////////////////////////
    //InitStep
    @Bean
    public Step initStep(@Qualifier("partitioner") Partitioner iPartitioner , @Qualifier("initSlaveStep") Step initSlaveStep){
        return stepBuilderFactorySic.get("initStep")
                .partitioner("stepPartitioner", iPartitioner)
                .step(initSlaveStep)
                .taskExecutor(stepExecutor())
                .gridSize(GRID_SIZE)
                .build();
    }

    @Bean
    public Step initSlaveStep(@Qualifier("individuItemReader") ItemReader<Individu> iReader, @Qualifier("asyncCompositeIndividuComPrefItemProcessor") AsyncItemProcessor<Individu , CommunicationPreferences> iProcessor , @Qualifier("initStepExecutionListener") InitStepExecutionListener initStepExecutionListener) {
        return stepBuilderFactorySic.get("initSlaveStep")
                .<Individu, Future<CommunicationPreferences>>chunk(CHUNK_NUMBER)
                .reader(iReader)
                .processor(iProcessor)
                .writer(asyncJpaItemWriter())
                .transactionManager(transactionManagerRepind)
                .listener(initStepExecutionListener)
                .build();
    }
    ///////////////////////////////////////////

    ///////////////////////////////////////////
    //DailyStep
    @Bean
    public Step dailyStep(@Qualifier("partitioner") Partitioner iPartitioner , @Qualifier("dailySlaveStep") Step dailySlaveStep){
        return stepBuilderFactorySic.get("dailyStep")
                .partitioner("stepPartitioner", iPartitioner)
                .step(dailySlaveStep)
                .taskExecutor(stepExecutor())
                .gridSize(GRID_SIZE)
                .build();
    }

    @Bean
    public Step dailySlaveStep(@Qualifier("individuItemReader") ItemReader<Individu> iReader, @Qualifier("asyncIndividuItemProcessor") AsyncItemProcessor<Individu , Individu> iProcessor , @Qualifier("asyncDsItemWriter") AsyncItemWriter<Individu> asyncDsItemWriter , @Qualifier("initStepExecutionListener") InitStepExecutionListener initStepExecutionListener) {
        return stepBuilderFactorySic.get("dailySlaveStep")
                .<Individu, Future<Individu>>chunk(1)
                .reader(iReader)
                .processor(iProcessor)
                .writer(asyncDsItemWriter)
                .faultTolerant()
                .skipPolicy(new DailySkipPolicy())
                .transactionManager(transactionManagerRepind)
                .listener(initStepExecutionListener)
                .build();
    }

    ////////////////////////////////////////////////////////////////////

    @Bean
    public AsyncItemProcessor<Individu,CommunicationPreferences> asyncCompositeIndividuComPrefItemProcessor(@Qualifier("compositeIndividuComPrefItemProcessor") ItemProcessor<Individu , CommunicationPreferences> iProcessor){
        AsyncItemProcessor<Individu, CommunicationPreferences> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(iProcessor);
        asyncItemProcessor.setTaskExecutor(asyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemProcessor<Individu,Individu> asyncIndividuItemProcessor(@Qualifier("individuItemProcessor") ItemProcessor<Individu , Individu> iProcessor){
        AsyncItemProcessor<Individu, Individu> asyncItemProcessor = new AsyncItemProcessor<>();
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
    @StepScope
    public Partitioner partitioner(@Value("#{jobParameters['fileName']}") String fileName,@Value("#{jobParameters['inputPath']}") String inputPath) {
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        Resource[] resources = null;
        try {
            resources = new PathMatchingResourcePatternResolver().getResources("file:"+inputPath+fileName.substring(0 , fileName.length() - 4 )+"-*.CSV");
        } catch (IOException e) {
            e.printStackTrace();
        }
        partitioner.setResources(resources);
        partitioner.partition(MAX_PARTITION_NUMBER);
        return partitioner;
    }

    ////////////////////////////////////////////////////////////////////
    //Individu Jpa Writer
    @Bean
    public ItemWriter<CommunicationPreferences> jpaItemWriter() {
        return new JpaItemWriterBuilder<CommunicationPreferences>()
                .entityManagerFactory(entityManagerFactoryRepind)
                .build();
    }
    @Bean
    public AsyncItemWriter<CommunicationPreferences> asyncJpaItemWriter(){
        AsyncItemWriter<CommunicationPreferences> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(jpaItemWriter());
        return asyncItemWriter;
    }

    @Bean
    public AsyncItemWriter<Individu> asyncDsItemWriter(@Qualifier("individuItemWriter") ItemWriter individuItemWriter){
        AsyncItemWriter<Individu> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(individuItemWriter);
        return asyncItemWriter;
    }

    ////////////////////////////////////////////////////////////////////

    @Bean
    @StepScope
    public MigrationKLSubscriptionsLogger migrationKLSubscriptionsLogger(@Value("#{jobParameters['fileName']}") String iCsvInput , @Value("#{jobParameters['outputPath']}") String iOutputPath){
        return new MigrationKLSubscriptionsLogger(iCsvInput , iOutputPath , tokens());
    }

    ////////////////////////////////////////////////////////////////////
    //Item Reader
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
    public LineMapper<Individu> lineMapper(@Qualifier("migrationKLSubMapper") MigrationKLSubMapper iMapper){
        DefaultLineMapper<Individu> lineMapper = new MigrationKLSubLineMapper();
        lineMapper.setLineTokenizer(tokenizer());
        lineMapper.setFieldSetMapper(iMapper);
        lineMapper.afterPropertiesSet();
        return lineMapper;
    }

    @Bean
    @StepScope
    public MigrationKLSubMapper migrationKLSubMapper(@Value("#{jobParameters['mode']}") String iMode){
        return new MigrationKLSubMapper(ModeEnum.valueOf(iMode.toUpperCase()));
    }

    @Bean
    public DelimitedLineTokenizer tokenizer(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens());
        tokenizer.setDelimiter(",");
        return tokenizer;
    }

    @Bean(name="tokens")
    public String[] tokens(){
        return new String[]{"SF_Key", EMAIL_ADDRESS.getValue(), GIN.getValue(), CIN.getValue(), FIRST_NAME.getValue(), SURNAME.getValue(), CIVILITY.getValue(), BIRTHDATE.getValue(), COUNTRY_CODE.getValue(), LANGUAGE_CODE.getValue(), SUBSCRIPTION_TYPE.getValue(), STATUS.getValue(), SIGNUP_SOURCE.getValue(), SIGNUP_DATE.getValue(), UPDATE_SOURCE.getValue(), UPDATE_DATE.getValue(), ADHOC_SUBSCRIPTION_CODE.getValue()};
    }
    ////////////////////////////////////////////////////////////////////

    @Bean
    protected Job initBatchJob(@Qualifier("initStep")Step initStep) {
        return jobBuilderFactorySic.get("initBatchJob").start(initStep).build();
    }

    @Bean
    protected Job dailyBatchJob(@Qualifier("dailyStep")Step dailyStep) {
        return jobBuilderFactorySic.get("dailyBatchJob").start(dailyStep).build();
    }

    @Bean
    public MigrationKLSubService migrationKLSubService(){
        return new MigrationKLSubService();
    }

    @Bean
    public BatchMigrationKLSubscriptions batchMigrationKLSubscriptions(){
        return new BatchMigrationKLSubscriptions();
    }
}
