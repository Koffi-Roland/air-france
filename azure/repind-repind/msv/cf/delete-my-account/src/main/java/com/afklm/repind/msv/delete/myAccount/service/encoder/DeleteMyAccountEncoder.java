package com.afklm.repind.msv.delete.myAccount.service.encoder;

import com.afklm.repind.msv.delete.myAccount.wrapper.WrapperDeleteMyAccountResponse;
import org.springframework.stereotype.Service;

@Service
public class DeleteMyAccountEncoder {

    public WrapperDeleteMyAccountResponse decode(String gin) {
        WrapperDeleteMyAccountResponse wrapperDeleteMyAccountResponse =  new WrapperDeleteMyAccountResponse();
        wrapperDeleteMyAccountResponse.setGin(gin);
        return wrapperDeleteMyAccountResponse;
    }
}
