package com.afklm.batch.deletecontract.processor;

import com.afklm.batch.deletecontract.model.DeleteContractsOutputModel;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.handicap.HandicapDataRepository;
import com.airfrance.repind.dao.handicap.HandicapRepository;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.handicap.HandicapData;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DeleteContractsBatchProcessor implements ItemProcessor<RoleContrats, DeleteContractsOutputModel> {

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Autowired
    protected HandicapRepository handicapRepository;

    @Autowired
    protected HandicapDataRepository handicapDataRepository;

    @Override
    @Transactional
    public DeleteContractsOutputModel process(@NotNull RoleContrats roleContrats) throws Exception {
        log.info("Start delete contracts batch processor");
        DeleteContractsOutputModel deleteContractsOutputModel = new DeleteContractsOutputModel();
        deleteContractsOutputModel.setRoleContrats(roleContrats);

        try {
            List<BusinessRoleDTO> businessRoleDTOList = businessRoleDS.findByGinAndType(roleContrats.getGin(),"C");
            if (!businessRoleDTOList.isEmpty()){
                deleteContractsOutputModel.setBusinessRoleDTO(businessRoleDTOList.stream().filter(br ->roleContrats.getNumeroContrat().equals(br.getNumeroContrat())).findFirst().orElse(null));
            }
        } catch (JrafDomainException e) {
            deleteContractsOutputModel.setBusinessRoleDTO(null);
            log.info("Don't find business role for gin:"+ roleContrats.getGin()+ "with contract numbers:"+ roleContrats.getNumeroContrat());
        }

        if (roleContrats.getTypeContrat().equals("S")){
            deleteContractsOutputModel.setHandicapToDeleteList(handicapRepository.findByGin(roleContrats.getGin()));
            if (!deleteContractsOutputModel.getHandicapToDeleteList().isEmpty()){
                List<HandicapData> handicapDataToDeleteList = new ArrayList<>();
                deleteContractsOutputModel.getHandicapToDeleteList().forEach(handicap -> handicapDataToDeleteList.addAll(handicapDataRepository.findByHandicapHandicapId(handicap.getHandicapId())));
                deleteContractsOutputModel.setHandicapDataToDeleteList(handicapDataToDeleteList);
            }

        }

        log.info("Delete contracts batch processor end");
        return deleteContractsOutputModel;
    }
}
