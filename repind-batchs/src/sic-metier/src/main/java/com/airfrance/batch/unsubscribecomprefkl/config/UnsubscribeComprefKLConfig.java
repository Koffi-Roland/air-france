package com.airfrance.batch.unsubscribecomprefkl.config;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.unsubscribecomprefkl.BatchUnsubscribeComprefKL;
import com.airfrance.batch.unsubscribecomprefkl.listener.CsvItemReaderListener;
import com.airfrance.batch.unsubscribecomprefkl.model.UnsubscribeComprefInput;
import com.airfrance.batch.unsubscribecomprefkl.processor.UnsubscribeComprefKLProcessor;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLService;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.batch.unsubscribecomprefkl.writer.UnsubscribeComprefKLWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.file.Paths;

import static com.airfrance.batch.unsubscribecomprefkl.enums.InputFieldEnum.*;


@ComponentScan(basePackages = {"com.airfrance.batch.unsubscribecomprefkl"})
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class UnsubscribeComprefKLConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Qualifier("transactionManagerRepind")
    private final PlatformTransactionManager transactionManagerRepind;
    private final CsvItemReaderListener csvItemReaderListener;
    private static final String DELIMITER = ";" ;
    private static final int CHUNK_SIZE = 100;

    @Autowired
    public UnsubscribeComprefKLConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                      PlatformTransactionManager transactionManagerRepind, CsvItemReaderListener csvItemReaderListener) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.transactionManagerRepind = transactionManagerRepind;
        this.csvItemReaderListener = csvItemReaderListener;
    }


    @Bean
    public Job unsubscribeComprefKLJob(@Qualifier("unsubscribeComprefKLStep") Step unsubscribeComprefKLStep){
        return jobBuilderFactory.get("unsubscribeComprefKLJob")
                .incrementer(new RunIdIncrementer())
                .start(unsubscribeComprefKLStep)
                .build();
    }

    @Bean
    public Step unsubscribeComprefKLStep(@Qualifier("csvUnsubscribeInputReader") ItemReader<UnsubscribeComprefInput> csvUnsubscribeInputReader,
                                     ItemProcessor<UnsubscribeComprefInput, UnsubscribeComprefInput> unsubscribeComprefKLProcessor){
        return stepBuilderFactory.get("unsubscribeComprefKLStep")
                .<UnsubscribeComprefInput, UnsubscribeComprefInput>chunk(CHUNK_SIZE)
                .reader(csvUnsubscribeInputReader)
                .processor(unsubscribeComprefKLProcessor)
                .writer(unsubscribeComprefKLWriter())
                .faultTolerant()
                .skip(Throwable.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(csvItemReaderListener)
                .transactionManager(transactionManagerRepind)
                .build();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<UnsubscribeComprefInput> csvUnsubscribeInputReader(@Value("#{jobParameters['inputPath']}") String inputPath,
                                                                      @Value("#{jobParameters['fileName']}") String fileName){
            return new FlatFileItemReaderBuilder<UnsubscribeComprefInput>()
                    .name("csvUnsubscribeInputReader")
                    .resource(new PathResource(Paths.get(inputPath, fileName)))
                    .delimited()
                    .delimiter(DELIMITER)
                    .names(ACTION_INDEX.name(), DOMAIN_COMPREF_INDEX.name(), COMGROUPTYPE_COMPREF_INDEX.name(), COMTYPE_COMPREF_INDEX.name(), GIN_INDEX.name(), MARKET_COMPREF_INDEX.name(), LANGUAGE_COMPREF_INDEX.name(), CAUSE_INDEX.name())
                    .linesToSkip(1)
                    .fieldSetMapper(fieldSet -> UnsubscribeComprefInput.builder()
                            .actionIndex(fieldSet.readString(ACTION_INDEX.name()))
                            .domainComprefIndex(fieldSet.readString(DOMAIN_COMPREF_INDEX.name()))
                            .comGroupTypeComprefIndex(fieldSet.readString(COMGROUPTYPE_COMPREF_INDEX.name()))
                            .comTypeComprefIndex(fieldSet.readString(COMTYPE_COMPREF_INDEX.name()))
                            .ginIndex(fieldSet.readString(GIN_INDEX.name()))
                            .marketComprefIndex(fieldSet.readString(MARKET_COMPREF_INDEX.name()))
                            .languageComprefIndex(fieldSet.readString(LANGUAGE_COMPREF_INDEX.name()))
                            .causeIndex(fieldSet.readString(CAUSE_INDEX.name()))
                            .build())
                    .beanMapperStrict(true)
                    .build();
    }


    // BEAN DEFINITION
    @Bean
    public BatchUnsubscribeComprefKL batchUnsubscribeComprefKL()
    {
        return new BatchUnsubscribeComprefKL();
    }
    @Bean
    public UnsubscribeComprefKLSummaryService unsubscribeComprefKLSummaryService() {
        return new UnsubscribeComprefKLSummaryService();
    }
    @Bean
    public UnsubscribeComprefKLService unsubscribeComprefKLService() {
        return new UnsubscribeComprefKLService();
    }
    @Bean
    public ItemProcessor<UnsubscribeComprefInput, UnsubscribeComprefInput> unsubscribeComprefKLProcessor() {
        return new UnsubscribeComprefKLProcessor();
    }
    @Bean
    public ItemWriter<UnsubscribeComprefInput> unsubscribeComprefKLWriter() {
        return new UnsubscribeComprefKLWriter();
    }
}
