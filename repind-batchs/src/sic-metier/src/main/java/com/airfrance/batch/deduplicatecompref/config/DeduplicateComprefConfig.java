package com.airfrance.batch.deduplicatecompref.config;

import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.deduplicatecompref.BatchDeduplicateCompref;
import com.airfrance.batch.deduplicatecompref.model.CommunicationPreferencesModel;
import com.airfrance.batch.deduplicatecompref.model.MarketLanguageModel;
import com.airfrance.batch.deduplicatecompref.processor.DeduplicateComprefProcessor;
import com.airfrance.batch.deduplicatecompref.processor.DeleteMarketLanguageProcessor;
import com.airfrance.batch.deduplicatecompref.writer.DeduplicateComprefWriter;
import com.airfrance.batch.deduplicatecompref.writer.DeleteMarketLanguageWriter;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


@ComponentScan(basePackages = {	"com.airfrance.batch.deduplicatecompref"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class DeduplicateComprefConfig {

    /**
     * The number of items in a chunk
     */
    private static final int CHUNK_SIZE = 100;
    /**
     * Default number of items (communication preferences) selected from database
     */
    public static final int DEFAULT_LIMIT_SELECTION = 20000;

    /**
     * The number of items (communication preferences) selected from database per page
     */
    public static final int PAGE_LIMIT_SELECTION = 10000;

    public static final String DEDUPLICATE_BATCH_MAX_SIZE = "DEDUPLICATE_BATCH_MAX_SIZE";

    /**
     *  Thread number
     */
    public static final int STEP_THREAD_NUMBER = 40;
    /**
     * Max Thread number
     */
    public static final int STEP_MAX_THREAD_NUMBER = 100;

    /**
     * Job builder factory -inject by spring
     */
    private final JobBuilderFactory jobBuilderFactory;
    /**
     * Step builder factory -inject by spring
     */
    private final StepBuilderFactory stepBuilderFactory;
    /**
     * Data source used for connection
     */
    @Qualifier("dataSourceRepind")
    private final DataSource dataSource;
    /**
     * Transaction manager - inject by spring
     */
    @Qualifier("transactionManagerRepind")
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * Variable Cati - inject by spring
     */
    private final VariablesDS variablesDS;

    /**
     * Limit number of items (communication preferences) selected from database
     */
    private final Integer limitSelection;

    @Autowired
    public DeduplicateComprefConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource, PlatformTransactionManager platformTransactionManager, VariablesDS variablesDS) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
        this.platformTransactionManager = platformTransactionManager;
        this.variablesDS = variablesDS;
        int limitSelected;
        try{
            limitSelected = Integer.parseInt(variablesDS.getByEnvKey(DEDUPLICATE_BATCH_MAX_SIZE).getEnvValue());
        } catch (JrafDomainException e) {
            limitSelected = DEFAULT_LIMIT_SELECTION;
        }
        this.limitSelection = limitSelected;
    }

    /**
     * Deduplicate Communication Preferences batch job
     *
     * @return Deduplicate communication preference batch job builder
     */
    @Bean
    public Job deduplicateComprefJob(Flow deduplicateComprefFlow) {
        return jobBuilderFactory.get("deduplicateComprefJob")
                .incrementer(new RunIdIncrementer())
                .start(deduplicateComprefFlow)
                .end()  //builds FlowJobBuilder instance
                .build(); //builds Job instance
    }

    /**
     * Deduplicate communication preferences  flow
     *
     * @return Flow communication preferences flow
     */
    @Bean
    public Flow deduplicateComprefFlow( Flow flowCommunicationPreferences,Flow flowDeleteMarketLanguage) {
        return new FlowBuilder<SimpleFlow>("deduplicateComprefFlow")
                .split(taskExecutor())
                .add(flowCommunicationPreferences, flowDeleteMarketLanguage)
                .build();
    }


    /**
     * Flow communication preferences
     *
     * @return Flow communication preferences   flow
     */
    @Bean
    public Flow flowCommunicationPreferences(Step communicationPreferencesStep) {
        return new FlowBuilder<SimpleFlow>("flowCommunicationPreferences")
                .start(communicationPreferencesStep)
                .build();
    }


    /**
     * Flow delete market language
     *
     * @return Flow market language  delete flow
     */
    @Bean
    public Flow flowDeleteMarketLanguage(Step marketLanguageStep) {
        return new FlowBuilder<SimpleFlow>("flowDeleteMarketLanguageStep")
                .start(marketLanguageStep)
                .build();
    }

    /**
     * Communication preferences batch step
     *
     * @return Step communication preferences  step
     */
    @Bean
    public Step communicationPreferencesStep(ItemReader<CommunicationPreferencesModel> communicationPreferencesItemReader)
    {
        return stepBuilderFactory.get("communicationPreferencesStep").<CommunicationPreferencesModel, CommunicationPreferencesModel>chunk(CHUNK_SIZE)
                .reader(communicationPreferencesItemReader)
                .processor(deduplicateComprefProcessor())
                .writer(deduplicateComprefWriter())
                .transactionManager(platformTransactionManager)
                .taskExecutor(taskExecutor())
                .build();
    }

    /**
     * Delete market language batch step
     *
     * @return Step Market Language
     */
    @Bean
    public Step marketLanguageStep( ItemReader<MarketLanguageModel> marketlanguageItemReader)
    {
        return stepBuilderFactory.get("marketLanguageStep").<MarketLanguageModel, MarketLanguageModel>chunk(CHUNK_SIZE)
                .reader(marketlanguageItemReader)
                .processor(deleteMarketLanguageProcessor())
                .writer(deleteMarketLanguageWriter())
                .transactionManager(platformTransactionManager)
                .taskExecutor(taskExecutor())
                .build();
    }

    /**
     * createJdbcPagingItemReader : Extracted method that will read from DB using given sql query. (to avoid duplication)
     * @param beanRowMapper beanRowMapper
     * @param selectClause selectClause
     * @param fromClause fromClause
     * @param whereClause whereClause
     * @param sortKey sortKey
     * @return JdbcPagingItemReader
     * @param <T> Template parameter
     */
    private <T> JdbcPagingItemReader<T> createJdbcPagingItemReader(Class<T>  beanRowMapper, String selectClause, String fromClause, String whereClause, String sortKey,Map<String, Object> jobParameters) {

        // Job parameters
        var comType = jobParameters.get("comType");
        var domain = jobParameters.get("domain");
        var comGroupType = jobParameters.get("comGroupType");

        JdbcPagingItemReader<T> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setPageSize(PAGE_LIMIT_SELECTION);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("endPos", this.limitSelection);
        parameterValues.put("comType", comType);
        parameterValues.put("comGroupType", comGroupType);
        parameterValues.put("domain", domain);

        reader.setRowMapper(new BeanPropertyRowMapper<>(beanRowMapper));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause(selectClause);
        queryProvider.setFromClause(fromClause);
        queryProvider.setWhereClause(whereClause);
        queryProvider.setSortKeys(Collections.singletonMap(sortKey, Order.DESCENDING));

        reader.setParameterValues(parameterValues);
        reader.setQueryProvider(queryProvider);

        return reader;
    }

    /**
     * Communication preferences  reader
     *
     * @return ItemReader<CommunicationPreferences> Communication preferences reader
     */
    @Bean
    @StepScope
    public ItemReader<CommunicationPreferencesModel> communicationPreferencesItemReader( @Value("#{jobParameters}") Map<String, Object> jobParameters)
    {
        String selectClause = "SELECT cp1.sgin as gin, MIN(cp1.com_pref_id) as comPrefId,:comType as comType ";
        String fromClause = """
                 FROM (
                        SELECT cp.sgin as gin, COUNT(*) as nbcp FROM COMMUNICATION_PREFERENCES cp
                        WHERE cp.domain = :domain
                        AND cp.com_group_type = :comGroupType
                        AND cp.com_type = :comType
                        GROUP BY cp.sgin) stats, COMMUNICATION_PREFERENCES cp1
                """;
        String whereClause = """
                stats.nbcp >= 2
                AND cp1.sgin IS NOT NULL
                AND cp1.sgin = stats.gin
                AND cp1.domain = :domain
                AND cp1.com_group_type = :comGroupType
                AND cp1.com_type = :comType
                AND ROWNUM < :endPos
                GROUP BY cp1.sgin
                """;
        String orderByClause = "gin";
        return createJdbcPagingItemReader(CommunicationPreferencesModel.class, selectClause, fromClause, whereClause, orderByClause,jobParameters);
    }

    /**
     * Market language  reader
     *
     * @return ItemReader<MarketLanguage> Market language  reader
     */
    @Bean
    @StepScope
    public ItemReader<MarketLanguageModel> marketlanguageItemReader(@Value("#{jobParameters}") Map<String, Object> jobParameters)
    {
        String selectClause = "SELECT ml2.com_pref_id as comPrefId, MAX(ml2.market_language_id) as marketLanguageId  ";
        String fromClause = """
                 FROM sic2.market_language ml2,
                      (SELECT ml.market as market1, ml.language_code as lang1, ml.com_pref_id as cp_id, COUNT (*) as dupe from sic2.market_language ml
                      INNER JOIN sic2.communication_preferences cp
                      ON cp.com_pref_id = ml.com_pref_id
                      WHERE cp.domain = :domain
                      AND cp.com_group_type = :comGroupType
                      AND cp.com_type =:comType
                      GROUP BY ml.market, ml.language_code, ml.com_pref_id) dupe_ml
                """;
        String whereClause = """
                dupe_ml.dupe > 1
                  AND ml2.market = dupe_ml.market1
                  AND ml2.language_code = dupe_ml.lang1
                  AND ml2.com_pref_id = dupe_ml.cp_id
                  AND ROWNUM < :endPos
                  GROUP BY ml2.com_pref_id
                """;
        String orderByClause = "comPrefId";
        return createJdbcPagingItemReader(MarketLanguageModel.class, selectClause, fromClause, whereClause, orderByClause,jobParameters);
    }

    /**
     * Task executor (We want our threads to be managed by spring and specify number of max thread)
     *
     * @return Task executor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-deduplicate-communication-preferences-");
        return executor;
    }

    /**
     * Instantiates  and inject deduplicate communication preferences bean in spring context to be managed (IoC)
     *
     * @return Deduplicate Communication preference
     */
    @Bean
    public BatchDeduplicateCompref batchDeduplicateCompref()
    {
        return new BatchDeduplicateCompref();
    }


    /**
     * Instantiates  and inject deduplicate communication preferences Processor bean in spring context to be managed (IoC)
     *
     * @return Deduplicate Communication Preferences Processor
     */
    @Bean
    public ItemProcessor<CommunicationPreferencesModel, CommunicationPreferencesModel> deduplicateComprefProcessor()
    {
        return new DeduplicateComprefProcessor();
    }

    /**
     * Instantiates  and inject deduplicate communication preferences Writer bean in spring context to be managed (IoC)
     *
     * @return Deduplicate Communication Preferences
     */
    @Bean
    public ItemWriter<CommunicationPreferencesModel> deduplicateComprefWriter()
    {

        return new DeduplicateComprefWriter();
    }

    /**
     * Instantiates  and inject delete market language Processor bean in spring context to be managed (IoC)
     *
     * @return DeleteMarketLanguageProcessor delete market language processor
     */
    @Bean
    public ItemProcessor<MarketLanguageModel, MarketLanguageModel> deleteMarketLanguageProcessor()
    {
        return new DeleteMarketLanguageProcessor();
    }

    /**
     * Instantiates  and inject delete market language Writer bean in spring context to be managed (IoC)
     *
     * @return DeleteMarketLanguageWriter delete market language writer
     */
    @Bean
    public ItemWriter<MarketLanguageModel> deleteMarketLanguageWriter()
    {
        return new DeleteMarketLanguageWriter();
    }
}
