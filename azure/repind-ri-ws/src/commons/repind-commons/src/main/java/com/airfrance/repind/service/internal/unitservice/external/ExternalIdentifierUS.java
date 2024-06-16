package com.airfrance.repind.service.internal.unitservice.external;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001793.v1.InWeboLoginCreateV10;
import com.afklm.soa.stubs.w001793.v1.req.InWeboLoginCreateRequest;
import com.afklm.soa.stubs.w001793.v1.res.InWeboLoginCreateResponse;
import com.airfrance.ref.exception.external.SendAliasException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.environnement.VariablesRepository;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierTransform;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.entity.environnement.Variables;
import com.airfrance.repind.entity.external.ExternalIdentifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExternalIdentifierUS {

	/** logger */
	private static final Log log = LogFactory.getLog(ExternalIdentifierUS.class);

	private static final String ENV_VAR_INWEBO = "INWEBO_SERVICE_ID";

	/* PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw u var) ENABLED START */

	/** references on associated Repository */
	@Autowired
	protected VariablesRepository variablesRepository;

	@Autowired
	protected ExternalIdentifierRepository externalIdentifierRepository;

	@Autowired	
    private ApplicationContext context;

	/* PROTECTED REGION END */
	/**
	 * Constructeur vide
	 */
	public ExternalIdentifierUS() {

	}

	/* PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw u m) ENABLED START */

	
	public boolean checkExistingAlias(IndividuDTO individuDTO, String context) {

		List<ExternalIdentifierDTO> listExternalIdentifier = individuDTO.getExternalIdentifierList();

		if (listExternalIdentifier != null && listExternalIdentifier.size() > 0) {
			for (ExternalIdentifierDTO externalIdentifier : listExternalIdentifier) {
				if (externalIdentifier.getType().equalsIgnoreCase(context)) {
					return true;
				}
			}
		}

		return false;
	}

	
	public String generateAlias() throws SendAliasException {

		if (ExternalIdentifierUS.log.isInfoEnabled()) {
			ExternalIdentifierUS.log.info("Starting generation of an alias.");
		}

		String alias = null;
		UUID aliasUUID = UUID.randomUUID();
		alias = String.valueOf(aliasUUID);

		if (alias == null) {
			ExternalIdentifierUS.log.error("ERROR: Error during generating Alias !");
			throw new SendAliasException("Error during generation of alias");
		}

		if (ExternalIdentifierUS.log.isInfoEnabled()) {
			ExternalIdentifierUS.log.info("Generation of alias is done.");
		}

		return alias;
	}

	
	public String sendAlias(String alias) throws SendAliasException, JrafDaoException {

		if (ExternalIdentifierUS.log.isInfoEnabled()) {
			ExternalIdentifierUS.log.info("Sending Alias to External Partner.");
		}

		/**
		 * Get the InWebo ServiceID from the table ENV_VAR
		 * RCT = 3238
		 * PRD = ??
		 */
		Optional<Variables> variableInWebo = variablesRepository.findById(ExternalIdentifierUS.ENV_VAR_INWEBO);

		if (!variableInWebo.isPresent() || variableInWebo.get().getEnvValue() == null) {
			ExternalIdentifierUS.log.error("ERROR-539: Unable to get serviceId number from DB.");
			throw new SendAliasException("Unable to get serviceId number from DB.");
		}

		Long inWeboServiceId = Long.valueOf(variableInWebo.get().getEnvValue());

		// Request for calling InWebo
		InWeboLoginCreateRequest inWeboRequest = new InWeboLoginCreateRequest();
		inWeboRequest.setUserid(0);
		inWeboRequest.setServiceid(inWeboServiceId);
		inWeboRequest.setLogin(alias);
		inWeboRequest.setStatus(0);
		inWeboRequest.setRole(0);
		inWeboRequest.setAccess(0);
		inWeboRequest.setCodetype(0);

		InWeboLoginCreateResponse inWeboResponse = new InWeboLoginCreateResponse();
		try {

			InWeboLoginCreateV10 consumerW001793v1 = (InWeboLoginCreateV10) context.getBean("consumerW001793v1");
			
			// Calling InWebo method to store the Alias
			inWeboResponse = consumerW001793v1.inWeboLoginCreate(inWeboRequest);
		} catch (SystemException e) {
			ExternalIdentifierUS.log.error("ERROR-539: " + e.getMessage());
			throw new SendAliasException(e.getMessage());
		} catch (Exception e) {
			ExternalIdentifierUS.log.error("ERROR: " + e.getMessage());
			throw new SendAliasException(e.getMessage());
		}

		if (inWeboResponse == null) {
			ExternalIdentifierUS.log.error("ERROR-539: Result is null.");
			throw new SendAliasException("Result is null.");
		} else {
			if (inWeboResponse.getErr() != null && !inWeboResponse.getErr().equalsIgnoreCase("OK")) {
				if (inWeboResponse.getErr() != null && inWeboResponse.getErr().equalsIgnoreCase("NOK:full")) {
					ExternalIdentifierUS.log.error("ERROR-539: Max number of users for the service has been reached.");
					throw new SendAliasException("Max number of users for the service has been reached.");
				} else if (inWeboResponse.getErr() != null
						&& inWeboResponse.getErr().equalsIgnoreCase("NOK:loginexists")) {
					ExternalIdentifierUS.log.error("ERROR-539: Login already exists.");
					throw new SendAliasException("Login already exists.");
				} else if (inWeboResponse.getErr() != null && inWeboResponse.getErr().equalsIgnoreCase("NOK:SN")) {
					ExternalIdentifierUS.log.error("ERROR-539: Invalid input parameters.");
					throw new SendAliasException("Invalid input parameters.");
				} else if (inWeboResponse.getErr() != null
						&& inWeboResponse.getErr().equalsIgnoreCase("NOK:srv unknown")) {
					ExternalIdentifierUS.log.error("ERROR-539: Wrong serviceId.");
					throw new SendAliasException("Wrong serviceId.");
				} else if (inWeboResponse.getErr() != null) {
					ExternalIdentifierUS.log.error("ERROR-539: " + inWeboResponse.getErr());
					throw new SendAliasException(inWeboResponse.getErr());
				}
			}
		}

		String loginId = Long.toString(inWeboResponse.getId());
		if (loginId == null) {
			ExternalIdentifierUS.log.error("ERROR-539: LoginId returned is null.");
			throw new SendAliasException("LoginId returned is null.");
		}

		if (ExternalIdentifierUS.log.isInfoEnabled()) {
			ExternalIdentifierUS.log.info("Alias sent to External Partner.");
		}

		return loginId;
	}

	
	public Set<ExternalIdentifierDTO> getValidAlias(IndividuDTO individuDTO) throws JrafDomainException {
		Set<ExternalIdentifierDTO> externalIdentiersDTO = new HashSet<>();

		List<ExternalIdentifier> externalIdentifiers = externalIdentifierRepository
				.findExternalIdentifierALL(individuDTO.getSgin());

		if (externalIdentifiers != null && externalIdentifiers.size() > 0) {
			for (ExternalIdentifier externalIdentifier : externalIdentifiers) {
				ExternalIdentifierDTO externalIdentifierDTO = ExternalIdentifierTransform
						.bo2DtoForProvide(externalIdentifier);
				// Remove _ID for every external identifier to have the same output format
				if (externalIdentifierDTO.getType().contains("_ID")) {
					externalIdentifierDTO.setType(externalIdentifierDTO.getType().substring(0,
							externalIdentifierDTO.getType().indexOf("_ID")));
				}
				externalIdentiersDTO.add(externalIdentifierDTO);
			}
		}

		return externalIdentiersDTO;
	}

	/* PROTECTED REGION END */
}
