package com.airfrance.batch.purgemyaccount.service;

import com.airfrance.batch.purgemyaccount.model.MyaLogicalToDelete;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.airfrance.batch.purgemyaccount.helper.Constant.SIGNATURE_MODIFICATION_PURGE_MYA;
import static com.airfrance.batch.purgemyaccount.helper.Constant.SITE_MODIFICATION_QVI;

@Service
@Slf4j
public class AccountDataService {

    @Autowired
    private AccountDataRepository accountDataRepository;

    /**
     * This method logically remove account Data
     * @param myaLogicalToDelete myaLogicalToDelete
     * @return boolean
     */
    @Transactional
    public boolean logicalDeleteAccountData(MyaLogicalToDelete myaLogicalToDelete){
        Integer id = myaLogicalToDelete.getId();
        String sgin = myaLogicalToDelete.getSgin();

        return this.accountDataRepository.findById(id)
                .map(accountData -> {
                    Date dateModification = new Date();
                    this.accountDataRepository.logicalDeleteAccountData(id, sgin, dateModification, SIGNATURE_MODIFICATION_PURGE_MYA, SITE_MODIFICATION_QVI);
                    return true;
                })
                .orElse(false);
    }

    /**
     * This method physically remove account_data
     * @param id id
     */
    @Transactional
    public void physicalDeleteAccountData(Integer id){
        try {
            this.accountDataRepository.deleteById(id);
        }catch(Exception e){
            log.error("Unable to delete physically account_data with ID={}: {}", id, e.getMessage());
        }
    }

}
