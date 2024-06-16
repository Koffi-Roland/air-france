package com.airfrance.batch.updateMarketLanguage.service;

import com.airfrance.batch.updateMarketLanguage.model.InputFileModel;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExecutionService {

    public static final String DELIMITER = ";";

    public static int line = 0;

    @Autowired
    private UpdateMarketLanguageService updateMarketLanguageService;

    @Transactional
    public void execute(String inputFile, String outputFile){

        try {

            List<InputFileModel> inputFileModelList = fileReader(inputFile);
            for(InputFileModel m : inputFileModelList){
                updateMarketLanguageService.updateCorrections(m);
                line++;
            }
            fileWriter(outputFile);

        } catch (JrafDaoException ex) {
            ex.printStackTrace();
        }
    }

   public List<InputFileModel> fileReader(String inputFile){

       List<InputFileModel> inputFileModelList = new ArrayList<InputFileModel>();

       try {
       File file = new File(inputFile);
       FileReader fr = new FileReader(file);
       BufferedReader br = new BufferedReader(fr);

       //read the lines and skip the header line of the file
       Stream<String> linesStream = br.lines().skip(1);
       inputFileModelList = linesStream.map(line -> line.split(DELIMITER))
               .map(a -> new InputFileModel(a[0], a[1], a[2], a[3]))
               .collect(Collectors.toList());
       br.close();
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       return inputFileModelList;
   }

   public void fileWriter(String outputFile){
       try {
           String outputFileName = "UpdateMarketLanguageOutput.txt";
           File output = new File(outputFile, outputFileName);
           output.createNewFile();
           try(FileWriter fileWriter = new FileWriter(output)) {
                fileWriter.write("Script properly executed.\n\n" +
                        "Results summary: \n" +
                        "Number of lines executed: " + line + "\n\n" +
                        "Number of Gin updated : " + updateMarketLanguageService.getGinsSuccess() + "\n\n" +
                        "Number of Gin failed (not found) : " + updateMarketLanguageService.getGinsFail());
            }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
