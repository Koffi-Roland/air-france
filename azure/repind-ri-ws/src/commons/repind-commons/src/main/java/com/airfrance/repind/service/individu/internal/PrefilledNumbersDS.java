package com.airfrance.repind.service.individu.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.PrefilledNumbersRepository;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersTransform;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.PrefilledNumbers;
import com.airfrance.repind.entity.individu.PrefilledNumbersData;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PrefilledNumbersDS {

    private static final Log log = LogFactory.getLog(PrefilledNumbersDS.class);

    @Autowired
	@Qualifier("individuDS")
	protected IndividuDS individuDS;
    
    @Autowired
	@Qualifier("roleDS")
	protected RoleDS roleDS;
    
    @Autowired
    protected IndividuRepository individuRepository;
    
    @Autowired
	protected PrefilledNumbersRepository prefilledNumbersRepository;
    
    @Autowired
    @Qualifier("prefilledNumbersDataDS")
    protected PrefilledNumbersDataDS prefilledNumbersDataDS;
    
	private final int MAX_LEVENSHTEIN_DISTANCE = 2;
	private final int INDEX_NOT_FOUND = -1;

    /*PROTECTED REGION ID(_LLtXIP_9EeKgd-1pexCeMQ u m) ENABLED START*/
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<PrefilledNumbersDTO> findPrefilledNumbers(String gin) throws JrafDomainException {
		
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to find prefilled numbers without GIN");
		}
		
		List<PrefilledNumbers> prefilledNumbersList = prefilledNumbersRepository.findPrefilledNumbers(gin);
		
		if(prefilledNumbersList==null) {
			return null;
		}
		
		return PrefilledNumbersTransform.bo2DtoLight(prefilledNumbersList);
	}
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void clearPrefilledNumbers(String gin) throws JrafDomainException {
    	
    	Optional<Individu> individuFromDB = individuRepository.findById(gin);
    	
    	if(!individuFromDB.isPresent()) {
			log.error("Unable to get following individual : "+gin);
			throw new JrafDomainException("Individual not found");
		}
		
		Set<PrefilledNumbers> prefilledNumbersFromDB = individuFromDB.get().getPrefilledNumbers();
		
		if(prefilledNumbersFromDB==null || prefilledNumbersFromDB.size()==0) {
			return;
		}
		
		prefilledNumbersFromDB.clear();
		
		try {
			individuRepository.saveAndFlush(individuFromDB.get());
		} catch (DataAccessException e) {
			log.error("Unable to clear prefilled numbers to following individual : "+individuFromDB);
			throw new JrafDomainException(e);
		}
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void updatePrefilledNumbers(String gin, List<PrefilledNumbersDTO> prefilledNumbersDTOFromWS) throws JrafDomainException {
    	
    	Optional<Individu> individuFromDB = individuRepository.findById(gin);
    	
		if (!individuFromDB.isPresent()) {
			log.error("Unable to get following individual : "+gin);
			throw new JrafDomainException("Individual not found");
		}
    	
		Set<PrefilledNumbers> prefilledNumbersFromWS = PrefilledNumbersTransform.dto2BoLight(prefilledNumbersDTOFromWS);
		Set<PrefilledNumbers> prefilledNumbersFromDB = individuFromDB.get().getPrefilledNumbers();
		
		// Merge prefilled numbers from WS into DB ones
		mergePrefilledNumbersDTO(new ArrayList<PrefilledNumbers>(prefilledNumbersFromWS), prefilledNumbersFromDB);
		
		// Update individual with prefilled numbers
		try {
			individuRepository.saveAndFlush(individuFromDB.get());
		} 
		catch (DataAccessException e) {
			log.error("Unable to merge prefilled numbers to following individual : "+individuFromDB);
			throw new JrafDomainException(e);
		}
    }
    
	public boolean isValidRoleContract(RoleContratsDTO roleContract) {
		
		Date today = new Date();
		
		Date startValidityDate = roleContract.getDateDebutValidite();
		
		if(startValidityDate==null || today.before(startValidityDate)) {
			return false;
		}
	
		Date endValidityDate = roleContract.getDateFinValidite();
		
		if(endValidityDate==null || today.after(endValidityDate)) {
			return false;
		}
		
		return true;
	}

    public boolean isConsistentSaphirNumber(RoleContratsDTO roleContratsDTO,String gin) {
		
		IndividuDTO providedIndividu;
		IndividuDTO associatedIndividu;
		try {
			providedIndividu = getIndividu(gin);
			associatedIndividu = getIndividu(roleContratsDTO.getGin());
		} catch(JrafDomainException e) {
			return false;
		}
		
		return isSameIndividu(providedIndividu, associatedIndividu);
	}
    
	public RoleContratsDTO getRoleContract(String contractNumber, String typeContrat) throws JrafDomainException {
		
		RoleContratsDTO roleContrats = new RoleContratsDTO();
		roleContrats.setNumeroContrat(contractNumber);
		roleContrats.setTypeContrat(typeContrat);
		
		List<RoleContratsDTO> roleContratsList = roleDS.findAll(roleContrats);
		
		if(roleContratsList==null || roleContratsList.size()==0) {
			return null;
		}
		
		if(roleContratsList.size()>1) {
			throw new JrafDomainException("Severals contracts for following contract number : "+contractNumber);
		}
		
		return roleContratsList.get(0);
	}
	
	/**
	 * This method is aimed to merge left list to right list
	 * 
	 * @param leftList
	 * @param rightList
	 * @return
	 */
	protected Set<PrefilledNumbers> mergePrefilledNumbersDTO(List<PrefilledNumbers> leftList, Set<PrefilledNumbers> rightSet) {
	
		if(rightSet==null) {
			rightSet = new HashSet<PrefilledNumbers>();
		}
		
		List<PrefilledNumbers> rightList = new ArrayList<PrefilledNumbers>(rightSet);
		rightSet.clear();
		
		for(PrefilledNumbers left : leftList) {

			int index = rightList.indexOf(left); // same gin and contract type
			// If the contract number is set, it's an update
			// Else it's a removal
			if(left.getContractNumber() != null && !left.getContractNumber().isEmpty()) {
				// New entry
				if(index==INDEX_NOT_FOUND) {
					rightList.add(left);
					prefilledNumbersRepository.saveAndFlush(left);
					continue;
				}

				// Update entry
				PrefilledNumbers right = rightList.get(index);
				right.setContractNumber(left.getContractNumber());
				right.setModificationDate(left.getModificationDate());
				right.setModificationSignature(left.getModificationSignature());
				right.setModificationSite(left.getModificationSite());
				updatePrefilledNumbersData(left, right);

			} else if(index!=INDEX_NOT_FOUND) {
				rightList.remove(index);
			}
		}
		
		if(!rightList.isEmpty()) {
			rightSet.addAll(rightList);
		}

		return rightSet;
	}
	
	protected void updatePrefilledNumbersData(PrefilledNumbers left, PrefilledNumbers right) {
		Set<PrefilledNumbersData> leftPrefilledNumbersDataSet = left.getPrefilledNumbersData();
		if(leftPrefilledNumbersDataSet != null && !leftPrefilledNumbersDataSet.isEmpty()) {
			Set<PrefilledNumbersData> rightPrefilledNumbersDataSet = right.getPrefilledNumbersData();

			if(rightPrefilledNumbersDataSet == null) {
				rightPrefilledNumbersDataSet = new HashSet<PrefilledNumbersData>();
			}
			Set<PrefilledNumbersData> oldRightPrefilledNumbersDataSet = new HashSet<PrefilledNumbersData>(rightPrefilledNumbersDataSet);
			rightPrefilledNumbersDataSet.clear();
			for(PrefilledNumbersData prefilledNumbersDataLeft : leftPrefilledNumbersDataSet) {
				boolean isFound = false;
				// Si on trouve un prefilled numbers data avec un label existant on 
				// est dans le cas d'une modification du prefilled numbers data
				for(PrefilledNumbersData prefilledNumbersDataRight : oldRightPrefilledNumbersDataSet) {
					if(prefilledNumbersDataRight.getPrefilledNumbersDataId() != null && prefilledNumbersDataLeft.getKey().equals(prefilledNumbersDataRight.getKey())) {
						prefilledNumbersDataRight.setValue(prefilledNumbersDataLeft.getValue());
						isFound = true;
						break;
					}
				}
				// Sinon on est dans le cas d'une création d'un nouveau prefilled numbers data
				if(!isFound) {
					oldRightPrefilledNumbersDataSet.add(prefilledNumbersDataLeft);
					prefilledNumbersDataLeft.setPrefilledNumbers(right);
				}
			}
			rightPrefilledNumbersDataSet.addAll(oldRightPrefilledNumbersDataSet);
		}
	}

	/**
	 * This method is aimed to get an individual from a gin
	 * 
	 * @param gin
	 * @return
	 * @throws JrafDomainException
	 */
	protected IndividuDTO getIndividu(String gin) throws JrafDomainException {
		IndividuDTO individu = new IndividuDTO();
		individu.setSgin(gin);
		individu = individuDS.get(individu);
		return individu;
	}
	
	/**
	 * This method is aimed to check if two individuals are the same
	 * 
	 * <p>
	 * Two individuals are the same if :<br/>
	 * <code>left gin = right gin</code><br/>
	 * otherwise when : <br/>
	 * <code>left name = right name</code> AND <code>left first-name ~ first-name</code>
	 * </ul>
	 * The Levenshtein Distance algorithm is used for first-name
	 * </p>
	 * 
	 * @param leftIndividu
	 * @param rightIndividu
	 * @return
	 */
	protected boolean isSameIndividu(IndividuDTO leftIndividu, IndividuDTO rightIndividu) {
		
		String leftGin = leftIndividu.getSgin();
		String rightGin = rightIndividu.getSgin();
		
		if(leftGin!=null && leftGin.equals(rightGin)) {
			return true;
		}
		
		String leftName = leftIndividu.getNom();
		String rightName = rightIndividu.getNom();
		
		if(leftName==null || !leftName.equals(rightName)) {
			return false;
		}
		
		String leftFirstName = leftIndividu.getPrenom();
		String rightFirstName = rightIndividu.getPrenom();
		
		int levenshteinDistance = 0;
		
		try {
			levenshteinDistance = SicStringUtils.getLevenshteinDistance(leftFirstName,rightFirstName);
		} catch(IllegalArgumentException e) {
			levenshteinDistance = MAX_LEVENSHTEIN_DISTANCE + 1;
		}
		
		if(levenshteinDistance>MAX_LEVENSHTEIN_DISTANCE) {
			return false;
		}
		
		return true;
		
	}
}
