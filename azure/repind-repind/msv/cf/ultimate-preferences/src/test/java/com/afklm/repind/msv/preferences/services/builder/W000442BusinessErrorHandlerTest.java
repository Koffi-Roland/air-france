package com.afklm.repind.msv.preferences.services.builder;

import com.afklm.repind.msv.preferences.model.error.RestError;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.common.systemfault.v1.SystemFault;
import com.afklm.soa.stubs.w000442.v8_0_1.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd9.BusinessError;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd9.BusinessErrorBloc;
import com.afklm.soa.stubs.w000442.v8_0_1.xsd9.BusinessErrorCodeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class W000442BusinessErrorHandlerTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    void handleBusinessErrorExceptionTest() {
        BusinessError businessError = new BusinessError();
        businessError.setOtherErrorCode("errorCode");
        BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
        businessErrorBloc.setBusinessError(businessError);

        BusinessErrorBlocBusinessException exception = new BusinessErrorBlocBusinessException("businessError", businessErrorBloc);

        RestError restError2 = W000442BusinessErrorHandler.handleBusinessErrorException(exception);

        assert restError2 != null;
        Assertions.assertEquals("business.400.001.-1", restError2.getCode());


    }
    @Test
    void handleBusinessErrorExceptionTestForNull() {
        BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
        businessErrorBloc.setBusinessError(null);

        BusinessErrorBlocBusinessException exception = new BusinessErrorBlocBusinessException("businessError", businessErrorBloc);

        RestError restError2 = W000442BusinessErrorHandler.handleBusinessErrorException(exception);

        Assertions.assertNull(restError2);


    }

    @Test
    void handleBusinessErrorExceptionTestForNullErrorCode() {
        BusinessError businessError = new BusinessError();
        businessError.setOtherErrorCode(null);
        BusinessErrorBloc businessErrorBloc = new BusinessErrorBloc();
        businessErrorBloc.setBusinessError(businessError);

        BusinessErrorBlocBusinessException exception = new BusinessErrorBlocBusinessException("businessError", businessErrorBloc);

        RestError restError = W000442BusinessErrorHandler.handleBusinessErrorException(exception);

        assert restError != null;
        Assertions.assertEquals("business.400.001.-1", restError.getCode());


    }


    @Test
    void handleSystemException() {
        SystemFault faultInfo = new SystemFault();
        faultInfo.setErrorCode("codeError");
        SystemException exception = new SystemException("message", faultInfo);
        RestError restError = W000442BusinessErrorHandler.handleSystemException(exception);

        Assertions.assertEquals("codeError", restError.getCode());


    }



    @Test
    void handleBusinessErrorTestNull() {
        RestError restError = W000442BusinessErrorHandler.handleBusinessError(null);
        Assertions.assertNull(restError);

    }

    @Test
    void handleBusinessError() {
        BusinessError businessError = new BusinessError();
        businessError.setOtherErrorCode("errorCode");
        RestError restError = W000442BusinessErrorHandler.handleBusinessError(businessError);
        assert restError != null;
        Assertions.assertEquals("business.400.001.-1", restError.getCode());

    }

    @Test
    void handleSystemError() {
        SystemFault faultInfo = new SystemFault();
        faultInfo.setErrorCode("codeError");
        RestError restError = W000442BusinessErrorHandler.handleSystemError(faultInfo);

        Assertions.assertEquals("codeError", restError.getCode());

    }

    @Test
    void handleBusinessError2(){
        BusinessError businessError = new BusinessError();
        businessError.setErrorCode(BusinessErrorCodeEnum.ERROR_001);
        businessError.setOtherErrorCode(null);

        RestError restError = W000442BusinessErrorHandler.handleBusinessError(businessError);
        assert restError != null;
        Assertions.assertEquals("business.400.001.1", restError.getCode());

    }
}
