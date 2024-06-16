package com.afklm.cati.common.service.impl;

import com.afklm.cati.common.dto.VariablesDTO;
import com.afklm.cati.common.entity.Variables;
import com.afklm.cati.common.exception.CatiCommonsException;
import com.afklm.cati.common.repository.VariablesRepository;
import com.afklm.cati.common.service.VariablesService;
import com.afklm.cati.common.exception.jraf.JrafDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Title : RefComPrefDgtImpl.java
 * </p>
 * Service Implementation to manage RefComPrefDgt
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
@Service
public class VariablesImpl implements VariablesService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VariablesImpl.class);

    private static final List<String> VARIABLES_NOT_ALTERABLE = new ArrayList<String>(Arrays.asList("generatepasswordpattern",
            "generatepasswordpatternstomatch",
            "generatepasswordpatternsdelimiter",
            "passwordpatternstomatch"));

    @Autowired
    private VariablesRepository variablesRepository;

    @Override
    public List<Variables> getAllVariablesAlterable() throws CatiCommonsException, JrafDaoException {
        return variablesRepository.getAllVariablesAlterable(VARIABLES_NOT_ALTERABLE);
    }

    @Override
    public List<Variables> getAllVariables() throws CatiCommonsException, JrafDaoException {
        return variablesRepository.findAll();
    }

    @Override
    public Optional<Variables> getVariables(String key) throws CatiCommonsException, JrafDaoException {
        return variablesRepository.findById(key);
    }

    @Override
    public void addVariables(VariablesDTO variablesDTO) throws CatiCommonsException, JrafDaoException {
        Variables variablesAdded = new Variables();
        variablesAdded.setEnvValue(variablesDTO.getEnvValue());
        variablesAdded.setEnvKey(variablesDTO.getEnvKey());
        variablesRepository.saveAndFlush(variablesAdded);
        String msg = "Variables added : " + variablesAdded.toString();
        LOGGER.info(msg);
    }

    @Override
    public void updateVariables(Variables variables) throws CatiCommonsException, JrafDaoException {
        Variables variablesUpdated = variablesRepository.saveAndFlush(variables);
        String msg = "Variables updated : " + variablesUpdated.toString();
        LOGGER.info(msg);
    }

    @Override
    public void removeVariables(String key, String matricule) throws CatiCommonsException, JrafDaoException {
        Optional<Variables> variables = getVariables(key);

        if (variables.isPresent()) {
            LOGGER.info("Variables with key " + key + " deleted by " + matricule + " : " + variables.toString());
        }
        variablesRepository.deleteById(key);
    }
}
