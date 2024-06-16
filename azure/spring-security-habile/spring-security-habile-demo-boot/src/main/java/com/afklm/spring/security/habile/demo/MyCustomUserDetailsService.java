package com.afklm.spring.security.habile.demo;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.demo.model.MyOwnUserDetails;
import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of the {@link CustomUserDetailsService} interface.
 * 
 * @author m408461
 */
@Component
@Profile("customUserDetails")
public class MyCustomUserDetailsService implements CustomUserDetailsService {

    @Override
    public UserDetails enrichUserDetails(HabileUserDetails userDetails) {
        MyOwnUserDetails customUserDetails = new MyOwnUserDetails(userDetails);
        customUserDetails.setMyCustomField("TECC-custom-field");
        return customUserDetails;
    }

    @Override
    public Object render(UserDetails userDetails) {
        return "no renderer";
    }

}
