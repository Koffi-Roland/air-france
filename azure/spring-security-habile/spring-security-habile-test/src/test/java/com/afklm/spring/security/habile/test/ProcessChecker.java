package com.afklm.spring.security.habile.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Process checker
 * 
 * @author m405991
 *
 */
public class ProcessChecker implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessChecker.class);

    private String[] ports;

    public ProcessChecker(String... ports) {
        this.ports = ports;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<URL> urls = new ArrayList<URL>();
        for (String port : ports) {
            urls.add(new URL("http://localhost:" + port));
        }
        LOGGER.info("Waiting for startup {}", urls);

        for (int i = 0; i < 30; i++) {
            urls.removeIf(url -> checkPort(url));
            if (urls.size() == 0) {
                break; // Exit loop
            }
            Thread.sleep(2000);
        }
        if (urls.size() != 0) {
            throw new IllegalStateException("Cannot start integration tests since endpoints are no more available " + urls);
        }
    }

    private boolean checkPort(URL url) {
        boolean result = false;
        try {
            HttpURLConnection yc = (HttpURLConnection) url.openConnection();
            LOGGER.info("Checking {} with response code {}", url, yc.getResponseCode());
            if (401 == yc.getResponseCode() || 200 == yc.getResponseCode()) {
                result = true;
            }
        } catch (IOException io) {
            LOGGER.info("Checking again endpoint " + url);
        }
        return result;
    }
}
