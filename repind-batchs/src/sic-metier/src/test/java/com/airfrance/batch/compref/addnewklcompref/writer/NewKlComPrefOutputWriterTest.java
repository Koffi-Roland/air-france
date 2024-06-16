package com.airfrance.batch.compref.addnewklcompref.writer;

import com.airfrance.batch.compref.addnewklcompref.utils.GenerateTestData;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NewKlComPrefOutputWriterTest {
    private static String EXPECTED_PATH = "path/";
    private static String EXPECTED_FILENAME = "file";
    private static String EXPECTED_FILE_EXT = ".txt";

    @Test
    void testConstructor() {
        NewKLComPrefOutputWriter writer = new NewKLComPrefOutputWriter(EXPECTED_PATH, EXPECTED_FILENAME, EXPECTED_FILE_EXT);

        assertEquals(EXPECTED_PATH + getCurrentDateFormatted(), writer.getOutputPath());
        assertEquals(EXPECTED_FILENAME, writer.getFileName());
        assertEquals(EXPECTED_FILE_EXT, writer.getFileExt());
    }

    private String getCurrentDateFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    @Test
    void testWriteMethod() throws Exception {
        NewKLComPrefOutputWriter writer = new NewKLComPrefOutputWriter(EXPECTED_PATH, EXPECTED_FILENAME, EXPECTED_FILE_EXT);
        List<CommunicationPreferences> inputList = List.of(GenerateTestData.createKLComprefForTest());
        writer.write(inputList);
        assertEquals(inputList.size(), writer.getAccumulatedItems().size());
    }

}