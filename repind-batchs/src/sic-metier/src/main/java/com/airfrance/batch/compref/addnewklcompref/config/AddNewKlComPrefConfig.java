package com.airfrance.batch.compref.addnewklcompref.config;


import com.airfrance.batch.compref.addnewklcompref.processor.NewKlComPrefProcessor;
import com.airfrance.batch.compref.addnewklcompref.reader.NewKlComPrefReader;
import com.airfrance.batch.compref.addnewklcompref.writer.NewKLComPrefOutputWriter;
import com.airfrance.batch.config.*;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;


@ComponentScan(basePackages = {	"com.airfrance.batch.compref.addnewklcompref"},
        excludeFilters={@ComponentScan.Filter(type= FilterType.ASPECTJ, pattern = "com.airfrance.repindutf8.config.*")})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class AddNewKlComPrefConfig {

    public static final String OUPUT_FILE_PATH = "/app/REPIND/data/PROSPECT/";

    public static final String OUPUT_FILE_NAME = "NewKLComprefOuput_";

    public static final String FILE_EXT = ".txt";

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PlatformTransactionManager transactionManagerRepind;

    public static final int CHUNK_NUMBER = 100;

    public AddNewKlComPrefConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean("addNewKlComPrefJob")
    public Job addNewKlComPrefJob() {
        return jobBuilderFactory.get("addNewKlComPrefJob")
                .start(addNewKlComPrefStep())
                .build();
    }

    @Bean
    public Step addNewKlComPrefStep() {
        return stepBuilderFactory.get("addNewKlComPrefStep")
                .<CommunicationPreferences, CommunicationPreferences>chunk(CHUNK_NUMBER)
                .reader(newKlComPrefReader())
                .processor(newKlComPrefProcessor())
                .writer(newKlComPrefWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    public ItemReader<CommunicationPreferences> newKlComPrefReader() {
        return new NewKlComPrefReader();
    }

    @Bean
    public ItemProcessor<CommunicationPreferences, CommunicationPreferences> newKlComPrefProcessor() {
        return new NewKlComPrefProcessor();
    }

    @Bean
    public ItemWriter<CommunicationPreferences> newKlComPrefWriter() {
        return new NewKLComPrefOutputWriter(OUPUT_FILE_PATH, OUPUT_FILE_NAME, FILE_EXT);
    }

}
