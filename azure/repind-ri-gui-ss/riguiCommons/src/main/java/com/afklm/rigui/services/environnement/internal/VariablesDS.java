package com.afklm.rigui.services.environnement.internal;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.environnement.VariablesRepository;
import com.afklm.rigui.dto.environnement.VariablesDTO;
import com.afklm.rigui.dto.environnement.VariablesTransform;
import com.afklm.rigui.entity.environnement.Variables;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class VariablesDS {

	private static final Log log = LogFactory.getLog(VariablesDS.class);

	@Autowired
	private VariablesRepository variablesRepository;

	public VariablesDTO getByEnvKey(String envKey) throws JrafDomainException {

		Optional<Variables> variables = variablesRepository.findById(envKey);

		if (!variables.isPresent()) {
			return null;
		}

		VariablesDTO variablesDTO = VariablesTransform.bo2DtoLight(variables.get());

		return variablesDTO;
	}

	
	public String getEnv(String envKey, String defaultValue) {

		VariablesDTO variablesDTO = null;

		try {
			variablesDTO = getByEnvKey(envKey);
		} catch (Exception e) {
			variablesDTO = null; // same error than null
		}

		if (variablesDTO == null) {

			if (VariablesDS.log.isErrorEnabled()) {
				VariablesDS.log.error("Unable to get value from '" + envKey
						+ "' defined in database, use default value : " + defaultValue);
			}

			return defaultValue;
		}

		return variablesDTO.getEnvValue();
	}

	
	public long getEnv(String pEnvKey, long pDefaultValue) {
		String defaultValue = String.valueOf(pDefaultValue);
		String value = getEnv(pEnvKey, defaultValue);
		return Long.parseLong(value);
	}
	
	
	public int getEnv(String pEnvKey, int pDefaultValue) {
		String defaultValue = String.valueOf(pDefaultValue);
		String value = getEnv(pEnvKey, defaultValue);
		return Integer.parseInt(value);
	}

	
	public double getEnv(String pEnvKey, double pDefaultValue) {
		String defaultValue = String.valueOf(pDefaultValue);
		String value = getEnv(pEnvKey, defaultValue);
		return Double.parseDouble(value);
	}

	
	public void update(VariablesDTO variable) throws InvalidParameterException {
		Optional<Variables> variableDB = variablesRepository.findById(variable.getEnvKey());
		variableDB.get().setEnvValue(variable.getEnvValue());
		variablesRepository.save(variableDB.get());
	}
}
