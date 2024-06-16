package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefDelegationInfoTypeRepository;
import com.airfrance.repind.dto.reference.RefDelegationInfoTypeDTO;
import com.airfrance.repind.dto.reference.RefDelegationInfoTypeTransform;
import com.airfrance.repind.entity.reference.RefDelegationInfoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class RefDelegationInfoTypeDS {
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;


    /** main dao */
    @Autowired
    private RefDelegationInfoTypeRepository refDelegationInfoTypeRepository;

    @Transactional(readOnly=true)
    public RefDelegationInfoTypeDTO get(RefDelegationInfoTypeDTO dto) throws JrafDomainException {
      return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefDelegationInfoTypeDTO get(Serializable oid) throws JrafDomainException {
    	  Optional<RefDelegationInfoType> refDelegationInfoType = refDelegationInfoTypeRepository.findById(((RefDelegationInfoType)oid).getCode());
          if (!refDelegationInfoType.isPresent()) {
          	return null;
          }
          return RefDelegationInfoTypeTransform.bo2DtoLight(refDelegationInfoType.get());
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
 
	
	public List<RefDelegationInfoTypeDTO> findByType(String type) throws JrafDomainException {
		String whereClause = "";
        String add="";
        StringBuffer buffer = new StringBuffer("select r from RefDelegationInfoType r where ");
        List<RefDelegationInfoTypeDTO> result = null;
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
}
