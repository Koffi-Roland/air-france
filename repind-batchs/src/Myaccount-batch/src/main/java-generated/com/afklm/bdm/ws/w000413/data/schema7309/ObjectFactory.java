
package com.afklm.bdm.ws.w000413.data.schema7309;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.bdm.ws.w000413.data.schema7309 package. 
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

    private final static QName _PersonalInformationElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "PersonalInformationElement");
    private final static QName _TravelDocumentsElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "TravelDocumentsElement");
    private final static QName _TravelPreferencesElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "TravelPreferencesElement");
    private final static QName _TravelCompanionsElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "TravelCompanionsElement");
    private final static QName _EmergencyContactsElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "EmergencyContactsElement");
    private final static QName _ApisDataElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "ApisDataElement");
    private final static QName _TravelDocumentElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "TravelDocumentElement");
    private final static QName _MarketingDataElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "MarketingDataElement");
    private final static QName _EmergencyContactElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "EmergencyContactElement");
    private final static QName _MarketingDataBusinessFaultElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "MarketingDataBusinessFaultElement");
    private final static QName _TravelCompanionElement_QNAME = new QName("http://www.af-klm.com/services/marketingdata/common-v1/xsd", "TravelCompanionElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.bdm.ws.w000413.data.schema7309
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmergencyContacts }
     * 
     */
    public EmergencyContacts createEmergencyContacts() {
        return new EmergencyContacts();
    }

    /**
     * Create an instance of {@link EmergencyContact }
     * 
     */
    public EmergencyContact createEmergencyContact() {
        return new EmergencyContact();
    }

    /**
     * Create an instance of {@link TravelCompanion }
     * 
     */
    public TravelCompanion createTravelCompanion() {
        return new TravelCompanion();
    }

    /**
     * Create an instance of {@link TravelCompanions }
     * 
     */
    public TravelCompanions createTravelCompanions() {
        return new TravelCompanions();
    }

    /**
     * Create an instance of {@link TravelDocuments }
     * 
     */
    public TravelDocuments createTravelDocuments() {
        return new TravelDocuments();
    }

    /**
     * Create an instance of {@link ApisData }
     * 
     */
    public ApisData createApisData() {
        return new ApisData();
    }

    /**
     * Create an instance of {@link TravelDocument }
     * 
     */
    public TravelDocument createTravelDocument() {
        return new TravelDocument();
    }

    /**
     * Create an instance of {@link MarketingData }
     * 
     */
    public MarketingData createMarketingData() {
        return new MarketingData();
    }

    /**
     * Create an instance of {@link MarketingDataBusinessFault }
     * 
     */
    public MarketingDataBusinessFault createMarketingDataBusinessFault() {
        return new MarketingDataBusinessFault();
    }

    /**
     * Create an instance of {@link PersonalInformation }
     * 
     */
    public PersonalInformation createPersonalInformation() {
        return new PersonalInformation();
    }

    /**
     * Create an instance of {@link TravelPreferences }
     * 
     */
    public TravelPreferences createTravelPreferences() {
        return new TravelPreferences();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonalInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "PersonalInformationElement")
    public JAXBElement<PersonalInformation> createPersonalInformationElement(PersonalInformation value) {
        return new JAXBElement<PersonalInformation>(_PersonalInformationElement_QNAME, PersonalInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TravelDocuments }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "TravelDocumentsElement")
    public JAXBElement<TravelDocuments> createTravelDocumentsElement(TravelDocuments value) {
        return new JAXBElement<TravelDocuments>(_TravelDocumentsElement_QNAME, TravelDocuments.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TravelPreferences }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "TravelPreferencesElement")
    public JAXBElement<TravelPreferences> createTravelPreferencesElement(TravelPreferences value) {
        return new JAXBElement<TravelPreferences>(_TravelPreferencesElement_QNAME, TravelPreferences.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TravelCompanions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "TravelCompanionsElement")
    public JAXBElement<TravelCompanions> createTravelCompanionsElement(TravelCompanions value) {
        return new JAXBElement<TravelCompanions>(_TravelCompanionsElement_QNAME, TravelCompanions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmergencyContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "EmergencyContactsElement")
    public JAXBElement<EmergencyContacts> createEmergencyContactsElement(EmergencyContacts value) {
        return new JAXBElement<EmergencyContacts>(_EmergencyContactsElement_QNAME, EmergencyContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApisData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "ApisDataElement")
    public JAXBElement<ApisData> createApisDataElement(ApisData value) {
        return new JAXBElement<ApisData>(_ApisDataElement_QNAME, ApisData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TravelDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "TravelDocumentElement")
    public JAXBElement<TravelDocument> createTravelDocumentElement(TravelDocument value) {
        return new JAXBElement<TravelDocument>(_TravelDocumentElement_QNAME, TravelDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarketingData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "MarketingDataElement")
    public JAXBElement<MarketingData> createMarketingDataElement(MarketingData value) {
        return new JAXBElement<MarketingData>(_MarketingDataElement_QNAME, MarketingData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmergencyContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "EmergencyContactElement")
    public JAXBElement<EmergencyContact> createEmergencyContactElement(EmergencyContact value) {
        return new JAXBElement<EmergencyContact>(_EmergencyContactElement_QNAME, EmergencyContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarketingDataBusinessFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "MarketingDataBusinessFaultElement")
    public JAXBElement<MarketingDataBusinessFault> createMarketingDataBusinessFaultElement(MarketingDataBusinessFault value) {
        return new JAXBElement<MarketingDataBusinessFault>(_MarketingDataBusinessFaultElement_QNAME, MarketingDataBusinessFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TravelCompanion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/marketingdata/common-v1/xsd", name = "TravelCompanionElement")
    public JAXBElement<TravelCompanion> createTravelCompanionElement(TravelCompanion value) {
        return new JAXBElement<TravelCompanion>(_TravelCompanionElement_QNAME, TravelCompanion.class, null, value);
    }

}
