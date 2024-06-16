
package com.afklm.repind.ws.createorupdateindividualdata.softcomputingtype;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.createorupdateindividualdata.softcomputingtype package. 
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

    private final static QName _SoftComputingResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd", "SoftComputingResponseElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.createorupdateindividualdata.softcomputingtype
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SoftComputingResponse }
     * 
     */
    public SoftComputingResponse createSoftComputingResponse() {
        return new SoftComputingResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SoftComputingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SoftComputingType-v1/xsd", name = "SoftComputingResponseElement")
    public JAXBElement<SoftComputingResponse> createSoftComputingResponseElement(SoftComputingResponse value) {
        return new JAXBElement<SoftComputingResponse>(_SoftComputingResponseElement_QNAME, SoftComputingResponse.class, null, value);
    }

}
