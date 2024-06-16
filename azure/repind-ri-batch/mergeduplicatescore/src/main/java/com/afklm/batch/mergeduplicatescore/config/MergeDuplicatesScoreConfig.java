package com.afklm.batch.mergeduplicatescore.config;

import com.afklm.batch.mergeduplicatescore.BatchMergeDuplicatesScore;
import com.afklm.batch.mergeduplicatescore.listener.CsvItemReaderListener;
import com.afklm.batch.mergeduplicatescore.model.InputRecord;
import com.afklm.batch.mergeduplicatescore.model.OutputRecord;
import com.afklm.batch.mergeduplicatescore.processor.FirstItemProcessor;
import com.afklm.batch.mergeduplicatescore.service.FileService;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicateScoreSummaryService;
import com.afklm.batch.mergeduplicatescore.service.MergeDuplicatesScoreService;
import com.afklm.batch.mergeduplicatescore.writer.CustomItemWriter;
import com.afklm.batch.mergeduplicatescore.writer.JdbcWriter;
import com.airfrance.batch.common.config.ConfigBatch;
import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.util.service.RestTemplateExtended;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import static com.airfrance.batch.common.enums.IOFieldEnum.*;
import static com.afklm.batch.mergeduplicatescore.helper.Constant.COMMA;
import static com.afklm.batch.mergeduplicatescore.helper.Constant.SEMI_COLON;

@ComponentScan(basePackages = {"com.afklm.batch.mergeduplicatescore"})
@EnableConfigurationProperties
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
@Configuration
public class MergeDuplicatesScoreConfig {

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

	@Value("${service.pcs.url}")
	private String pcsMsUrl;

	@Value("${pcs.mashrey.key}")
	private String mashreyKey;

	@Value("${pcs.mashrey.secret}")
	private String mashreySecret;

	private static final int CHUNK_SIZE = 50;
	private static final String INPUT_DELIMITER = SEMI_COLON;
	private static final String OUTPUT_DELIMITER = COMMA;

	@Bean
	public Job initBatchJob(JobBuilderFactory jobBuilderFactorySic, Step firstStep) {
		return jobBuilderFactorySic.get("initBatchJob")
				.incrementer(new RunIdIncrementer())
				.start(firstStep)
				.build();
	}

	@Bean
	public Step firstStep(StepBuilderFactory stepBuilderFactory,
						  FlatFileItemReader<InputRecord> csvItemReader,
						  FirstItemProcessor firstItemProcessor) {
		return stepBuilderFactory.get("firstStep")
				.<InputRecord, OutputRecord>chunk(CHUNK_SIZE)
				.reader(csvItemReader)
				.processor(firstItemProcessor)
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
	public FlatFileItemReader<InputRecord> csvItemReader(@Value("#{jobParameters['inputPath']}")String inputPath,
														 @Value("#{jobParameters['mergeMaxSize']}")Long mergeMaxSize,
														 @Value("#{jobParameters['mergeCurrentCount']}")Long mergeCurrentCount) throws JrafDomainException, IOException {

		FlatFileItemReader<InputRecord> flatFileItemReader = new FlatFileItemReader<>();

		fileService.setBaseDirectory(inputPath);
		Path fileInProcessing = fileService.getFileInProcessing();
		log.info("[+] offset is at {} ", mergeCurrentCount);
		if(fileService.checkEOF(mergeCurrentCount,fileInProcessing)){
			fileService.moveFileToProcessed(fileInProcessing);
			fileInProcessing = fileService.getFileInProcessing();
			mergeCurrentCount = mergeDuplicatesScoreService().updateMergeCurrentCount(0);
			log.info("[+] EOF move existing file to PROCESSED_DIR and retrieve a new file in PROCESSING_DIR");
		}
		log.info("[+] now processing file : {}", fileInProcessing);

		flatFileItemReader.setResource(new PathResource(fileInProcessing));

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(ELEMENT_DUPLICATE.getValue(), NB_GINS.getValue(), GINS.getValue());
		tokenizer.setDelimiter(INPUT_DELIMITER);

		BeanWrapperFieldSetMapper<InputRecord> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(InputRecord.class);

		DefaultLineMapper<InputRecord> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(tokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		flatFileItemReader.setLineMapper(defaultLineMapper);

		flatFileItemReader.setLinesToSkip(mergeCurrentCount.intValue());
		flatFileItemReader.setMaxItemCount(mergeMaxSize.intValue());

		return flatFileItemReader;
	}

	@Bean
	@StepScope
	public CompositeItemWriter<OutputRecord> compositeItemWriter(){
		CompositeItemWriter<OutputRecord> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(Arrays.asList(jdbcWriter, new CustomItemWriter(csvItemWriter)));
		return compositeItemWriter;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<OutputRecord> csvItemWriter(@Value("#{jobParameters['outputPath']}") String outputPath,
														  @Value("#{jobParameters['resultFileName']}") String resultFileName,
														  @Value("#{jobParameters['mergeMaxSize']}")Long mergeMaxSize,
														  @Value("#{jobParameters['mergeCurrentCount']}")Long mergeCurrentCount) throws JrafDomainException {

		FlatFileItemWriter<OutputRecord> flatFileItemWriter = new FlatFileItemWriter<>();

		FileSystemResource fileSystemResource = new FileSystemResource(new File(outputPath, resultFileName));
		flatFileItemWriter.setResource(fileSystemResource);

		flatFileItemWriter.setHeaderCallback( writer ->
			writer.write("SGIN,SGIN_FUSION,DDATE_FUSION")
		);

		BeanWrapperFieldExtractor<OutputRecord> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
		beanWrapperFieldExtractor.setNames(new String[] {
				 GIN_SOURCE.getValue(), GIN_TARGET.getValue(), MERGE_DATE_AS_STRING.getValue()
		});

		DelimitedLineAggregator<OutputRecord> delimitedLineAggregator = new DelimitedLineAggregator<>();
		delimitedLineAggregator.setDelimiter(OUTPUT_DELIMITER);
		delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

		flatFileItemWriter.setLineAggregator(delimitedLineAggregator);
		flatFileItemWriter.open(new ExecutionContext());

		long newMergeCount = mergeCurrentCount + mergeMaxSize;
		mergeDuplicatesScoreService().updateMergeCurrentCount(newMergeCount);
		log.info("[+] New offset is at {} ", newMergeCount);

		return flatFileItemWriter;
	}

	@Bean
	public BatchMergeDuplicatesScore batchMergeDuplicatesScore() {
		return new BatchMergeDuplicatesScore();
	}

	@Bean
	public MergeDuplicatesScoreService mergeDuplicatesScoreService() {
		return new MergeDuplicatesScoreService();
	}

	@Bean
	public MergeDuplicateScoreSummaryService mergeDuplicateScoreSummaryService() {
		return new MergeDuplicateScoreSummaryService();
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean(name = "provideScore")
	public RestTemplateExtended consumerAutoMerge() {
		RestTemplateExtended restTemplate = new RestTemplateExtended();
		restTemplate.setApiKey(mashreyKey);
		restTemplate.setApiSecret(mashreySecret);
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(pcsMsUrl));

		return restTemplate;
	}

}
