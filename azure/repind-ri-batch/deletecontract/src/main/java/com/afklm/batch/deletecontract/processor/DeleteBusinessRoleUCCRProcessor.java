package com.afklm.batch.deletecontract.processor;

import com.afklm.batch.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.afklm.batch.deletecontract.model.DeleteBusinessRoleUCCROutputModel;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.entity.role.RoleUCCR;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class DeleteBusinessRoleUCCRProcessor implements ItemProcessor<RoleUCCR, DeleteBusinessRoleUCCROutputModel> {

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Override
    @Transactional
    public DeleteBusinessRoleUCCROutputModel process(@NotNull RoleUCCR roleUCCR) throws Exception {
        log.info("Start delete business roles UCCR processor");
        DeleteBusinessRoleUCCROutputModel deleteBusinessRoleUCCROutputModel = new DeleteBusinessRoleUCCROutputModel();
        deleteBusinessRoleUCCROutputModel.setRoleUCCR(roleUCCR);

        try {
            List<BusinessRoleDTO> businessRoleDTOList = businessRoleDS.findByGinAndType(roleUCCR.getGin(),"U");
            if (!businessRoleDTOList.isEmpty()){
                deleteBusinessRoleUCCROutputModel.setBusinessRoleDTO(businessRoleDTOList.stream().filter(br ->roleUCCR.getCleRole().equals(br.getCleRole())).findFirst().orElse(null));
            }
        } catch (JrafDomainException e) {
            deleteBusinessRoleUCCROutputModel.setBusinessRoleDTO(null);
            log.info("Don't find business role for gin:"+ roleUCCR.getGin()+ "with contract numbers:"+ roleUCCR.getUccrID());
        }

        log.info("Delete business roles UCCR processor end");
        return deleteBusinessRoleUCCROutputModel;
    }
}

