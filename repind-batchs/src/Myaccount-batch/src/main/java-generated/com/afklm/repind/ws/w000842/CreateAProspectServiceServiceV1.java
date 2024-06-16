
package com.afklm.repind.ws.w000842;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "CreateAProspectServiceService-v1", targetNamespace = "http://www.af-klm.com/services/passenger/CreateAProspectService-v1/wsdl", wsdlLocation = "http://www.af-klm.com/passenger_CreateAProspect-v01.wsdl")
public class CreateAProspectServiceServiceV1
    extends Service
{

    private final static URL CREATEAPROSPECTSERVICESERVICEV1_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.afklm.repind.ws.w000842.CreateAProspectServiceServiceV1 .class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.afklm.repind.ws.w000842.CreateAProspectServiceServiceV1 .class.getResource(".");
            url = new URL(baseUrl, "http://www.af-klm.com/passenger_CreateAProspect-v01.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://www.af-klm.com/passenger_CreateAProspect-v01.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        CREATEAPROSPECTSERVICESERVICEV1_WSDL_LOCATION = url;
    }

    public CreateAProspectServiceServiceV1(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CreateAProspectServiceServiceV1() {
        super(CREATEAPROSPECTSERVICESERVICEV1_WSDL_LOCATION, new QName("http://www.af-klm.com/services/passenger/CreateAProspectService-v1/wsdl", "CreateAProspectServiceService-v1"));
    }

    /**
     * 
     * @return
     *     returns CreateAProspectServiceV1
     */
    @WebEndpoint(name = "CreateAProspectService-v1-soap11http")
    public CreateAProspectServiceV1 getCreateAProspectServiceV1Soap11Http() {
        return super.getPort(new QName("http://www.af-klm.com/services/passenger/CreateAProspectService-v1/wsdl", "CreateAProspectService-v1-soap11http"), CreateAProspectServiceV1.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CreateAProspectServiceV1
     */
    @WebEndpoint(name = "CreateAProspectService-v1-soap11http")
    public CreateAProspectServiceV1 getCreateAProspectServiceV1Soap11Http(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.af-klm.com/services/passenger/CreateAProspectService-v1/wsdl", "CreateAProspectService-v1-soap11http"), CreateAProspectServiceV1.class, features);
    }

}