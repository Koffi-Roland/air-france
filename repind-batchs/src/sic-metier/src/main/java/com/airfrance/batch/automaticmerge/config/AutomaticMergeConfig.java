package com.airfrance.batch.automaticmerge.config;


import com.airfrance.batch.automaticmerge.BatchAutomaticMerge;
import com.airfrance.batch.automaticmerge.enums.IOFieldEnum;
import com.airfrance.batch.automaticmerge.listener.CsvItemReaderListener;
import com.airfrance.batch.automaticmerge.model.InputRecord;
import com.airfrance.batch.automaticmerge.model.OutputRecord;
import com.airfrance.batch.automaticmerge.processor.AutomaticMergeProcessor;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeService;
import com.airfrance.batch.automaticmerge.service.AutomaticMergeSummaryService;
import com.airfrance.batch.automaticmerge.service.FileService;
import com.airfrance.batch.automaticmerge.writer.CustomItemWriter;
import com.airfrance.batch.automaticmerge.writer.JdbcWriter;
import com.airfrance.batch.common.service.ProvideIndividualScoreService;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ExecutionContext;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static com.airfrance.batch.automaticmerge.helper.Constant.COMMA;
import static com.airfrance.batch.automaticmerge.helper.Constant.SEMI_COLON;

@ComponentScan(basePackages = {"com.airfrance.batch.automaticmerge"})
@EnableConfigurationProperties
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
@Configuration
public class AutomaticMergeConfig {

	@Autowired
	@Qualifier("transactionManagerRepind")
	private PlatformTransactionManager transactionManagerRepind;
	@Autowired
	private JdbcWriter jdbcWriter;

	@Autowired
	private FlatFileItemWriter<OutputRecord> csvItemWriter;

	@Autowired
	private FileService fileService;

	@Autowired
	private CsvItemReaderListener csvItemReaderListener;

	private static final int CHUNK_SIZE = 100;
	private static final String INPUT_DELIMITER = SEMI_COLON;
	private static final String OUTPUT_DELIMITER = COMMA;


	@Bean
	public Job automaticMergeBatchJob(JobBuilderFactory jobBuilderFactorySic, Step firstStep) {
		return jobBuilderFactorySic.get("automaticMergeBatchJob")
				.incrementer(new RunIdIncrementer())
				.start(firstStep)
				.build();
	}

	@Bean
	public Step firstStep(StepBuilderFactory stepBuilderFactory,
						  FlatFileItemReader<InputRecord> csvItemReader,
						  AutomaticMergeProcessor automaticMergeProcessor) {
		return stepBuilderFactory.get("firstStep")
				.<InputRecord, OutputRecord>chunk(CHUNK_SIZE)
				.reader(csvItemReader)
				.processor(automaticMergeProcessor)
				.writer(compositeItemWriter())
				.faultTolerant()
				.skip(Throwable.class)
				.skipPolicy(new AlwaysSkipItemSkipPolicy())
				.listener(csvItemReaderListener)
				.transactionManager(transactionManagerRepind)
				.build();
	}


	@Bean
	@StepScope
	@Transactional
	public FlatFileItemReader<InputRecord> csvItemReader(@Value("#{jobParameters['inputPath']}")String inputPath) throws IOException {

		FlatFileItemReader<InputRecord> flatFileItemReader = new FlatFileItemReader<>();

		fileService.setBaseDirectory(inputPath);
		Path fileInProcessing = fileService.getFileInProcessing();
		log.info("[+] now processing file : {}", fileInProcessing);

		flatFileItemReader.setResource(new PathResource(fileInProcessing));

		DelimitedLineTokenizer tokenizer = getDelimitedLineTokenizer();
		BeanWrapperFieldSetMapper<InputRecord> beanWrapperFieldSetMapper = getInputRecordBeanWrapperFieldSetMapper();

		DefaultLineMapper<InputRecord> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(tokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		flatFileItemReader.setLineMapper(defaultLineMapper);

		return flatFileItemReader;
	}

	@NotNull
	private static BeanWrapperFieldSetMapper<InputRecord> getInputRecordBeanWrapperFieldSetMapper() {
		BeanWrapperFieldSetMapper<InputRecord> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(InputRecord.class);
		return beanWrapperFieldSetMapper;
	}

	@NotNull
	private static DelimitedLineTokenizer getDelimitedLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(IOFieldEnum.ELEMENT_DUPLICATE.getValue(), IOFieldEnum.NB_GINS.getValue(), IOFieldEnum.GINS.getValue());
		tokenizer.setDelimiter(INPUT_DELIMITER);
		return tokenizer;
	}

	@Bean
	@StepScope
	public CompositeItemWriter<OutputRecord> compositeItemWriter(){
		CompositeItemWriter<OutputRecord> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(Arrays.asList(jdbcWriter, new CustomItemWriter(csvItemWriter, automaticMergeSummaryService())));
		return compositeItemWriter;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<OutputRecord> csvItemWriter(@Value("#{jobParameters['outputPath']}") String outputPath,
														  @Value("#{jobParameters['resultFileName']}") String resultFileName) {

		FlatFileItemWriter<OutputRecord> flatFileItemWriter = new FlatFileItemWriter<>();

		FileSystemResource fileSystemResource = new FileSystemResource(new File(outputPath, resultFileName));
		flatFileItemWriter.setResource(fileSystemResource);

		flatFileItemWriter.setHeaderCallback( writer ->
			writer.write("SGIN,SGIN_FUSION,DDATE_FUSION")
		);

		BeanWrapperFieldExtractor<OutputRecord> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
		beanWrapperFieldExtractor.setNames(new String[] {
				 IOFieldEnum.GIN_SOURCE.getValue(), IOFieldEnum.GIN_TARGET.getValue(), IOFieldEnum.MERGE_DATE_AS_STRING.getValue()
		});

		DelimitedLineAggregator<OutputRecord> delimitedLineAggregator = new DelimitedLineAggregator<>();
		delimitedLineAggregator.setDelimiter(OUTPUT_DELIMITER);
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

		flatFileItemWriter.setLineAggregator(delimitedLineAggregator);
		flatFileItemWriter.open(new ExecutionContext());
		return flatFileItemWriter;
	}

	@Bean
	public BatchAutomaticMerge batchAutomaticMerge() {
		return new BatchAutomaticMerge();
	}

	@Bean
	public AutomaticMergeService automaticMergeService() {
		return new AutomaticMergeService();
	}

	@Bean
	public AutomaticMergeSummaryService automaticMergeSummaryService() {
		return new AutomaticMergeSummaryService();
	}

	@Bean
	public ProvideIndividualScoreService provideIndividualScoreService() {
		return new ProvideIndividualScoreService();
	}

}
