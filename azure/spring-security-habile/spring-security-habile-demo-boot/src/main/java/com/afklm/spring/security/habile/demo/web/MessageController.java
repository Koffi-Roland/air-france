package com.afklm.spring.security.habile.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * Websocket controller sample.
 *
 * @author TECC
 *
 */
@RestController
public class MessageController {

    /**
     * Bean in charge of distributing messages to clients
     */
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * Greeting with VIEW authority
     * 
     * @param message message
     */
    @MessageMapping("/topicToServerWithViewAuth")
    @PreAuthorize("hasAuthority('VIEW')")
    public void greeting(String message) {
        template.convertAndSend("/topic/topicFromServer", "BROADCAST VIEW: " + message);
    }

    /**
     * Greeting with UPDATE authority
     * 
     * @param message message
     */
    @MessageMapping("/topicToServerWithUpdateAuth")
    @PreAuthorize("hasAuthority('UPDATE')")
    public void greetingNotAllowed(String message) {
        template.convertAndSend("/topic/topicFromServer", "BROADCAST UPDATE: " + message);
    }
}
