package com.afklm.repind.msv.handicap.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LoggingFilterTest {

    @InjectMocks
    private LoggingFilter filter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testDoFilterWithSwagerPath() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/swagger");
        Mockito.doNothing().when(filterChain).doFilter(Mockito.eq(request), Mockito.eq(response));
        filter.doFilter(request, response, filterChain);
        Mockito.verify(filterChain, times(1)).doFilter(Mockito.eq(request), Mockito.eq(response));
    }

    @Test
    public void testDoFilterWithNonSwaggerPath() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/toto");
        Collection<String> collection = new ArrayList<String>();
        collection.add("Hello");
        Mockito.when(request.getHeaderNames()).thenReturn(Collections.enumeration(collection));
        Mockito.doNothing().when(filterChain).doFilter(Mockito.any(), Mockito.any());
        assertDoesNotThrow(() ->  filter.doFilter(request, response, filterChain));
    }

    @Test
    void isLoggable() {
        String contentType = null;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = "application/hal+json";
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.APPLICATION_JSON_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.APPLICATION_XML_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.APPLICATION_XHTML_XML_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.TEXT_HTML_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.TEXT_PLAIN_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = MediaType.TEXT_XML_VALUE;
        Assert.assertTrue(filter.isLoggable(contentType));

        contentType = "application/octet-stream";
        Assert.assertFalse(filter.isLoggable(contentType));

        contentType = "application/toto";
        Assert.assertFalse(filter.isLoggable(contentType));

        contentType = "toto";
        Assert.assertFalse(filter.isLoggable(contentType));

    }

}
