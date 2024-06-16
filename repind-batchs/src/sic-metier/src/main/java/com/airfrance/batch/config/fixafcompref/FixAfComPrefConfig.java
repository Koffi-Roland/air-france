package com.airfrance.batch.config.fixafcompref;

import com.airfrance.batch.compref.fixafcompref.logger.FixAfComPrefLogger;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.compref.fixafcompref.BatchFixAfComPref;
import com.airfrance.batch.compref.fixafcompref.mapper.FixAfComPrefLineMapper;
import com.airfrance.repind.entity.individu.Individu;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@ComponentScan(basePackages = {	"com.airfrance.batch.compref.fixafcompref" , "com.airfrance.batch.config.fixafcompref"})
@Configuration
@EnableConfigurationProperties
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
public class FixAfComPrefConfig {

    public final int CHUNK_NUMBER = 40;

    @Autowired
    public EntityManagerFactory entityManagerFactoryRepind;

    @Autowired
    public PlatformTransactionManager transactionManagerRepind;

    @Autowired
    public StepBuilderFactory stepBuilderFactorySic;

    @Autowired
    public JobBuilderFactory jobBuilderFactorySic;

    @Bean
    public Step fixAfComPrefStep(FlatFileItemReader<Individu> individuItemReader , ItemProcessor<Individu, Individu> fixAfComPreftemProcessor, ItemWriter<Individu> individuItemWriter) {
        return stepBuilderFactorySic.get("fixAfComPrefStep")
                .<Individu, Individu>chunk(CHUNK_NUMBER)
                .reader(individuItemReader)
                .processor(fixAfComPreftemProcessor)
                .writer(individuItemWriter)
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Individu> individuItemReader(@Qualifier("lineMapper") LineMapper<Individu> iLineMapper , @Value("#{jobParameters['fileName']}") String filename , @Value("#{jobParameters['inputPath']}") String inputPath) throws MalformedURLException {
        return new FlatFileItemReaderBuilder<Individu>()
                .name("individuItemReader")
                .delimited()
                .names(tokens())
                .resource(new UrlResource("file:"+inputPath+filename))
                .lineMapper(iLineMapper)
                .build();
    }

    @Bean
    public LineMapper<Individu> lineMapper(@Qualifier("fixAfComPrefMapper") FieldSetMapper<Individu> iMapper){
        DefaultLineMapper<Individu> lineMapper = new FixAfComPrefLineMapper();
        lineMapper.setLineTokenizer(tokenizer());
        lineMapper.setFieldSetMapper(iMapper);
        lineMapper.afterPropertiesSet();
        return lineMapper;
    }

    @Bean
    public DelimitedLineTokenizer tokenizer(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(tokens());
        tokenizer.setDelimiter(";");
        return tokenizer;
    }

    @Bean(name="tokens")
    public String[] tokens(){
        return new String[]{"GIN_IDV" , "DAT_OPTIN" , "COD_MKT" , "COD_LGE"};
    }

    @Bean
    protected Job fixAfComPrefJob(@Qualifier("fixAfComPrefStep")Step fixAfComPrefStep) {
        return jobBuilderFactorySic.get("fixAfComPrefJob").start(fixAfComPrefStep).build();
    }

    @Bean
    public BatchFixAfComPref batchFixAfComPref(){
        return new BatchFixAfComPref();
    }

    @Bean
    public Map<String , FieldSet> mapContext(){
        return new HashMap<>();
    }

    @Bean
    @StepScope
    public FixAfComPrefLogger fixAfComPrefLogger(@Value("#{jobParameters['fileName']}") String iCsvInput , @Value("#{jobParameters['outputPath']}") String iOutputPath){
        return new FixAfComPrefLogger(iCsvInput , iOutputPath , tokens());
    }

}
