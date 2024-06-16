package com.afklm.rigui.services.helper.resources;

import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.model.individual.ModelAccountData;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afklm.rigui.model.individual.ModelAlert;
import com.afklm.rigui.entity.individu.Alert;

@Component
public class AccountDataHelper {

    @Autowired
    public DozerBeanMapper dozerBeanMapper;

    /**
     * This method convert an AccountData object to a ModelAccountData object.
     * @param accountData
     * @return
     */
    public ModelAccountData buildAccountModel(AccountData accountData) {
        return this.dozerBeanMapper.map(accountData, ModelAccountData.class);
    }

}
