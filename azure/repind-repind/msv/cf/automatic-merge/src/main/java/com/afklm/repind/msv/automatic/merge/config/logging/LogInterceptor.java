package com.afklm.repind.msv.automatic.merge.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final ObjectMapper mapper = new ObjectMapper();
/*
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);

        List<Map<String, Object>> body = new ArrayList<>();
        if(!request.getInputStream().isFinished()){
            body = new ObjectMapper().readValue(request.getInputStream(), List.class);
        }
        HttpLog inputRequestLog = new HttpLog(HttpType.REQUEST, requestId, request.getRequestURI(), request.getMethod(), null, body, null, null);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        log.info(ow.writeValueAsString(inputRequestLog));

        long startTime = Instant.now().toEpochMilli();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        long startTime = (Long) request.getAttribute("startTime");
        String requestId = (String) request.getAttribute("requestId");
        Long duration = Instant.now().toEpochMilli() - startTime;

        HttpLog outputRequest = new HttpLog(HttpType.RESPONSE, requestId, request.getRequestURI(), request.getMethod(), null, null, response.getStatus(), duration);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        log.info(ow.writeValueAsString(outputRequest));
    }*/
}
