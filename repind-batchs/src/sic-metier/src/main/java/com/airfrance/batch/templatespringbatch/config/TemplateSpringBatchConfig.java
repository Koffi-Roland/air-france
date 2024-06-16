package com.airfrance.batch.templatespringbatch.config;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.templatespringbatch.BatchTemplateSpringB;
import com.airfrance.batch.templatespringbatch.model.InputModel;
import com.airfrance.batch.templatespringbatch.model.OutputModel;
import com.airfrance.batch.templatespringbatch.processor.TemplateSpringBatchProcessor;
import com.airfrance.batch.templatespringbatch.reader.TemplateSpringBatchReader;
import com.airfrance.batch.templatespringbatch.service.TemplateSpringBatchService;
import com.airfrance.batch.templatespringbatch.writer.TemplateSpringBatchWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

@ComponentScan(basePackages = {	"com.airfrance.batch.templatespringbatch"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class TemplateSpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("transactionManagerRepind")
    private PlatformTransactionManager transactionManagerRepind;

    private static final int CHUNK_SIZE = 50;

    @Bean
    public Job templateSpringBatchJob(Step cardProcessingStep) {
        return jobBuilderFactory.get("templateSpringBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(cardProcessingStep)
                .build();
    }

    @Bean
    public Step templateSpringBatchStep(ItemReader<InputModel> templateSpringBatchReader,
                                   ItemProcessor<InputModel, OutputModel> templateSpringBatchProcessor,
                                   ItemWriter<OutputModel> templateSpringBatchWriter) {
        return stepBuilderFactory.get("templateSpringBatchStep")
                .<InputModel, OutputModel>chunk(CHUNK_SIZE)
                .reader(templateSpringBatchReader)
                .processor(templateSpringBatchProcessor)
                .writer(templateSpringBatchWriter)
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    public BatchTemplateSpringB batchTemplateSpringB() {
        return new BatchTemplateSpringB();
    }

    @Bean
    public TemplateSpringBatchService templateSpringBatchService() {
        return new TemplateSpringBatchService();
    }

    @Bean
    public ItemReader<InputModel> templateSpringBatchReader() {
        return new TemplateSpringBatchReader();
    }

    @Bean
    public ItemProcessor<InputModel, OutputModel> templateSpringBatchProcessor() {
        return new TemplateSpringBatchProcessor();
    }

    @Bean
    public ItemWriter<OutputModel> templateSpringBatchWriter() {
        return new TemplateSpringBatchWriter();
    }
}