package com.afklm.spring.security.habile.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

/**
 * Configuration of the websocket part accordingly of what is in the yaml file.
 * It is possible to configure: broker, endpoints, detination prefixes and
 * heartbeat.<br/>
 * 
 * @author TECC
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Value("${afklm.websocket.heartbeat-period:10000}")
    private long heartbeatPeriod; // In msec

    @Value("${afklm.websocket.broker:}")
    private String broker;

    @Value("${afklm.websocket.destination-prefix:}")
    private String appPrefix;

    @Value("#{'${afklm.websocket.endpoints:}'.split(',')}")
    private List<String> endpoints;

    /**
     * Bean in charge of scheduling the heartbeat messages.
     * 
     * @return task scheduler
     */
    @Bean
    public TaskScheduler heartBeatScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(broker)
            .setHeartbeatValue(new long[] {heartbeatPeriod, heartbeatPeriod})
            .setTaskScheduler(heartBeatScheduler());

        registry.setApplicationDestinationPrefixes(appPrefix);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoints.stream().toArray(String[]::new)).setAllowedOrigins("*");
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
            // .simpDestMatchers("/topic/topicForAdmin").hasRole("P_XXXXX_ADMIN")
            .anyMessage()
            .authenticated();
    }

}