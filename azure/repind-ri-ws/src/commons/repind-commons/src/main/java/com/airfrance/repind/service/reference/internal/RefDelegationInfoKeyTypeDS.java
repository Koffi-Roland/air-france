package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDaoException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefDelegationInfoKeyTypeRepository;
import com.airfrance.repind.dto.reference.RefDelegationInfoKeyTypeDTO;
import com.airfrance.repind.dto.reference.RefDelegationInfoKeyTypeTransform;
import com.airfrance.repind.entity.reference.RefDelegationInfoKeyType;
import com.airfrance.repind.util.SicDateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class RefDelegationInfoKeyTypeDS  {

	/** logger */
	private static final Log log = LogFactory.getLog(RefPreferenceKeyTypeDS.class);

	/** the Entity Manager*/
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private RefDelegationInfoKeyTypeRepository refDelegationInfoKeyTypeRepository;

    @Transactional(readOnly=true)
	public RefDelegationInfoKeyTypeDTO get(RefDelegationInfoKeyTypeDTO dto) throws JrafDomainException {
		return get(dto.getRefId());
	}

    @Transactional(readOnly=true)
	public RefDelegationInfoKeyTypeDTO get(Serializable oid) throws JrafDomainException {
		Optional<RefDelegationInfoKeyType> refDelegationInfoKeyType = refDelegationInfoKeyTypeRepository.findById(((RefDelegationInfoKeyType)oid).getRefId());

		// transformation light bo -> dto
		if (!refDelegationInfoKeyType.isPresent()) {
			return null;
		}
		return RefDelegationInfoKeyTypeTransform.bo2DtoLight(refDelegationInfoKeyType.get());
	}

	/**
	 * @return EntityManager
	 */ 
	public EntityManager getEntityManager() {
		/*PROTECTED REGION ID(_RLJS8HIQEeeRrZw0c1ut0ggem ) ENABLED START*/
		return entityManager;
		/*PROTECTED REGION END*/
	}

	/**
	 *  @param entityManager EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	/** 
	 * refKeyAndTypeExist
	 * @param key in String
	 * @param type in String
	 * @return The refKeyAndTypeExist as <code>Boolean</code>
	 * @throws JrafDomainException en cas d'exception
	 */
    @Transactional(readOnly=true)
	public Boolean refKeyAndTypeExist(String key, String type, String value) throws JrafDomainException {
		List<RefDelegationInfoKeyType> result = null;

		/*StringBuilder strQuery = new StringBuilder("SELECT ref from RefDelegationInfoKeyType ref ");
		strQuery.append("WHERE ref.type=? and ref.key=?");

		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter(1, type.toUpperCase());
		query.setParameter(2, key);*/

		try {
			//result = (List<RefDelegationInfoKeyType>)query.getResultList();
			result = refDelegationInfoKeyTypeRepository.refKeyAndTypeExist(type, key);
			if (result == null || result.size() == 0 || result.size() > 1) {
				return false;
			}
			
			if(result.size() == 1){
				RefDelegationInfoKeyType del = result.get(0);
				if(del.getDataType().equals("Date")){
					if(!SicDateUtils.checkFormatDateDDMMYYYY(value)){
						throw new InvalidParameterException("Format date birthday dd/MM/yyyy");
					}
				}
			}
			
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	public List<RefDelegationInfoKeyType> getKeyListByType(String type) throws JrafDaoException {
		List<RefDelegationInfoKeyType> result = null;

		StringBuilder strQuery = new StringBuilder("SELECT ref from RefDelegationInfoKeyType ref ");
		strQuery.append("WHERE ref.type = ? ");

		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter(1, type.toUpperCase());

		try {
			result = (List<RefDelegationInfoKeyType>)query.getResultList();
			if (result != null) {
				log.info("Number of ref_preference_key_type from DB : " + result.size());
			}
		} catch (NoResultException e) {
			result = null;
		} catch (Exception e) {
			throw new JrafDaoException(e);
		}

		return result;
	}
}
