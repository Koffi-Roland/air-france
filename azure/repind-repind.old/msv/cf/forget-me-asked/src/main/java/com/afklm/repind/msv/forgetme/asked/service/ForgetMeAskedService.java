package com.afklm.repind.msv.forgetme.asked.service;

import com.afklm.repind.msv.forgetme.asked.entity.ForgottenIndividual;
import com.afklm.repind.msv.forgetme.asked.repository.IForgottenIndividualRepository;
import com.afklm.repind.msv.forgetme.asked.service.encoder.ForgetMeAskedEncoder;
import com.afklm.repind.msv.forgetme.asked.utils.EContext;
import com.afklm.repind.msv.forgetme.asked.wrapper.WrapperForgetMeAskedResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class ForgetMeAskedService {

    private IForgottenIndividualRepository forgottenIndividualRepository;
    private ForgetMeAskedEncoder forgetMeAskedEncoder;

    @Transactional(readOnly = true)
    public ResponseEntity<WrapperForgetMeAskedResponse> search(){
        log.info("Forget me asked service start");
        Collection<ForgottenIndividual> forgottenIndividuals = forgottenIndividualRepository.findForgottenIndividualsByContextIs(EContext.ASKED.getValue());

        return new ResponseEntity<>(forgetMeAskedEncoder.decode(forgottenIndividuals), HttpStatus.OK);
    }
}
