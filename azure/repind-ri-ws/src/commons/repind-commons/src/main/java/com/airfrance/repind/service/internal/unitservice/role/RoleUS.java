package com.airfrance.repind.service.internal.unitservice.role;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class RoleUS {

    @Autowired
    private RoleContratsRepository roleContratsRepository;

    public RoleUS() {
    }

    public boolean existFrequencePlusContractSharingSameValidEmail(String pEmail, String pGin) throws JrafDomainException {

        Assert.notNull(pEmail);

        boolean found = false;
        if (StringUtils.isEmpty(pGin)) {
            found = roleContratsRepository.countFrequencePlusContractsOwnedByGinsSharingSameValidEmail(pEmail) > 0;
        } else {
            found = roleContratsRepository.countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail(pEmail, pGin) > 0;
        }

        return found;
    }
    
}
