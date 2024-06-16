package com.afklm.spring.security.habile.userdetails.dao;

import com.afklm.spring.security.habile.HabilePrincipal;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interface defining the retrieval of a {@link HabilePrincipal} from its employee id.<br/>
 * Populated fields are:
 * <ul>
 * <li>userId (employee id)</li>
 * <li>lastname</li>
 * <li>firstname</li>
 * <li>list of profiles owned by the employee</li>
 * </ul>
 *
 * @author TECC
 */
public interface UserRetrieverDao {

    /**
     * Technical error message accessing userinfo endpoint
     */
    String PING_ERROR_MESSAGE = "Error accessing ping authentication provider for token: '%s'";

    /**
     * Technical error message
     */
    String WS_ERROR_MESSAGE = "Error accessing authentication provider user:'%s'";

    /**
     * Biz error message
     */
    String WS_BIZ_ERROR_MESSAGE = "Error authentication '%s'";

    /**
     * Returns the HabilePrincipal
     *
     * @param employeeId employeeId
     * @param smSession may be unused
     * @return habile principal
     * @throws UsernameNotFoundException
     */
    HabilePrincipal getUser(String employeeId, String smSession) throws UsernameNotFoundException;
}
