package com.afklm.repind.msv.forgetme.asked.repository;

import com.afklm.repind.msv.forgetme.asked.entity.ForgottenIndividual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;

public interface IForgottenIndividualRepository extends JpaRepository<ForgottenIndividual,Long> {
    Collection<ForgottenIndividual> findForgottenIndividualsByContextIs(String iContext);
}
