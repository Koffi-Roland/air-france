package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefPreferenceTypeRepository;
import com.airfrance.repind.dto.reference.RefPreferenceTypeDTO;
import com.airfrance.repind.dto.reference.RefPreferenceTypeTransform;
import com.airfrance.repind.entity.reference.RefPreferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RefPreferenceTypeDS {

    /** logger */
    private static final Log log = LogFactory.getLog(RefPreferenceTypeDS.class);

    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


    @Autowired
    private RefPreferenceTypeRepository refPreferenceTypeRepository;

    @Transactional(readOnly=true)
    public RefPreferenceTypeDTO get(RefPreferenceTypeDTO dto) throws JrafDomainException {
        return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefPreferenceTypeDTO get(String id) throws JrafDomainException {
    	Optional<RefPreferenceType> refPreferenceType = refPreferenceTypeRepository.findById(id);
        if (!refPreferenceType.isPresent()) {
        	return null;
        }
        return RefPreferenceTypeTransform.bo2DtoLight(refPreferenceType.get());
    }
    
    public RefPreferenceTypeRepository getRefPreferenceTypeRepository() {
		return refPreferenceTypeRepository;
	}

	public void setRefPreferenceTypeRepository(RefPreferenceTypeRepository refPreferenceTypeRepository) {
		this.refPreferenceTypeRepository = refPreferenceTypeRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_TQSfoHBREeeA-oB3G9fmBAgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<RefPreferenceTypeDTO> findByType(String type) throws JrafDomainException {
    	String whereClause = "";
        String add="";
        StringBuffer buffer = new StringBuffer("select r from RefPreferenceType r where ");
        List<RefPreferenceTypeDTO> result = null;
		if (type != null) {
			buffer.append("( r.code = :type ) ").append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("type", type.toUpperCase());
			result = query.getResultList();
			if(result.size() == 0){
				return null;
			}
			return  result;
		}
		throw new JrafDomainException("Missing parameter type");
    }

    /**
     * 
     * @return All preference type from data base
     */
    @Transactional(readOnly=true)
	public List<RefPreferenceTypeDTO> getAll() {

		Function<RefPreferenceType, RefPreferenceTypeDTO> transformToDto = ref -> {
			try {
				return RefPreferenceTypeTransform.bo2DtoLight(ref);

			} catch (JrafDomainException e) {
				 log.error(e.getMessage());
				 return null;
			}
		};

		List<RefPreferenceTypeDTO> result = refPreferenceTypeRepository.findAll().stream().map(transformToDto)
				.collect(Collectors.toList());
		return result;
	}
}
