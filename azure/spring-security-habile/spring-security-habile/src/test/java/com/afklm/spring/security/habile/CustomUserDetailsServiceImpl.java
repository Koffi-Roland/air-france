package com.afklm.spring.security.habile;

import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Profile("customUserServiceDetailsServiceConfig")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Override
    public HabileUserDetails enrichUserDetails(HabileUserDetails userDetails) {
        CustomHabileUserDetails customHUD = new CustomHabileUserDetails(userDetails);
        customHUD.setJunitCustomField("TECCSE JUNIT CUSTOM FIELD");
        return customHUD;
    }

    @Override
    public Object render(UserDetails userDetails) {
        MyUserResource userResource = new MyUserResource();
        userResource.setUsername(userDetails.getUsername());
        userResource.setCustomField(((CustomHabileUserDetails) userDetails).getJunitCustomField());
        return userResource;
    }
}

class CustomHabileUserDetails extends HabileUserDetails {

    private static final long serialVersionUID = 1L;
    private String junitCustomField;

    public CustomHabileUserDetails(HabileUserDetails habileUserDetails) {
        super(habileUserDetails.getUsername(),
            habileUserDetails.getLastname(),
            habileUserDetails.getFirstname(),
            habileUserDetails.getEmail(),
            habileUserDetails.getAuthorities(),
            null);
    }

    public String getJunitCustomField() {
        return junitCustomField;
    }

    public void setJunitCustomField(String junitCustomField) {
        this.junitCustomField = junitCustomField;
    }
}

class MyUserResource {
    private String username;
    private String customField;

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}