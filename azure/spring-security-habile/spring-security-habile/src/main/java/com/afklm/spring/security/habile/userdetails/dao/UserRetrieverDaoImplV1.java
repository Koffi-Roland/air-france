package com.afklm.spring.security.habile.userdetails.dao;

import static com.afklm.spring.security.habile.SpringSecurityHabileMessageCode.SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000479.v1.HblWsBusinessException;
import com.afklm.soa.stubs.w000479.v1.ProvideUserRightsAccessV10;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRQ;
import com.afklm.soa.stubs.w000479.v1.rights.ProvideUserRightsAccessRS;
import com.afklm.spring.security.habile.HabilePrincipal;

import jakarta.xml.ws.WebServiceException;

/**
 * Implementation of the {@link UserRetrieverDao} based on the w000479
 * Web Service provided by Habile.<br/>
 * It requires:
 * <ul>
 * <li>in SOAREPO the declaration of your application as a w000479 consumer</li>
 * <li>the integration of the AFKL SDFW framework as a R/R consumer</li>
 * <li>the declaration of a Bean implementing the {@link ProvideUserRightsAccessV10} interface</li>
 * </ul>
 * 
 * @author TECC
 *
 */
@Component("habileUserRetrieverDaoImplV1")
@ConditionalOnClass(ProvideUserRightsAccessV10.class)
@Primary
public class UserRetrieverDaoImplV1 implements UserRetrieverDao {

    @Autowired(required = false)
    protected ProvideUserRightsAccessV10 habileConsumer;

    @Override
    public HabilePrincipal getUser(String id, String smSession) {
        if (habileConsumer == null) {
            throw new UsernameNotFoundException(SS4H_MSG_RT_INCONSISTANCY_SM_USER_W000479.format());
        }
        HabilePrincipal principal;
        ProvideUserRightsAccessRQ provideUserRightsAccessRQ = new ProvideUserRightsAccessRQ();
        provideUserRightsAccessRQ.setUserId(id);
        try {
            ProvideUserRightsAccessRS response = habileConsumer.provideUserRightsAccess(provideUserRightsAccessRQ);
            principal = new HabilePrincipal(id, response.getFirstName(), response.getLastName(), response.getProfileList().getProfileName());
        } catch (HblWsBusinessException e) {
            throw new UsernameNotFoundException(String.format(WS_BIZ_ERROR_MESSAGE, id), e);
        } catch (SystemException | WebServiceException e) {
            throw new UsernameNotFoundException(String.format(WS_ERROR_MESSAGE, id), e);
        }
        return principal;
    }
}
