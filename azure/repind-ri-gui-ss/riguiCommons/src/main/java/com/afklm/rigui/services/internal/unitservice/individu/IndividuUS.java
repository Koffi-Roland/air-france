package com.afklm.rigui.services.internal.unitservice.individu;

/*PROTECTED REGION ID(_q6a0wPcUEd-Kx8TJdz7fGw US i) ENABLED START*/

// add not generated imports here


import com.afklm.rigui.dao.individu.IndividuRepository;
import com.afklm.rigui.dao.role.RoleContratsRepository;
import com.afklm.rigui.dto.individu.adh.searchindividualbymulticriteria.*;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.fonetik.PhEntree;
import com.afklm.rigui.fonetik.PhonetikInput;
import com.afklm.rigui.services.adresse.internal.AdresseDS;
import com.afklm.rigui.util.NormalizedStringUtils;
import com.afklm.rigui.exception.MissingParameterException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IndividuUS {

    /** logger */
    private static final Log log = LogFactory.getLog(IndividuUS.class);
    
    private static final String CONTEXT_DEAD_INDIVIDUAL_FOR_SEARCH = "DECEASED_FOR_AMEX";

    @Autowired
    private AdresseDS adresseDS;
    
    @Autowired
    private RoleContratsRepository roleContratsRepository;
    
    /** Reference sur le unit service MyAccountUS **/
    @Autowired
    private MyAccountUS myAccountUS;


	public static final  String _REF_133 = "133";
	public static final  String _REF_323 = "323";
    
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private IndividuRepository individuRepository;

    /**
     * Constructeur vide
     */
    public IndividuUS() {
    }

	/**
	 * Trim and return null if string is empty
	 * @param str
	 * @return
	 */	
	protected String cleanString(String str) {
		String result = null;
		if (str!=null) {
			str=str.trim();
			if (str.length()>0) {
				result = str;
			}
		}
		return result;
	}

}
