package com.afklm.rigui.services.resources;

import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.entity.external.ExternalIdentifier;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.model.individual.ModelAccountData;
import com.afklm.rigui.model.individual.ModelExternalIdentifier;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.helper.resources.AccountDataHelper;
import com.sun.xml.ws.api.tx.at.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountDataService {

    @Autowired
    private AccountDataRepository accountDataRepository;

    @Autowired
    private AccountDataHelper accountDataHelper;

    /**
     * This method GET all account data according to the GIN in parameter.
     *
     * @param gin
     * @return a list of Account
     * @throws ServiceException
     */
    @Transactional
    public List<ModelAccountData> getAll(String gin) throws ServiceException {

        if (StringUtils.isEmpty(gin)) {
            throw new IllegalArgumentException("Unable de get individual with empty gin");
        }

        AccountData accountData = accountDataRepository.findBySgin(gin);

        if (accountData == null) {
            return null;
        }


        // Email identifier and FB Identifier are in the same object. We need two object to display
        // on two different card in RIGUI. In order to do this we duplicate the data two time max
        // and put the Email identifier and FB in the field emailFbIdentifier
        return Stream.of(accountData.getEmailIdentifier(), accountData.getFbIdentifier()).toList()
                .stream()
                .filter(Objects::nonNull)
                .map((identifier) -> {
                    ModelAccountData model = accountDataHelper.buildAccountModel(accountData);
                    model.setEmailFbIdentifier(identifier);
                    return model;
                }).collect(Collectors.toList());
    }

}
