package com.airfrance.repind.service.agence.internal;

import com.airfrance.repind.entity.refTable.RefTableREF_CODE_GDS;
import com.airfrance.repind.entity.refTable.RefTableREF_ERREUR;
import com.airfrance.repind.entity.refTable.RefTableREF_STATUTPM;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.OuiNonFlagEnum;
import com.airfrance.repind.dao.agence.AgenceRepository;
import com.airfrance.repind.dao.agence.OfficeIDRepository;
import com.airfrance.repind.dao.firme.PersonneMoraleRepository;
import com.airfrance.repind.dto.agence.AgenceDTO;
import com.airfrance.repind.dto.agence.AgenceTransform;
import com.airfrance.repind.dto.agence.OfficeIDDTO;
import com.airfrance.repind.dto.agence.OfficeIDTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.agence.OfficeID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * Title : OfficeIdDS.java
 * </p>
 * 
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Service
public class OfficeIdDS {

	/** logger */
	private static final Log log = LogFactory.getLog(OfficeIdDS.class);

	private static final String SITE_TRANSFER_OID = "DEFAULT";
	private static final String SIGNATURE_TRANSFER_OID = "TRANS OFFICEID";
	private static final String ERROR_AGENCY_DOESN_T_EXIST = ": AGENCY DOESN'T EXIST";
	private static final String ERROR_AGENCY_IS_INACTIVE = ": AGENCY IS INACTIVE";
	private static final String ERROR_OFFICE_ID_SHOULD_BE_9_CHARACTERS = ": OFFICE ID SHOULD BE 9-CHARACTERS";
	private static final String ERROR_SPACES_NOT_ALLOWED_IN_OFFICE_ID = ": SPACES NOT ALLOWED IN OFFICE ID";
	private static final String ERROR_OFFICE_ID_MISSING = ": OFFICE ID";

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** main dao */
	@Autowired
	private OfficeIDRepository officeIDRepository;

	/**
	 * Agency DAO
	 */
	@Autowired
	private AgenceRepository agenceRepository;
	
	@Autowired
	private PersonneMoraleRepository persMoraleRepository;
	
	/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw u var) ENABLED START */
	// add your custom variables here if necessary
	/* PROTECTED REGION END */

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public Integer countWhere(OfficeIDDTO dto) throws JrafDomainException {
		/*
		 * PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM countWhere) ENABLED START
		 */
		Integer count = null;
		OfficeID officeID = null;
		// conversion light de dto -> bo
		officeID = OfficeIDTransform.dto2BoLight(dto);

		// execution du denombrement
		count = (int) officeIDRepository.count(Example.of(officeID));
		return count;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(OfficeIDDTO officeIDDTO) throws JrafDomainException {

		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM create) ENABLED START */
		OfficeID officeID = null;

		// transformation light dto -> bo
		officeID = OfficeIDTransform.dto2BoLight(officeIDDTO);

		// creation en base
		// Appel create de l'Abstract
		officeIDRepository.saveAndFlush(officeID);

		// Version update and Id update if needed
		OfficeIDTransform.bo2DtoLight(officeID, officeIDDTO);
		/* PROTECTED REGION END */
	}
	
	 /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(OfficeIDDTO officeIDDTO, Agence agency) throws JrafDomainException {

       /*PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM create) ENABLED START*/
    	
        OfficeID officeID = null;

        officeID = OfficeIDTransform.dto2BoLight(officeIDDTO);
        
        // Liaison avec l'agence...
        officeID.setAgence(agency);

        // Création...
        officeIDRepository.saveAndFlush(officeID);

        // Mise à jour du DTO...
        officeIDDTO = OfficeIDTransform.bo2Dto(officeID);
        
       /*PROTECTED REGION END*/
    }
    
    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void createInAgency(OfficeIDDTO officeIDDTO, AgenceDTO agencyDTO) throws JrafDomainException {

       /*PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM create) ENABLED START*/
    	
        OfficeID officeID = null;
        
        List<OfficeID> officeIdList= officeIDRepository.findByCodeGDSAndOfficeID(officeIDDTO.getCodeGDS(), officeIDDTO.getOfficeID());
        
        if (officeIdList == null || officeIdList.isEmpty())
        {
        	officeIDDTO.setAgence(agencyDTO);
        	officeID = OfficeIDTransform.dto2Bo(officeIDDTO);
        	//Create officeID
        	officeIDRepository.saveAndFlush(officeID);
        	if (agencyDTO.getOffices() != null)
        		agencyDTO.getOffices().add(officeIDDTO);
        	else
        	{
        		agencyDTO.setOffices(new HashSet<OfficeIDDTO>());
        		agencyDTO.getOffices().add(officeIDDTO);
        	}
        		
        		
        }
        
        else
        {
	       /* List<OfficeID> officeIdListWithGin = officeIDRepository.findByCodeGDSAndOfficeIDAndAgenceGin(officeIDDTO.getCodeGDS(), officeIDDTO.getOfficeID(), agencyDTO.getGin());
	        if (officeIdListWithGin == null || officeIdListWithGin.isEmpty())
	        	officeIDDTO.setAgence(agencyDTO);
	        else*/
	        	throw new JrafDomainException(RefTableREF_ERREUR._REF_917);
	         
	        /*officeID = OfficeIDTransform.dto2Bo(officeIDDTO);       
	        agencyDTO.getOffices().add(officeIDDTO);
	        // CrÃ©ation...
	        officeIDRepository.saveAndFlush(officeID);*/
        }

        // Mise Ã  jour du DTO...
        officeIDDTO = OfficeIDTransform.bo2Dto(officeID);
       
    }

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(OfficeIDDTO dto) throws JrafDomainException {

		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM remove) ENABLED START */
		OfficeID officeID = null;

		// transformation light dto -> bo
		officeID = OfficeIDTransform.dto2BoLight(dto);

		// suppression en base
		officeIDRepository.delete(officeID);
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Serializable oid) throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM remove2) ENABLED START */
		officeIDRepository.deleteById((Long) oid);
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(OfficeIDDTO officeIDDTO) throws JrafDomainException {

		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM update) ENABLED START */
		OfficeID officeID = null;

		// chargement du bo
		if (officeIDDTO.getCle() == null) {
			officeID = new OfficeID();
			// transformation light dto -> bo
			officeID = OfficeIDTransform.dto2Bo(officeIDDTO);
		} else {
			officeID = officeIDRepository.getOne(officeIDDTO.getCle());
		}

		if (officeIDDTO.getAgence() != null) {
			Agence ag = new Agence();
			AgenceTransform.dto2Bo(officeIDDTO.getAgence(), ag);			
			officeID.setAgence(ag);
		}

		officeIDRepository.saveAndFlush(officeID);
		officeIDDTO = OfficeIDTransform.bo2Dto(officeID);

		/* PROTECTED REGION END */

	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(OfficeIDDTO officeIDDTO, Agence agency) throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM update) ENABLED START */
		OfficeID officeID = OfficeIDTransform.dto2Bo(officeIDDTO);


		if(officeID != null) {
			if(officeID.getCle()!=null) {
				officeID.setAgence(agency);	
			}
			officeIDRepository.saveAndFlush(officeID);
			persMoraleRepository.refresh(agency);
		}
		

		/* PROTECTED REGION END */
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public List<OfficeIDDTO> findAll() throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM findAll) ENABLED START */
		List boFounds = null;
		List<OfficeIDDTO> dtoFounds = null;
		OfficeIDDTO dto = null;
		OfficeID officeID = null;

		// execution du find
		boFounds = officeIDRepository.findAll();

		// transformation bo -> DTO
		if (boFounds != null) {
			dtoFounds = new ArrayList<>(boFounds.size());
			Iterator i = boFounds.iterator();
			while (i.hasNext()) {
				officeID = (OfficeID) i.next();
				dto = OfficeIDTransform.bo2Dto(officeID);
				dtoFounds.add(dto);
			}
		}
		return dtoFounds;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public List<OfficeIDDTO> findByExample(OfficeIDDTO dto) throws JrafDomainException {
		/*
		 * PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM findByExample1) ENABLED
		 * START
		 */
		List<OfficeIDDTO> objFounds = null;

		OfficeID officeIDEx = OfficeIDTransform.dto2BoLight(dto);
		List<OfficeID> boFounds = officeIDRepository.findAll(Example.of(officeIDEx));

		// transformation bo -> DTO
		if (boFounds != null) {
			objFounds = new ArrayList<>(boFounds.size());
			Iterator<OfficeID> i = boFounds.iterator();
			while (i.hasNext()) {
				OfficeID officeID = i.next();
				objFounds.add(OfficeIDTransform.bo2Dto(officeID));
			}
		}

		return objFounds;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public Integer count() throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM count) ENABLED START */
		return (int) officeIDRepository.count();
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public OfficeIDDTO get(OfficeIDDTO dto) throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM get) ENABLED START */
		OfficeID officeID = null;
		OfficeIDDTO officeIDDTO = null;
		// get en base
		officeID = officeIDRepository.getOne(dto.getCle());

		// transformation light bo -> dto
		if (officeID != null) {
			officeIDDTO = OfficeIDTransform.bo2Dto(officeID);
		}
		return officeIDDTO;
		/* PROTECTED REGION END */
	}

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public OfficeIDDTO get(Serializable oid) throws JrafDomainException {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHw DS-CM getOid) ENABLED START */
		OfficeID officeID = null;
		OfficeIDDTO officeIDDTO = null;
		// get en base
		officeID = officeIDRepository.getOne((Long) oid);

		// transformation light bo -> dto
		if (officeID != null) {
			officeIDDTO = OfficeIDTransform.bo2Dto(officeID);
		}
		return officeIDDTO;
		/* PROTECTED REGION END */
	}

	public List<OfficeID> findBySgin(String sgin) {
    	return officeIDRepository.findByAgenceGin(sgin);
	}

	/**
	 * If office Id is already attached to an agency, it is detached from the
	 * existing agency and attached to the new agency. else a new office Id record
	 * is created.
	 * 
	 * @param officeIdDto the office Id to be transfered
	 * @return the updated Office id
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = { JrafDomainRollbackException.class,
			JrafDaoException.class }, noRollbackFor = JrafDomainNoRollbackException.class)
	public OfficeIDDTO transferOfficeId(OfficeIDDTO officeIdDto) throws JrafDomainException {
		validate(officeIdDto);
		Agence targetAgency = retrieveAgency(officeIdDto.getAgence());
		List<OfficeID> existingOfficeIds = officeIDRepository.findByCodeGDSAndOfficeID(officeIdDto.getCodeGDS(),
				officeIdDto.getOfficeID());
		OfficeID toBeUpdated;
		if (!CollectionUtils.isEmpty(existingOfficeIds)) {
			toBeUpdated = existingOfficeIds.get(0);
		} else {
			toBeUpdated = OfficeIDTransform.dto2BoLight(officeIdDto);
		}

		// Office Id update
		toBeUpdated.setAgence(targetAgency);
		toBeUpdated.setSignatureMaj(SIGNATURE_TRANSFER_OID);
		Date today = new Date();
		toBeUpdated.setDateMaj(today);
		toBeUpdated.setDateLastResa(today);
		toBeUpdated.setMajManuelle(OuiNonFlagEnum.OUI.toString());
		toBeUpdated.setSiteMaj(SITE_TRANSFER_OID);

		// Agency update
		targetAgency.setDateModification(today);
		targetAgency.setSignatureModification(SIGNATURE_TRANSFER_OID);

		OfficeID updated = officeIDRepository.saveAndFlush(toBeUpdated);
		agenceRepository.saveAndFlush(targetAgency);

		return OfficeIDTransform.bo2Dto(updated);
	}

	private void validate(OfficeIDDTO officeIdDto) throws JrafDaoException {
		String gdsCode = officeIdDto.getCodeGDS();
		if (StringUtils.isBlank(gdsCode) || !RefTableREF_CODE_GDS.instance().estValide(gdsCode, StringUtils.EMPTY)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_232);
		}

		String officeId = officeIdDto.getOfficeID();
		if (StringUtils.isBlank(officeId)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_133 + ERROR_OFFICE_ID_MISSING);
		} else if (officeId.contains(StringUtils.SPACE)) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_932 + ERROR_SPACES_NOT_ALLOWED_IN_OFFICE_ID);
		}

		if (RefTableREF_CODE_GDS._REF_1A.equals(gdsCode) && officeId.length() != 9) {
			throw new JrafDaoException(RefTableREF_ERREUR._REF_932 + ERROR_OFFICE_ID_SHOULD_BE_9_CHARACTERS);
		}
	}

	private Agence retrieveAgency(AgenceDTO targetAgency) throws JrafDaoException {
		if (targetAgency != null && StringUtils.isNotEmpty(targetAgency.getGin())) {
			Optional<Agence> response = agenceRepository.findById(targetAgency.getGin());
			if (response.isPresent()) {
				Agence agency = response.get();
				if (RefTableREF_STATUTPM._REF_X.equals(agency.getStatut())) {
					throw new JrafDaoException(RefTableREF_ERREUR._REF_189 + ERROR_AGENCY_IS_INACTIVE);
				}
				return agency;
			} else {
				throw new JrafDaoException(RefTableREF_ERREUR._REF_189 + ERROR_AGENCY_DOESN_T_EXIST);
			}
		}
		throw new JrafDaoException(RefTableREF_ERREUR._REF_189);
	}

	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_vpGyVGk4EeGhB9497mGnHwgem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional(readOnly=true)
	public Set<OfficeIDDTO> getByAgenceGin(String agenceGin) throws JrafDomainException {
		List<OfficeID> officeIDList = new ArrayList<>();
		Set<OfficeIDDTO> officeIDDTOList = new HashSet<>();
		// get en base
		officeIDList = officeIDRepository.findByAgenceGinOrderByRowNum(agenceGin);

		// transformation light bo -> dto
		if (!officeIDList.isEmpty()) {
			for (OfficeID officeID : officeIDList) {
				officeIDDTOList.add(OfficeIDTransform.bo2Dto(officeID)) ;
			}
		}
		return officeIDDTOList;
	}
}
