package com.afklm.repind.msv.search.gin.by.email.controller.utils;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.individual.Individu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestUtils {

    public static Collection<EmailEntity> createEmailCollection(int size) {

        List<EmailEntity> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            result.add(createEmailEntity());
        }

        result.add(createBadEmailEntity());

        return result;
    }

    private static EmailEntity createEmailEntity() {
        EmailEntity emailEntity = new EmailEntity();
        Individu individu = new Individu();
        individu.setGin("12345678");
        individu.setStatutIndividu("V");
        emailEntity.setEmail("jane.marple@repind.com");
        emailEntity.setIndividu(individu);
        emailEntity.setAin("12345678");

        return emailEntity;
    }

    private static EmailEntity createBadEmailEntity() {
        EmailEntity emailEntity = new EmailEntity();
        Individu individu = new Individu();
        individu.setGin("22345678");
        individu.setStatutIndividu("T");
        emailEntity.setEmail("jane.marple@repind.com");
        emailEntity.setIndividu(individu);
        emailEntity.setAin("12345678");

        return emailEntity;
    }
}
