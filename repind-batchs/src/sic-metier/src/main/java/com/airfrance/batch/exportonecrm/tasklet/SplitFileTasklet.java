package com.airfrance.batch.exportonecrm.tasklet;

import com.airfrance.batch.exportonecrm.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class SplitFileTasklet implements Tasklet {
    private long maxBytes = 125000000;
    private static final String PATH = "INPUT_PATH";//to be defined
    private static final String OUTPUT_PATH = "OUTPUT_PATH"; //to be defined
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<FileInfo> filesToProcess = getFilesInPath();
        filesToProcess.forEach(file -> {
            try{
                Resource resource = new FileSystemResource(file.getPath());
                long fileSize = resource.contentLength();

                if (fileSize <= maxBytes) {
                    // No splitting required
                    copyFile(resource.getFile(), new File(OUTPUT_PATH, file.getFileName()));
                } else {
                    // Splitting required
                    splitFile(Path.of(OUTPUT_PATH), resource.getFile());
                }
            } catch (IOException e) {
                log.error("[-] error in split file tasklet execute");
            }

        });


        return RepeatStatus.FINISHED;
    }

    private List<FileInfo> getFilesInPath() throws IOException {
            return Files.walk(Paths.get(PATH))
                    .filter(Files::isRegularFile) // Filter out directories
                    .map(path -> new FileInfo(path, path.getFileName().toString()))
                    .toList();

    }

    private void splitFile(Path pathFile, File fileName) throws IOException {
        // Input and output file paths
        String outputFilePrefix = "output_";
        long totalBytes = 0;
        try {
            // Open input file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // Initialize variables
            String line;
            int fileCount = 1;
            Path filePath = Paths.get(pathFile.toString(), outputFilePrefix + fileCount + fileName.getName());
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));

            // Read lines from input file
            while ((line = reader.readLine()) != null) {
                // Write line to output file
                totalBytes += line.getBytes().length;

                writer.write(line);
                writer.newLine();

                // If batch size is reached, close current output file and open a new one
                if (totalBytes >= maxBytes) {
                    log.info("size={} maxbytes={}", totalBytes, maxBytes);
                    writer.close();
                    totalBytes = 0;
                    fileCount++;
                    filePath = Paths.get(pathFile.toString(), outputFilePrefix + fileCount + fileName.getName());
                    writer = new BufferedWriter(new FileWriter(filePath.toFile()));
                }
            }

            // Close resources
            reader.close();
            writer.close();

            log.info("File splitting complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void copyFile(File sourceFile, File destFile) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile))) {
            FileCopyUtils.copy(inputStream, outputStream);
        }
    }
}