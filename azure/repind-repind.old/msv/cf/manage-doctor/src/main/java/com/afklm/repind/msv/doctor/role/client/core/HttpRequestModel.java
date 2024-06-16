package com.afklm.repind.msv.doctor.role.client.core;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.doctor.role.model.error.SystemError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class HttpRequestModel {
    private HttpHeaders httpHeaders = new HttpHeaders();

    public Map<String, String> returnUriVariables() throws BusinessException {
        try {
            return Arrays.stream(Introspector.getBeanInfo(this.getClass() , HttpRequestModel.class).getPropertyDescriptors()).
                    filter(pd -> Objects.nonNull(pd.getReadMethod()) && !"body".equalsIgnoreCase(pd.getName()))
                    .collect(Collectors.toMap(PropertyDescriptor::getName, pd -> {
                        try {
                            return pd.getReadMethod().invoke(this).toString();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }));
        } catch (Exception e) {
            throw new BusinessException(SystemError.API_CLIENT_CALL_ERROR, e);
        }
    }
}
