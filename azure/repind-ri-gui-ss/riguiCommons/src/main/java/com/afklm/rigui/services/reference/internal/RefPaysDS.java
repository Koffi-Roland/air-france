package com.afklm.rigui.services.reference.internal;

/*PROTECTED REGION ID(_e6SokBd0EeK8rbfCW6etbQ DS i) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.TableReferenceEnum;
import com.afklm.rigui.dao.reference.PaysRepository;
import com.afklm.rigui.dao.reference.RefProvinceRepository;
import com.afklm.rigui.entity.reference.Pays;
import com.afklm.rigui.entity.reference.RefProvince;
import com.afklm.rigui.util.service.PaginationResult;
import com.afklm.rigui.util.service.PaginationUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


/*PROTECTED REGION END*/


/**
 * <p>Title : RefPaysDS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class RefPaysDS {

    /** logger */
    private static final Log log = LogFactory.getLog(RefPaysDS.class);

    @Autowired
    private PaysRepository paysRepository;

    private final String YES = "O";

    @Autowired
    private RefProvinceRepository refProvinceRepository;

    @Transactional(readOnly=true)
    public Pays get(Pays bo) throws JrafDomainException {
        return paysRepository.findById(bo.getCodePays()).get();
    }

    /**
     * codePaysIsValide
     * @param codePays in String
     * @return The codePaysIsValide as <code>boolean</code>
     */
    @Transactional(readOnly=true)
    public boolean codePaysIsValide(String codePays){
    	
    	if (!StringUtils.isEmpty(codePays)) {
    		codePays = codePays.toUpperCase();
    	}
    	
		Optional<Pays> pays = paysRepository.findById(codePays);

        return pays.isPresent();
    }

    /**
     * codePaysIsNormalizable
     * @param codePays in String
     * @return The true if pays contains "YES" inside Normalization column
     */
    @Transactional(readOnly=true)
    public boolean codePaysIsNormalizable(String codePays){

        if (!StringUtils.isEmpty(codePays)) {
            codePays = codePays.toUpperCase();
        }

        Optional<Pays> pays = paysRepository.findById(codePays);

        return pays.filter(value -> YES.equals(value.getNormalisable())).isPresent();

    }

    /**
     * codePaysIsForcage
     * @param codePays in String
     * @return The true if pays contains "YES" inside Forcage column
     */
    @Transactional(readOnly=true)
    public boolean codePaysIsForcage(String codePays){

        if (!StringUtils.isEmpty(codePays)) {
            codePays = codePays.toUpperCase();
        }

        Optional<Pays> pays = paysRepository.findById(codePays);

        return pays.filter(value -> YES.equals(value.getForcage())).isPresent();

    }
    

    @Transactional(readOnly=true)
    public PaginationResult<Pays> providePaysWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<Pays> paginationResult = new PaginationResult<>();
    	
    	Long totalResultsFound = paysRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.PAYS, totalResultsFound);
    	
    	List<Pays> listPays = paysRepository.providePaysWithPagination(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (Pays pays : listPays) {
    		paginationResult.getListResults().add(pays);
    	}
    	
    	return paginationResult;
    }

    /**
     * codeProvinceIsValid
     * @param  province in String
     * @param codePays in String
     * @return The match of couple provinde/pays as <code>boolean</code>
     * @throws JrafDomainException in case of exception
     */
    public boolean codeProvinceIsValid(String province, String codePays) throws JrafDomainException {

        if (StringUtils.isNotBlank(province)) {
            province = province.toUpperCase();
        }
        if (StringUtils.isNotBlank(codePays)) {
            codePays = codePays.toUpperCase();
        }

        List<RefProvince> refProvince = refProvinceRepository.isValidProvinceCode(province, codePays);

        return refProvince != null && !refProvince.isEmpty();
    }
}
