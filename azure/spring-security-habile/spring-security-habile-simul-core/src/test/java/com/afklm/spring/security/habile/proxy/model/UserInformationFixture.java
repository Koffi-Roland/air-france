package com.afklm.spring.security.habile.proxy.model;

public class UserInformationFixture {

    public static UserInformation user() {
        UserInformation user = new UserInformation();
        user.setUserId("junit-userId");
        user.setFirstName("junit-userId");
        user.setLastName("junit-lastName");
        user.setEmail("junit-email");
        user.getProfiles().add("junit-profile1");
        user.getProfiles().add("junit-profile2");

        return user;
    }
}
