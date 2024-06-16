package com.afklm.repind.v7.createorupdateindividualws.helpers;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.contract.InvalidSaphirNumberException;
import com.airfrance.ref.exception.contract.NotConsistentSaphirNumberException;
import com.airfrance.ref.exception.contract.SaphirContractNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.individu.internal.PrefilledNumbersDS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("prefilledNumbersHelperV7")
@Slf4j
public class PrefilledNumbersHelper {

	@Autowired
	protected PrefilledNumbersDS prefilledNumbersDS;
	
	public enum Contract {
		SAPHIR_NUMBER_TYPE("Saphir","S"),
		CORPORATE_CONTRACT_REFERENCE_NUMBER_TYPE ("Corporate Contract Reference", "CC"),
		CORPORATE_ACCOUNT_CODE_NUMBER_TYPE  ("Corporate Account code", "AC"),
		ARIES_REFERENCE_NUMBER_TYPE ("ARIES Reference", "AR"),
		BLUE_BIZ_REFERENCE_NUMBER_TYPE ("BlueBiz Reference", "BB");
		
		public String nameField;
		public String type;
		
		Contract(String name, String type) {
			this.nameField = name;
			this.type = type;
		}
		
		public static String errorMessage(){
			StringBuilder sb = new StringBuilder("Only the following contract are allowed :");
			for(Contract contract : Contract.values()) {
				sb.append("\n");
				sb.append(contract.nameField);
				sb.append(" - type = ");
				sb.append(contract.type);
			}
			return sb.toString();
		}
		
		public static Contract getContract(String value) {
			for(Contract contract : Contract.values()) {
				if(contract.type.equals(value)) {
					return contract;
				}
			}
			return null;
		}
	}

	public void clearPrefilledNumbers(String gin) throws JrafDomainException {
		
		// UPDATE INDIVIDUAL WITH INDIVIDUAL NUMBERS
		try {
			prefilledNumbersDS.clearPrefilledNumbers(gin);
		} 
		// Clear failed -> raise a technical error
		catch (JrafDomainException e) {
			log.error("Unable to clear prefilled numbers to following individual : {}",gin);
			
			throw new JrafDomainException("Unable to clear prefilled numbers");
		}		
	}
	
	/**
	 * This method is aimed to update prefilled numbers of an individual
	 * 
	 * @param gin
	 * @param prefilledNumbersList
	 * @throws BusinessException 
	 */
	public void updatePrefilledNumbers(String gin, List<PrefilledNumbersDTO> prefilledNumbersList, SignatureDTO signature) throws JrafDomainException {
		
		if(prefilledNumbersList==null) {
			throw new MissingParameterException("prefilled numbers list is mandatory");
		}
		
		// Signature is mandatory -> Mandatory field exception
		if(prefilledNumbersList.size() != 0 && signature==null) {
			throw new MissingParameterException("Signature data is mandatory");
		}
				
		// Link prefilled numbers to individual
		for(PrefilledNumbersDTO prefilledNumber : prefilledNumbersList) {
			prefilledNumber.setSgin(gin);
			prefilledNumber.setCreationDate(new Date());
			prefilledNumber.setCreationSignature(signature.getSignature());
			prefilledNumber.setCreationSite(signature.getSite());
			prefilledNumber.setModificationDate(new Date());
			prefilledNumber.setModificationSignature(signature.getSignature());
			prefilledNumber.setModificationSite(signature.getSite());
		}

		// Update individual with prefilled numbers
		try {
			prefilledNumbersDS.updatePrefilledNumbers(gin, prefilledNumbersList);
		} 
		// Update failed -> raise a technical error
		catch (JrafDomainException e) {
			log.error("Unable to update prefilled numbers to following individual : {}",gin);
			throw new JrafDomainException("Unable to update prefilled numbers");
		}
	}
	
	/**
	 * This method is aimed to check prefilled numbers list
	 * 
	 * <p>
	 * A prefilled numbers list is valid when :
	 * <ul>
	 *  <li>it strictly contains one item</li>
	 *  <li>this item is a prefilled number</li>
	 * </ul>
	 * </p>
	 * 
	 * @param prefilledNumbersList
	 * @param gin 
	 * @throws IllegalArgumentException when list is null or empty
	 */
	public void checkPrefilledNumbers(List<PrefilledNumbersDTO> prefilledNumbersList, String gin) throws JrafDomainException {
		
		// Unable to check a null or empty list
		if(prefilledNumbersList==null || prefilledNumbersList.size()==0) {
			throw new IllegalArgumentException("Nothing to check on an empty list");
		}

		for(PrefilledNumbersDTO prefilledNumbers : prefilledNumbersList) {
			// The item has to be a conform prefilled number
			Contract contract = Contract.getContract(prefilledNumbers.getContractType());
			if(contract == null) {
				throw new InvalidParameterException(Contract.errorMessage());
			}

			switch(contract) {
			case SAPHIR_NUMBER_TYPE:
				checkSaphirNumber(prefilledNumbers.getContractNumber(), gin);
				break;
			case CORPORATE_CONTRACT_REFERENCE_NUMBER_TYPE:
				//TODO
				break;
			case CORPORATE_ACCOUNT_CODE_NUMBER_TYPE:
				//TODO
				break;
			case ARIES_REFERENCE_NUMBER_TYPE:
				//TODO
				break;
			case BLUE_BIZ_REFERENCE_NUMBER_TYPE:
				break;
			}
		}
		
	}
	
	/**
	 * This method is aimed to check saphir number
	 * 
	 * <p>
	 * A saphir number is valid when :
	 * <ul>
	 * 	<li>It exists in database</li>
	 * 	<li>The contract dates are valid</li>
	 *  <li>It is consistent with the individuals datas (name+firstname)</li>
	 * </ul>
	 * </p>
	 * 
	 * @param saphirNumber
	 * @param gin (from individu)
	 * @throws BusinessException
	 */
	public void checkSaphirNumber(String saphirNumber, String gin) throws JrafDomainException {

		RoleContratsDTO roleContract;
		try {
			roleContract = prefilledNumbersDS.getRoleContract(saphirNumber,Contract.SAPHIR_NUMBER_TYPE.type);
		} catch (JrafDomainException e) {
			roleContract = null; // same exception as null result
		}

		// The saphir number exists in the database
		if (roleContract == null) {
			log.error("Contract not found for following saphir number : {}",saphirNumber);
			throw new SaphirContractNotFoundException("No contract found for input saphir number : " + saphirNumber);
		}

		// The saphir number is still valid (dates)
		if (!prefilledNumbersDS.isValidRoleContract(roleContract)) {
			log.error("Not valid saphir number : {}",saphirNumber);
			throw new InvalidSaphirNumberException("Input saphir number expired");
		}

		// The saphir number is consistent with the individuals informations
		if (!prefilledNumbersDS.isConsistentSaphirNumber(roleContract, gin)) {
			log.error("Not consistent saphir number : {}",saphirNumber);
			throw new NotConsistentSaphirNumberException("Firsname or lastname is not matching");
		}
	}	
}
