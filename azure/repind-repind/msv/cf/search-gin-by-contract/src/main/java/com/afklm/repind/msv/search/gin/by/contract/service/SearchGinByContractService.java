package com.afklm.repind.msv.search.gin.by.contract.service;

import com.afklm.repind.common.enums.IndividuStatusEnum;
import com.afklm.repind.common.entity.role.BusinessRole;
import com.afklm.repind.msv.search.gin.by.contract.repository.IBusinessRoleRepository;
import com.afklm.repind.msv.search.gin.by.contract.service.encoder.SearchGinByContractEncoder;
import com.afklm.repind.msv.search.gin.by.contract.wrapper.WrapperSearchGinByContractResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SearchGinByContractService {

    private static final int FB_CONTRACT_LENGTH = 10;
    private static final int BDC_CONTRACT_LENGTH = 9;

    private IBusinessRoleRepository businessRoleRepository;

    private SearchGinByContractEncoder searchGinByContractEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperSearchGinByContractResponse> search(String num, boolean merge) {
        Collection<BusinessRole> contract = businessRoleRepository.findByContractNumber(num);

        if (contract == null || contract.isEmpty()) {
            contract = complementarySearch(num);
        }

        contract = contract.stream().filter(item -> this.filterIndividualMerged(item, merge))
                .collect(Collectors.toSet());

        log.info("Search by contract : {} results found", contract.size());
        return new ResponseEntity<>(searchGinByContractEncoder.decode(contract), HttpStatus.OK);
    }

    public Collection<BusinessRole> complementarySearch(String num) {
        Collection<BusinessRole> contract = new ArrayList<>();
        if (num != null && num.length() == FB_CONTRACT_LENGTH) {
            num = "00" + num;
            contract = businessRoleRepository.findByContractNumber(num);
        } else if (num != null && num.length() == BDC_CONTRACT_LENGTH) {
            num = "000" + num;
            contract = businessRoleRepository.findByContractNumber(num);
        }

        return contract;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public boolean filterIndividualMerged(BusinessRole br, boolean merge) {
        return merge || br.getIndividu() != null && br.getIndividu().getGin() != null && !br.getIndividu().getStatutIndividu().equals(IndividuStatusEnum.MERGED.toString());
    }
}
