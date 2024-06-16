package com.airfrance.batch.adrInvalidBarecode.config;

import com.airfrance.batch.adrInvalidBarecode.BatchAdrInvalidBarecode;
import com.airfrance.batch.adrInvalidBarecode.model.InputRecord;
import com.airfrance.batch.adrInvalidBarecode.model.OutputRecord;
import com.airfrance.batch.adrInvalidBarecode.processor.AdrInvalidBarecodeProcessor;
import com.airfrance.batch.adrInvalidBarecode.service.AdrInvalidBarecodeService;
import com.airfrance.batch.adrInvalidBarecode.writer.JdbcWriter;
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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.Arrays;

import static com.airfrance.batch.adrInvalidBarecode.enums.IOFieldEnum.*;

@ComponentScan(basePackages = {	"com.airfrance.batch.adrInvalidBarecode"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class AdrInvalidBarecodeConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("transactionManagerRepind")
    private PlatformTransactionManager transactionManagerRepind;

    @Autowired
    private FlatFileItemWriter<OutputRecord> csvItemWriter;
    private static final String DELIMITER = ";" ;

    private static final int CHUNK_SIZE = 50;

    @Autowired
    private JdbcWriter jdbcWriter;

    @Autowired
    private FlatFileItemReader<InputRecord> csvItemReader;
    @Bean
    public Job adrInvalidBarecodeJob(@Qualifier("adrInvalidBarecodeStep") Step adrInvalidBarecodeStep) {

        return jobBuilderFactory.get("adrInvalidBarecodeJob")
                .incrementer(new RunIdIncrementer())
                .start(adrInvalidBarecodeStep)
                .build();
    }
    @Bean
    public Step adrInvalidBarecodeStep(FlatFileItemReader<InputRecord> csvItemReader,
                                        ItemProcessor<InputRecord, OutputRecord> adrInvalidBarecodeProcessor
                                       ) {
        return stepBuilderFactory.get("adrInvalidBarecodeStep")
                .<InputRecord, OutputRecord>chunk(CHUNK_SIZE)
                .reader(csvItemReader)
                .processor(adrInvalidBarecodeProcessor)
                .writer(compositeItemWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<InputRecord> csvItemReader(@Value("#{jobParameters['inputPath']}") String inputPath,
                                                         @Value("#{jobParameters['fileName']}")String fileName) {

        FlatFileItemReader<InputRecord> flatFileItemReader = new FlatFileItemReader<>();

        File file = new File(inputPath, fileName);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        flatFileItemReader.setResource(fileSystemResource);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(NUMERO_CONTRAT.getValue(), SAIN.getValue(), DATE_MODIFICATION.getValue());
        tokenizer.setDelimiter(DELIMITER);

        BeanWrapperFieldSetMapper<InputRecord> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(InputRecord.class);

        DefaultLineMapper<InputRecord> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(tokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<OutputRecord> compositeItemWriter(){
        CompositeItemWriter<OutputRecord> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(jdbcWriter, csvItemWriter));
        return compositeItemWriter;
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<OutputRecord> csvItemWriter(@Value("#{jobParameters['outputPath']}") String outputPath,
                                                          @Value("#{jobParameters['resultFileName']}") String resultFileName) {
        FlatFileItemWriter<OutputRecord> flatFileItemWriter = new FlatFileItemWriter<>();

        File file = new File(outputPath, resultFileName);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        flatFileItemWriter.setResource(fileSystemResource);

        flatFileItemWriter.setHeaderCallback( writer ->
                writer.write("NumeroContrat ; Sain ; DateModification ; Message")
        );

        BeanWrapperFieldExtractor<OutputRecord> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(new String[] {
                NUMERO_CONTRAT.getValue(), SAIN.getValue(), DATE_MODIFICATION.getValue(), MESSAGE.getValue()
        });

        DelimitedLineAggregator<OutputRecord> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(DELIMITER);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);
        return flatFileItemWriter;
    }



    @Bean
    public BatchAdrInvalidBarecode batchAdrInvalidBarecode() {
        return new BatchAdrInvalidBarecode();
    }

    @Bean
    public AdrInvalidBarecodeService adrInvalidBarecodeService() {
        return new AdrInvalidBarecodeService();
    }

    @Bean
    public ItemProcessor<InputRecord, OutputRecord> adrInvalidBarecodeProcessor() {
        return new AdrInvalidBarecodeProcessor();
    }


}

