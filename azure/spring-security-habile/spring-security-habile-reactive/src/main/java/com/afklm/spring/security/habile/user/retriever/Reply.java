package com.afklm.spring.security.habile.user.retriever;

import java.util.List;

/**
 * Interface providing the methods a w000479-v0X reply has to implement.
 * This allows to use polymorphism for the Reply stubs.
 */
public interface Reply {
    /**
     * Method returning the user's last name.
     *
     * @return a string containing the user's last name
     */
    String getLastName();

    /**
     * Method returning the user's first name.
     *
     * @return a string containing the user's first name
     */
    String getFirstName();

    /**
     * Method returning the user's profile list
     *
     * @return a list of string representing the user's profile set
     */
    List<String> getProfileList();

    /**
     * Method returning the user's email adress if it's available
     *
     * @return the user's email if it's available or an empty string
     */
    String getEmail();
}
