package com.afklm.repind.msv.automatic.merge.config.logging;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse,
                            FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
    }
}
