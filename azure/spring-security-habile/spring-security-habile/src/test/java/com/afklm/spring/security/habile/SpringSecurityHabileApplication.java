package com.afklm.spring.security.habile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 * Entry point to start the application.
 * 
 * @author TECC
 */
@SpringBootApplication
@Controller
public class SpringSecurityHabileApplication {

    public static final String MSG_TO_CLIENT = "This is a message sent to all clients when the server is receiving a msg.";

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/topicToServer")
    public void greeting(String message) throws Exception {
        // TODO make something more testable
        System.err.println("Message received Marc - " + message);
        template.convertAndSend("/topic/topicFromServer", MSG_TO_CLIENT);
    }

    @MessageMapping("/topicToServerNotGranted")
// TODO: Investigate why following @PreAuthorize annotation makes some tests to fail with strange CGLIB and ClassCastException issues
// (UserRetrieverDaoConditionalTest, SpringSecurityHabileApplicationTest, CustomSecurityConfigTest, SecMobilfilterTest)
//    @PreAuthorize("hasRole('P_TESTtoto')")
    public void greetingNotGranted(String message) throws Exception {
        // TODO make something more testable
        System.err.println("Message received Marc - " + message);
        template.convertAndSend("/topic/topicFromServer", MSG_TO_CLIENT);
    }

    /**
     * Main entry point
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityHabileApplication.class, args);
    }
}
