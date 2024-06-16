package com.afklm.repind.msv.forgetme.asked.service.encoder;

import com.afklm.repind.msv.forgetme.asked.entity.ForgottenIndividual;
import com.afklm.repind.msv.forgetme.asked.wrapper.WrapperForgetMeAskedResponse;
import com.afklm.repind.msv.forgetme.asked.wrapper.WrapperForgottenIndividual;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ForgetMeAskedEncoder {

    public WrapperForgetMeAskedResponse decode(Collection<ForgottenIndividual> iForgottenIndividuals){
        return new WrapperForgetMeAskedResponse().withAskedIndividuals(iForgottenIndividuals.stream().map(i -> new WrapperForgottenIndividual()
                                                                                                            .withGin(i.getIdentifier())
                                                                                                            .withCreationDate(i.getModificationDate()))
                                                                                                    .collect(Collectors.toSet()));
    }
}
