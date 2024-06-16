
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.response;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.createorupdateindividualdata.v08.data.response package. 
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

    private final static QName _InformationResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/response-v8/xsd", "InformationResponseElement");
    private final static QName _BusinessErrorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/response-v8/xsd", "BusinessErrorElement");
    private final static QName _PostalAddressResponseElement_QNAME = new QName("http://www.af-klm.com/services/passenger/response-v8/xsd", "PostalAddressResponseElement");
    private final static QName _BusinessErrorBlocElement_QNAME = new QName("http://www.af-klm.com/services/passenger/response-v8/xsd", "BusinessErrorBlocElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.createorupdateindividualdata.v08.data.response
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InformationResponse }
     * 
     */
    public InformationResponse createInformationResponse() {
        return new InformationResponse();
    }

    /**
     * Create an instance of {@link BusinessError }
     * 
     */
    public BusinessError createBusinessError() {
        return new BusinessError();
    }

    /**
     * Create an instance of {@link PostalAddressResponse }
     * 
     */
    public PostalAddressResponse createPostalAddressResponse() {
        return new PostalAddressResponse();
    }

    /**
     * Create an instance of {@link BusinessErrorBloc }
     * 
     */
    public BusinessErrorBloc createBusinessErrorBloc() {
        return new BusinessErrorBloc();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InformationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/response-v8/xsd", name = "InformationResponseElement")
    public JAXBElement<InformationResponse> createInformationResponseElement(InformationResponse value) {
        return new JAXBElement<InformationResponse>(_InformationResponseElement_QNAME, InformationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/response-v8/xsd", name = "BusinessErrorElement")
    public JAXBElement<BusinessError> createBusinessErrorElement(BusinessError value) {
        return new JAXBElement<BusinessError>(_BusinessErrorElement_QNAME, BusinessError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostalAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/response-v8/xsd", name = "PostalAddressResponseElement")
    public JAXBElement<PostalAddressResponse> createPostalAddressResponseElement(PostalAddressResponse value) {
        return new JAXBElement<PostalAddressResponse>(_PostalAddressResponseElement_QNAME, PostalAddressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessErrorBloc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/response-v8/xsd", name = "BusinessErrorBlocElement")
    public JAXBElement<BusinessErrorBloc> createBusinessErrorBlocElement(BusinessErrorBloc value) {
        return new JAXBElement<BusinessErrorBloc>(_BusinessErrorBlocElement_QNAME, BusinessErrorBloc.class, null, value);
    }

}
