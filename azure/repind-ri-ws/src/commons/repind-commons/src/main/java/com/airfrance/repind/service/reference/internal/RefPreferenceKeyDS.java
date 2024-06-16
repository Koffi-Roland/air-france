package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefPreferenceKeyRepository;
import com.airfrance.repind.dto.reference.RefPreferenceKeyDTO;
import com.airfrance.repind.dto.reference.RefPreferenceKeyTransform;
import com.airfrance.repind.entity.reference.RefPreferenceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class RefPreferenceKeyDS {

    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    @Autowired
    private RefPreferenceKeyRepository refPreferenceKeyRepository;

    @Transactional(readOnly=true)
    public RefPreferenceKeyDTO get(RefPreferenceKeyDTO dto) throws JrafDomainException {
       return get(dto.getCodeKey());
    }

    @Transactional(readOnly=true)
    public RefPreferenceKeyDTO get(String id) throws JrafDomainException {
    	 Optional<RefPreferenceKey> refPreferenceKey = refPreferenceKeyRepository.findById(id);
         if (!refPreferenceKey.isPresent()) {
         	return null;
         }
         return RefPreferenceKeyTransform.bo2DtoLight(refPreferenceKey.get());
    }


    public RefPreferenceKeyRepository getRefPreferenceKeyRepository() {
		return refPreferenceKeyRepository;
	}

	public void setRefPreferenceKeyRepository(RefPreferenceKeyRepository refPreferenceKeyRepository) {
		this.refPreferenceKeyRepository = refPreferenceKeyRepository;
	}

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly=true)
    public List<RefPreferenceKeyDTO> findByKey(String key) throws JrafDomainException {
    	String whereClause = "";
        String test = "=";
        String add="";
        String upper="";
        StringBuffer buffer = new StringBuffer("select r from RefPreferenceKey r where ");
        List<RefPreferenceKeyDTO> result = null;
		if (key != null) {
			buffer.append("( r.codeKey = :key ) ").append(add);
			whereClause = buffer.toString();
			Query query = getEntityManager().createQuery(whereClause);
			query.setParameter("key", key);
			result = query.getResultList();
			if(result.size() == 0){
				return null;
			}
			return  result;
		}
		throw new JrafDomainException("Missing parameter key");
    }
}
