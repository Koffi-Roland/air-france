package com.afklm.repind.ci.tools.docker.images.cleaner;

import com.afklm.repind.ci.tools.docker.images.cleaner.enums.Action;
import com.afklm.repind.ci.tools.docker.images.cleaner.service.DockerImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class Application
        implements CommandLineRunner {

    private DockerImageService dockerImageService;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        log.info("APPLICATION FINISHED");
        System.exit(0);
    }

    @Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");

        String[] argsSplitted = (args.length > 0)?args[0].split(" "):null;
        if(argsSplitted == null){
            log.error("No args");
            System.exit(1);
        }

        String action = (argsSplitted.length > 0)?argsSplitted[0]:null;

        String repository = (argsSplitted.length > 1)?argsSplitted[1]:null;
        String tag = (argsSplitted.length > 1)?argsSplitted[2]:null;

        try{
            if(action == null){
                throw new IllegalArgumentException("Action not provided");
            }

            switch (Action.valueOf(action)){
                case ADD:
                    dockerImageService.add(repository, tag);
                    break;
                case PURGE:
                    dockerImageService.purge();
                    log.info("Purge finished");
                    break;
                default:
            }
        }catch(IllegalArgumentException exception){
            log.error("Invalid action (Possible value : ADD, PURGE)");
            System.exit(1);
        }

    }
}