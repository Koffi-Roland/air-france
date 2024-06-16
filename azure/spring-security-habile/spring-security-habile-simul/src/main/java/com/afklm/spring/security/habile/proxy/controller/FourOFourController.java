package com.afklm.spring.security.habile.proxy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.StringJoiner;

import static org.springframework.http.HttpStatus.OK;

/**
 * Class that handles 404 URI used by the fallback feature
 */
@RestController
public class FourOFourController {

    /**
     * URI handling 404 on backend
     */
    public static final String BACKEND_FALLBACK_URI = "/404BEC";
    /**
     * URI handling 404 on frontend
     */
    public static final String FRONTEND_FALLBACK_URI = "/404FEC";

    @Value("${habile.proxy.angular.url:http://localhost:4200}")
    private String frontendUrl;

    @Value("${habile.proxy.backend.url:http://localhost:8080}")
    private String backendUrl;

    @Value("#{'${habile.proxy.backend.endpoints:}'.split(',')}")
    private List<String> backendEndpoints;

    public static final String NOT_FOUND_HTML_TEMPLATE = "<html><h1><font color='red'>Error contacting the #target_system#</font></h1>"
            + "Make sure it is available on:#exposed_endpoints#</html>";

    /**
     * 404 on backend resource
     * @return
     */
    @GetMapping(BACKEND_FALLBACK_URI)
    public ResponseEntity<String> notFoundBackend() {
        return new ResponseEntity<>(NOT_FOUND_HTML_TEMPLATE
                .replaceAll("#target_system#", "backend")
                .replaceAll("#exposed_endpoints#", getConsolidatedBackendUrls()), OK);
    }

    /**
     * Build the list of endpoints dedicated to the backend.
     * @return
     */
    private String getConsolidatedBackendUrls() {
        if( backendEndpoints.size() ==1 ) {
            return "<ul><li>" + backendUrl + backendEndpoints.get(0) + "</li></ul>";
        } else {
            StringJoiner joiner = new StringJoiner("</li><li>", "<ul><li>", "</li></ul>");
            backendEndpoints.stream()
                    .filter(StringUtils::hasText)
                    .map(String::trim)
                    .forEach(p -> joiner.add(backendUrl + p));
            return joiner.toString();
        }
    }

    /**
     * 404 on frontend resource
     * @return
     */
    @GetMapping(FRONTEND_FALLBACK_URI)
    public ResponseEntity<String> notFoundFrontend() {
        return new ResponseEntity<>(NOT_FOUND_HTML_TEMPLATE
                .replaceAll("#target_system#", "frontend")
                .replaceAll("#exposed_endpoints#", "<ul><li>" + frontendUrl +"</li></ul>"), OK);
    }
}
