package com.airfrance.batch.purgemyaccount.config;

import com.airfrance.batch.common.metric.SummaryService;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.purgemyaccount.BatchPurgeMyAccount;
import com.airfrance.batch.purgemyaccount.model.AcPhysicalDelete;
import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.batch.purgemyaccount.model.MyaPursBackup;
import com.airfrance.batch.purgemyaccount.model.RcPhysicalDelete;
import com.airfrance.batch.purgemyaccount.model.PrPhysicalDelete;
import com.airfrance.batch.purgemyaccount.model.PayPhysicalDelete;
import com.airfrance.batch.purgemyaccount.processor.BackupMyaLogicalProcessor;
import com.airfrance.batch.purgemyaccount.processor.MyaLogicalDeleteProcessor;
import com.airfrance.batch.purgemyaccount.service.PurgeMyAccountService;
import com.airfrance.batch.purgemyaccount.tasklet.AccountDataToDeleteTasklet;
import com.airfrance.batch.purgemyaccount.tasklet.ViewAccountPurgeTasklet;
import com.airfrance.batch.purgemyaccount.writer.AcPhysicalDeleteWriter;
import com.airfrance.batch.purgemyaccount.writer.PayPhysicalDeleteWriter;
import com.airfrance.batch.purgemyaccount.writer.PrPhysicalDeleteWriter;
import com.airfrance.batch.purgemyaccount.writer.RcPhysicalDeleteWriter;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import static com.airfrance.batch.purgemyaccount.helper.Constant.*;

@ComponentScan(basePackages = {	"com.airfrance.batch.purgemyaccount"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class PurgeMyAccountConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Qualifier("transactionManagerRepind")
    private final PlatformTransactionManager transactionManagerRepind;
    @Qualifier("dataSourceRepind")
    private final DataSource dataSourceRepind;
    private final VariablesDS variablesDS;


    private static final String DELIMITER = ";" ;
    private static final int CHUNK_SIZE = 100;
    public static final int STEP_THREAD_NUMBER = 40;
    public static final int STEP_MAX_THREAD_NUMBER = 100;
    private static final String PURGE_MYA_BATCH_MAX_SIZE = "PURGE_MYA_BATCH_MAX_SIZE" ;
    private final Integer limitSelection;
    private final Integer defaultLimitSelection = 1000;


    @Autowired
    public PurgeMyAccountConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                PlatformTransactionManager transactionManagerRepind, DataSource dataSourceRepind,
                                VariablesDS variablesDS) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.transactionManagerRepind = transactionManagerRepind;
        this.dataSourceRepind = dataSourceRepind;
        this.variablesDS = variablesDS;
        int limitSelectionTmp;
        try{
            limitSelectionTmp = Integer.parseInt(variablesDS.getByEnvKey(PURGE_MYA_BATCH_MAX_SIZE).getEnvValue());
        } catch (JrafDomainException e) {
            limitSelectionTmp = defaultLimitSelection;
        }
        this.limitSelection = limitSelectionTmp;
        log.info("[+] PurgeMyA max size = {}", this.limitSelection);
    }


    // ############################# JOB 1  LOGICAL DELETION #########################################

    /**
     * JOB 1 : LOGICAL DELETION , will execute first step to create view then  will execute in parallel the creation
     * of backup file and do logical delete in both table ACCOUNT_DATA and ROLE_CONTRATS
     * @param initStepLogical : init step to drop+create view MYA_TO_PURGE
     * @param backupMyaLogicalStep : backupMyaLogicalStep
     * @param deleteMyaLogicalStep : deleteMyaLogicalStep
     * @return Job
     */
    @Bean
    public Job purgeMyAccountLogicalJob(@Qualifier("initStepLogical") Step initStepLogical,
                                        @Qualifier("backupMyaLogicalStep") Step backupMyaLogicalStep,
                                        @Qualifier("deleteMyaLogicalStep") Step deleteMyaLogicalStep){
        return jobBuilderFactory.get("purgeMyAccountLogicalJob")
                .incrementer(new RunIdIncrementer())
                .start(initStepLogical)
                .next(backupMyaLogicalStep)
                .next(deleteMyaLogicalStep)
                .build();
    }


    /**
     * Step 1 : initStepLogical - tasklet that drop+create view MYA_TO_PURGE
     * @return Step
     */
    @Bean
    public Step initStepLogical() {
        return stepBuilderFactory.get("initStepLogical")
                .tasklet(viewAccountPurgeTasklet())
                .build();
    }


    /**
     * Step 2 : backupMyaLogicalStep - creation of backup file
     * @return Step
     */
    @Bean
    public Step backupMyaLogicalStep(ItemReader<MyaPursBackup> backupMyaLogicalReader,
                                     ItemProcessor<MyaPursBackup, MyaPursBackup> backupMyaLogicalProcessor,
                                     ItemWriter<MyaPursBackup> csvBackupMyaWriter){
        return stepBuilderFactory.get("backupMyaLogicalStep")
                .<MyaPursBackup, MyaPursBackup>chunk(CHUNK_SIZE)
                .reader(backupMyaLogicalReader)
                .processor(backupMyaLogicalProcessor)
                .writer(csvBackupMyaWriter)
                .transactionManager(transactionManagerRepind)
                .taskExecutor(taskExecutor())
                .build();
    }

    /**
     * Step 3 : deleteMyaLogicalStep - logical delete on table ACCOUNT_DATA and ROLE_CONTRATS
     * @return Step
     */
    @Bean
    public Step deleteMyaLogicalStep(ItemReader<MyaLogicalToDelete> myaLogicalDeleteReader,
                                     ItemProcessor<MyaLogicalToDelete, MyaLogicalToDelete> myaLogicalDeleteProcessor,
                                     ItemWriter<MyaLogicalToDelete> csvMyaUpdatedItemWriter){
        return stepBuilderFactory.get("deleteMyaLogicalStep")
                .<MyaLogicalToDelete, MyaLogicalToDelete>chunk(CHUNK_SIZE)
                .reader(myaLogicalDeleteReader)
                .processor(myaLogicalDeleteProcessor)
                .writer(csvMyaUpdatedItemWriter)
                .transactionManager(transactionManagerRepind)
                .taskExecutor(taskExecutor())
                .build();
    }



    // ############################# JOB 2 : Physical deletion  ############################################
    /**
     * JOB 2 : physical DELETION Job, will create two Steps that will be processed in parallel using splitFlow
     * flowPhysicalDeleteAccountData: will make call to step 'physicalDeleteAccountDataStep'
     * flowPhysicalDeleteRoleContrat : will make call to step 'physicalDeleteRoleContratStep'
     * @return Job
     */

    @Bean
    public Job purgeMyAccountPhysicalJob() {
        Flow initStepPhysical = new FlowBuilder<SimpleFlow>("flowInitPhysical")
                .start(initStepPhysical())
                .build();
        return jobBuilderFactory.get("purgeMyAccountPhysicalJob")
                .incrementer(new RunIdIncrementer())
                .start(initStepPhysical)
                .next(physicalSplitFlow())
                .end()  //builds FlowJobBuilder instance
                .build(); //builds Job instance
    }

    /**
     * SPLIT FLOW : physicalSplitFlow
     * @return Flow
     */
    @Bean
    public Flow physicalSplitFlow() {
        return new FlowBuilder<SimpleFlow>("physicalSplitFlow")
                .split(taskExecutor())
                .add(flowPhysicalDeleteAccountData(), flowPhysicalDeleteRoleContrat(),
                        flowPhysicalDeletePreference(), flowPhysicalDeletePayment())
                .build();
    }

    /**
     * Flow flowPhysicalDeleteAccountData : Flow that call Step 'physicalDeleteAccountDataStep'
     * @return Flow
     */
    @Bean
    public Flow flowPhysicalDeleteAccountData() {
        return new FlowBuilder<SimpleFlow>("flowPhysicalDeleteAccountData")
                .start(physicalDeleteAccountDataStep())
                .build();
    }

    /**
     * Flow flowPhysicalDeleteRoleContrat : Flow that call Step 'physicalDeleteRoleContratStep'
     * @return Flow
     */
    @Bean
    public Flow flowPhysicalDeleteRoleContrat() {
        return new FlowBuilder<SimpleFlow>("flowPhysicalDeleteRoleContrat")
                .start(physicalDeleteRoleContratStep())
                .build();
    }

    /**
     * Flow flowPhysicalDeletePreference : Flow that call Step 'physicalDeletePreferenceStep'
     * @return Flow
     */
    @Bean
    public Flow flowPhysicalDeletePreference() {
        return new FlowBuilder<SimpleFlow>("flowPhysicalDeletePreference")
                .start(physicalDeletePreferenceStep())
                .build();
    }

    /**
     * Flow flowPhysicalDeletePayment : Flow that call Step 'physicalDeletePaymentStep'
     * @return Flow
     */
    @Bean
    public Flow flowPhysicalDeletePayment() {
        return new FlowBuilder<SimpleFlow>("flowPhysicalDeletePayment")
                .start(physicalDeletePaymentStep())
                .build();
    }

    /**
     * TASKLET : accountDataToDelete
     * @return Step
     */
    @Bean
    public Step initStepPhysical() {
        return stepBuilderFactory.get("initStepPhysical")
                .tasklet(accountDataToDeleteTasklet())
                .build();
    }

    /**
     * physicalDeleteAccountDataStep : This Step delete physically account_data
     * @return Step
     */
    @Bean
    public Step physicalDeleteAccountDataStep(){
        return stepBuilderFactory.get("acPhysicalDeleteStep")
                .<AcPhysicalDelete, AcPhysicalDelete>chunk(CHUNK_SIZE)
                .reader(acPhysicalToDeleteReader())
                .writer(acPhysicalDeleteItemWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    /**
     * physicalDeleteRoleContratStep : This Step delete physically role_contrats
     * @return Step
     */
    @Bean
    public Step physicalDeleteRoleContratStep(){
        return stepBuilderFactory.get("rcPhysicalDeleteStep")
                .<RcPhysicalDelete, RcPhysicalDelete>chunk(CHUNK_SIZE)
                .reader(rcPhysicalToDeleteReader())
                .writer(rcPhysicalDeleteItemWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    /**
     * physicalDeletePreferenceStep : This Step delete physically preferences
     * @return Step
     */
    @Bean
    public Step physicalDeletePreferenceStep(){
        return stepBuilderFactory.get("prPhysicalDeleteStep")
                .<PrPhysicalDelete, PrPhysicalDelete>chunk(CHUNK_SIZE)
                .reader(prPhysicalToDeleteReader())
                .writer(prPhysicalDeleteItemWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }

    /**
     * physicalDeletePaymentPreferenceStep : This Step delete physically payment preferences
     * @return Step
     */
    @Bean
    public Step physicalDeletePaymentStep(){
        return stepBuilderFactory.get("payPhysicalDeleteStep")
                .<PayPhysicalDelete, PayPhysicalDelete>chunk(CHUNK_SIZE)
                .reader(payPhysicalToDeleteReader())
                .writer(payPhysicalDeleteItemWriter())
                .transactionManager(transactionManagerRepind)
                .build();
    }


    /**
     * READER1 : backup des MYA purs à purger non connectés depuis 2 ans
     * @return ItemReader<MyaPursToPurge>
     */
    @Bean
    @StepScope
    public ItemReader<MyaPursBackup> backupMyaLogicalReader() {

        String selectClause = " SELECT ac.ID AS acId, ac.IVERSION AS acIversion, ac.ACCOUNT_IDENTIFIER AS acAccountIdentifier, ac.SGIN AS acSgin, ac.FB_IDENTIFIER AS acFbIdentifier, ac.EMAIL_IDENTIFIER AS acEmailIdentifier, ac.PERSONALIZED_IDENTIFIER AS acPersonalizedIdentifier, ac.STATUS AS acStatus, ac.PASSWORD AS acPassword, ac.PASSWORD_TO_CHANGE AS acPasswordToChange, ac.TEMPORARY_PWD AS acTmpPwd, ac.TEMPORARY_PWD_END_DATE AS acTmpPwdEndDate, ac.SECRET_QUESTION_OLD AS acSecretQuestionOld, ac.SECRET_QUESTION_ANSWER AS acSecretQuestionAnswer, ac.NB_FAILURE_AUTHENTIFICATION AS acNbFailureAuthentification,ac.NB_FAILURE_SECRET_QUESTION_ANS AS acNbFailureSecretQuestionAns, ac.ENROLEMENT_POINT_OF_SELL AS acEnrolementPointOfSell, ac.CARRIER AS acCarrier, ac.LAST_CONNECTION_DATE AS acLastConnectionDate, ac.LAST_PWD_RESET_DATE AS acLastPwdResetDate, ac.ACCOUNT_DELETION_DATE AS acCountDeletionDate, ac.SSITE_CREATION AS acSiteCreation, ac.SSIGNATURE_CREATION AS acSignatureCreation, ac.DDATE_CREATION AS acDateCreation, ac.SSITE_MODIFICATION AS acSiteModification, ac.SSIGNATURE_MODIFICATION AS acSignatureModification, ac.DDATE_MODIFICATION AS acDateModification, ac.IP_ADDRESS AS acIpAddress, ac.ACCOUNT_UPGRADE_DATE AS acAccountUpgradeDate, ac.SECRET_QUESTION AS acSecretQuestion, ac.ACCOUNT_LOCKED_DATE AS acAccountLockedDDate, ac.SECRET_QUESTION_ANSWER_UPPER AS acSecretQuestionAnswerUpper, ac.LAST_SECRET_ANSW_MODIFICATION AS acLastSecretAnswModification, ac.SOCIAL_NETWORK_ID AS acSocialNetworkId, ac.LAST_SOCIAL_NETWORK_LOGON_DATE AS acLastSocialNetworkLogonDate, ac.LAST_SOCIAL_NETWORK_USED AS acLastSocialNetworkUsed, ac.LAST_SOCIAL_NETWORK_ID AS acLastSocialNetworkId, rc.SRIN AS rcRin, rc.IVERSION AS rcIversion, rc.SNUMERO_CONTRAT AS rcNumeroContrat, rc.SGIN AS rcSgin, rc.SETAT AS rcSetat, rc.STYPE_CONTRAT AS rcStypeContrat, rc.SSOUS_TYPE AS rcSsoustype, rc.STIER AS rcStier, rc.SCODE_COMPAGNIE AS rcScodeCompagnie, rc.IVERSION_PRODUIT AS rcIversionProduit, rc.DFIN_VALIDITE AS rcDfinValidite, rc.DDEBUT_VALIDITE AS rcDdebutValidite, rc.SFAMILLE_TRAITEMENT AS rcSfamilleTraitement, rc.SFAMILLE_PRODUIT AS rcSfamilleProduit, rc.ICLE_ROLE AS rcIcleRole, rc.DDATE_CREATION AS rcDdateCreation, rc.SSIGNATURE_CREATION AS rcSsignatureCreation, rc.DDATE_MODIFICATION AS rcDdateModification, rc.SSIGNATURE_MODIFICATION AS rcSsignatureModification, rc.SSITE_CREATION AS rcSsiteCreation, rc.SSITE_MODIFICATION AS rcSsiteModification, rc.SAGENCE_IATA AS rcSagenceIata, rc.IATA AS rcIata, rc.SSOURCE_ADHESION AS rcSsourceAdhesion, rc.SPERMISSION_PRIME AS rcSpermissionPrime, rc.ISOLDE_MILES AS rcIsoldeMiles, rc.IMILES_QUALIF AS rcImilesQualif, rc.IMILES_QUALIF_PREC AS rcImilesQualifPrec, rc.ISEGMENTS_QUALIF AS rcIsegmentsQualif, rc.ISEGMENTS_QUALIF_PREC AS rcIsegmentsQualifPrec, rc.CUSCO_CREATED AS rcCuscoCreated, rc.SMEMBER_TYPE AS rcSmemberType ";
        String fromClause = " FROM SIC2.TMP_MYA_TO_PURGE ac inner join SIC2.role_contrats rc on rc.SGIN = ac.SGIN ";
        String whereClause = " WHERE rownum < :endPosition and rc.STYPE_CONTRAT= 'MA' ";
        String orderByClause = "acDateCreation";

        return createJdbcPagingItemReader(MyaPursBackup.class, selectClause, fromClause, whereClause, orderByClause);
    }

    /**
     * READER2 : get myaccount purs to be logically deleted
     * @return ItemReader<MyaLogicalToDelete>
     */
    @Bean
    @StepScope
    public ItemReader<MyaLogicalToDelete> myaLogicalDeleteReader() {

        String selectClause = " SELECT distinct ad.ID as id, ad.IVERSION as iversion, ad.SGIN as sgin, ad.STATUS as status, ad.DDATE_MODIFICATION as ddateModification,  rc.SRIN as srin, rc.SETAT as setat, rc.SNUMERO_CONTRAT as snumeroContrat, rc.STYPE_CONTRAT as stypeContrat, ad.DDATE_CREATION as ddateCreation ";
        String fromClause = " FROM SIC2.TMP_MYA_TO_PURGE ad inner join SIC2.role_contrats rc on rc.SGIN = ad.sgin ";
        String whereClause = "WHERE ad.STATUS in ('V', 'E') and  rc.STYPE_CONTRAT= 'MA' and rownum < :endPosition ";
        String orderByClause = "ddateCreation";

        return createJdbcPagingItemReader(MyaLogicalToDelete.class, selectClause, fromClause, whereClause, orderByClause);
    }

    /**
     * READER3 : get accountdata to be physically deleted
     * @return ItemReader<MyaLogicalToDelete>
     */
    @Bean
    @StepScope
    public ItemReader<AcPhysicalDelete> acPhysicalToDeleteReader() {
        String selectClause = "SELECT ac.ID as id, ac.ACCOUNT_IDENTIFIER as accountIdentifier ";
        String fromClause = "FROM SIC2.DQ_ACCOUNT_DATA_TO_DELETE ac ";
        String whereClause = "where ROWNUM <= :endPosition";
        String sortKeys = "id";
        return createJdbcPagingItemReader(AcPhysicalDelete.class, selectClause, fromClause, whereClause, sortKeys);
    }


    /**
     * READER4 : get roleContrats to be physically deleted
     * @return ItemReader<MyaLogicalToDelete>
     */
    @Bean
    @StepScope
    public ItemReader<RcPhysicalDelete> rcPhysicalToDeleteReader() {
        String selectClause = " SELECT rc.ICLE_ROLE as icleRole ";
        String fromClause = " FROM  SIC2.DQ_ACCOUNT_DATA_TO_DELETE ad LEFT JOIN SIC2.ROLE_CONTRATS rc ON ad.ACCOUNT_IDENTIFIER = rc.SNUMERO_CONTRAT ";
        String whereClause = "WHERE rc.STYPE_CONTRAT = 'MA' and ROWNUM <= :endPosition";
        String orderByClause = "icleRole";

        return createJdbcPagingItemReader(RcPhysicalDelete.class, selectClause, fromClause, whereClause, orderByClause);
    }

    /**
     * READER5 : get preferences to be physically deleted
     * @return ItemReader<MyaLogicalToDelete>
     */
    @Bean
    @StepScope
    public ItemReader<PrPhysicalDelete> prPhysicalToDeleteReader() {
        String selectClause = " SELECT pr.PREFERENCE_ID as preferenceId ";
        String fromClause = " FROM  SIC2.DQ_ACCOUNT_DATA_TO_DELETE ad INNER JOIN SIC2.PREFERENCE pr ON ad.SGIN = pr.SGIN ";
        String whereClause = "WHERE ROWNUM <= :endPosition";
        String orderByClause = "preferenceId";

        return createJdbcPagingItemReader(PrPhysicalDelete.class, selectClause, fromClause, whereClause, orderByClause);
    }

    /**
     * READER6 : get payments to be physically deleted
     * @return ItemReader<MyaLogicalToDelete>
     */
    @Bean
    @StepScope
    public ItemReader<PayPhysicalDelete> payPhysicalToDeleteReader() {
        String selectClause = " SELECT ad.SGIN as gin ";
        String fromClause = " FROM  SIC2.DQ_ACCOUNT_DATA_TO_DELETE ad ";
        String whereClause = "WHERE ROWNUM <= :endPosition";
        String orderByClause = "gin";

        return createJdbcPagingItemReader(PayPhysicalDelete.class, selectClause, fromClause, whereClause, orderByClause);
    }



    /**
     * WRITER1 : csvBackupMyaWriter write backups into csv files
     * @param outputPath output path to write backup file
     * @param resultBackupFilename backup filename
     * @return FlatFileItemWriter<MyaPursToPurge>
     */
    @Bean
    @StepScope
    public FlatFileItemWriter<MyaPursBackup> csvBackupMyaWriter(@Value("#{jobParameters['outputPath']}") String outputPath,
                                                                @Value("#{jobParameters['resultBackupFilename']}") String resultBackupFilename) {
        return createFlatFileItemWriter(outputPath, resultBackupFilename, MYA_BACKUP_HEADER, MYA_BACKUP_WRAPPER_FIELDS);
    }

    /**
     * WRITER2 : csvMyaUpdatedItemWriter write successfully logical deleted MYA
     * @param outputPath output path to write backup file
     * @param resultSuccessFilename backup filename
     * @return FlatFileItemWriter<MyaPursToPurge>
     */
    @Bean
    @StepScope
    public FlatFileItemWriter<MyaLogicalToDelete> csvMyaUpdatedItemWriter(@Value("#{jobParameters['outputPath']}") String outputPath,
                                                                          @Value("#{jobParameters['resultSuccessFilename']}") String resultSuccessFilename) {
        return createFlatFileItemWriter(outputPath, resultSuccessFilename, MYA_SUCCESS_LOGICAL_DELETE_HEADER, MYA_SUCCESS_LOGICAL_DELETE_WRAPPER_FIELDS);
    }




    // BEAN DEFINITION
    @Bean
    public BatchPurgeMyAccount batchPurgeMyAccount()
    {
        return new BatchPurgeMyAccount();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(STEP_THREAD_NUMBER);
        executor.setMaxPoolSize(STEP_MAX_THREAD_NUMBER);
        executor.setQueueCapacity(STEP_MAX_THREAD_NUMBER * CHUNK_SIZE);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("MultiThreaded-purge-myaccount");
        return executor;
    }
    /**
     *  Bean definition for tasklet 1 : drop + creation of TABLE TMP_MYA_TO_PURGE
     * @return ViewAccountPurgeTasklet
     */
    @Bean
    public ViewAccountPurgeTasklet viewAccountPurgeTasklet() {
        return new ViewAccountPurgeTasklet(new JdbcTemplate(dataSourceRepind));
    }
    /**
     *  Bean definition for tasklet 2 : drop + creation of TABLE DQ_ACCOUNT_DATA_TO_DELETE
     * @return AccountDataToDeleteTasklet
     */
    @Bean
    public AccountDataToDeleteTasklet accountDataToDeleteTasklet() {
        return new AccountDataToDeleteTasklet(new JdbcTemplate(dataSourceRepind), limitSelection);
    }

    @Bean
    public PurgeMyAccountService purgeMyAccountService() {
        return new PurgeMyAccountService();
    }
    @Bean
    public SummaryService summaryService() {
        return new SummaryService();
    }
    @Bean
    public ItemProcessor<MyaLogicalToDelete, MyaLogicalToDelete> myaLogicalDeleteProcessor() {
        return new MyaLogicalDeleteProcessor();
    }
    @Bean
    public ItemProcessor<MyaPursBackup, MyaPursBackup> backupMyaLogicalProcessor() {
        return new BackupMyaLogicalProcessor();
    }
    @Bean
    public ItemWriter<AcPhysicalDelete> acPhysicalDeleteItemWriter() {
        return new AcPhysicalDeleteWriter();
    }
    @Bean
    public ItemWriter<RcPhysicalDelete> rcPhysicalDeleteItemWriter() {
        return new RcPhysicalDeleteWriter();
    }
    @Bean
    public ItemWriter<PrPhysicalDelete> prPhysicalDeleteItemWriter() {
        return new PrPhysicalDeleteWriter();
    }
    @Bean
    public ItemWriter<PayPhysicalDelete> payPhysicalDeleteItemWriter() {
        return new PayPhysicalDeleteWriter();
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
    private <T> JdbcPagingItemReader<T> createJdbcPagingItemReader(Class<T>  beanRowMapper, String selectClause, String fromClause, String whereClause, String sortKey) {
        JdbcPagingItemReader<T> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSourceRepind);
        reader.setPageSize(limitSelection);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("endPosition", limitSelection);

        reader.setRowMapper(new BeanPropertyRowMapper<>(beanRowMapper));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause(selectClause);
        queryProvider.setFromClause(fromClause);
        queryProvider.setWhereClause(whereClause);
        queryProvider.setSortKeys(Collections.singletonMap(sortKey, Order.ASCENDING));

        reader.setParameterValues(parameterValues);
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
        delimitedLineAggregator.setDelimiter(DELIMITER);
        delimitedLineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);

        return flatFileItemWriter;
    }


}
