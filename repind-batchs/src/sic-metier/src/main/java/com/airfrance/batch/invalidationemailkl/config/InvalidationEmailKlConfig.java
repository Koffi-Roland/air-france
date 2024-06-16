package com.airfrance.batch.invalidationemailkl.config;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.invalidationemailkl.BatchInvalidationEmailKL;
import com.airfrance.batch.invalidationemailkl.listener.CsvItemReaderListener;
import com.airfrance.batch.invalidationemailkl.model.InputInvalid;
import com.airfrance.batch.invalidationemailkl.processor.InvalidationEmailKlProcessor;
import com.airfrance.batch.invalidationemailkl.service.FileService;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKlService;
import com.airfrance.batch.invalidationemailkl.writer.InvalidationEmailKlWriter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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

import java.io.IOException;
import java.nio.file.Path;

@ComponentScan(basePackages = {	"com.airfrance.batch.invalidationemailkl"})
@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class InvalidationEmailKlConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Qualifier("transactionManagerRepind")
    private final PlatformTransactionManager transactionManagerRepind;
    private final CsvItemReaderListener csvItemReaderListener;
    private final FileService fileService;
    private static final String DELIMITER = ";" ;
    private static final int CHUNK_SIZE = 100;

    @Autowired
    public InvalidationEmailKlConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                     PlatformTransactionManager transactionManagerRepind, CsvItemReaderListener csvItemReaderListener,
                                     FileService fileService) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.fileService = fileService;
        this.transactionManagerRepind = transactionManagerRepind;
        this.csvItemReaderListener = csvItemReaderListener;
    }


    @Bean
    public Job invalidationEmailJob(@Qualifier("invalidationEmailStep") Step invalidationEmailStep){
        return jobBuilderFactory.get("invalidationEmailJob")
                .incrementer(new RunIdIncrementer())
                .start(invalidationEmailStep)
                .build();
    }

    @Bean
    public Step invalidationEmailStep(ItemReader<InputInvalid> csvItemReader,
                                     ItemProcessor<InputInvalid, InputInvalid> invalidationEmailKlProcessor){
        return stepBuilderFactory.get("invalidationEmailStep")
                .<InputInvalid, InputInvalid>chunk(CHUNK_SIZE)
                .reader(csvItemReader)
                .processor(invalidationEmailKlProcessor)
                .writer(invalidationEmailKlWriter())
                .faultTolerant()
                .skip(Throwable.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(csvItemReaderListener)
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<InputInvalid> csvItemReader(@Value("#{jobParameters['inputPath']}") String inputPath,
                                                          @Value("#{jobParameters['outputPath']}") String outputPath) throws IOException {

        FlatFileItemReader<InputInvalid> flatFileItemReader = new FlatFileItemReader<>();

        fileService.setInputPath(inputPath);
        fileService.setOutputPath(outputPath);
        Path fileInProcessing = fileService.getNewFileToProcess();
        fileService.setSourceInput(fileInProcessing);
        fileService.copyFileToBackup(fileInProcessing);
        log.info("[+] now processing file : {}", fileInProcessing);

        flatFileItemReader.setResource(new PathResource(fileInProcessing));

        DelimitedLineTokenizer tokenizer = getDelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<InputInvalid> beanWrapperFieldSetMapper = getInputRecordBeanWrapperFieldSetMapper();

        DefaultLineMapper<InputInvalid> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        flatFileItemReader.setLineMapper(defaultLineMapper);
        flatFileItemReader.setLinesToSkip(1); //to skip the header line

        return flatFileItemReader;
    }

    @NotNull
    private static BeanWrapperFieldSetMapper<InputInvalid> getInputRecordBeanWrapperFieldSetMapper() {
        BeanWrapperFieldSetMapper<InputInvalid> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(InputInvalid.class);
        return beanWrapperFieldSetMapper;
    }

    @NotNull
    private static DelimitedLineTokenizer getDelimitedLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("actionIndex", "comReturnCodeIndex", "contactTypeIndex", "contactIndex", "causeIndex");
        tokenizer.setDelimiter(DELIMITER);
        return tokenizer;
    }





    // BEAN DEFINITION
    @Bean
    public BatchInvalidationEmailKL batchInvalidationEmailKL()
    {
        return new BatchInvalidationEmailKL();
    }
    @Bean
    public InvalidationEmailKLSummaryService invalidationEmailKLSummaryService() {
        return new InvalidationEmailKLSummaryService();
    }
    @Bean
    public InvalidationEmailKlService invalidationEmailKlService() {
        return new InvalidationEmailKlService();
    }
    @Bean
    public ItemProcessor<InputInvalid, InputInvalid> invalidationEmailKlProcessor() {
        return new InvalidationEmailKlProcessor();
    }
    @Bean
    public ItemWriter<InputInvalid> invalidationEmailKlWriter() {
        return new InvalidationEmailKlWriter();
    }
}
