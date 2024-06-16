
package com.afklm.repind.ws.createorupdateindividualdata.v08.data;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.createorupdateindividualdata.v08.data package. 
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

    private final static QName _CreateUpdateIndividualRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/data-v8/xsd", "CreateUpdateIndividualRequestElement");
    private final static QName _CreateUpdateIndividualResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/data-v8/xsd", "CreateUpdateIndividualResponseElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.createorupdateindividualdata.v08.data
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateUpdateIndividualRequest }
     * 
     */
    public CreateUpdateIndividualRequest createCreateUpdateIndividualRequest() {
        return new CreateUpdateIndividualRequest();
    }

    /**
     * Create an instance of {@link CreateUpdateIndividualResponse }
     * 
     */
    public CreateUpdateIndividualResponse createCreateUpdateIndividualResponse() {
        return new CreateUpdateIndividualResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUpdateIndividualRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/data-v8/xsd", name = "CreateUpdateIndividualRequestElement")
    public JAXBElement<CreateUpdateIndividualRequest> createCreateUpdateIndividualRequestElement(CreateUpdateIndividualRequest value) {
        return new JAXBElement<CreateUpdateIndividualRequest>(_CreateUpdateIndividualRequestElement_QNAME, CreateUpdateIndividualRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUpdateIndividualResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/data-v8/xsd", name = "CreateUpdateIndividualResponseElement")
    public JAXBElement<CreateUpdateIndividualResponse> createCreateUpdateIndividualResponseElement(CreateUpdateIndividualResponse value) {
        return new JAXBElement<CreateUpdateIndividualResponse>(_CreateUpdateIndividualResponseElement_QNAME, CreateUpdateIndividualResponse.class, null, value);
    }

}
