package com.airfrance.repind.service.individu.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ApplicationCodeEnum;
import com.airfrance.repind.dao.individu.UsageClientsRepository;
import com.airfrance.repind.entity.individu.UsageClients;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UsageClientsDS {
	
	
    @Autowired
	private UsageClientsRepository usageClientsRepository;

	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public void add(String gin, String code) throws InvalidParameterException {
		add(gin, code, new Date());
	}
	
	@Transactional
	public void add(String gin, String code, Date date) throws InvalidParameterException {
		if (EnumUtils.isValidEnum(ApplicationCodeEnum.class, code)) {
			if(StringUtils.isBlank(gin)  || StringUtils.isBlank(code) || date == null) {
				throw new InvalidParameterException("gin or code or date is missing");
			}
			UsageClients usageClients = new UsageClients();
			usageClients.setScode(code);
			usageClients.setSgin(gin);
			for (UsageClients usage : usageClientsRepository.findByGinAndCode(gin, code)) {
				usageClientsRepository.delete(usage);
			}
			
			usageClients.setDate_modification(date);
			usageClients.setSconst("O");
			usageClientsRepository.saveAndFlush(usageClients);
		}
		/*else {
			throw new InvalidParameterException("gin or code or date is missing");
		}*/
	}
}
