package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.role.RoleContrats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.airfrance.batch.purgemyaccount.helper.Constant.SIGNATURE_MODIFICATION_PURGE_MYA;
import static com.airfrance.batch.purgemyaccount.helper.Constant.SITE_MODIFICATION_QVI;

@Service
@Slf4j
public class RoleContratService {
    @Autowired
    private RoleContratsRepository roleContratsRepository;

    @Autowired
    private BusinessRoleRepository businessRoleRepository;

    @Transactional
    public boolean logicalDeleteRoleContrat(MyaLogicalToDelete myaLogicalToDelete){
        String numeroContrat = myaLogicalToDelete.getSnumeroContrat();
        String srin = myaLogicalToDelete.getSrin();
        String typeContrat = myaLogicalToDelete.getStypeContrat();

        RoleContrats roleContrats = roleContratsRepository.findBySrin(srin);
        Date dateModification = new Date();

        if(roleContrats != null){
            roleContratsRepository.logicalDeleteRoleContract(numeroContrat, srin, typeContrat, dateModification,  SIGNATURE_MODIFICATION_PURGE_MYA, SITE_MODIFICATION_QVI);
            return true;
        }
        return false;
    }

    @Transactional
    public void physicalDeleteRoleContrat(Integer cleRole){
        try{
            roleContratsRepository.deleteByCleRole(cleRole);
            businessRoleRepository.deleteByCleRole(cleRole);
        }catch (Exception e){
            log.error("Unable to delete physically role_contrats with ICLE_ROLE= {}: {}", cleRole, e.getMessage());
        }
    }

}
