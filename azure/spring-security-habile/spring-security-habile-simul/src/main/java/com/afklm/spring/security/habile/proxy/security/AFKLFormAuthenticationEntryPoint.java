package com.afklm.spring.security.habile.proxy.security;

import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * Habile FormAuthenticationEntryPoint
 * 
 * @author m408461
 *
 */
public final class AFKLFormAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AFKLFormAuthenticationEntryPoint.class);

    private String port;

    private static final String HTML_CONTENT = "<html>\r\n" +
            "<head>\r\n" +
            "<title>Click Provider Link</title>\r\n" +
            "<meta charset=\"UTF-8\">\r\n" +
            "<link rel=\"shortcut icon\" href=\"/pa/favicon.ico\"/>\r\n" +
            "<script type=\"text/javascript\">\r\n" +
            "function navigate() {\r\n" +
            "window.location = 'http:\\/\\/localhost:{{port}}\\/login';\r\n" +
            "}\r\n" +
            "</script>\r\n" +
            "</head>\r\n" +
            "<body onLoad=\"navigate()\">\r\n" +
            "<noscript>\r\n" +
            "<p>\r\n" +
            "<strong>Note: </strong>Since your browser does not support JavaScript, you must click the Provider link to proceed.\r\n" +
            "</p>\r\n" +
            "<a href=\"http:\\/\\/localhost:8001\\/login\">Provider</a>\r\n" +
            "</noscript>\r\n" +
            "</body>\r\n" +
            "</html>";

    private ServerRequestCache requestCache = new WebSessionServerRequestCache();
    private HabileSessionManager habileSessionManager;

    public AFKLFormAuthenticationEntryPoint(HabileSessionManager habileSessionManager, String port) {
        this.habileSessionManager = habileSessionManager;
        this.port = port;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        LOGGER.debug("Requesting authentication");
        ServerHttpResponse response = exchange.getResponse();
        habileSessionManager.notifyLogin(exchange.getResponse());
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        List<MediaType> cts = exchange.getRequest().getHeaders().getAccept();

        Optional<MediaType> jsonRequested = cts.stream()
            .filter(t -> t.equals(MediaType.APPLICATION_JSON) || t.equals(MediaType.APPLICATION_JSON_UTF8))
            .findFirst();

        return this.requestCache.saveRequest(exchange)
            .then(jsonRequested.map(mt -> response.writeWith(Mono.empty()))
                .orElse(returnHtml(response)));
    }

    Mono<Void> returnHtml(ServerHttpResponse response) {
        LOGGER.debug("Returning HTML content");
        DataBuffer db = response.bufferFactory().allocateBuffer().write(HTML_CONTENT.replace("{{port}}", this.port).getBytes(StandardCharsets.UTF_8));;
        response.getHeaders().setContentType(MediaType.TEXT_HTML);
        return response.writeWith(Mono.just(db));
    }
}
