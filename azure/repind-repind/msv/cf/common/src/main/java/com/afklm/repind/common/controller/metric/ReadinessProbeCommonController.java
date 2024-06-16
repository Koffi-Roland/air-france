package com.afklm.repind.common.controller.metric;


import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/infra/healthcheck")
public class ReadinessProbeCommonController {

    @Operation(summary = "ReadinessProbe", description = "ReadinessProbe")
    @GetMapping(headers = "Accept=application/json")
    public ResponseEntity<Void> readinessProbe() {
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
