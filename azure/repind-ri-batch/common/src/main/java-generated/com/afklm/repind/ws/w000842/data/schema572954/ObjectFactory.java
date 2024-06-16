
package com.afklm.repind.ws.w000842.data.schema572954;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.repind.ws.w000842.data.schema572954 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateAProspectResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", "CreateAProspectResponseElement");
    private final static QName _BusinessErrorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", "BusinessErrorElement");
    private final static QName _CreateAProspectRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", "CreateAProspectRequestElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.repind.ws.w000842.data.schema572954
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateAProspectResponse }
     * 
     */
    public CreateAProspectResponse createCreateAProspectResponse() {
        return new CreateAProspectResponse();
    }

    /**
     * Create an instance of {@link BusinessError }
     * 
     */
    public BusinessError createBusinessError() {
        return new BusinessError();
    }

    /**
     * Create an instance of {@link CreateAProspectRequest }
     * 
     */
    public CreateAProspectRequest createCreateAProspectRequest() {
        return new CreateAProspectRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAProspectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", name = "CreateAProspectResponseElement")
    public JAXBElement<CreateAProspectResponse> createCreateAProspectResponseElement(CreateAProspectResponse value) {
        return new JAXBElement<CreateAProspectResponse>(_CreateAProspectResponseElement_QNAME, CreateAProspectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", name = "BusinessErrorElement")
    public JAXBElement<BusinessError> createBusinessErrorElement(BusinessError value) {
        return new JAXBElement<BusinessError>(_BusinessErrorElement_QNAME, BusinessError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAProspectRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/createAProspectDataType-v1/xsd", name = "CreateAProspectRequestElement")
    public JAXBElement<CreateAProspectRequest> createCreateAProspectRequestElement(CreateAProspectRequest value) {
        return new JAXBElement<CreateAProspectRequest>(_CreateAProspectRequestElement_QNAME, CreateAProspectRequest.class, null, value);
    }

}
