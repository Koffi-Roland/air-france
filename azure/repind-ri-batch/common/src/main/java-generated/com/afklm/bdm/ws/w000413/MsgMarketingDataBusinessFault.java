
package com.afklm.bdm.ws.w000413;

import com.afklm.bdm.ws.w000413.data.schema7309.MarketingDataBusinessFault;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "MarketingDataBusinessFaultElement", targetNamespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd")
public class MsgMarketingDataBusinessFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private MarketingDataBusinessFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public MsgMarketingDataBusinessFault(String message, MarketingDataBusinessFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param message
     * @param cause
     */
    public MsgMarketingDataBusinessFault(String message, MarketingDataBusinessFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.afklm.bdm.ws.w000413.data.schema7309.MarketingDataBusinessFault
     */
    public MarketingDataBusinessFault getFaultInfo() {
        return faultInfo;
    }

}
