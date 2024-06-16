
package com.afklm.repind.ws.w000443;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "SystemFault", targetNamespace = "http://www.af-klm.com/services/common/SystemFault-v1/xsd")
public class SystemFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private com.afklm.repind.ws.w000443.data.schema864.SystemFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public SystemFault(String message, com.afklm.repind.ws.w000443.data.schema864.SystemFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public SystemFault(String message, com.afklm.repind.ws.w000443.data.schema864.SystemFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.afklm.repind.ws.w000443.data.schema864.SystemFault
     */
    public com.afklm.repind.ws.w000443.data.schema864.SystemFault getFaultInfo() {
        return faultInfo;
    }

}
