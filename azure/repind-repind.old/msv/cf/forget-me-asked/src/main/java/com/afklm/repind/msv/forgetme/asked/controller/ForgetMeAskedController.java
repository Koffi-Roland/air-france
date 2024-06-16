package com.afklm.repind.msv.forgetme.asked.controller;


import com.afklm.repind.msv.forgetme.asked.service.ForgetMeAskedService;
import com.afklm.repind.msv.forgetme.asked.wrapper.WrapperForgetMeAskedResponse;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/forget-me-asked")
@AllArgsConstructor
public class ForgetMeAskedController {

    private ForgetMeAskedService forgetMeAskedService;

    @ApiOperation(value = "Forget Me Asked", notes = "Forget Me Asked", response = WrapperForgetMeAskedResponse.class)
    @GetMapping(value = "/", headers = "Accept=application/json", produces = "application/json; charset=utf-8")
    public ResponseEntity<WrapperForgetMeAskedResponse> searchAskedForgottenIndividuals(){
        log.info("ForgotMe Asked Rest call");
        return forgetMeAskedService.search();
    }
}
