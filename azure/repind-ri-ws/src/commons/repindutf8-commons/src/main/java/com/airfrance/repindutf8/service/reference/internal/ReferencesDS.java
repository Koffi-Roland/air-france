package com.airfrance.repindutf8.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repindutf8.dao.RefErreurRepository;
import com.airfrance.repindutf8.dto.reference.RefErreurDTO;
import com.airfrance.repindutf8.dto.reference.RefErreurTransform;
import com.airfrance.repindutf8.entity.RefErreur;
import com.airfrance.repindutf8.service.reference.IReferencesDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
public class ReferencesDS implements IReferencesDS {
    
    @Autowired
    private RefErreurRepository refErreurRepository;

    /** 
     * callError
     * @param numError in String
     * @param details in String
     * @return The callError as <code>String</code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public String callError(String numError, String details) throws JrafDomainException {
    	RefErreurDTO refError = new RefErreurDTO();
    	if(numError.length()>3) {
			numError = numError.substring(numError.length()-3); //récupération du code erreur
    	}
    	refError.setScode(numError);
    	if(details!= null && details.length()>0) {
    		return findByExample(refError).get(0).getLibelleEn()+": "+details;
    	}else {
    		return findByExample(refError).get(0).getLibelleEn();
    	}
    }
    
    @Override
	public List<RefErreurDTO> findByExample(RefErreurDTO dto) throws JrafDomainException {
    	RefErreur email = RefErreurTransform.dto2BoLight(dto);
		List<RefErreurDTO> result = new ArrayList<>();
		for (RefErreur found : refErreurRepository.findAll(Example.of(email))) {
			result.add(RefErreurTransform.bo2DtoLight(found));
			}
		return result;
	}

}
