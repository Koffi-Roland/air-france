package com.afklm.spring.security.habile.proxy.service;

import com.afklm.spring.security.habile.proxy.model.SimulConfiguration;
import com.afklm.spring.security.habile.proxy.model.UserInformation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration Service.
 *
 * @author TECCSE
 *
 */
@Service
public class ConfigurationService implements InitializingBean {

    @Autowired
    private ApplicationArguments appArgs;

    /** Option used for JSON configuration simulation file */
    public static final String CONFIG_SIMUL_OPTION = "config.simul";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationService.class);

    private Map<String, UserInformation> userInformationMap = new HashMap<>();

    private SimulConfiguration simulConfiguration = new SimulConfiguration();

    /**
     * Returns true if no user are defined
     * 
     * @return
     */
    public boolean hasNoUser() {
        return userInformationMap.isEmpty();
    }

    /**
     * Get list of UserInformation
     * 
     * @return list of UserInformation
     */
    public Collection<UserInformation> getUserInformationList() {
        return userInformationMap.values();
    }

    /**
     * Get User information
     * 
     * @param userId user ID
     * @return null if user is not found
     */
    public UserInformation getUserInformation(String userId) {
        return userInformationMap.get(userId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Simulation started with option names: {}", appArgs.getOptionNames());
        List<String> configs = appArgs.getOptionValues(CONFIG_SIMUL_OPTION);
        if (CollectionUtils.isEmpty(configs)) {
            LOGGER.warn("No configuration provided, please provide one with option --{}=<path to your config file>", CONFIG_SIMUL_OPTION);
        } else {
            LOGGER.info("Loading configuration '{}'", configs.get(0));
            loadConfiguration(new FileInputStream(new File(configs.get(0))));
        }
    }

    /**
     * Load configuration
     * 
     * @param is
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void loadConfiguration(InputStream is) throws JsonParseException, JsonMappingException, IOException {
        userInformationMap.clear();
        ObjectMapper jsonMapper = new ObjectMapper();
        simulConfiguration = jsonMapper.readValue(is, new TypeReference<SimulConfiguration>() {});
        simulConfiguration.getUsers().forEach(u -> userInformationMap.put(u.getUserId(), u));
    }

    /**
     * Get anonymous paths
     * 
     * @return
     */
    public String[] getAnonymousPaths() {
        return simulConfiguration.getAnonymousPaths();
    }

    public String[] getPublicPaths() {
        return simulConfiguration.getPublicPaths();
    }
}
