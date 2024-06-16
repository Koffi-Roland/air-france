package com.airfrance.batch.contract.deletecontract.writer;

import com.airfrance.batch.contract.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.role.RoleUCCRRepository;
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
public class DeleteBusinessRoleUCCRWriter implements ItemWriter<DeleteBusinessRoleUCCROutputModel> {

    @Autowired
    protected RoleUCCRRepository roleUCCRRepository;

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Override
    @Transactional
    public void write(@NotNull List<? extends DeleteBusinessRoleUCCROutputModel> list) throws Exception {
        log.info("Start delete business role UCCR writer");

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(roleToDelete -> {

                roleUCCRRepository.deleteById(roleToDelete.getRoleUCCR().getCleRole());
                log.info("Deleted role UCCR: "+ roleToDelete.getRoleUCCR().getCleRole().toString());

                if(roleToDelete.getBusinessRoleDTO() !=null){
                    try {
                        businessRoleDS.deleteBusinessRole(roleToDelete.getBusinessRoleDTO());
                        log.info("Deleted business role: "+ roleToDelete.getBusinessRoleDTO().getCleRole().toString());
                    } catch (JrafDomainException e) {
                        log.info("Can't delete business role: "+ roleToDelete.getBusinessRoleDTO().getCleRole().toString());
                    }
                }

            });
        }

        log.info("Delete business role UCCR writer end");
    }
}

