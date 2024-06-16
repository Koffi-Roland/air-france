
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommontype;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.provideindividualdata.siccommontype.siccommontype package. 
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

    private final static QName _InformationElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "InformationElement");
    private final static QName _SignatureElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "SignatureElement");
    private final static QName _BusinessErrorCommonElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "BusinessErrorCommonElement");
    private final static QName _InternalBusinessErrorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "InternalBusinessErrorElement");
    private final static QName _RequestorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "RequestorElement");
    private final static QName _RequestorV2Element_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "RequestorV2Element");
    private final static QName _WarningElement_QNAME = new QName("http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", "WarningElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.provideindividualdata.siccommontype.siccommontype
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Requestor }
     * 
     */
    public Requestor createRequestor() {
        return new Requestor();
    }

    /**
     * Create an instance of {@link RequestorV2 }
     * 
     */
    public RequestorV2 createRequestorV2() {
        return new RequestorV2();
    }

    /**
     * Create an instance of {@link Warning }
     * 
     */
    public Warning createWarning() {
        return new Warning();
    }

    /**
     * Create an instance of {@link InternalBusinessError }
     * 
     */
    public InternalBusinessError createInternalBusinessError() {
        return new InternalBusinessError();
    }

    /**
     * Create an instance of {@link BusinessErrorCommon }
     * 
     */
    public BusinessErrorCommon createBusinessErrorCommon() {
        return new BusinessErrorCommon();
    }

    /**
     * Create an instance of {@link Information }
     * 
     */
    public Information createInformation() {
        return new Information();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Information }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "InformationElement")
    public JAXBElement<Information> createInformationElement(Information value) {
        return new JAXBElement<Information>(_InformationElement_QNAME, Information.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Signature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "SignatureElement")
    public JAXBElement<Signature> createSignatureElement(Signature value) {
        return new JAXBElement<Signature>(_SignatureElement_QNAME, Signature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessErrorCommon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "BusinessErrorCommonElement")
    public JAXBElement<BusinessErrorCommon> createBusinessErrorCommonElement(BusinessErrorCommon value) {
        return new JAXBElement<BusinessErrorCommon>(_BusinessErrorCommonElement_QNAME, BusinessErrorCommon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternalBusinessError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "InternalBusinessErrorElement")
    public JAXBElement<InternalBusinessError> createInternalBusinessErrorElement(InternalBusinessError value) {
        return new JAXBElement<InternalBusinessError>(_InternalBusinessErrorElement_QNAME, InternalBusinessError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Requestor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "RequestorElement")
    public JAXBElement<Requestor> createRequestorElement(Requestor value) {
        return new JAXBElement<Requestor>(_RequestorElement_QNAME, Requestor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestorV2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "RequestorV2Element")
    public JAXBElement<RequestorV2> createRequestorV2Element(RequestorV2 value) {
        return new JAXBElement<RequestorV2>(_RequestorV2Element_QNAME, RequestorV2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Warning }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/SicCommonType-v1/xsd", name = "WarningElement")
    public JAXBElement<Warning> createWarningElement(Warning value) {
        return new JAXBElement<Warning>(_WarningElement_QNAME, Warning.class, null, value);
    }

}
