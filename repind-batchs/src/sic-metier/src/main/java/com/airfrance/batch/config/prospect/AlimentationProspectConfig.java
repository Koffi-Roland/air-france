package com.airfrance.batch.config.prospect;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.prospect.bean.AlimentationProspectItem;
import com.airfrance.batch.prospect.bean.ProspectInputRecord;
import com.airfrance.batch.prospect.helper.BeanWrapperFieldSetMapperCustom;
import com.airfrance.batch.prospect.listener.AlimentationProspectListener;
import com.airfrance.batch.prospect.processor.AlimentationProspectItemProcessor;
import com.airfrance.batch.prospect.property.AlimentationProspectProperty;
import com.airfrance.batch.prospect.reader.ProspectLineMapperCustom;
import com.airfrance.batch.prospect.service.AlimentationProspectService;
import com.airfrance.batch.prospect.tasklet.AlimentationProspectOutputTasklet;
import com.airfrance.batch.prospect.writer.AlimentationProspectItemWriter;
import com.airfrance.repind.service.ws.internal.CreateOrUpdateIndividualDS;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import static com.airfrance.batch.prospect.helper.AlimentationProspectConstant.*;

@PropertySource("classpath:/app/alimentation-prospect.properties")
@EnableConfigurationProperties(AlimentationProspectProperty.class)
@ComponentScan(basePackages = {	"com.airfrance.batch.prospect" , "com.airfrance.batch.config.prospect"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class AlimentationProspectConfig {

    private final JobBuilderFactory jobs;

    private final StepBuilderFactory steps;

    private final PlatformTransactionManager transactionManagerRepind;

    private final CreateOrUpdateIndividualDS createOrUpdateIndividualDS;

    private final AlimentationProspectService prospectService;

    public AlimentationProspectConfig(
            JobBuilderFactory jobs,
            StepBuilderFactory steps,
            @Qualifier("transactionManagerRepind") PlatformTransactionManager transactionManagerRepind,
            CreateOrUpdateIndividualDS createOrUpdateIndividualDS,
            AlimentationProspectService prospectService) {
        this.jobs = jobs;
        this.steps = steps;
        this.transactionManagerRepind = transactionManagerRepind;
        this.createOrUpdateIndividualDS = createOrUpdateIndividualDS;
        this.prospectService = prospectService;
    }

    @Bean("alimentationProspectJob")
    public Job alimentationProspectJob(
            @Qualifier("alimentationProspectParseFileStep") Step parseFile,
            @Qualifier("alimentationAgenceOutputStep") Step createOutput) {
        return jobs
                .get("alimentationProspectJob")
                .start(parseFile)
                .next(createOutput)
                .preventRestart()
                .build();
    }

    @Bean
    public Step alimentationProspectParseFileStep() {
        return steps
                .get("alimentationProspectParseFileStep")
                .listener(new AlimentationProspectListener())
                .transactionManager(transactionManagerRepind)
                .<ProspectInputRecord, AlimentationProspectItem>chunk(1)
                .reader(alimentationProspectReader(null))
                .processor(alimentationProspectProcessor())
                .writer(alimentationProspectWriter())
                .build();
    }

    /**
     * Step to create the output file
     * @param createOutputFile task that will create the actual files
     * @return the new step to create the output files
     */
    @Bean
    public Step alimentationAgenceOutputStep(AlimentationProspectOutputTasklet createOutputFile) {
        return steps
                .get("alimentationAgenceOutputStep")
                .tasklet(createOutputFile)
                .build();
    }

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FlatFileItemReader<ProspectInputRecord> alimentationProspectReader(@Value("#{jobParameters['inputFile']}") String inputFile) {
        Resource resource = new FileSystemResource(inputFile);
        prospectService.setFilePath(inputFile);

        return new FlatFileItemReaderBuilder<ProspectInputRecord>()
                .name("alimentationProspectReader")
                .resource(resource)
                .lineMapper(prospectInputRecordLineMapper())
                .build();
    }

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AlimentationProspectItemProcessor alimentationProspectProcessor() {
        return new AlimentationProspectItemProcessor();
    }

    @Bean
    @Scope(value = "step", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AlimentationProspectItemWriter alimentationProspectWriter() {
        return new AlimentationProspectItemWriter(createOrUpdateIndividualDS, prospectService);
    }

    // Open input file and map it to object
    private LineMapper<ProspectInputRecord> prospectInputRecordLineMapper() {
        DefaultLineMapper<ProspectInputRecord> alimProspectLineMapper = new ProspectLineMapperCustom();

        LineTokenizer recordTokenizer = alimentationProspectRecordLineTokenizer();
        alimProspectLineMapper.setLineTokenizer(recordTokenizer);

        FieldSetMapper<ProspectInputRecord> recordMapper = alimentationProspectRecordMapper();
        alimProspectLineMapper.setFieldSetMapper(recordMapper);

        return alimProspectLineMapper;
    }

    private LineTokenizer alimentationProspectRecordLineTokenizer() {
        DelimitedLineTokenizer recordTokenizer = new DelimitedLineTokenizer();
        recordTokenizer.setDelimiter(FILE_DELIMITER);
        recordTokenizer.setNames(
                EMAIL,
                GIN,
                FB_NUMBER,
                FIRSTNAME,
                LASTNAME,
                CIVILITY,
                DATE_OF_BIRTH,
                COUNTRY_CODE,
                LANGUAGE_CODE,
                SUBSCRIPTION_TYPE,
                OPTIN,
                SOURCE,
                DATE_OF_CONSENT,
                DEPARTURE_AIRPORT);
        return recordTokenizer;
    }

    private FieldSetMapper<ProspectInputRecord> alimentationProspectRecordMapper() {
        BeanWrapperFieldSetMapperCustom<ProspectInputRecord> mapper = new BeanWrapperFieldSetMapperCustom<>();
        mapper.setTargetType(ProspectInputRecord.class);
        return mapper;
    }
}
