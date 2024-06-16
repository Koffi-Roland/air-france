package com.airfrance.batch.exportonecrm.config;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.exportonecrm.BatchExportOneCrm;
import com.airfrance.batch.exportonecrm.model.*;
import com.airfrance.batch.exportonecrm.processor.ComprefOcpProcessor;
import com.airfrance.batch.exportonecrm.processor.ComprefProcessor;
import com.airfrance.batch.exportonecrm.processor.MarketlanguageProcessor;
import com.airfrance.batch.exportonecrm.service.ExportOneCrmService;
import com.airfrance.batch.exportonecrm.tasklet.SplitFileTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.time.LocalDate;
import java.util.Collections;
import java.util.concurrent.ThreadPoolExecutor;

@ComponentScan(basePackages = {	"com.airfrance.batch.exportonecrm"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class ExportOneCrmConfig {
    public static final String OUTPUT_PATH = "/app/REPIND/data/exportonecrm";
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Qualifier("transactionManagerRepind")
    private final PlatformTransactionManager transactionManagerRepind;

    @Qualifier("dataSourceRepind")
    private final DataSource dataSource;
    private static final int CHUNK_SIZE = 100;
    public static final int STEP_THREAD_NUMBER = 40;
    public static final int STEP_MAX_THREAD_NUMBER = 100;

    public ExportOneCrmConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, PlatformTransactionManager transactionManagerRepind, DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.transactionManagerRepind = transactionManagerRepind;
        this.dataSource = dataSource;
    }


    @Bean
    public Job exportOneCrmJob(
            @Qualifier("exportComprefOcpStep") Step exportComprefOcpStep,
            @Qualifier("exportPreferenceStep") Step exportPreferenceStep,
            @Qualifier("exportComprefStep") Step exportComprefStep,
            @Qualifier("exportMarketLanguageStep") Step exportMarketLanguageStep,
            @Qualifier("exportPreferenceDataStep") Step exportPreferenceDataStep
    ) {
        return jobBuilderFactory.get("exportOneCrmJob")
                .incrementer(new RunIdIncrementer())
                .start(exportMarketLanguageStep)
//                .next(exportComprefStep)
//                .next(exportPreferenceStep)
//                .next(exportPreferenceDataStep)
//                .next(exportComprefOcpStep)
//                .next(splitStep())
                .build();
    }

    @Bean
    public Step splitStep() {
        return stepBuilderFactory.get("splitStep")
                .tasklet(splitFileTasklet())
                .build();
    }

    // #################### MARKET_LANGUAGE ###############################
    @Bean
    public Step exportMarketLanguageStep(
    ) {
        return stepBuilderFactory.get("exportMarketLanguageStep")
                .<MarketLanguage, MarketLanguage>chunk(CHUNK_SIZE)
                .reader(marketLanguageReader())
                .processor(marketLanguageProcessor())
                .writer(marketLanguageWriter())
                .transactionManager(transactionManagerRepind)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<MarketLanguage> marketLanguageReader() {
        String selectClause = """
                    TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD"T"HH24:MI:SS.FF3')  AS ACTION_DATE,
                    cp.SGIN as GIN,
                    ml.COM_PREF_ID,
                    ml.MARKET_LANGUAGE_ID,
                    ml.LANGUAGE_CODE,
                    ml.MARKET,
                    ml.OPTIN,
                    ml.DATE_OPTIN
                """;
        String fromClause = " MARKET_LANGUAGE ml LEFT JOIN COMMUNICATION_PREFERENCES cp ON ml.COM_PREF_ID = cp.COM_PREF_ID";
        String orderByClause = "GIN";

        return createJdbcPagingItemReader(MarketLanguage.class, selectClause, fromClause, null, null, orderByClause);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<MarketLanguage> marketLanguageWriter() {
        String header = "ACTION_DATE,GIN,COM_PREF_ID,MARKET_LANGUAGE_ID,LANGUAGE_CODE,MARKET,OPTIN,DATE_OPTIN";
        String[] namesExtractor = {
                "ACTION_DATE", "GIN", "COM_PREF_ID","MARKET_LANGUAGE_ID","LANGUAGE_CODE","MARKET","OPTIN","DATE_OPTIN"
        };

        return createFlatFileItemWriter(
                OUTPUT_PATH,
                "marketlanguage_"+ LocalDate.now()+".csv",
                header, namesExtractor);

    }


    // #################### COMPREF ###########################
    @Bean
    public Step exportComprefStep(
    ) {
        return stepBuilderFactory.get("exportComprefStep")
                .<ComPref, ComPref>chunk(CHUNK_SIZE)
                .reader(comprefReader())
                .processor(comprefProcessor())
                .writer(comprefWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ComPref> comprefReader() {
        String selectClause = """
                TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD"T"HH24:MI:SS.FF3')  AS ACTION_DATE,
                SGIN as GIN,
                CHANNEL,
                COM_GROUP_TYPE,
                COM_TYPE,
                CREATION_DATE,
                CREATION_SIGNATURE,
                CREATION_SITE,
                DATE_OPTIN,
                DOMAIN,
                ACCOUNT_IDENTIFIER,
                MODIFICATION_DATE,
                MODIFICATION_SIGNATURE,
                MODIFICATION_SITE,
                COM_PREF_ID,
                SUBSCRIBE ,
                DATE_OPTIN_PARTNERS,
                DATE_OF_ENTRY ,
                OPTIN_PARTNERS
                """;
        String fromClause = " communication_preferences ";
        String orderByClause = "GIN";

        return createJdbcPagingItemReader(ComPref.class, selectClause, fromClause, null, null, orderByClause);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<ComPref> comprefWriter() {
        String header = "ACTION_DATE,GIN,CHANNEL,COM_GROUP_TYPE,COM_TYPE, CREATION_DATE ,CREATION_SIGNATURE ,CREATION_SITE, DATE_OPTIN ,DOMAIN ,ACCOUNT_IDENTIFIER," +
                "MODIFICATION_DATE,MODIFICATION_SIGNATURE,MODIFICATION_SITE,COM_PREF_ID,SUBSCRIBE,DATE_OPTIN_PARTNERS,DATE_OF_ENTRY,OPTIN_PARTNERS";
        String[] namesExtractor = {
                "ACTION_DATE","GIN","CHANNEL","COM_GROUP_TYPE","COM_TYPE", "CREATION_DATE" ,"CREATION_SIGNATURE" ,"CREATION_SITE", "DATE_OPTIN" ,"DOMAIN" ,"ACCOUNT_IDENTIFIER",
                        "MODIFICATION_DATE","MODIFICATION_SIGNATURE","MODIFICATION_SITE","COM_PREF_ID","SUBSCRIBE","DATE_OPTIN_PARTNERS","DATE_OF_ENTRY","OPTIN_PARTNERS"
        };

        return createFlatFileItemWriter(
                OUTPUT_PATH,
                "compref_"+ LocalDate.now()+".csv",
                header, namesExtractor);

    }


    // #################### PREFERENCE ###############################
    @Bean
    public Step exportPreferenceStep(
            ) {
        return stepBuilderFactory.get("exportPreferenceStep")
                .<Preference, Preference>chunk(CHUNK_SIZE)
                .reader(preferenceReader())
                .writer(preferenceWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<Preference> preferenceReader() {
        String selectClause = """
                 TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD"T"HH24:MI:SS.FF3')  AS ACTION_DATE,
                SGIN as GIN,
                PREFERENCE_ID,
                STYPE,
                ILINK,
                DDATE_CREATION,
                SSITE_CREATION,
                SSIGNATURE_CREATION,
                DDATE_MODIFICATION,
                SSITE_MODIFICATION,
                SSIGNATURE_MODIFICATION
                """;
        String fromClause = " PREFERENCE p ";
        String orderByClause = "PREFERENCE_ID";

        return createJdbcPagingItemReader(Preference.class, selectClause, fromClause, null, null, orderByClause);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<Preference> preferenceWriter() {
        String header = "ACTION_DATE,GIN,PREFERENCE_ID,STYPE,ILINK,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION";
        String[] namesExtractor = {
                "ACTION_DATE", "GIN", "PREFERENCE_ID", "STYPE", "ILINK", "DDATE_CREATION", "SSITE_CREATION", "SSIGNATURE_CREATION", "DDATE_MODIFICATION", "SSITE_MODIFICATION", "SSIGNATURE_MODIFICATION"
        };

        return createFlatFileItemWriter(
                OUTPUT_PATH,
                "preference_"+ LocalDate.now()+".csv",
                header, namesExtractor);
    }

    // #################### PREFERENCE DATA ###############################
    @Bean
    public Step exportPreferenceDataStep(
    ) {
        return stepBuilderFactory.get("exportPreferenceStep")
                .<PreferenceData, PreferenceData>chunk(CHUNK_SIZE)
                .reader(preferenceDataReader())
                .writer(preferenceDataWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<PreferenceData> preferenceDataReader() {
        String selectClause = """
                    TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD"T"HH24:MI:SS.FF3')  AS ACTION_DATE, --verify to update with modificationDate
                    pf.SGIN as GIN,
                    pfd.PREFERENCE_DATA_ID,
                    pfd.PREFERENCE_ID,
                    pfd.SKEY,
                    pfd.SVALUE,
                    pfd.DDATE_CREATION,
                    pfd.SSITE_CREATION,
                    pfd.SSIGNATURE_CREATION,
                    pfd.DDATE_MODIFICATION,
                    pfd.SSITE_MODIFICATION,
                    pfd.SSIGNATURE_MODIFICATION
                """;
        String fromClause = " PREFERENCE_DATA pfd LEFT JOIN PREFERENCE pf on pf.PREFERENCE_ID = pfd.PREFERENCE_ID ";
        String orderByClause = "GIN";

        return createJdbcPagingItemReader(PreferenceData.class, selectClause, fromClause, null, null, orderByClause);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<PreferenceData> preferenceDataWriter() {
        String header = "ACTION_DATE,GIN,PREFERENCE_DATA_ID,PREFERENCE_ID,SKEY,SVALUE,DDATE_CREATION,SSITE_CREATION,SSIGNATURE_CREATION,DDATE_MODIFICATION,SSITE_MODIFICATION,SSIGNATURE_MODIFICATION";
        String[] namesExtractor = {
                "ACTION_DATE","GIN","PREFERENCE_DATA_ID","PREFERENCE_ID","SKEY","SVALUE","DDATE_CREATION","SSITE_CREATION","SSIGNATURE_CREATION","DDATE_MODIFICATION","SSITE_MODIFICATION","SSIGNATURE_MODIFICATION"
        };

        return createFlatFileItemWriter(
                OUTPUT_PATH,
                "preferencedata_"+ LocalDate.now()+".csv",
                header, namesExtractor);
    }

    // ##################### COMPREF OCP #################################
    @Bean
    public Step exportComprefOcpStep(
    ) {
        return stepBuilderFactory.get("exportComprefOcpStep")
                .<ComPrefOcp, ComPrefOcp>chunk(CHUNK_SIZE)
                .reader(comprefOcpReader())
                .processor(comprefOcpProcessor())
                .writer(comprefOcpWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ComPrefOcp> comprefOcpReader() {
        String selectClause = """
              'UPDATE' as ACTION_TYPE,
              TO_CHAR(SYSTIMESTAMP, 'YYYY-MM-DD"T"HH24:MI:SS.FF3')  AS ACTION_DATE,
              SGIN as GIN,
              'COMMUNICATION_PREFERENCES' as ACTION_TABLE,
              JSON_OBJECT(
                      'identifier' VALUE p.COM_PREF_ID,
                      'domain' VALUE p.DOMAIN,
                      'gin' VALUE p.SGIN,
                      'comGroupType' VALUE p.COM_GROUP_TYPE,
                      'comType' VALUE p.COM_TYPE,
                      'optin' VALUE p.SUBSCRIBE,
                      'dateOptin' VALUE p.DATE_OPTIN,
                      'media1' VALUE p.MEDIA1,
                      'creationDate' VALUE p.CREATION_DATE,
                      'creationSignature' VALUE p.CREATION_SIGNATURE,
                      'creationSite' VALUE p.CREATION_SITE,
                      'modificationDate' VALUE p.MODIFICATION_DATE,
                      'modificationSignature' VALUE p.MODIFICATION_SIGNATURE,
                      'modificationSite' VALUE p.MODIFICATION_SITE,
                          
                      'marketLanguage' VALUE JSON_ARRAYAGG(
                          JSON_OBJECT(
                            'id' VALUE c.MARKET_LANGUAGE_ID,
                            'languageCode' VALUE c.LANGUAGE_CODE,
                            'market' value c.MARKET ,
                            'optin' value c.OPTIN ,
                            'optinDate' value c.DATE_OPTIN
                          )
                    )
                 ) AS CONTENT_DATA
                """;
        String fromClause = """
                FROM communication_preferences p
                left JOIN market_language c ON p.com_pref_id = c.com_pref_id
                """;
        String groupClause = """
                 GROUP BY p.com_pref_id, p.sgin, p.com_group_type, p.com_type,
                p.subscribe, p.domain, p.date_optin, p.creation_date, p.media1,
                p.creation_signature,p.creation_site, p.modification_date,p.modification_signature,
                p.modification_site
                """;
        String orderByClause = "GIN";

        return createJdbcPagingItemReader(ComPrefOcp.class, selectClause, fromClause, null, groupClause, orderByClause);
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<ComPrefOcp> comprefOcpWriter() {
        String header = "ACTION_TYPE,ACTION_DATE,GIN,ACTION_TABLE,CONTENT_DATA";
        String[] namesExtractor = {
                "ACTION_TYPE", "ACTION_DATE", "GIN", "ACTION_TABLE", "CONTENT_DATA"
        };
        return createFlatFileItemWriter(
                OUTPUT_PATH,
                "comprefocp_"+ LocalDate.now()+".csv",
                header, namesExtractor);

    }


    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-comprefocp");
        return executor;
    }

    @Bean
    public BatchExportOneCrm batchExportOneCrm() {
        return new BatchExportOneCrm();
    }

    @Bean
    public ExportOneCrmService exportOneCrmService() {
        return new ExportOneCrmService();
    }

    /**
     * createJdbcPagingItemReader : Extracted method that will read from DB using given sql query. (to avoid duplication)
     * @param beanRowMapper beanRowMapper
     * @param selectClause selectClause
     * @param fromClause fromClause
     * @param whereClause whereClause
     * @param sortKey sortKey
     * @return JdbcPagingItemReader
     * @param <T> T
     */
    private <T> JdbcPagingItemReader<T> createJdbcPagingItemReader(Class<T>  beanRowMapper, String selectClause, String fromClause, String whereClause, String groupClause, String sortKey) {
        JdbcPagingItemReader<T> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(100000);


        reader.setRowMapper(new BeanPropertyRowMapper<>(beanRowMapper));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause(selectClause);
        queryProvider.setFromClause(fromClause);
        if(whereClause != null)  queryProvider.setWhereClause(whereClause);
        if(groupClause != null)  queryProvider.setGroupClause(groupClause);
        queryProvider.setSortKeys(Collections.singletonMap(sortKey, Order.ASCENDING));

        reader.setQueryProvider(queryProvider);

        return reader;
    }


    /**
     * createFlatFileItemWriter : Extracted method that will generate csv File. (to avoid duplication)
     * @param outputPath outputPath
     * @param resultFileName resultFileName
     * @param header header
     * @param wrapperFields wrapperFields
     * @return FlatFileItemWriter
     * @param <T> T
     */
    private <T> FlatFileItemWriter<T> createFlatFileItemWriter(String outputPath, String resultFileName,
                                                               String header, String[] wrapperFields) {
        FlatFileItemWriter<T> flatFileItemWriter = new FlatFileItemWriter<>();

        File file = new File(outputPath, resultFileName);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        flatFileItemWriter.setResource(fileSystemResource);

        flatFileItemWriter.setHeaderCallback(writer ->
                writer.write(header)
        );

        BeanWrapperFieldExtractor<T> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(wrapperFields);

        DelimitedLineAggregator<T> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(",");
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);

        return flatFileItemWriter;
    }

    @Bean
    public SplitFileTasklet splitFileTasklet() {
        return new SplitFileTasklet();
    }

    @Bean
    public ItemProcessor<ComPrefOcp, ComPrefOcp> comprefOcpProcessor() {
        return new ComprefOcpProcessor();
    }

    @Bean
    public ItemProcessor<ComPref, ComPref> comprefProcessor() {
        return new ComprefProcessor();
    }

    @Bean
    public ItemProcessor<MarketLanguage, MarketLanguage> marketLanguageProcessor() {
        return new MarketlanguageProcessor();
    }


    @Bean
    public SummaryService summaryService() {
        return new SummaryService();
    }
}
