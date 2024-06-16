package com.airfrance.repind.service.agence.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.agence.MembreReseauRepository;
import com.airfrance.repind.dao.reference.ReseauRepository;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.MembreReseauDTO;
import com.airfrance.repind.dto.agence.MembreReseauTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.MembreReseau;
import com.airfrance.repind.entity.reference.Reseau;
import com.airfrance.repind.service.internal.unitservice.agence.MembreReseauUS;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * MembreReseauDS
 */
@Service
public class MembreReseauDS {

	/** logger */
//	private static final Log log = LogFactory.getLog(MembreReseauDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** WORLD NETWORK */
	private static final String WORLD_NETWORK = "RM";

	/** NETWORK */
	private static final String NETWORK = "R";

	/** SUB_NETWORK */
	private static final String SUB_NETWORK = "S";

	/** unit service : membreReseauUS **/
	@Autowired
	private MembreReseauUS membreReseauUS;

	/** references on associated DAOs */
	@Autowired
	private MembreReseauRepository membreReseauRepository;

	@Autowired
	private ReseauRepository reseauRepository;

    @Transactional(readOnly=true)
	public Integer count() throws JrafDomainException {
		return (int) membreReseauRepository.count();
	}

    @Transactional(readOnly=true)
	public Integer countWhere(MembreReseauDTO dto) throws JrafDomainException {
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(dto);
		return (int) membreReseauRepository.count(Example.of(membreReseau));
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(MembreReseauDTO dto) throws JrafDomainException {
		/*
		 * PROTECTED REGION ID(_kpmxwBBUEeGa28JReN915w DS-CM countWhere) ENABLED
		 * START
		 */
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(dto);
		membreReseauRepository.saveAndFlush(membreReseau);
		/*
		 * PROTECTED REGION ID(_kpmxwBBUEeGa28JReN915w DS-CM countWhere) ENABLED
		 * START
		 */
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createNetworkMember(MembreReseauDTO membreReseauDTO, String reseauCode, AgenceDTO agency) throws JrafDomainException {
		
		//Transform...
		membreReseauDTO.setAgence(agency);
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(membreReseauDTO);
		
		// Retrieve network...
		Optional<Reseau> reseau = reseauRepository.findById(reseauCode);
		if (!reseau.isPresent()) {
			throw new JrafDomainException("UNABLE TO FIND RESEAU : " + reseauCode);
		}
		
		// Link(s)...
		membreReseau.setReseau(reseau.get());
		
		// Persist...
		membreReseauRepository.saveAndFlush(membreReseau);
		
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public MembreReseauDTO createNetworkMember(MembreReseauDTO membreReseauDTO) throws JrafDomainException {
		
		//Transform...
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(membreReseauDTO);
		
		// Retrieve network...
		Optional<Reseau> reseau = reseauRepository.findById(membreReseauDTO.getReseau().getCode());
		if (!reseau.isPresent()) {
			throw new JrafDomainException("UNABLE TO FIND RESEAU : " + membreReseauDTO.getReseau().getCode());
		}
		
		// Link(s)...
		membreReseau.setReseau(reseau.get());
		
		// Persist...
		MembreReseau newMembreReseau = membreReseauRepository.saveAndFlush(membreReseau);
		
		return MembreReseauTransform.bo2Dto(newMembreReseau);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(MembreReseauDTO membreReseauDTO, Agence agency) throws JrafDomainException {
		
		// Transform...
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(membreReseauDTO);
		membreReseau.setAgence(agency);
		
		// Create...
		membreReseauRepository.saveAndFlush(membreReseau);
		
		// Update DTO...
		membreReseauDTO = MembreReseauTransform.bo2DtoLight(membreReseau);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(MembreReseauDTO dto) throws JrafDomainException {
        MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(dto);
        membreReseauRepository.delete(membreReseau);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Integer oid) throws JrafDomainException {
		membreReseauRepository.deleteById(oid);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(MembreReseauDTO dto) throws JrafDomainException {
		MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(dto);
		membreReseauRepository.saveAndFlush(membreReseau);
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public MembreReseauDTO updateNetworkLink(MembreReseauDTO dto) throws JrafDomainException {
		MembreReseau membreReseau = MembreReseauTransform.dto2Bo(dto);
		MembreReseau membreReseauUpdated =membreReseauRepository.saveAndFlush(membreReseau);
		return MembreReseauTransform.bo2Dto(membreReseauUpdated);
	}

    @Transactional(readOnly=true)
	public List<MembreReseauDTO> findAll() throws JrafDomainException {
		List<MembreReseauDTO> results = new ArrayList<>();
		for(MembreReseau found : membreReseauRepository.findAll()) {
			results.add(MembreReseauTransform.bo2DtoLight(found));
		}
		return results;
	}

    @Transactional(readOnly=true)
    public List<MembreReseauDTO> findByExample(MembreReseauDTO dto) throws JrafDomainException {
        MembreReseau membreReseau = MembreReseauTransform.dto2BoLight(dto);
        List<MembreReseauDTO> result = new ArrayList<>();
        for (MembreReseau found : membreReseauRepository.findAll(Example.of(membreReseau))) {
			result.add(MembreReseauTransform.bo2DtoLight(found));
		}
		return result;
    }

    @Transactional(readOnly=true)
	public MembreReseauDTO get(Integer oid) throws JrafDomainException {
		Optional<MembreReseau> membreReseau = membreReseauRepository.findById(oid);

		if (!membreReseau.isPresent()) {
			return null;
		}

		return MembreReseauTransform.bo2DtoLight(membreReseau.get());
	}
    
    @Transactional(readOnly=true)
	public MembreReseauDTO findById(Integer oid) throws JrafDomainException {
		Optional<MembreReseau> membreReseau = membreReseauRepository.findById(oid);

		if (!membreReseau.isPresent()) {
			return null;
		}

		return MembreReseauTransform.bo2Dto(membreReseau.get());
	}


    @Transactional(readOnly=true)
	public void checkNetworkLinkInput(AgenceDTO agenceDTO, Agence agence) throws JrafDomainException {

		if (!UList.isNullOrEmpty(agenceDTO.getReseaux())) {
			for (MembreReseauDTO membreReseauDTO : agenceDTO.getReseaux()) {
				// create or update
				if (membreReseauDTO.getReseau() != null && membreReseauDTO.getReseau().getCode() != null) {
					Optional<Reseau> reseau = reseauRepository.findById(membreReseauDTO.getReseau().getCode());
					// Check if network exists on database
					checkNetwork(reseau);

					if (membreReseauDTO.getCle() != null) {
						// update
						checkLinkToUpdate(membreReseauDTO, reseau.get(),agence);
					} else {
						// creation
						checkLinkForCreation(membreReseauDTO, reseau.get(), agenceDTO, agence);
					}

				} else {
					// Delete
					checkLinkToDelete(membreReseauDTO);

				}

			}
		}

	}
    
	@Transactional(readOnly = true)
	public List<MembreReseauDTO> findActiveByPmAndCode(String gin, String code) throws JrafDomainException {
		List<MembreReseauDTO> result = new ArrayList<>();
		List<MembreReseau> response = membreReseauRepository.findActiveByPmAndCode(gin, code);
		for (MembreReseau link : response) {
			result.add(MembreReseauTransform.bo2DtoLight(link));
		}
		return result;
	}

	/**
	 * 
	 * Start and closure dates of the link should be correct and coherent :
	 * Start date < closure date 
	 * Start date >= Opening date of the agency 
	 * Start date >= Opening date of the network
	 * 
	 * An agency can be linked only to a single network (R), network (RM) or
	 * sub-network (S) at once. 
	 * 
	 * It is not possible to link an agency to a
	 * sub-network (S) if the agency is not already linked (active link) to a
	 * network (R
	 * 
	 * @param membreReseau
	 * @param reseau
	 * @param agenceDTO
	 * @param agence
	 * @throws JrafDomainException
	 */
	private void checkLinkForCreation(MembreReseauDTO membreReseau, Reseau reseau, AgenceDTO agenceDTO, Agence agence)
			throws JrafDomainException {

		Date dateDebut = membreReseau.getDateDebut();
		Date dateFin = membreReseau.getDateFin();

		if (dateDebut == null) {
			throw new JrafDomainException("133-VALIDITY START DATE");
		}

		if (dateFin != null && dateDebut.after(dateFin)) {
			throw new JrafDomainException("941-CHECK END DATE");
		}
		
		if (reseau.getDateCreation() != null && !isValidDateRange(dateDebut, reseau.getDateCreation(), true)) {
			throw new JrafDomainException("941-CHECK VALIDITY START DATE");
		}
		if (reseau.getDateFermeture() != null && !isValidDateRange(reseau.getDateFermeture(), dateDebut, true)) {
			throw new JrafDomainException("941-CHECK VALIDITY START DATE");
		}

		if (agence.getDateCreation() != null && !isValidDateRange(dateDebut, agence.getDateDebut(), true)) {
			throw new JrafDomainException("941-CHECK VALIDITY START DATE");
		}
		if (agence.getDateFin() != null && !isValidDateRange(agence.getDateFin(),dateDebut, true)) {
			throw new JrafDomainException("941-CHECK VALIDITY START DATE");
		}
		
		List<MembreReseau> listExistingLinks = membreReseauUS.getLinkNetworksByType(agence.getGin(),
				reseau.getType());
		if (!overlapsExistingLink(listExistingLinks, dateDebut, dateFin)) {
			throw new JrafDomainException("941-EXISTING LINK");
		}

		if (SUB_NETWORK.equals(reseau.getType())) {
			List<MembreReseau> membreReseauParents = membreReseauUS.getParentLinkNetwork(agence.getGin(), reseau.getCode());
			if (CollectionUtils.isEmpty(membreReseauParents)) {
				throw new JrafDomainException("941-MISSING LINK NETWORK");
			}

			if (!eligibilityParentsLink(membreReseauParents, membreReseau)) {
				throw new JrafDomainException("941-MISSING LINK NETWORK");
			}
		}
	}

	/**
	 * If Link already exists with the same type and code 
	 * check if periods don't overlap
	 *  
	 * @param listExistingLinks
	 * @param startNewLink
	 * @param endNewLink
	 * @return
	 */
	private boolean overlapsExistingLink(List<MembreReseau> listExistingLinks, Date startNewLink, Date endNewLink) {
		boolean existingLink = true;
		if (!CollectionUtils.isEmpty(listExistingLinks)) {
			for (MembreReseau membreReseau : listExistingLinks) {
				Date startExistingLink = membreReseau.getDateDebut();
				Date endExistingLink = membreReseau.getDateFin();
				
				if (startNewLink.before(startExistingLink) || compareSameDate(startNewLink, startExistingLink)) {
					return false;
				}
				
				if(endExistingLink!=null && endExistingLink.after(startNewLink)){
					return false;
				}
				
				if (endNewLink != null && endExistingLink != null) {
					Interval intervalNewLink = new Interval(startNewLink.getTime(), endNewLink.getTime());
					Interval intervalExistingLink = new Interval(startExistingLink.getTime(),
							endExistingLink.getTime());
					if (intervalNewLink.overlaps(intervalExistingLink)) {
						return false;
					}
				}
				

			}
		}
		return existingLink;
	}

	/**
	 * Eligibility Parents Link --> Active link 
	 * 
	 * @param membreReseauParents
	 * @param startDateSubNetwork
	 * @return
	 */
	private boolean eligibilityParentsLink(List<MembreReseau> membreReseauParents, MembreReseauDTO membreReseauDTO) {
		boolean parentEligible = false;
		for (MembreReseau membreReseau : membreReseauParents) {
			Date startLinkNetwork = membreReseau.getDateDebut();
			Date endLinkNetwork = membreReseau.getDateFin();
			if (startLinkNetwork != null && startLinkNetwork.before(membreReseauDTO.getDateDebut())
					|| compareSameDate(membreReseauDTO.getDateDebut(), startLinkNetwork)) {
				if (endLinkNetwork == null) {
					return true;
				} else {
					if (endLinkNetwork.after(membreReseauDTO.getDateDebut())) {
						if (membreReseauDTO.getDateFin() != null && membreReseauDTO.getDateFin().after(endLinkNetwork) || membreReseauDTO.getDateFin() == null) {
							membreReseauDTO.setDateFin(endLinkNetwork);
							return true;
						} else if (compareSameDate(membreReseauDTO.getDateFin(), endLinkNetwork) || membreReseauDTO.getDateFin().before(endLinkNetwork)) {
							return true;
						}
					}
				}
			} else {
				return false;
			}

		}

		return parentEligible;
	}

	/**
	 * Same YEAR/MONTH/DAY
	 * 
	 * @param startDateSubNetwork
	 * @param startLinkNetwork
	 * @return
	 */
	private static boolean compareSameDate(Date startDateSubNetwork, Date startLinkNetwork) {
		Calendar calLinkNetwork = Calendar.getInstance();
		Calendar calLinkSubNetwork = Calendar.getInstance();
		calLinkNetwork.setTime(startLinkNetwork);
		calLinkSubNetwork.setTime(startDateSubNetwork);

		return calLinkNetwork.get(Calendar.YEAR) == calLinkSubNetwork.get(Calendar.YEAR)
				&& calLinkNetwork.get(Calendar.DAY_OF_YEAR) == calLinkSubNetwork.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Check if AIN exist 
	 * It is not possible to delete a link between an agency and a network, if another link exists with that agency and sub-network. 
	 * 
	 * @param membreReseauDTO
	 * @throws JrafDomainException
	 */
	private void checkLinkToDelete(MembreReseauDTO membreReseauDTO) throws JrafDomainException {
		inputCoherencyForDelete(membreReseauDTO);
		Optional<MembreReseau> membreReseauToDelete = membreReseauRepository.findById(membreReseauDTO.getCle());
		if (!membreReseauToDelete.isPresent()) {
			throw new JrafDomainException("943-INEXISTANT AIN");
		} else {
			if (membreReseauToDelete.get().getReseau().getType().equals(NETWORK)
					|| membreReseauToDelete.get().getReseau().getType().equals(WORLD_NETWORK)) {
				int countSubNetworks = membreReseauUS.countSubNetworks(membreReseauToDelete.get());
				if (countSubNetworks > 0) {
					throw new JrafDomainException("943-ATTACHED SUBNETWORKS");
				}

			}
		}
	}

	/**
	 * Start date and code network are unchangeable. 
	 * Closure date of the link should be correct and coherent 
	 * Start date < closure date 
	 * 
	 * @param membreReseau
	 * @param reseau
	 * @param agence 
	 * @throws JrafDomainException
	 */
	private void checkLinkToUpdate(MembreReseauDTO membreReseau, Reseau reseau, Agence agence) throws JrafDomainException {
		Optional<MembreReseau> membreReseauToUpdate = membreReseauRepository.findById(membreReseau.getCle());

		if (!membreReseauToUpdate.isPresent()) {
			throw new JrafDomainException("942-INEXISTANT AIN");
		}
		
		if (!membreReseauToUpdate.get().getAgence().getGin().equals(agence.getGin())) {
			throw new JrafDomainException("942-CHECK GIN");
		}
		
		if (membreReseau.getDateFin() != null && membreReseau.getDateDebut().after(membreReseau.getDateFin())) {
			throw new JrafDomainException("942-CHECK VALIDITY END DATE");
		}

	}

	/**
	 * Check existing validity start date  database with input 
	 * 
	 * @param membreReseau
	 * @param membreReseauToUpdate
	 * @return
	 */
	private boolean checkValidityStartDate(MembreReseauDTO membreReseau, MembreReseau membreReseauToUpdate) {
		if (membreReseau.getDateDebut() != null && membreReseauToUpdate.getDateDebut() != null) {
			return compareSameDate(membreReseau.getDateDebut(), membreReseauToUpdate.getDateDebut());
		} else {
			return false;
		}
	}

	/***
	 * Check if network exists on database
	 * 
	 * @param reseau
	 * @throws JrafDomainException
	 */
	private void checkNetwork(Optional<Reseau> reseau) throws JrafDomainException {
		if (!reseau.isPresent()) {
			throw new JrafDomainException("932-CODE NETWORK VALUE");
		}
	}

	/**
	 * Only AIN should be mentioned in delete
	 * 
	 * @param pMembreReseau
	 * @throws JrafDomainException
	 */
	private void inputCoherencyForDelete(MembreReseauDTO pMembreReseau) throws JrafDomainException {
		if (pMembreReseau.getDateDebut() != null || pMembreReseau.getDateFin() != null) {
			throw new JrafDomainException("932-ONLY AIN FOR DELETE");
		}
	}
	
	 /**
	   * Returns true if startDate1 is after startDate2 or if startDate1 equals startDate2.
	   * Returns false if either value is null.  If equalOK, returns true if the
	   * dates are equal.
	   **/
	  public static boolean isValidDateRange(Date startDate1, Date startDate2, boolean equalOK) {
	      // false if either value is null
	      if (startDate1 == null || startDate2 == null) { return false; }
	      
	      if (equalOK) {
	          // true if they are equal
	          if (compareSameDate(startDate1, startDate2)) { return true; }
	      }
	      
	      // true if endDate after startDate
	      if (startDate1.after(startDate2)) { return true; }
	      
	      return false;
	  }
	
	
	/**
	 * Check if Reseau on input the same as database
	 * 
	 * @param membreReseau
	 * @param reseau
	 * @return
	 */
	private boolean checkReseauCoherency(MembreReseau membreReseau, Reseau reseau) {
		return membreReseau.getReseau().getCode().equals(reseau.getCode());
	}

	public MembreReseauUS getMembreReseauUS() {
		return membreReseauUS;
	}

	public void setMembreReseauUS(MembreReseauUS membreReseauUS) {
		this.membreReseauUS = membreReseauUS;
	}
	
	public MembreReseauRepository getMembreReseauRepository() {
		return membreReseauRepository;
	}

	public void setMembreReseauRepository(MembreReseauRepository membreReseauRepository) {
		this.membreReseauRepository = membreReseauRepository;
	}

	public Integer getCleMembreReseau(String gGinValue, String pCodeValue, Date pDateDebut)
			throws JrafDomainException {
		return membreReseauRepository.getCleMembreReseau(gGinValue,pCodeValue,pDateDebut);
	}

	public Integer getCleMembreReseauWithoutEntryDate(String gGinValue, String pCodeValue)
			throws JrafDomainException {
		return membreReseauRepository.getCleMembreReseauWithoutEntryDate(gGinValue,pCodeValue);
	}
	
	public void createNetworkMember(MembreReseauDTO membreReseauDTO,
			String reseauCode, Agence agency) throws JrafDomainException {
		// TODO Auto-generated method stub
		
	}
}
