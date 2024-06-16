package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.cati.common.spring.rest.resources.EntryPointResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AF-KLM
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class EntryPointController {

    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public EntryPointResource entryPointGet() {

        EntryPointResource entryPoint = new EntryPointResource();
        entryPoint.setApplicationName("Cati WEB Application");

        return entryPoint;
    }

}
