
package com.afklm.bdm.ws.w000413.data.schema7312;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.bdm.ws.w000413.data.schema7312 package. 
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

    private final static QName _StoreMDResponseElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/store-v1/xsd", "StoreMDResponseElement");
    private final static QName _StoreMDRequestElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/store-v1/xsd", "StoreMDRequestElement");
    private final static QName _DeleteMDRequestElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/store-v1/xsd", "DeleteMDRequestElement");
    private final static QName _DeleteMDResponseElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/store-v1/xsd", "DeleteMDResponseElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.bdm.ws.w000413.data.schema7312
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StoreMDRequest }
     * 
     */
    public StoreMDRequest createStoreMDRequest() {
        return new StoreMDRequest();
    }

    /**
     * Create an instance of {@link DeleteMDRequest }
     * 
     */
    public DeleteMDRequest createDeleteMDRequest() {
        return new DeleteMDRequest();
    }

    /**
     * Create an instance of {@link DeleteMDResponse }
     * 
     */
    public DeleteMDResponse createDeleteMDResponse() {
        return new DeleteMDResponse();
    }

    /**
     * Create an instance of {@link StoreMDResponse }
     * 
     */
    public StoreMDResponse createStoreMDResponse() {
        return new StoreMDResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreMDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/store-v1/xsd", name = "StoreMDResponseElement")
    public JAXBElement<StoreMDResponse> createStoreMDResponseElement(StoreMDResponse value) {
        return new JAXBElement<StoreMDResponse>(_StoreMDResponseElement_QNAME, StoreMDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreMDRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/store-v1/xsd", name = "StoreMDRequestElement")
    public JAXBElement<StoreMDRequest> createStoreMDRequestElement(StoreMDRequest value) {
        return new JAXBElement<StoreMDRequest>(_StoreMDRequestElement_QNAME, StoreMDRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMDRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/store-v1/xsd", name = "DeleteMDRequestElement")
    public JAXBElement<DeleteMDRequest> createDeleteMDRequestElement(DeleteMDRequest value) {
        return new JAXBElement<DeleteMDRequest>(_DeleteMDRequestElement_QNAME, DeleteMDRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/store-v1/xsd", name = "DeleteMDResponseElement")
    public JAXBElement<DeleteMDResponse> createDeleteMDResponseElement(DeleteMDResponse value) {
        return new JAXBElement<DeleteMDResponse>(_DeleteMDResponseElement_QNAME, DeleteMDResponse.class, null, value);
    }

}
