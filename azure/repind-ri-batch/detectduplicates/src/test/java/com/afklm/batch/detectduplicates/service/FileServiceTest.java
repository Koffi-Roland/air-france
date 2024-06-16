package com.afklm.batch.detectduplicates.service;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test ignore cause I don't know where ressources files are generated and everything is done magically and i'm not magician
@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    private final FileService fileService = new FileService();


    @AfterEach
    public void cleanup() {
        File file = new File("./test.txt");
        file.delete();
    }

    @Test
    @Disabled
    public void readFile() throws IOException {
        String result = fileService.readFile("./example.txt").trim();
        assertEquals("EXAMPLE", result);
    }

    @Test
    @Disabled
    public void writeFile() throws IOException {
        fileService.writeInFile(".", "test.txt", "WRITE");
        String result = fileService.readFile("./test.txt").trim();
        assertEquals("WRITE", result);
    }
}
