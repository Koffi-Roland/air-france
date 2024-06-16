package com.afklm.spring.security.habile.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

/**
 * Configuration of the websocket part accordingly of what is in the yaml file.
 * It is possible to configure: broker, endpoints, detination prefixes and
 * heartbeat.<br/>
 * Also sets the {@link HttpSessionInterceptor} for the security.
 * 
 * @author TECC
 *
 */
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
@ConditionalOnProperty(name = "afklm.security.websocket.broker")
public class WebSocketConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Value("${afklm.security.websocket.heartbeat-period:10000}")
    private long heartbeatPeriod; // In msec

    @Value("${afklm.security.websocket.broker:}")
    private String broker;

    @Value("${afklm.security.websocket.destination-prefix:}")
    private String appPrefix;

    @Value("#{'${afklm.security.websocket.endpoints:}'.split(',')}")
    private List<String> endpoints;

    @Bean
    public TaskScheduler heartBeatScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        if (StringUtils.hasText(broker)) {
            registry.enableSimpleBroker(broker)
                .setHeartbeatValue(new long[] {heartbeatPeriod, heartbeatPeriod})
                .setTaskScheduler(heartBeatScheduler());
        }
        if (StringUtils.hasText(appPrefix)) {
            registry.setApplicationDestinationPrefixes(appPrefix);
        }
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoints.stream().toArray(String[]::new))
            .setAllowedOrigins("*");
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.anyMessage().authenticated();
    }
}