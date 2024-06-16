package com.airfrance.repind.service.firm.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.firme.NumeroIdentRepository;
import com.airfrance.repind.dto.firme.NumeroIdentDTO;
import com.airfrance.repind.dto.firme.NumeroIdentTransform;
import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.firme.PersonneMorale;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NumeroIdentDS {

    /** logger */
    private static final Log log = LogFactory.getLog(NumeroIdentDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


    @Autowired
    private NumeroIdentRepository numeroIdentRepository;

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(NumeroIdentDTO numeroIdentDTO) throws JrafDomainException {

       /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA DS-CM create) ENABLED START*/
        NumeroIdent numeroIdent = null;

        // transformation light dto -> bo
        numeroIdent = NumeroIdentTransform.dto2BoLight(numeroIdentDTO);
        
        String personneMoraleGin = numeroIdentDTO.getPersonneMorale().getGin();
        PersonneMorale personneMorale = entityManager.find(PersonneMorale.class, personneMoraleGin);
        if (personneMorale == null)
        	throw new JrafDomainException("Unable to find personneMorale with GIN : '" + personneMoraleGin + "'");
        
        numeroIdent.setPersonneMorale(personneMorale);
        
        if (numeroIdent.getDateOuverture() == null) {
        	numeroIdent.setDateOuverture(new Date());
        }
        
        // creation en base
        // Appel create de l'Abstract 
        numeroIdent = numeroIdentRepository.saveAndFlush(numeroIdent);

        // Version update and Id update if needed
        NumeroIdentTransform.bo2DtoLight(numeroIdent, numeroIdentDTO);
       /*PROTECTED REGION END*/
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(NumeroIdentDTO dto) throws JrafDomainException {

       /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA DS-CM remove) ENABLED START*/
        NumeroIdent numeroIdent = null;
        
        // transformation light dto -> bo
        numeroIdent = NumeroIdentTransform.dto2BoLight(dto);
        
        // suppression en base
        numeroIdentRepository.delete(numeroIdent);
       /*PROTECTED REGION END*/
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(Long oid) throws JrafDomainException {
        /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA DS-CM remove2) ENABLED START*/
    	numeroIdentRepository.deleteById(oid);
        /*PROTECTED REGION END*/
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(NumeroIdentDTO numeroIdentDTO) throws JrafDomainException {
       Optional<NumeroIdent> numeroIdent = numeroIdentRepository.findById(numeroIdentDTO.getKey());
       
       if (numeroIdent.isPresent()) {
           // transformation light dto -> bo
           NumeroIdentTransform.dto2BoLight(numeroIdentDTO, numeroIdent.get());
           numeroIdentRepository.saveAndFlush(numeroIdent.get());
       }
       else {
           numeroIdentRepository.saveAndFlush(NumeroIdentTransform.dto2BoLight(numeroIdentDTO));
       }
    }

    @Transactional(readOnly=true)
    public List<NumeroIdentDTO> findAll() throws JrafDomainException {
        /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA DS-CM findAll) ENABLED START*/
        List boFounds = null;
        List<NumeroIdentDTO> dtoFounds = null;
        NumeroIdentDTO dto = null;
        NumeroIdent numeroIdent = null;

        // execution du find
        boFounds = numeroIdentRepository.findAll();
        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<NumeroIdentDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                numeroIdent = (NumeroIdent) i.next();
                dto = NumeroIdentTransform.bo2Dto(numeroIdent);
                dtoFounds.add(dto);
            }
        }
        return dtoFounds;
        /*PROTECTED REGION END*/
    }

    @Transactional(readOnly=true)
    public Long count() throws JrafDomainException {
        /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA DS-CM count) ENABLED START*/
        return numeroIdentRepository.count();
        /*PROTECTED REGION END*/
    }

    @Transactional(readOnly=true)
    public List<NumeroIdentDTO> findByExample(NumeroIdentDTO dto) throws JrafDomainException {
        NumeroIdent numeroIdent = NumeroIdentTransform.dto2BoLight(dto);
        List<NumeroIdentDTO> result = new ArrayList<>();
        for (NumeroIdent found : numeroIdentRepository.findAll(Example.of(numeroIdent))) {
			result.add(NumeroIdentTransform.bo2Dto(found));
		}
		return result;
    }

    @Transactional(readOnly=true)
    public NumeroIdentDTO get(NumeroIdentDTO dto) throws JrafDomainException {
        Optional<NumeroIdent> numeroIdent = numeroIdentRepository.findById(dto.getKey());
        
        if (!numeroIdent.isPresent()) {
        	return null;
        }

        return NumeroIdentTransform.bo2Dto(numeroIdent.get());
    }

    @Transactional(readOnly=true)
    public NumeroIdentDTO get(Long oid) throws JrafDomainException {
        Optional<NumeroIdent> numeroIdent = numeroIdentRepository.findById(oid);
        
        if (!numeroIdent.isPresent()) {
        	return null;
        }

        return NumeroIdentTransform.bo2Dto(numeroIdent.get());
    }

    public NumeroIdentRepository getNumeroIdentRepository() {
		return numeroIdentRepository;
	}

	public void setNumeroIdentRepository(NumeroIdentRepository numeroIdentRepository) {
		this.numeroIdentRepository = numeroIdentRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtAgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_NTKU0OjvEeCMIaqimW3ZtA u m) ENABLED START*/
    // add your custom methods here if necessary

    @Transactional(readOnly=true)
    public List<NumeroIdentDTO> findByNumeroIATAMere(String numeroIATAMere, String ginAgency) throws JrafDomainException {
		log.debug("START findByNumeroIATAMere in " + System.currentTimeMillis());
    	
    	// Resultat
    	NumeroIdent numeroIdent = null;
    	NumeroIdentDTO numeroIdentDTO = null;
    	List<NumeroIdentDTO> dtoFounds = null;

    	List<NumeroIdent> numerosIdent = numeroIdentRepository.findByNumeroIATAMere(numeroIATAMere, ginAgency);
        
    	// transformation bo -> DTO
        if (numerosIdent != null) {
            dtoFounds = new ArrayList<NumeroIdentDTO>(numerosIdent.size());
            Iterator i = numerosIdent.iterator();
            while (i.hasNext()) {
                numeroIdent = (NumeroIdent) i.next();
                numeroIdentDTO = NumeroIdentTransform.bo2Dto(numeroIdent);
                dtoFounds.add(numeroIdentDTO);
            }
        }
        return dtoFounds;
    }

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public List<NumeroIdentDTO> findByKeyNumberType(String keyNumber, List<String> typeList) throws JrafDomainException {
		log.debug("START findByKeyNumberType in " + System.currentTimeMillis());
    	
    	// Resultat
    	NumeroIdent numeroIdent = null;
    	NumeroIdentDTO numeroIdentDTO = null;
    	List<NumeroIdentDTO> dtoFounds = null;

    	List<NumeroIdent> numerosIdent = numeroIdentRepository.findByKeyNumberType(keyNumber, typeList);
        
    	// transformation bo -> DTO
        if (numerosIdent != null) {
            dtoFounds = new ArrayList<>(numerosIdent.size());
            Iterator i = numerosIdent.iterator();
            while (i.hasNext()) {
                numeroIdent = (NumeroIdent) i.next();
                numeroIdentDTO = NumeroIdentTransform.bo2Dto(numeroIdent);
                dtoFounds.add(numeroIdentDTO);
            }
        }
        return dtoFounds;
    }
	
	@Transactional(readOnly = true)
	public boolean isUniqueAtDate(Long key, String numero, String type, Date dateOuverture,
			Date dateFermeture) throws ParseException{
    	
		String pattern ="ddmmyyyy";
		DateFormat df = new SimpleDateFormat(pattern);

		
		numero = numero.toUpperCase();
		
		List<NumeroIdent> numerosIdent = numeroIdentRepository.findAllByKeyNumberTypeDate(key, numero, type, df.format(dateOuverture), df.format(dateFermeture));
    	
    	int size = numerosIdent.size();
    	
    	if (size==0) {
			return true;
		}
    	else {
    		for (NumeroIdent numeroIdent : numerosIdent) {
				
    			Date dateNow = new Date();
    			Date dbDate = numeroIdent.getDateFermeture();
    			//They add 2 years in cpp batch
    			int addTwoYears = Integer.parseInt(df.format(dbDate))+2;
    			Date newDbDate = new SimpleDateFormat("ddMMyyyy").parse(String.valueOf(addTwoYears));
    			
    			if (dbDate!=null && newDbDate.before(dateNow)) {
    				return true;
				}
			}
    	}
    	
    	return false;
	}
	
	@Transactional(readOnly = true)
	public boolean isUnique(Long key, String numero, String type){
		
		numero = numero.toUpperCase();
		
		List<NumeroIdent> numerosIdent = numeroIdentRepository.findAllByKeyNumberType(key, numero, type);

		int size = numerosIdent.size();
		
		if (size==0) {
			return true;
		}
    	else {
    		for (NumeroIdent numeroIdent : numerosIdent) {
				
    			Date dateNow = new Date();
    			Date dbDate = numeroIdent.getDateFermeture();
    			//They add 2 years in cpp batch
    			Date newDbDate = DateUtils.addYears(dbDate, 2);
    			
    			if (dbDate!=null && newDbDate.before(dateNow)) {
    				return true;
				}
			}
    	}
    	
    	return false;
		
	}
	
	/**
	 * Retrieves the active legal entity identifier by the key number and type.
	 * 
	 * @param keyNumber the unique identification number
	 * @param typeList  the type of identification number
	 * @return the active legal entity identifier by the key number and type
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public Map<String, NumeroIdentDTO> findActiveByNumeroType(@NotNull String keyNumber, @NotNull List<String> typeList)
			throws JrafDomainException {
		Map<String, NumeroIdentDTO> result = null;
		List<NumeroIdent> numeroIdentList = numeroIdentRepository.findActiveByNumeroType(keyNumber, typeList);
		if (!CollectionUtils.isEmpty(numeroIdentList)) {
			result = new HashMap<>();
			for (NumeroIdent numeroIdent : numeroIdentList) {
				result.put(numeroIdent.getType(), NumeroIdentTransform.bo2Dto(numeroIdent));
			}
		}
		return result;
	}

	/**
	 * Retrieves the active legal entity identifier by its GIN
	 * 
	 * @param gin the id
	 * @return the active legal entity identifier
	 * @throws JrafDomainException
	 */
	@Transactional(readOnly = true)
	public List<NumeroIdentDTO> findByPm(@NotNull String gin) throws JrafDomainException {
		List<NumeroIdentDTO> result = null;
		List<NumeroIdent> numeroIdentList = numeroIdentRepository.findByPMGin(gin);
		if (!CollectionUtils.isEmpty(numeroIdentList)) {
			result = new ArrayList<>();
			for (NumeroIdent numeroIdent : numeroIdentList) {
				result.add(NumeroIdentTransform.bo2Dto(numeroIdent));
			}
		}
		return result;
	}

    @Transactional(readOnly = true)
    public Set<NumeroIdentDTO> findByPMGin(String pmGin) throws JrafDomainException {
        log.debug("START findByPMGin in " + System.currentTimeMillis());

        // Resultat
        NumeroIdent numeroIdent = null;
        NumeroIdentDTO numeroIdentDTO = null;
        Set<NumeroIdentDTO> dtoFounds = null;

        List<NumeroIdent> numerosIdent = numeroIdentRepository.findByPMGin(pmGin);

        // transformation bo -> DTO
        if (numerosIdent != null) {
            dtoFounds = new HashSet<>();
            Iterator<NumeroIdent> i = numerosIdent.iterator();
            while (i.hasNext()) {
                numeroIdent =  i.next();
                numeroIdentDTO = NumeroIdentTransform.bo2Dto(numeroIdent);
                dtoFounds.add(numeroIdentDTO);
            }
        }
        return dtoFounds;
    }
}
