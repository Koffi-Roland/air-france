package com.afklm.repind.msv.manage.individual.identifier.service;

import com.afklm.repind.common.repository.role.RoleContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleContractService {

    private RoleContractRepository roleContratsRepository;

    public boolean existFrequencePlusContractSharingSameValidEmail(String pEmail, String pGin) {
        return (roleContratsRepository.countFrequencePlusContractsOwnedByOtherGinsSharingSameValidEmail(pEmail, pGin) > 0);
    }

    public boolean existFrequencePlusContractSharingSameValidEmail(String pEmail) {
        return (roleContratsRepository.countFrequencePlusContractsOwnedByGinsSharingSameValidEmail(pEmail) > 0);
    }
}
