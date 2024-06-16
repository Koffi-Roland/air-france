package com.afklm.spring.security.habile.proxy.security;

/**
 * Habile proxy state
 * 
 * Used to mimic the different states of Habile proxy
 * 
 * @author M405991
 *
 */
public enum HabileState {
    /** Indicates that a login is in progress */
    LOGIN,
    /** Indicates that a user is logged in */
    LOGGED,
    /** Indicates that a logout was done */
    LOGOUT,
    /** Indicates that session expired */
    EXPIRED
}
