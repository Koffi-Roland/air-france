package com.airfrance.batch.cleanDuplicateUTFData;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.config.cleanDuplicateUtfData.BatchCleanDuplicateUTFDataConf;
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
public class BatchCleanDuplicateUTFData implements IBatch, AutoCloseable {

    //oracle
    final Connection oracleConnection;

    public BatchCleanDuplicateUTFData(  @Qualifier("dataSourceRepindUtf8") DataSource datasource) throws SQLException{
        oracleConnection = datasource.getConnection();
    }


    public static void main(String[] args) throws Exception {
        log.info("Start of BatchCleanDuplicateUTFData.");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchCleanDuplicateUTFDataConf.class);
        try (BatchCleanDuplicateUTFData batch = (BatchCleanDuplicateUTFData) ctx.getBean("batchCleanDuplicateUTFData")) {
            batch.execute();
            log.info("End BatchCleanDuplicateUTFData.");
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        deleteDuplicateIndividualFromUtfDataTable("FIRST_NAME");
        deleteDuplicateIndividualFromUtfDataTable("LAST_NAME");
    }

    public void deleteDuplicateIndividualFromUtfDataTable(String skey) throws SQLException {
        int countDuplicate = 0;
        do {
            log.info("trying to delete duplicated <{}> associated to UTF_DATA_ID from table UTF_DATA ", skey);
            String query = "DELETE from UTF_DATA U where UTF_DATA_ID in (SELECT MAX(UTF_DATA_ID) FROM UTF_DATA" +
                    "                              WHERE SKEY='" + skey + "'" +
                    "                              GROUP BY UTF_ID" +
                    "                              HAVING  COUNT(UTF_ID) > 1 )";
            // execute query
            try (PreparedStatement stmt = oracleConnection.prepareStatement(query)) {
                countDuplicate = stmt.executeUpdate(query);
                if (countDuplicate == 0) {
                    log.warn("No duplicate found in table UTF_DATA");
                } else {
                    log.info("<{}> UTF_DATA_ID was deleted with success.", countDuplicate);
                }
            } catch (SQLException e) {
                log.error("unable to delete UTF_DATA_ID", e);
            }
        } while (countDuplicate > 0);

        oracleConnection.commit();
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
