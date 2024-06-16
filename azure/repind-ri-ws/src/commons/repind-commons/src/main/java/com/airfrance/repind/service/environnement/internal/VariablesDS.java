package com.airfrance.repind.service.environnement.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.dto.environnement.VariablesTransform;
import com.airfrance.repind.entity.environnement.Variables;
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

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private VariablesRepository variablesRepository;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	
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
