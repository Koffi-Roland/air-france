package com.afklm.batch.deletecontract.config;

import com.airfrance.batch.common.config.ConfigBatch;
import com.airfrance.batch.common.config.JpaPPConfig;
import com.airfrance.batch.common.config.JpaRepindConfig;
import com.airfrance.batch.common.config.JpaRepindUtf8Config;

import com.afklm.batch.deletecontract.DeleteContractsBatch;
import com.afklm.batch.deletecontract.mapper.RoleContratsMapper;
import com.afklm.batch.deletecontract.mapper.RoleUCCRMapper;
import com.afklm.batch.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.afklm.batch.deletecontract.model.DeleteContractsOutputModel;
import com.afklm.batch.deletecontract.processor.DeleteBusinessRoleUCCRProcessor;
import com.afklm.batch.deletecontract.processor.DeleteContractsBatchProcessor;
import com.afklm.batch.deletecontract.service.DeleteContractsBatchService;
import com.afklm.batch.deletecontract.writer.DeleteBusinessRoleUCCRWriter;
import com.afklm.batch.deletecontract.writer.DeleteContractsBatchWriter;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.entity.role.RoleUCCR;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;

@ComponentScan(basePackages = {	"com.afklm.batch.deletecontract"})
@Configuration
@EnableBatchProcessing
@Import({ConfigBatch.class , JpaRepindConfig.class, JpaRepindUtf8Config.class , JpaPPConfig.class})
@Slf4j
public class DeleteContractsBatchConfig {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("transactionManagerRepind")
    private PlatformTransactionManager transactionManagerRepind;

    @Autowired
    @Qualifier("dataSourceRepind")
    DataSource dataSource;

    @Autowired
    private VariablesDS variableDS;

    private static final String PURGEDELAY = "BATCH_DELETE_CONTRACTS_PURGE_DELAY";

    private static final int CHUNK_SIZE = 50;

    @Bean
    public Job deleteContractsBatchJob(Step deleteContractsBatchStep, Step deleteBusinessRoleUCCRStep, JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("deleteContractsBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(deleteContractsBatchStep)
                .next(deleteBusinessRoleUCCRStep)
                .build();
    }

    @Bean
    public Step deleteContractsBatchStep(ItemReader<RoleContrats> deleteContractsBatchReader,
                                         ItemProcessor<RoleContrats, DeleteContractsOutputModel> deleteContractsBatchProcessor,
                                         ItemWriter<DeleteContractsOutputModel> deleteContractsBatchWriter) {
        return stepBuilderFactory.get("deleteContractsBatchStep")
                .<RoleContrats, DeleteContractsOutputModel>chunk(CHUNK_SIZE)
                .reader(deleteContractsBatchReader)
                .processor(deleteContractsBatchProcessor)
                .writer(deleteContractsBatchWriter)
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    public Step deleteBusinessRoleUCCRStep(ItemReader<RoleUCCR> deleteBusinessRoleUCCRReader,
                                         ItemProcessor<RoleUCCR, DeleteBusinessRoleUCCROutputModel> deleteBusinessRoleUCCRProcessor,
                                         ItemWriter<DeleteBusinessRoleUCCROutputModel> deleteBusinessRoleUCCRWriter) {
        return stepBuilderFactory.get("deleteBusinessRoleUCCRStep")
                .<RoleUCCR, DeleteBusinessRoleUCCROutputModel>chunk(CHUNK_SIZE)
                .reader(deleteBusinessRoleUCCRReader)
                .processor(deleteBusinessRoleUCCRProcessor)
                .writer(deleteBusinessRoleUCCRWriter)
                .transactionManager(transactionManagerRepind)
                .build();
    }

    @Bean
    public DeleteContractsBatch deleteContractsBatch() {
        return new DeleteContractsBatch();
    }

    @Bean
    public DeleteContractsBatchService deleteContractsBatchService() {
        return new DeleteContractsBatchService();
    }

    @Bean
    @StepScope
    public ItemReader<RoleContrats> deleteContractsBatchReader() throws JrafDomainException {
        log.info("Parsing the table ROLE_CONTRATS and get all contract with statut X with last date modification < "+getPurgeDelay()+" days");

        final RoleContratsMapper roleContratsMapper = new RoleContratsMapper();

        JdbcPagingItemReader<RoleContrats> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(roleContratsMapper);

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("FROM ROLE_CONTRATS");
        queryProvider.setWhereClause("WHERE SETAT='X' AND DDATE_MODIFICATION<(CURRENT_TIMESTAMP - INTERVAL "+"'"+getPurgeDelay()+"'"+" DAY)");
        queryProvider.setSortKeys(Collections.singletonMap("SRIN", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);

        log.info("Parsing finished successfully");
        return reader;
    }

    private String getPurgeDelay() throws JrafDomainException {
        VariablesDTO variablesDTO = variableDS.getByEnvKey(PURGEDELAY);

        if (variablesDTO != null) {
            if (!StringUtils.isEmpty(variablesDTO.getEnvValue())) {
                int periodInDays = Integer.parseInt(variablesDTO.getEnvValue());
                if (periodInDays >= 0) {
                    return Integer.toString(periodInDays);
                } else {
                    log.info("Purge delay cannot not be negative.");
                }
            } else {
                log.info("Purge delay is empty. Return default delay: 2");
                return "2";
            }
        }
        log.info("Purge delay value from DB. Purge delay is empty. Return default delay: 2");
        return "2";
    }

    @Bean
    public ItemProcessor<RoleContrats, DeleteContractsOutputModel> deleteContractsBatchProcessor() {
        return new DeleteContractsBatchProcessor();
    }

    @Bean
    public ItemWriter<DeleteContractsOutputModel> deleteContractsBatchWriter() {
        return new DeleteContractsBatchWriter();
    }


    @Bean
    @StepScope
    public ItemReader<RoleUCCR> deleteBusinessRoleUCCRReader() throws JrafDomainException {
        log.info("Parsing the table ROLE_UCCR and get all contracts with statut X");

        final RoleUCCRMapper roleUCCRMapper = new RoleUCCRMapper();

        JdbcPagingItemReader<RoleUCCR> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setRowMapper(roleUCCRMapper);

        OraclePagingQueryProvider queryProvider = new OraclePagingQueryProvider();
        queryProvider.setSelectClause("*");
        queryProvider.setFromClause("FROM ROLE_UCCR");
        queryProvider.setWhereClause("WHERE SETAT='X'");
        queryProvider.setSortKeys(Collections.singletonMap("ICLE_ROLE", Order.DESCENDING));
        reader.setQueryProvider(queryProvider);

        log.info("Parsing finished successfully");
        return reader;
    }

    @Bean
    public ItemProcessor<RoleUCCR, DeleteBusinessRoleUCCROutputModel> deleteBusinessRoleUCCRProcessor() {
        return new DeleteBusinessRoleUCCRProcessor();
    }

    @Bean
    public ItemWriter<DeleteBusinessRoleUCCROutputModel> deleteBusinessRoleUCCRWriter() {
        return new DeleteBusinessRoleUCCRWriter();
    }
}
