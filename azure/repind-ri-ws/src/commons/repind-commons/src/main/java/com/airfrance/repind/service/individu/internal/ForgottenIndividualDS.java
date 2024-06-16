package com.airfrance.repind.service.individu.internal;

import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.individu.ForgottenIndividualRepository;
import com.airfrance.repind.dto.individu.ForgottenIndividualDTO;
import com.airfrance.repind.dto.individu.ForgottenIndividualTransform;
import com.airfrance.repind.entity.individu.ForgottenIndividual;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Service
public class ForgottenIndividualDS {

	/** logger */
	private static final Log log = LogFactory.getLog(ForgottenIndividualDS.class);

	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;


	/** main dao */
	@Autowired
	private ForgottenIndividualRepository forgottenIndividualRepository;

    private static String SIGNATURE_BATCH = "BATCH_QVI";
    private static String PROCESSED = "P";
	/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ u var) ENABLED START*/
	// add your custom variables here if necessary
	/*PROTECTED REGION END*/

	@Transactional
	public Long getNextValue(){
		return forgottenIndividualRepository.getNextValue();
	}

	public void saveOrUpdate(ForgottenIndividualDTO forgottenIndividualDTO) throws JrafDaoException, JrafDomainException{
		forgottenIndividualRepository.saveAndFlush(ForgottenIndividualTransform.dto2BoLight(forgottenIndividualDTO));
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Integer countWhere(ForgottenIndividualDTO dto) throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM countWhere) ENABLED START*/
		Integer count = null;
		ForgottenIndividual forgottenIndividual = null;
		//  light transform dto -> bo
		forgottenIndividual = ForgottenIndividualTransform.dto2BoLight(dto);

		// execution du denombrement
		count = (int) forgottenIndividualRepository.count(Example.of(forgottenIndividual));
		return count;
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(ForgottenIndividualDTO forgottenIndividualDTO) throws JrafDomainException {

		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM create) ENABLED START*/
		ForgottenIndividual forgottenIndividual = null;

		// light transformation dto -> bo
		forgottenIndividual = ForgottenIndividualTransform.dto2BoLight(forgottenIndividualDTO);

		// create in database (call the abstract class)
		forgottenIndividualRepository.saveAndFlush(forgottenIndividual);

		// Version update and Id update if needed
		ForgottenIndividualTransform.bo2DtoLight(forgottenIndividual, forgottenIndividualDTO);
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(ForgottenIndividualDTO dto) throws JrafDomainException {

		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM remove) ENABLED START*/
		ForgottenIndividual forgottenIndividual = null;

		// light transform dto -> bo
		forgottenIndividual = ForgottenIndividualTransform.dto2BoLight(dto);

		// delete (database)
		forgottenIndividualRepository.delete(forgottenIndividual);
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Long id) throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM remove2) ENABLED START*/
		forgottenIndividualRepository.deleteById(id);
		/*PROTECTED REGION END*/
	}


	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(ForgottenIndividualDTO forgottenIndividualDTO) throws JrafDomainException {

		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM update) ENABLED START*/
		ForgottenIndividual forgottenIndividual = null;

		// chargement du bo
		forgottenIndividual = forgottenIndividualRepository.getOne(forgottenIndividualDTO.getForgottenIndividualId());


		// transformation light dto -> bo
		ForgottenIndividualTransform.dto2BoLight(forgottenIndividualDTO, forgottenIndividual);

		/*PROTECTED REGION END*/

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<ForgottenIndividualDTO> findAll() throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM findAll) ENABLED START*/
		List boFounds = null;
		List<ForgottenIndividualDTO> dtoFounds = null;
		ForgottenIndividualDTO dto = null;
		ForgottenIndividual forgottenIndividual = null;

		// execution du find
		boFounds = forgottenIndividualRepository.findAll();
		// transformation bo -> DTO
		if (boFounds != null) {
			dtoFounds = new ArrayList<ForgottenIndividualDTO>(boFounds.size());
			Iterator i = boFounds.iterator();
			while (i.hasNext()) {
				forgottenIndividual = (ForgottenIndividual) i.next();
				dto = ForgottenIndividualTransform.bo2DtoLight(forgottenIndividual);
				dtoFounds.add(dto);
			}
		}
		return dtoFounds;
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Integer count() throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM count) ENABLED START*/
		return (int) forgottenIndividualRepository.count();
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 * @deprecated
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<ForgottenIndividualDTO> findWhere(ForgottenIndividualDTO dto) throws JrafDomainException {
		List<ForgottenIndividualDTO> objFounds = null;
		objFounds = findByExample(dto);
		return objFounds;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<ForgottenIndividualDTO> findByExample(ForgottenIndividualDTO dto) throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM findByExample1) ENABLED START*/
		ForgottenIndividual forgottenIndividual = ForgottenIndividualTransform.dto2BoLight(dto);
		List<ForgottenIndividualDTO> result = new ArrayList<ForgottenIndividualDTO>();
		for (ForgottenIndividual forgottenIndividualElement : forgottenIndividualRepository
				.findAll(Example.of(forgottenIndividual))) {
			result.add(ForgottenIndividualTransform.bo2DtoLight(forgottenIndividualElement));
		}
		return result;
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ForgottenIndividualDTO get(ForgottenIndividualDTO dto) throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM get) ENABLED START*/
		ForgottenIndividualDTO forgottenIndividualDTO = null;
		// get en base
		Optional<ForgottenIndividual> forgottenIndividual = forgottenIndividualRepository
				.findById(dto.getForgottenIndividualId());


		// transformation light bo -> dto
		if (forgottenIndividual.isPresent()) {
			forgottenIndividualDTO = ForgottenIndividualTransform.bo2DtoLight(forgottenIndividual.get());
		}
		return forgottenIndividualDTO;
		/*PROTECTED REGION END*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ForgottenIndividualDTO get(Long id) throws JrafDomainException {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ DS-CM getOid) ENABLED START*/
		ForgottenIndividualDTO forgottenIndividualDTO = null;
		// get en base
		Optional<ForgottenIndividual> forgottenIndividual = forgottenIndividualRepository.findById(id);

		// transformation light bo -> dto
		if (forgottenIndividual.isPresent()) {
			forgottenIndividualDTO = ForgottenIndividualTransform.bo2DtoLight(forgottenIndividual.get());
		}
		return forgottenIndividualDTO;
		/*PROTECTED REGION END*/
	}


	/**
	 * @return EntityManager
	 */ 
	public EntityManager getEntityManager() {
		/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQgem ) ENABLED START*/
		return entityManager;
		/*PROTECTED REGION END*/
	}

	/**
	 *  @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public boolean isExisting(String gin) throws JrafDomainException {
		ForgottenIndividualDTO forgottenIndividualDTO = new ForgottenIndividualDTO();
		forgottenIndividualDTO.setIdentifier(gin);
		List<ForgottenIndividualDTO> forgottenIndividualDTOs = this.findByExample(forgottenIndividualDTO);

		if(forgottenIndividualDTOs != null && forgottenIndividualDTOs.size() > 0)
			return true;
		return false;

	}


	/*PROTECTED REGION ID(_N19V0NQmEee-fsR_-mfQcQ u m) ENABLED START*/
	// add your custom methods here if necessary

	
	public List<ForgottenIndividualDTO> findByIdentifier(String identifier, String identifierType) throws JrafDomainException {
		List<ForgottenIndividualDTO> resultList = null;
		List<ForgottenIndividual> foundList = forgottenIndividualRepository.findByIdentifier(identifier,
				identifierType);

		if (foundList != null && !foundList.isEmpty()) {
			resultList = new ArrayList<ForgottenIndividualDTO>();
			for (ForgottenIndividual bo : foundList) {
				resultList.add(ForgottenIndividualTransform.bo2DtoLight(bo));
			}
		}
		return resultList;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class) 
	public List<ForgottenIndividualDTO> getIndividualForPhysicalPurge() throws JrafDomainException{
		List<ForgottenIndividualDTO> forgottenIndividualDTOs = new ArrayList<>();
	
		List<ForgottenIndividual> forgottenIndividuals = forgottenIndividualRepository.findByContext("C");
		
		if(forgottenIndividuals != null && !forgottenIndividuals.isEmpty()){
			for(ForgottenIndividual f : forgottenIndividuals){
				forgottenIndividualDTOs.add(ForgottenIndividualTransform.bo2DtoLight(f));
			}
		}

		return forgottenIndividualDTOs;
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class) 
	public List<ForgottenIndividualDTO> getIndividualForPhysicalPurge(int periodInDays) throws JrafDomainException{
		List<ForgottenIndividualDTO> forgottenIndividualDTOs = new ArrayList<>();
	
		List<ForgottenIndividual> forgottenIndividuals = forgottenIndividualRepository.findByContext("C");
		List<ForgottenIndividual> forgottenIndividualsOCP = forgottenIndividualRepository.findByContextWithPeriod("F", periodInDays);
		
		if(forgottenIndividuals != null && !forgottenIndividuals.isEmpty()){
			for(ForgottenIndividual f : forgottenIndividuals){
				forgottenIndividualDTOs.add(ForgottenIndividualTransform.bo2DtoLight(f));
			}
		}
		
		if(forgottenIndividualsOCP != null && !forgottenIndividualsOCP.isEmpty()){
			for(ForgottenIndividual f : forgottenIndividualsOCP){
				forgottenIndividualDTOs.add(ForgottenIndividualTransform.bo2DtoLight(f));
			}
		}

		return forgottenIndividualDTOs;
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class) 
	public boolean updateIndividualForPhysicalPurge(String gin) throws JrafDomainException {
		ForgottenIndividualDTO forgottenIndividualDTO = new ForgottenIndividualDTO();
		forgottenIndividualDTO.setIdentifier(gin);
		ForgottenIndividualDTO f = this.findByExample(forgottenIndividualDTO).get(0);
		f.setContext(PROCESSED);
		f.setSignature(SIGNATURE_BATCH);
		f.setDeletionDate(new Date());
		f.setModificationDate(new Date());
		this.saveOrUpdate(f);
		
		return true;
	}
	/*PROTECTED REGION END*/
}
