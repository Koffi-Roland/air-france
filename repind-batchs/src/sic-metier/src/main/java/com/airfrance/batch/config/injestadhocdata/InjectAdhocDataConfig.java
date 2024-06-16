package com.airfrance.batch.config.injestadhocdata;

import com.airfrance.batch.compref.injestadhocdata.BatchInjestAdhocData;
import com.airfrance.batch.compref.injestadhocdata.bean.AdhocDataItem;
import com.airfrance.batch.compref.injestadhocdata.bean.InputRecord;
import com.airfrance.batch.compref.injestadhocdata.listener.InitStepExecutionListener;
import com.airfrance.batch.compref.injestadhocdata.property.InjestAdhocDataPropoerty;
import com.airfrance.batch.compref.injestadhocdata.service.InjestAdhocDataService;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
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
import java.util.concurrent.Future;

@PropertySource("classpath:/app/injest-adhoc-data.properties")
@EnableConfigurationProperties(InjestAdhocDataPropoerty.class)
@ComponentScan(basePackages = { "com.airfrance.batch.compref.injestadhocdata",
		"com.airfrance.batch.config.injestadhocdata" })
@Configuration
@Import({ ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class })
@Slf4j
public class InjectAdhocDataConfig {

	public static final int STEP_MAX_THREAD_NUMBER = 10;
	public static final int CHUNK_NUMBER = 40;
	public static final int MAX_PARTITION_NUMBER = 50;
	public static final int GRID_SIZE = 10;

	@Autowired
	public EntityManagerFactory entityManagerFactoryRepind;

	@Autowired
	public StepBuilderFactory stepBuilderFactorySic;

	@Bean
	public Step initStep(@Qualifier("partitioner") Partitioner iPartitioner,
						 @Qualifier("initSlaveStep") Step initSlaveStep) {
		return stepBuilderFactorySic.get("initStep").partitioner("stepPartitioner", iPartitioner).step(initSlaveStep)
				.taskExecutor(stepExecutor()).gridSize(GRID_SIZE).build();
	}

	@Bean
	public Step initSlaveStep(@Qualifier("adhocDataJsonItemReader") ItemReader<InputRecord> iReader,
							  @Qualifier("asyncAdhocDataItemProcessor") AsyncItemProcessor<InputRecord, AdhocDataItem> iProcessor,
							  @Qualifier("asyncDsItemWriter") AsyncItemWriter<AdhocDataItem> asyncDsItemWriter,
							  @Qualifier("initStepExecutionListener") InitStepExecutionListener initStepExecutionListener,
							  PlatformTransactionManager transactionManagerRepind) {
		return stepBuilderFactorySic.get("initSlaveStep").<InputRecord, Future<AdhocDataItem>>chunk(CHUNK_NUMBER)
				.reader(iReader).processor(iProcessor).writer(asyncDsItemWriter)
				.transactionManager(transactionManagerRepind).listener(initStepExecutionListener).build();
	}

	@Bean
	public AsyncItemProcessor<InputRecord, AdhocDataItem> asyncAdhocDataItemProcessor(
			@Qualifier("adhocDataItemProcessor") ItemProcessor<InputRecord, AdhocDataItem> iProcessor) {
		AsyncItemProcessor<InputRecord, AdhocDataItem> asyncItemProcessor = new AsyncItemProcessor<>();
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
		executor.setThreadNamePrefix("InjectAdhocData Processor -");
		executor.initialize();
		return executor;
	}

	@Bean
	public TaskExecutor stepExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(STEP_MAX_THREAD_NUMBER);
		executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
		executor.setQueueCapacity(MAX_PARTITION_NUMBER);
		executor.afterPropertiesSet();
		executor.setThreadNamePrefix("InjectAdhocData Step -");
		return executor;
	}

	@Bean
	@StepScope
	public Partitioner partitioner(@Value("#{jobParameters['fileName']}") String fileName,
								   @Value("#{jobParameters['inputPath']}") String inputPath) {
		MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
		Resource[] resources = null;
		try {
			resources = new PathMatchingResourcePatternResolver().getResources("file:" + inputPath + fileName);
			partitioner.setResources(resources);
			partitioner.partition(MAX_PARTITION_NUMBER);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return partitioner;
	}

	@Bean
	public AsyncItemWriter<AdhocDataItem> asyncDsItemWriter(
			@Qualifier("adhocDataWriter") ItemWriter<AdhocDataItem> adhocDataWriter) {
		AsyncItemWriter<AdhocDataItem> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(adhocDataWriter);
		return asyncItemWriter;
	}

	@Bean
	@StepScope
	public JsonItemReader<InputRecord> adhocDataJsonItemReader(@Value("#{stepExecutionContext['fileName']}") String filename) throws MalformedURLException {
		return new JsonItemReaderBuilder<InputRecord>()
				.jsonObjectReader(new JacksonJsonObjectReader<>(InputRecord.class))
				.resource(new UrlResource(filename))
				.name("adhocDataJsonItemReader")
				.build();
	}

	@Bean
	protected Job initBatchJob(@Qualifier("initStep") Step initStep, JobBuilderFactory jobBuilderFactorySic) {
		return jobBuilderFactorySic.get("initBatchJob").start(initStep).build();
	}

	@Bean
	public InjestAdhocDataService injestAdhocDataService() {
		return new InjestAdhocDataService();
	}

	@Bean
	public BatchInjestAdhocData batchInjestAdhocData() {
		return new BatchInjestAdhocData();
	}
}
