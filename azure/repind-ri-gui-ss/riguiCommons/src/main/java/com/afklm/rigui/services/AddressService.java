package com.afklm.rigui.services;

import java.util.HashSet;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afklm.rigui.dao.adresse.PostalAddressRepository;
import com.afklm.rigui.dao.adresse.Usage_mediumRepository;
import com.afklm.rigui.entity.adresse.PostalAddress;
import com.afklm.rigui.entity.adresse.Usage_medium;

/**
 * <p>
 * Title : IndividuAllImpl.java
 * </p>
 * Service Implementation to manage IndividuAll
 * <p>
 * Copyright : Copyright (c) 2018
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */

@Service
public class AddressService {

	@Autowired
	private PostalAddressRepository addressRepository;
		
	@Autowired
	private Usage_mediumRepository usageMediumRepository;
    
	@Autowired
	public DozerBeanMapper dozerBeanMapper;

}
