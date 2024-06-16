package com.airfrance.batch.kpicalculation.config;

import com.airfrance.batch.common.exception.ConfigurationException;
import com.airfrance.batch.config.ConfigBatch;
import com.airfrance.batch.config.JpaPPConfig;
import com.airfrance.batch.config.JpaRepindConfig;
import com.airfrance.batch.config.JpaRepindUtf8Config;
import com.airfrance.batch.kpicalculation.BatchKPICalculation;
import com.airfrance.batch.common.entity.kpi.HistorizationKPIEntity;
import com.airfrance.batch.kpicalculation.model.KPIStatModel;
import com.airfrance.batch.kpicalculation.processor.KPICalculationProcessor;
import com.airfrance.batch.kpicalculation.processor.OldDataProcessor;
import com.airfrance.batch.kpicalculation.writer.KPICalculationWriter;
import com.airfrance.batch.kpicalculation.writer.OldDataWriter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.OraclePagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collections;

@ComponentScan(basePackages = {"com.airfrance.batch.kpicalculation"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class, JpaRepindConfig.class, JpaRepindUtf8Config.class, JpaPPConfig.class})
@Slf4j
public class KPICalculationConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dataSourceRepind")
    private DataSource dataSource;

    @Autowired
    @Qualifier("transactionManagerRepind")
    private PlatformTransactionManager platformTransactionManager;

    private static final int CHUNK_SIZE = 50;

    @Bean
    public Job kpiCalculationJob(@Qualifier("numberOfIndividualStep") Step numberOfIndividualStep,
                                 @Qualifier("numberOfContractStep") Step numberOfContractStep,
                                 @Qualifier("numberOfComPrefStep") Step numberOfComPrefStep,
                                 @Qualifier("numberOfForgetMeStep") Step numberOfForgetMeStep,
                                 @Qualifier("numberOfIndividualCreationStep") Step numberOfIndividualCreationStep,
                                 @Qualifier("cleanOldDataStep") Step cleanOldDataStep) {
        return jobBuilderFactory.get("kpiCalculationJob")
                .incrementer(new RunIdIncrementer())
                .start(numberOfIndividualStep)
                .next(numberOfContractStep)
                .next(numberOfComPrefStep)
                .next(numberOfForgetMeStep)
                .next(numberOfIndividualCreationStep)
                .next(cleanOldDataStep)
                .build();
    }

    @Bean
    public Step numberOfIndividualStep(ItemReader<KPIStatModel> kpiCalculationIndividualNumberReader,
                                       ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor,
                                       ItemWriter<KPIStatModel> kpiCalculationWriter) {
        return stepBuilderFactory.get("numberOfIndividualStep").<KPIStatModel, KPIStatModel>chunk(CHUNK_SIZE)
                .reader(kpiCalculationIndividualNumberReader)
                .processor(kpiCalculationProcessor)
                .writer(kpiCalculationWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step numberOfContractStep(ItemReader<KPIStatModel> kpiCalculationContractNumberReader,
                                     ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor,
                                     ItemWriter<KPIStatModel> kpiCalculationWriter) {
        return stepBuilderFactory.get("numberOfContractStep").<KPIStatModel, KPIStatModel>chunk(CHUNK_SIZE)
                .reader(kpiCalculationContractNumberReader)
                .processor(kpiCalculationProcessor)
                .writer(kpiCalculationWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step numberOfComPrefStep(ItemReader<KPIStatModel> kpiCalculationComPrefNumberReader,
                                    ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor,
                                    ItemWriter<KPIStatModel> kpiCalculationWriter) {
        return stepBuilderFactory.get("numberOfComPrefStep").<KPIStatModel, KPIStatModel>chunk(CHUNK_SIZE)
                .reader(kpiCalculationComPrefNumberReader)
                .processor(kpiCalculationProcessor)
                .writer(kpiCalculationWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step numberOfForgetMeStep(ItemReader<KPIStatModel> kpiCalculationForgetMeNumberReader,
                                    ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor,
                                    ItemWriter<KPIStatModel> kpiCalculationWriter) {
        return stepBuilderFactory.get("numberOfForgetMeStep").<KPIStatModel, KPIStatModel>chunk(CHUNK_SIZE)
                .reader(kpiCalculationForgetMeNumberReader)
                .processor(kpiCalculationProcessor)
                .writer(kpiCalculationWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step numberOfIndividualCreationStep(ItemReader<KPIStatModel> kpiCalculationIndividualCreationNumberReader,
                                     ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor,
                                     ItemWriter<KPIStatModel> kpiCalculationWriter) {
        return stepBuilderFactory.get("numberOfIndividualCreationStep").<KPIStatModel, KPIStatModel>chunk(CHUNK_SIZE)
                .reader(kpiCalculationIndividualCreationNumberReader)
                .processor(kpiCalculationProcessor)
                .writer(kpiCalculationWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public Step cleanOldDataStep(ItemReader<HistorizationKPIEntity> oldDataReader,
                                 ItemProcessor<HistorizationKPIEntity, HistorizationKPIEntity> oldDataProcessor,
                                 ItemWriter<HistorizationKPIEntity> oldDataWriter) {
        return stepBuilderFactory.get("cleanOldDataStep").<HistorizationKPIEntity, HistorizationKPIEntity>chunk(CHUNK_SIZE)
                .reader(oldDataReader)
                .processor(oldDataProcessor)
                .writer(oldDataWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }
    @Bean
    public BatchKPICalculation batchKpiCalculation() {
        return new BatchKPICalculation();
    }

    @Bean
    @StepScope
    public ItemReader<KPIStatModel> kpiCalculationIndividualNumberReader() {
        log.info("Calculation of the number of individual of each status.");
        log.info("parsing the table INDIVIDUS_ALL...");

        JdbcPagingItemReader<KPIStatModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(KPIStatModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT SSTATUT_INDIVIDU as label, count(*) as value, 'Number of individual' as kpi");
        queryProvider.setFromClause("FROM INDIVIDUS_ALL");
        queryProvider.setGroupClause("GROUP BY SSTATUT_INDIVIDU");

        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<KPIStatModel> kpiCalculationContractNumberReader() {
        log.info("Calculation of the number of gin of each contract type.");
        log.info("parsing the table ROLE_CONTRACTS...");

        JdbcPagingItemReader<KPIStatModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(KPIStatModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT STYPE_CONTRAT as label, \"Number of gin\" as value, 'Number of contract' as kpi");
        queryProvider.setFromClause("FROM (SELECT count(SGIN) as \"Number of gin\", STYPE_CONTRAT FROM role_contrats GROUP BY STYPE_CONTRAT HAVING count(SGIN) > 0)");
        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<KPIStatModel> kpiCalculationComPrefNumberReader() {
        log.info("Calculation of the number of com pref of each type.");
        log.info("parsing the table COMMUNICATION_PREFERENCES...");

        JdbcPagingItemReader<KPIStatModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(KPIStatModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT count(c.COM_TYPE) as value, COALESCE(c.domain,'null') || ' - ' || c.COM_TYPE as label, c.subscribe as optionalData, 'Number of com pref' as kpi ");
        queryProvider.setFromClause("FROM communication_preferences c ");
        queryProvider.setGroupClause("GROUP BY c.domain, c.COM_TYPE, c.subscribe HAVING count(c.COM_TYPE) > 0 ");
        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<KPIStatModel> kpiCalculationForgetMeNumberReader() {
        log.info("Calculation of the number of forget me of each type.");
        log.info("parsing the table Forgotten_individual...");

        JdbcPagingItemReader<KPIStatModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(KPIStatModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT context as label, count(context) as value , 'Number of forgetme' as kpi");
        queryProvider.setFromClause("FROM forgotten_individual ");
        queryProvider.setGroupClause("GROUP BY context HAVING context != 'C'");
        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<KPIStatModel> kpiCalculationIndividualCreationNumberReader() {
        log.info("Calculation of the number of individual creation of each status of today.");
        log.info("parsing the table INDIVIDUS_ALL...");

        JdbcPagingItemReader<KPIStatModel> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(KPIStatModel.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT COUNT(SGIN) as value, STYPE as label, 'Individual created' as kpi");
        queryProvider.setFromClause("FROM individus_all");
        queryProvider.setWhereClause("WHERE TRUNC(DDATE_CREATION) = TRUNC(SYSDATE)");
        queryProvider.setGroupClause("GROUP BY STYPE");
        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<HistorizationKPIEntity> oldDataReader() {
        log.info("Fetch all old data");
        log.info("parsing the table HISTORIZATION_KPI...");

        JdbcPagingItemReader<HistorizationKPIEntity> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(new BeanPropertyRowMapper<>(HistorizationKPIEntity.class));

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("SELECT ID as \"id\", KPI as \"kpi\", LABEL as \"label\", VALUE as \"value\", OPTIONAL_DATA as \"optionalData\", DDATE as \"date\"");
        queryProvider.setFromClause("FROM HISTORIZATION_KPI h");
        queryProvider.setWhereClause("WHERE h.DDATE <= ADD_MONTHS(SYSDATE, -12) ");

        queryProvider.setSortKeys(Collections.singletonMap("value", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);
        return reader;
    }

    @Bean
    public ItemProcessor<KPIStatModel, KPIStatModel> kpiCalculationProcessor() {
        return new KPICalculationProcessor();
    }

    @Bean
    public ItemProcessor<HistorizationKPIEntity, HistorizationKPIEntity> oldDataProcessor() {
        return new OldDataProcessor();
    }

    @Bean
    public ItemWriter<KPIStatModel> kpiCalculationWriter() {
        return new KPICalculationWriter();
    }

    @Bean
    public ItemWriter<HistorizationKPIEntity> oldDataWriter() {
        return new OldDataWriter();
    }
}
