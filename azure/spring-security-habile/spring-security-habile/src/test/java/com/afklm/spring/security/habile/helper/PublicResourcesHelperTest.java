package com.afklm.spring.security.habile.helper;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import com.afklm.spring.security.habile.properties.Ss4hProperties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicResourcesHelperTest {

    @Mock
    private Ss4hProperties afklSecurityProperties;
    @InjectMocks
    private PublicResourcesHelper publicResourcesHelper;

    private final List<String> defaultUrlPatterns = Arrays.asList("/ws/**", "/**/*.css", "/**/*.gif", "/**/*.png", "/**/*.jpeg", "/**/*.jpg");

    @Test
    void testGetPublicEndpoints_OnlyDefaultPatterns() {
        Logger logger = (Logger) LoggerFactory.getLogger(PublicResourcesHelper.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        when(afklSecurityProperties.getPublicEndpoints()).thenReturn(new ArrayList<>());
        List<String> publicResources = publicResourcesHelper.getPublicResources();
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(6, publicResources.size());
        assertEquals(defaultUrlPatterns, publicResources);
        assertEquals(1, logsList.size());
        assertEquals("SS4H-MSG-002: Configuring public URL patterns: '/ws/**', '/**/*.css', '/**/*.gif', '/**/*.png', '/**/*.jpeg', '/**/*.jpg'", logsList.get(0).getMessage());
    }

    @Test
    void testGetPublicEndpoints_DefaultPatternsAndPublicEndpoints() {
        Logger logger = (Logger) LoggerFactory.getLogger(PublicResourcesHelper.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        List<String> allPublicResources = new ArrayList<>(defaultUrlPatterns);
        allPublicResources.add("/public/**");
        when(afklSecurityProperties.getPublicEndpoints()).thenReturn(Collections.singletonList("/public/**"));
        List<String> publicResources = publicResourcesHelper.getPublicResources();
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(7, publicResources.size());
        assertEquals(allPublicResources, publicResources);
        assertEquals(1, logsList.size());
        assertEquals("SS4H-MSG-002: Configuring public URL patterns: '/ws/**', '/**/*.css', '/**/*.gif', '/**/*.png', '/**/*.jpeg', '/**/*.jpg', '/public/**'", logsList.get(0).getMessage());
    }
}
