package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.TableReferenceEnum;
import com.afklm.rigui.dao.reference.ErrorRepository;
import com.afklm.rigui.dao.reference.RefComPrefRepository;
import com.afklm.rigui.dto.reference.*;
import com.afklm.rigui.entity.reference.*;
import com.afklm.rigui.util.service.PaginationResult;
import com.afklm.rigui.util.service.PaginationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


/**
 * <p>Title : ReferenceDS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class ReferenceDS {

    /** logger */
    private static final Log log = LogFactory.getLog(ReferenceDS.class);

    /** main dao */
    @Autowired
    private RefComPrefRepository refComPrefRepository;

    @Autowired
    private ErrorRepository errorRepository;

    @Transactional(readOnly=true)
    public RefComPrefDTO get(RefComPrefDTO dto) throws JrafDomainException {
      return get(dto.getRefComprefId());
    }

    @Transactional(readOnly=true)
    public RefComPrefDTO get(Integer id) throws JrafDomainException {
    	  Optional<RefComPref> refComPref = refComPrefRepository.findById(id);
          if (!refComPref.isPresent()) {
          	return null;
          }
          return RefComPrefTransform.bo2DtoLight(refComPref.get());
    }


    public RefComPrefRepository getRefComPrefRepository() {
		return refComPrefRepository;
	}

	public void setRefComPrefRepository(RefComPrefRepository refComPrefRepository) {
		this.refComPrefRepository = refComPrefRepository;
	}

    /** 
     * getRefComPref
     * @param domain in String
     * @param groupType in String
     * @param type in String
     * @return The getRefComPref as <code>List<RefComPref></code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<RefComPref> getRefComPref(String domain, String groupType, String type, String market) throws JrafDomainException {
        /*PROTECTED REGION ID(_mBlFgMamEeKA2b5enmBS_w) ENABLED START*/
    	List<RefComPref> refComPrefs = null;
        RefComPref refComPref = new RefComPref();
        if(domain!=null) {
        	RefComPrefDomain rcpDomain = new RefComPrefDomain();
        	rcpDomain.setCodeDomain(domain);
        	refComPref.setDomain(rcpDomain);
        }
        if(groupType!=null) {
        	RefComPrefGType rcpGroupType = new RefComPrefGType();
        	rcpGroupType.setCodeGType(groupType);
        	refComPref.setComGroupeType(rcpGroupType);
        }
        if(type!=null) {
        	RefComPrefType rcpType = new RefComPrefType();
        	rcpType.setCodeType(type);
        	refComPref.setComType(rcpType);
        }
        if(market!=null) {
        	refComPref.setMarket(market);
        }
        refComPrefs = refComPrefRepository.findAll(Example.of(refComPref));
        return refComPrefs;
        /*PROTECTED REGION END*/
    }

    /** 
     * getRefComPref
     * @param domain in String
     * @param groupType in String
     * @param type in String
     * @return The getRefComPref as <code>List<RefComPref></code>
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<RefComPref> getRefComPref(String domain, String groupType, String type) throws JrafDomainException {
        /*PROTECTED REGION ID(_mBlFgMamEeKA2b5enmBS_w) ENABLED START*/
    	
        return getRefComPref(domain, groupType, type, null);
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto) throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findByExample1) ENABLED START*/
        List<ErrorDTO> objFounds = null;
        objFounds = findByExample(dto, Integer.valueOf(-1), Integer.valueOf(-1));
        return objFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto, Integer nbLignes,
            Integer indexDepart) throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findByExample2) ENABLED START*/
        List<ErrorDTO> objFounds = null;
        objFounds = findByExample(dto, nbLignes, indexDepart, Boolean.FALSE,
                Boolean.FALSE);
        return objFounds;
        /*PROTECTED REGION END*/
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public List<ErrorDTO> findByExample(ErrorDTO dto, Integer nbLignes,
            Integer indexDepart, Boolean ignoreCase, Boolean likeMode)
            throws JrafDomainException {
        /*PROTECTED REGION ID(_TUSJ0IJTEeKhdftDNws56g DS-CM findWhere) ENABLED START*/
        List boFounds = null;
        List<ErrorDTO> dtoFounds = null;
        ErrorDTO refErreurDTO = null;
        com.afklm.rigui.entity.reference.Error refErreur = null;
        // transformation light dto -> bo
        refErreur = ErrorTransform.dto2BoLight(dto);

        // execution du find
        boFounds = (List) errorRepository.findAll(Example.of(refErreur));

        // transformation bo -> DTO
        if (boFounds != null) {
            dtoFounds = new ArrayList<ErrorDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                refErreur = (com.afklm.rigui.entity.reference.Error) i.next();
                refErreurDTO = ErrorTransform.bo2DtoLight(refErreur);
                dtoFounds.add(refErreurDTO);
            }
        }
        return dtoFounds;
        /*PROTECTED REGION END*/
    }

}
