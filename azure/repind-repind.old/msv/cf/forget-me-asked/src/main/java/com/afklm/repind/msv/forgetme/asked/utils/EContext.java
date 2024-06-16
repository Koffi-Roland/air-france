package com.afklm.repind.msv.forgetme.asked.utils;

import lombok.Getter;

@Getter
public enum EContext {
    ASKED("A"),
    CONFIRM("C"),
    FORGET("F");

    private String value;

    EContext (String iValue){
        value = iValue;
    }
}
