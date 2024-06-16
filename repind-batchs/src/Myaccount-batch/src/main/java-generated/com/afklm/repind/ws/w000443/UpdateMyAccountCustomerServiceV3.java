
package com.afklm.repind.ws.w000443;

import com.afklm.repind.ws.w000443.data.schema591279.UpdateMyAccountCustomerRequestV3;
import com.afklm.repind.ws.w000443.data.schema591279.UpdateMyAccountCustomerResponseV3;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "UpdateMyAccountCustomerService-v3", targetNamespace = "http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerService-v3/wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.afklm.repind.ws.w000443.data.schema591279.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema576962.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema591280.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema552823.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema587190.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema864.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema588217.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema576961.ObjectFactory.class,
    com.afklm.repind.ws.w000443.data.schema571954.ObjectFactory.class
})
public interface UpdateMyAccountCustomerServiceV3 {


    /**
     * 
     * @param request
     * @return
     *     returns com.afklm.repind.ws.w000443.data.schema591279.UpdateMyAccountCustomerResponseV3
     * @throws SystemFault
     * @throws MsgBusinessFault
     */
    @WebMethod(action = "http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerService-v3/updateMyAccountCustomer")
    @WebResult(name = "UpdateMyAccountCustomerResponseV3Element", targetNamespace = "http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd", partName = "retour")
    public UpdateMyAccountCustomerResponseV3 updateMyAccountCustomer(
        @WebParam(name = "UpdateMyAccountCustomerRequestV3Element", targetNamespace = "http://www.af-klm.com/services/passenger/UpdateMyAccountCustomerType-v1/xsd", partName = "request")
        UpdateMyAccountCustomerRequestV3 request)
        throws MsgBusinessFault, SystemFault
    ;

}