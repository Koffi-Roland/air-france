package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.NotFoundException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefProductRepository;
import com.afklm.rigui.dto.reference.RefProductDTO;
import com.afklm.rigui.dto.reference.RefProductTransform;
import com.afklm.rigui.entity.reference.RefProduct;
import com.afklm.rigui.exception.TooManyResultsDaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class RefProductDS {

    @Autowired
    private RefProductRepository refProductRepository;

    @Transactional(readOnly=true)
    public RefProductDTO get(RefProductDTO dto) throws JrafDomainException {
      return get(dto.getProductId());
    }

    @Transactional(readOnly=true)
    public RefProductDTO get(Integer id) throws JrafDomainException {
    	  Optional<RefProduct> refProduct = refProductRepository.findById(id);
          if (!refProduct.isPresent()) {
          	return null;
          }
          return RefProductTransform.bo2DtoLight(refProduct.get());
    }

    /*PROTECTED REGION ID(_IIGR8AJuEeeb1IzCQutBDQ u m) ENABLED START*/

    @Transactional(readOnly=true)
    public String getContractTypeByProductType(String productType, String subProductType) throws JrafDomainException {

    	RefProduct rpDto = new RefProduct();
    	rpDto.setProductType(productType);
    	rpDto.setSubProductType(subProductType);
	    rpDto.setGenerateComPref(null);
    	List<RefProduct> rps = refProductRepository.findAll(Example.of(rpDto));
    	String contractType = null;
    	for (RefProduct rp : rps) {
    		String tmp = rp.getContractType();
    		if (contractType == null) {
    			contractType = tmp;
    		} else if (contractType.equals(rp.getContractType())) {
    			continue;
    		} else {
    			throw new TooManyResultsDaoException("The product type cannot be treated, please use the subProductType");
    		}
    	}
    	return contractType;
    }
    	/*PROTECTED REGION END*/


    @Transactional(readOnly=true)
	public RefProductDTO getProductByProductType(String productType, String subProductType) throws JrafDomainException {
		RefProduct rpDTO = new RefProduct();
	    rpDTO.setProductType(productType);
	    rpDTO.setSubProductType(subProductType);
	    rpDTO.setGenerateComPref(null);
	    List<RefProduct> rps = refProductRepository.findAll(Example.of(rpDTO));
	     
	    if (rps != null && rps.size() > 1) {
	        throw new TooManyResultsDaoException("The product type cannot be treated, please use the subProductType");
	    }
	     
	    if (rps == null || rps.isEmpty()) {
	    	throw new NotFoundException("The product not found");
	    }
	 
	    return RefProductTransform.bo2DtoLight(rps.get(0));
	}

	
	
}
