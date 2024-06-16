package com.afklm.rigui.spring.rest.controllers.resources;

import com.afklm.rigui.model.individual.ModelAccountData;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.resources.AccountDataService;
import com.afklm.rigui.wrapper.resources.WrapperResourceRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/individual")
public class AccountDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDataController.class);

    @Autowired
    private AccountDataService accountDataService;

    @RequestMapping(method = RequestMethod.GET, value = "/{gin}/account", produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_READ_ACCOUNT', 'ROLE_READ')")
    public ResponseEntity<WrapperResourceRead> getAccountDetails(@PathVariable String gin) throws ServiceException {
        LOGGER.info("Getting account data for given GIN {}", gin);
        List<ModelAccountData> accounts = accountDataService.getAll(gin);

        WrapperResourceRead wrapper = new WrapperResourceRead();
        wrapper.data = accounts;

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

}
