
package com.afklm.repind.ws.provideindividualdata.v07.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.provideindividualdata.v07.data package. 
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

    private final static QName _ProvideIndividualInformationRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/data-v7/xsd", "ProvideIndividualInformationRequestElement");
    private final static QName _ProvideIndividualInformationResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/data-v7/xsd", "ProvideIndividualInformationResponseElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.provideindividualdata.v07.data
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProvideIndividualInformationRequest }
     * 
     */
    public ProvideIndividualInformationRequest createProvideIndividualInformationRequest() {
        return new ProvideIndividualInformationRequest();
    }

    /**
     * Create an instance of {@link ProvideIndividualInformationResponse }
     * 
     */
    public ProvideIndividualInformationResponse createProvideIndividualInformationResponse() {
        return new ProvideIndividualInformationResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProvideIndividualInformationRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/data-v7/xsd", name = "ProvideIndividualInformationRequestElement")
    public JAXBElement<ProvideIndividualInformationRequest> createProvideIndividualInformationRequestElement(ProvideIndividualInformationRequest value) {
        return new JAXBElement<ProvideIndividualInformationRequest>(_ProvideIndividualInformationRequestElement_QNAME, ProvideIndividualInformationRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProvideIndividualInformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/data-v7/xsd", name = "ProvideIndividualInformationResponseElement")
    public JAXBElement<ProvideIndividualInformationResponse> createProvideIndividualInformationResponseElement(ProvideIndividualInformationResponse value) {
        return new JAXBElement<ProvideIndividualInformationResponse>(_ProvideIndividualInformationResponseElement_QNAME, ProvideIndividualInformationResponse.class, null, value);
    }

}
