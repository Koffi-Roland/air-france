package com.afklm.batch.cod;


import com.afklm.batch.cod.config.BatchDeleteDoctorContractInPIDConfig;
import com.afklm.batch.cod.config.CodProperties;
import com.airfrance.batch.common.IBatch;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.pool.OracleDataSource;
import org.neo4j.driver.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BatchDeleteDoctorContractInPID implements IBatch, AutoCloseable {

    //noe4j
    private final Driver neo4jDriver;

    //oracle
    private final Connection oracleConnection;

    public BatchDeleteDoctorContractInPID(CodProperties codProperties, Environment environment) throws SQLException {
        System.setProperty("oracle.net.tns_admin", System.getenv("TNS_ADMIN"));
        neo4jDriver = GraphDatabase.driver(
                codProperties.getNeo4jRepindUrl(),
                AuthTokens.basic(
                        environment.getProperty("neo4jCodUsername"),
                        environment.getProperty("neo4jCodPassword")
                )
        );
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setDriverType("oracle.jdbc.driver.OracleDriver");
        dataSource.setTNSEntryName(codProperties.getOracleSicTNS());
        dataSource.setURL(codProperties.getOracleSicUrl());
        dataSource.setUser(environment.getProperty("sicUsername"));
        dataSource.setPassword(environment.getProperty("sicPassword"));

        oracleConnection = dataSource.getConnection();
    }

    public static void main(String[] args) throws Exception {
        log.info("Start of BatchDeleteDoctorContractInPID.");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchDeleteDoctorContractInPIDConfig.class);
        try (BatchDeleteDoctorContractInPID batch = (BatchDeleteDoctorContractInPID) ctx.getBean("batchDeleteDoctorContractInPID")) {
            batch.execute();
            log.info("End BatchDeleteDoctorContractInPID.");
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        List<String> roleIdsWithStatusX = retrieveRoleIdsWithStatusX();
        log.info("Found <{}> role Ids in cod neo4j with X status", roleIdsWithStatusX.size());
        roleIdsWithStatusX.forEach(roleId -> {
            try {
                deleteBusinessRoleByContractNumber(roleId);
            } catch (SQLException e) {
                log.error("unable to delete role id", e);
            }
        });
    }

    public List<String> retrieveRoleIdsWithStatusX() {
        TransactionWork<List<String>> tw = tx -> {
            Result result = tx.run("MATCH (a:Role) where a.doctorStatus=\"X\" return a.roleId");
            return result.list().stream()
                    .map(org.neo4j.driver.Record::values)
                    .map(values -> values.get(0))
                    .map(Value::asString)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        };
        try (Session session = neo4jDriver.session()) {
            return session.readTransaction(tw);
        }
    }

    public void deleteBusinessRoleByContractNumber(String contractNumber) throws SQLException {
        log.info("trying to delete business Role with contract number <{}>", contractNumber);
        String query = "DELETE from BUSINESS_ROLE where STYPE='D' AND SNUMERO_CONTRAT='" + contractNumber + "'";
        // execute Update query
        try (PreparedStatement stmt = oracleConnection.prepareStatement(query)) {
            int count = stmt.executeUpdate(query);
            if (count == 0) {
                log.warn("Role Id with contract number <{}> not found", contractNumber);
            } else {
                log.info("Role Id with contract number <{}> deleted with success.", contractNumber);
            }
        } catch (SQLException e) {
            log.error("unable to delete role id with contract number <{}>", contractNumber, e);
        }
        oracleConnection.commit();
    }

    @Override
    public void close() {
        try {
            log.info("Closing connections...");
            neo4jDriver.close();
            oracleConnection.close();
        } catch (SQLException e) {
            log.error("unable to close connection", e);
        }
    }
}

