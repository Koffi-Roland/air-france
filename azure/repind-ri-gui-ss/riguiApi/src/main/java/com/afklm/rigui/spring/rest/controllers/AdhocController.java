package com.afklm.rigui.spring.rest.controllers;

import com.afklm.rigui.model.individual.requests.ModelAdhocRequest;
import com.afklm.rigui.services.ServiceException;
import com.afklm.rigui.services.adhoc.AdhocService;
import com.afklm.rigui.wrapper.adhoc.WrapperAdhoc;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adhoc")
public class AdhocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdhocController.class);

    @Autowired
    private AdhocService adhocService;

    @RequestMapping(method = RequestMethod.POST, value = "/validation/{airlineCode}", produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADHOC')")
    public ResponseEntity<WrapperAdhoc> validate(
            @PathVariable String airlineCode,
            @RequestBody ModelAdhocRequest adhocRequest) throws ServiceException, SystemException {
        WrapperAdhoc wrapper = adhocService.validate(adhocRequest, airlineCode);
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/{airlineCode}", produces = "application/json; charset=utf-8")
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_ADHOC')")
    public ResponseEntity<Boolean> upload(
            @PathVariable String airlineCode,
            @RequestBody ModelAdhocRequest adhocRequest) throws ServiceException, SystemException {
        Boolean ok = adhocService.upload(adhocRequest, airlineCode);
        return new ResponseEntity<>(ok, ok ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
