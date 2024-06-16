package com.afklm.batch.deletecontract.writer;

import com.afklm.batch.deletecontract.model.DeleteContractsOutputModel;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.handicap.HandicapDataRepository;
import com.airfrance.repind.dao.handicap.HandicapRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class DeleteContractsBatchWriter implements ItemWriter<DeleteContractsOutputModel> {

    @Autowired
    protected HandicapRepository handicapRepository;

    @Autowired
    protected HandicapDataRepository handicapDataRepository;

    @Autowired
    protected RoleContratsRepository roleContratsRepository;

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Override
    @Transactional
    public void write(@NotNull List<? extends DeleteContractsOutputModel> list) throws Exception {
        log.info("Start delete contracts batch writer");

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(contratToDelete -> {

                if (!CollectionUtils.isEmpty(contratToDelete.getHandicapDataToDeleteList())){
                    contratToDelete.getHandicapDataToDeleteList().forEach(handicapData -> handicapDataRepository.deleteById(handicapData.getHandicapDataId()));
                }

                if (!CollectionUtils.isEmpty(contratToDelete.getHandicapToDeleteList())){
                    contratToDelete.getHandicapToDeleteList().forEach(handicap -> handicapRepository.deleteById(handicap.getHandicapId()));
                }

                if(contratToDelete.getBusinessRoleDTO() !=null){
                    try {
                        businessRoleDS.deleteBusinessRole(contratToDelete.getBusinessRoleDTO());
                    } catch (JrafDomainException e) {
                        log.info("Can't delete business role: "+ contratToDelete.getBusinessRoleDTO().getCleRole().toString());
                    }
                }
                roleContratsRepository.deleteById(contratToDelete.getRoleContrats().getSrin());

            });
        }

        log.info("Delete contracts batch writer end");
    }
}
