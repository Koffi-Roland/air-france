package com.afklm.spring.security.habile.test;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * Session interceptor
 * 
 * Used to allow usage of cookies used for sessions handling and also CSRF one's
 * 
 * Cookie expiration is taken into account
 * 
 * @author M405991
 *
 */
@Component
public class SessionInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionInterceptor.class);

    private final List<HttpCookie> cookies = new ArrayList<>();
    
    private String xsrfToken = null;
    private boolean blockXsrfToken = false;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        injectCookies(request);
        ClientHttpResponse response = execution.execute(request, body);
        saveCookies(response);
        return response;
    }

    private void injectCookies(HttpRequest request) {
        LOGGER.info("Inject cookies");
        StringBuilder sb = new StringBuilder();
        for (HttpCookie cookie : cookies) {
            if ("XSRF-TOKEN".equals(cookie.getName())) {
                if (blockXsrfToken) {
                    blockXsrfToken = false;
                } else {
                    LOGGER.info("Adding header X-XSRF-TOKEN '{}'", cookie.getValue());
                    request.getHeaders().add("X-XSRF-TOKEN", xsrfToken != null ? xsrfToken : cookie.getValue());
                    xsrfToken = null;
                }
            }
            if (!cookie.hasExpired()) {
                sb.append(cookie.getName()).append('=').append(cookie.getValue()).append(";");
            } else {
                LOGGER.info("Cookie expired '{}", cookie.getName());
            }
        }
        LOGGER.info("Adding cookie '{}'", sb.toString());
        request.getHeaders().add("Cookie", sb.toString());
    }

    private void saveCookies(ClientHttpResponse response) {
        LOGGER.info("Save cookies");
        final List<String> cooks = response.getHeaders().get("Set-Cookie");
        if (cooks != null && !cooks.isEmpty()) {
            LOGGER.info("Got cookies {}", cooks);
            cooks.stream().map((c) -> HttpCookie.parse(c)).forEachOrdered((cook) -> {
                cook.forEach((a) -> {
                    HttpCookie cookieExists = cookies.stream()
                        .filter(x -> a.getName().equals(x.getName()))
                        .findAny()
                        .orElse(null);
                    if (cookieExists != null) {
                        LOGGER.info("Cookie updated '{}'", a);
                        cookies.remove(cookieExists);
                        cookies.add(a);
                    } else {
                        LOGGER.info("Cookie added '{}'", a);
                        cookies.add(a);
                    }
                });
            });
        }
    }

    /**
     * Clear all existing cookies
     */
    public void clear() {
        LOGGER.info("Clearing cookies {}", cookies);
        cookies.clear();
    }

    /**
     * Value used to inject as X-XSRF-TOKEN for the next call.
     * @param newValue
     */
    public void updateXsrfToken(String newValue) {
        xsrfToken = newValue;
    }

    /**
     * Blocks the injection of the X-XSRF-TOKEN for the next call.
     */
    public void blockXsrfToken() {
        blockXsrfToken = true;
    }

    /**
     * Get cookie
     * 
     * @param name name
     * @return cookie or null
     */
    public HttpCookie getCookie(String name) {
        return cookies.stream()
            .filter(x -> name.equals(x.getName()))
            .findAny()
            .orElse(null);
    }
}
