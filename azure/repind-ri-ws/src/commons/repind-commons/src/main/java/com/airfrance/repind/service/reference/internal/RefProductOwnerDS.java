package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.ContractNotFoundException;
import com.airfrance.ref.exception.contract.RoleContractUnauthorizedOperationException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefOwnerRepository;
import com.airfrance.repind.dao.reference.RefProductOwnerRepository;
import com.airfrance.repind.dao.reference.RefProductRepository;
import com.airfrance.repind.dto.reference.RefProductOwnerDTO;
import com.airfrance.repind.dto.reference.RefProductOwnerIdDTO;
import com.airfrance.repind.dto.reference.RefProductOwnerTransform;
import com.airfrance.repind.entity.reference.RefOwner;
import com.airfrance.repind.entity.reference.RefProduct;
import com.airfrance.repind.entity.reference.RefProductOwner;
import com.airfrance.repind.entity.reference.RefProductOwnerId;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefProductOwnerDS {

    /** logger */
    private static final Log log = LogFactory.getLog(RefProductOwnerDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
    @Autowired
    private RefOwnerRepository refOwnerRepository;
    
    @Autowired
    private RefProductRepository refProductRepository;
    
    @Autowired
    private RefProductOwnerRepository refProductOwnerRepository;

    @Transactional(readOnly=true)
    public RefProductOwnerDTO get(RefProductOwnerDTO dto) throws JrafDomainException {
       return get(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public RefProductOwnerDTO get(Serializable oid) throws JrafDomainException {
        RefProductOwnerId tmpOid = new RefProductOwnerId();
        RefProductOwnerTransform.dto2BoOneToOneRelation((RefProductOwnerIdDTO) oid, tmpOid);
        Optional<RefProductOwner> refProductOwner = refProductOwnerRepository.findById(tmpOid);
        if (!refProductOwner.isPresent()) {
        	return null;
        }
        return RefProductOwnerTransform.bo2DtoLight(refProductOwner.get());
    }

    public RefProductOwnerRepository getRefProductOwnerRepository() {
		return refProductOwnerRepository;
	}

	public void setRefProductOwnerRepository(RefProductOwnerRepository refProductOwnerRepository) {
		this.refProductOwnerRepository = refProductOwnerRepository;
	}

	/**
     * @return javax.persistence.EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_fBDzcP2cEeaexJbSRqCy0Qgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional(readOnly=true)
	public List<RefProductOwnerDTO> findAllOwnersByProductId(Integer pId) throws JrafDomainException {		
		List<RefProductOwner> entities =  entityManager
				.createQuery("from RefProductOwner r where r.id.refProduct.productId = :id")
				.setParameter("id", pId)
				.getResultList();
		List<RefProductOwnerDTO> results = new ArrayList<>();
		for(RefProductOwner entity : entities) {
			results.add(RefProductOwnerTransform.bo2DtoLight(entity));
		}
		return results;
	}
	
	
	public List<RefProductOwnerDTO> findByExample(RefProductOwnerDTO dto) throws JrafDomainException {
		RefProductOwner email = RefProductOwnerTransform.dto2BoLight(dto);
		List<RefProductOwnerDTO> result = new ArrayList<>();
		for (RefProductOwner found : refProductOwnerRepository.findAll(Example.of(email))) {
			result.add(RefProductOwnerTransform.bo2DtoLight(found));
		}
		return result;
	}
	
	
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public List<RefProductOwnerDTO> getAssociations(String productType, String subProductType, String appCode,
			String consumer) throws JrafDomainException {
		
		// REPIND-1009 : no check on appCode
		appCode = null;
		
		// Get the product type
		List<RefProduct> products = getAllProductByProductOrSubProduct(productType, subProductType);
		// Get the owner  
		List<RefOwner> owners = getAllOwnerByAppCodeOrOwner(appCode, consumer);

		List<Integer> productsId = null;
		List<Integer> ownersId = null;
		
		if (owners != null && !owners.isEmpty()) {
			// Get all associations between products and owners
			ownersId = new ArrayList<Integer>();
			for (RefOwner owner : owners) {
				ownersId.add(owner.getOwnerId());
			}
		}
		
		if (products != null && !products.isEmpty()) {
			productsId = new ArrayList<Integer>();
			for (RefProduct product : products) {
				productsId.add(product.getProductId());
			}		
		}

		List<RefProductOwner> rpos = null;
		
		if(productsId != null && ownersId != null){
			rpos = refProductOwnerRepository.getAssociationsByOwnersAndProducts(ownersId, productsId);
		} else if(productsId != null){
			rpos = refProductOwnerRepository.getAssociationsByProducts(productsId);
		} else if(ownersId != null){
			rpos = refProductOwnerRepository.getAssociationsByOwners(ownersId);
		}
		
		List<RefProductOwnerDTO> rposDto = new ArrayList<>();
		if (rpos == null || rpos.isEmpty()) {
			return rposDto;
		}

		
		for (RefProductOwner rpo : rpos) {
			rposDto.add(RefProductOwnerTransform.bo2DtoLight(rpo));
		}

		return rposDto;
	}
	
	/**
	 * Get the list of all product using the productType and subProductType
	 * @param productType
	 * @param subProductType
	 * @return {@link List}
	 * @throws JrafDomainException error 212 if productType or SubProduct Type does not exist
	 */
	private List<RefProduct> getAllProductByProductOrSubProduct(String productType, String subProductType) throws JrafDomainException {
		RefProduct rp = new RefProduct();
		rp.setGenerateComPref(null);
		// productType is not null or empty
		if (StringUtils.isNotBlank(productType)) {
			rp.setProductType(productType);			
		}
		// subProductType is not null or empty
		if (StringUtils.isNotBlank(subProductType)) {
			rp.setSubProductType(subProductType);			
		}

		// Get all products using their productType and/or subProductType
		List<RefProduct> rps = refProductRepository.findAll(Example.of(rp));
		if ((rps == null || rps.isEmpty()) 
				&& (StringUtils.isNotBlank(productType)
						|| StringUtils.isNotBlank(subProductType))) {
			throw new ContractNotFoundException("ProductType: " + productType + ", SubProductType: " + subProductType + " not found");
		}

		
		// Convert DTO to BO for further usage
		List<RefProduct> products = new ArrayList<>();
		for (RefProduct rpDto : rps) {
			products.add(rpDto);
		}

		return products;
	}
	
	/**
	 * Get the list of all owners using the appCOde or consumerId
	 * @param appCode
	 * @param consumer
	 * @return {@link List}
	 * @throws JrafDomainException error 300 if appCode or consumer does not exist
	 */
	private List<RefOwner> getAllOwnerByAppCodeOrOwner(String appCode, String consumer) throws JrafDomainException {
		RefOwner ro = new RefOwner();
		// appCode is not null or empty
		if (StringUtils.isNotBlank(appCode)) {
			ro.setAppCode(appCode);
		}
		// consumer is not null or empty
		if (StringUtils.isNotBlank(consumer)) {
			ro.setConsumerId(consumer);
		}
		// Get all owners using their appCode and/or consumerId
		List<RefOwner> ros = refOwnerRepository.findAll(Example.of(ro));
		if ((ros == null || ros.isEmpty()) 
				&& (StringUtils.isNotBlank(appCode)
						|| StringUtils.isNotBlank(consumer))) {
			throw new RoleContractUnauthorizedOperationException("300", "ApplicationCode: " + appCode + ", ConsumerId: " + consumer + " not found");
		}

		// Convert DTO to BO for further usage
		List<RefOwner> owners = new ArrayList<>();
		for (RefOwner roDto : ros) {
			owners.add(roDto);
		}
		return owners;
	}
}
