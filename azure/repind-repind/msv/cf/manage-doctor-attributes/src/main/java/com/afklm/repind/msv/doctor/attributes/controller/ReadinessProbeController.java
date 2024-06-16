package com.afklm.repind.msv.doctor.attributes.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/infra/healthcheck")
public class ReadinessProbeController {

    @ApiOperation(value = "ReadinessProbe", notes = "ReadinessProbe")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> readinessProbe() {
        return new ResponseEntity<>( null , HttpStatus.OK);
    }
}
