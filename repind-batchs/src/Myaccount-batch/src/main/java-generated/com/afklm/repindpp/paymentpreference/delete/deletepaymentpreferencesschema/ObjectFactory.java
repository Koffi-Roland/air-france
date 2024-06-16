
package com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema package.
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

    private final static QName _DeletePaymentPreferencesResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", "DeletePaymentPreferencesResponseElement");
    private final static QName _DeletePaymentPreferencesRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", "DeletePaymentPreferencesRequestElement");
    private final static QName _BusinessErrorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", "BusinessErrorElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.repindpp.paymentpreference.delete.deletepaymentpreferencesschema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeletePaymentPreferencesRequest }
     * 
     */
    public DeletePaymentPreferencesRequest createDeletePaymentPreferencesRequest() {
        return new DeletePaymentPreferencesRequest();
    }

    /**
     * Create an instance of {@link BusinessError }
     * 
     */
    public BusinessError createBusinessError() {
        return new BusinessError();
    }

    /**
     * Create an instance of {@link DeletePaymentPreferencesResponse }
     * 
     */
    public DeletePaymentPreferencesResponse createDeletePaymentPreferencesResponse() {
        return new DeletePaymentPreferencesResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePaymentPreferencesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", name = "DeletePaymentPreferencesResponseElement")
    public JAXBElement<DeletePaymentPreferencesResponse> createDeletePaymentPreferencesResponseElement(DeletePaymentPreferencesResponse value) {
        return new JAXBElement<DeletePaymentPreferencesResponse>(_DeletePaymentPreferencesResponseElement_QNAME, DeletePaymentPreferencesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePaymentPreferencesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", name = "DeletePaymentPreferencesRequestElement")
    public JAXBElement<DeletePaymentPreferencesRequest> createDeletePaymentPreferencesRequestElement(DeletePaymentPreferencesRequest value) {
        return new JAXBElement<DeletePaymentPreferencesRequest>(_DeletePaymentPreferencesRequestElement_QNAME, DeletePaymentPreferencesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/DeletePaymentPreferencesSchema-v1/xsd", name = "BusinessErrorElement")
    public JAXBElement<BusinessError> createBusinessErrorElement(BusinessError value) {
        return new JAXBElement<BusinessError>(_BusinessErrorElement_QNAME, BusinessError.class, null, value);
    }

}
