package com.airfrance.batch.cleanContactData;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.config.cleanContactData.BatchCleanContactDataConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Service
public class BatchCleanContactData implements IBatch, AutoCloseable {

    //oracle
    final Connection oracleConnection;

    public BatchCleanContactData(  @Qualifier("dataSourceRepind") DataSource datasource) throws SQLException {
        oracleConnection = datasource.getConnection();
    }


    public static void main(String[] args) throws Exception {
        log.info("Start of batchCleanContactData.");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchCleanContactDataConf.class);
        try (BatchCleanContactData batch = (BatchCleanContactData) ctx.getBean("batchCleanContactData")) {
            batch.execute();
            log.info("End BatchCleanContactData.");
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        deleteXstatusFromEmailTable("EMAILS");
        deleteXstatusFromTelecomTable("TELECOMS");
        deleteXstatusFromPostAdrTable("POST_ADR", "SAIN_ADR", "SAIN_ADR1");
    }

    public void deleteXstatusFromEmailTable(String email) throws SQLException {
        int countUpdatedEmail = 0;
        int countEmail = 0;
        log.info("trying to delete email <{}> who has status x from table email ", email);
        String queryUpdate1= "UPDATE EMAILS SET SSITE_MODIFICATION='BATCH_QVI' where " +
                "                    SSTATUT_MEDIUM='X'" +
                "                    and SGIN IS NOT NULL" +
                "                    and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                    and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                    where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        String query1 = "DELETE from EMAILS where SSTATUT_MEDIUM='X'" +
                "                    and SGIN IS NOT NULL" +
                "                    and SSITE_MODIFICATION='BATCH_QVI'" +
                "                    and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                    and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                    where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        oracleConnection.setAutoCommit(false);

        // execute update query
        try (PreparedStatement stmt = oracleConnection.prepareStatement(queryUpdate1)) {

            countUpdatedEmail = stmt.executeUpdate(queryUpdate1);

            if (countUpdatedEmail == 0) {
                log.warn("No Email found in table EMAILS with x status to update");
            } else {
                oracleConnection.commit();
                log.info("<{}> Email was updated with success.", countUpdatedEmail);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to update countUpdatedEmail", e);
        }

        // execute delete query
        try (PreparedStatement stmt = oracleConnection.prepareStatement(query1)) {

            countEmail = stmt.executeUpdate(query1);
            if (countEmail == 0) {
                log.warn("No Email found in table EMAILS with x status");
            } else {
                oracleConnection.commit();

                log.info("<{}> Email was deleted with success.", countEmail);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to delete countEmail", e);
        }
    }

    public void deleteXstatusFromTelecomTable(String telecom) throws SQLException {
        int countTelecom = 0;
        int countUpdatedTelecom = 0;
        log.info("trying to delete Telecom <{}> who has status x from table TELECOMS ", telecom);

        String queryUpdate2= "UPDATE TELECOMS SET SSITE_MODIFICATION='BATCH_QVI' where " +
                "                    SSTATUT_MEDIUM='X'" +
                "                    and SGIN IS NOT NULL" +
                "                    and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                    and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                    where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        String query2 = "DELETE from TELECOMS where SSTATUT_MEDIUM='X'" +
                "                    and SGIN IS NOT NULL" +
                "                    and SSITE_MODIFICATION='BATCH_QVI'" +
                "                    and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                    and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                    where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        oracleConnection.setAutoCommit(false);

        // execute query update
        try (PreparedStatement stmt = oracleConnection.prepareStatement(queryUpdate2)) {

            countUpdatedTelecom = stmt.executeUpdate(queryUpdate2);
            if (countUpdatedTelecom == 0) {
                log.warn("No Telecom found in table TELECOMS with x status to update");
            } else {
                oracleConnection.commit();
                log.info("<{}> Telecom was updated with success.", countUpdatedTelecom);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to update countTelecom", e);
        }

        // execute query
        try (PreparedStatement stmt = oracleConnection.prepareStatement(query2)) {

            countTelecom = stmt.executeUpdate(query2);
            if (countTelecom == 0) {
                log.warn("No Telecom found in table TELECOMS with x status");
            } else {
                oracleConnection.commit();
                log.info("<{}> Telecom was deleted with success.", countTelecom);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to delete countTelecom", e);
        }
    }

    public void deleteXstatusFromPostAdrTable(String PostAdr, String FormAdr, String UsagMed) throws SQLException {
        int countUpdatedPostAdr = 0;
        int countPostAdr = 0;
        int countFormAdr = 0;
        int countUsagMed = 0;

        log.info("trying to delete PostAdr <{}> who has status x from table ADR_POST and SAIN <{}> from table FORMALIZED_ADR " +
                "and SAIN <{}> from table USAGE_MEDIUMS ", PostAdr, FormAdr, UsagMed);

        String queryUpdate= "UPDATE SIC2.ADR_POST SET SSITE_MODIFICATION='BATCH_QVI' where " +
                "                    SSTATUT_MEDIUM='X'" +
                "                    and SGIN IS NOT NULL" +
                "                    and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                    and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                    where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        String query3 = "DELETE FROM SIC2.FORMALIZED_ADR where SAIN_ADR in (select SAIN from SIC2.ADR_POST where SSTATUT_MEDIUM='X'" +
                "                                                                                   and SGIN IS NOT NULL" +
                "                                                                                   and SSITE_MODIFICATION='BATCH_QVI'" +
                "                                                                                   and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                                                                                   and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                                                                                   where ENVKEY='MAX_CONTACT_DATA_DELETION'))";

        String query4 = "DELETE FROM SIC2.USAGE_MEDIUMS where SAIN_ADR in (select SAIN from SIC2.ADR_POST where SSTATUT_MEDIUM='X'" +
                "                                                                                   and SGIN IS NOT NULL" +
                "                                                                                   and SSITE_MODIFICATION='BATCH_QVI'" +
                "                                                                                   and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                                                                                   and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                                                                                   where ENVKEY='MAX_CONTACT_DATA_DELETION'))";

        String query5 = "DELETE FROM SIC2.ADR_POST where SSTATUT_MEDIUM='X'" +
                "                              and SGIN IS NOT NULL" +
                "                              and SSITE_MODIFICATION='BATCH_QVI'" +
                "                              and (SYSDATE - DDATE_MODIFICATION)* 24 > 24" +
                "                              and ROWNUM <= (select ENVVALUE from ENV_VAR " +
                "                              where ENVKEY='MAX_CONTACT_DATA_DELETION')";

        oracleConnection.setAutoCommit(false);

        // execute query update
        try (PreparedStatement stmtUpdate = (oracleConnection.prepareStatement(queryUpdate))) {
            countUpdatedPostAdr = stmtUpdate.executeUpdate(queryUpdate);
            if (countUpdatedPostAdr == 0) {
                log.warn("No PostAdr found in table ADR_POST with x status to update");
            } else {
                oracleConnection.commit();
                log.info("<{}> PostAdr was updated with success.", countUpdatedPostAdr);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to update PostAdr", e);
        }

        // execute query 3
        try (PreparedStatement stmt1 = (oracleConnection.prepareStatement(query3))) {
            countFormAdr = stmt1.executeUpdate(query3);
            if (countFormAdr == 0) {
                log.warn("No sain found in table FORMALIZED_ADR associated to postal_adr with x status");
            } else {
                oracleConnection.commit();
                log.info("<{}> sain was deleted with success from table FORMALIZED_ADR .", countFormAdr);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to delete sain from table FORMALIZED_ADR", e);
        }

        // execute query 4
        try (PreparedStatement stmt2 = (oracleConnection.prepareStatement(query4))) {
            countUsagMed = stmt2.executeUpdate(query4);
            if (countUsagMed == 0) {
                log.warn("No sain found in table USAGE_MEDIUMS associated to postal_adr with x status");
            } else {
                oracleConnection.commit();
                log.info("<{}> sain was deleted with success from table USAGE_MEDIUMS.", countUsagMed);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to delete sain from table USAGE_MEDIUMS", e);
        }



        // execute query 5
        try (PreparedStatement stmt3 = (oracleConnection.prepareStatement(query5))) {
            countPostAdr = stmt3.executeUpdate(query5);
            if (countPostAdr == 0) {
                log.warn("No PostAdr found in table ADR_POST with x status");
            } else {
                oracleConnection.commit();
                log.info("<{}> PostAdr was deleted with success.", countPostAdr);
            }
        } catch (SQLException e) {
            try {
                oracleConnection.rollback();
            } catch (SQLException rollbackException) {
                log.error("Error rolling back transaction", rollbackException);
            }
            log.error("unable to delete PostAdr", e);
        }
    }

    @Override
    public void close() {
        try {
            log.info("Closing connections...");
            oracleConnection.close();
        } catch (SQLException e) {
            log.error("unable to close connection", e);
        }
    }
}
