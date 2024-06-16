package com.afklm.repind.utils;

import com.afklm.soa.stubs.w000431.v3.BusinessException;
import com.afklm.soa.stubs.w000431.v3.data.BusinessError;
import com.afklm.soa.stubs.w000431.v3.data.BusinessErrorEnum;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.service.marketing.HandleCommunication;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.util.logging.Logger;

@Slf4j
public class EnrollMyAccountUtils {

    private EnrollMyAccountUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates an event to CRMPush to send an welcome email after a myAccount Enrollment
     * @param gin gin of enrolled customer
     * @param enrollData data from input
     * @param appContext application context
     */
    public static void callHandleCommunicationAccountEnrollService(String gin, MyAccountCustomerRequestDTO enrollData, ApplicationContext appContext) {
        try {
            // REPIND-1617 : quick fix for CRM_PUSH
            String signature = enrollData.getSignature().getSignature();
            if(StringUtils.isNotBlank(signature)){
                signature = StringUtils.substring(signature.trim(), 0, 10);
            }

            // Call HandleCommunication Service
            HandleCommunication handleComm = new HandleCommunication(appContext);
            handleComm.askHandleCommMyAccEnrol(gin, enrollData.getEmailIdentifier(), enrollData.getComType(), enrollData.getWebsite(), enrollData.getPointOfSell(), signature);
        } catch (Exception e) {
            log.error(LoggerUtils.buidErrorMessage(e), e);
        }
    }
}
