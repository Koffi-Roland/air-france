
package com.afklm.repind.ws.w000842.data.schema571954;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.repind.ws.w000842.data.schema571954 package. 
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

    private final static QName _SignatureElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "SignatureElement");
    private final static QName _IdentificationDataElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "IdentificationDataElement");
    private final static QName _TelecomsElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "TelecomsElement");
    private final static QName _ContextDataElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "ContextDataElement");
    private final static QName _TimeInformationsElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "TimeInformationsElement");
    private final static QName _MarketLanguageElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "MarketLanguageElement");
    private final static QName _ReturnDetailsElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "ReturnDetailsElement");
    private final static QName _ProfileInformationElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "ProfileInformationElement");
    private final static QName _LocalizationDataElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "LocalizationDataElement");
    private final static QName _MediaElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "MediaElement");
    private final static QName _CommunicationPreferencesElement_QNAME = new QName("http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", "CommunicationPreferencesElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.repind.ws.w000842.data.schema571954
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdentificationData }
     * 
     */
    public IdentificationData createIdentificationData() {
        return new IdentificationData();
    }

    /**
     * Create an instance of {@link LocalizationData }
     * 
     */
    public LocalizationData createLocalizationData() {
        return new LocalizationData();
    }

    /**
     * Create an instance of {@link MarketLanguage }
     * 
     */
    public MarketLanguage createMarketLanguage() {
        return new MarketLanguage();
    }

    /**
     * Create an instance of {@link CommunicationPreferences }
     * 
     */
    public CommunicationPreferences createCommunicationPreferences() {
        return new CommunicationPreferences();
    }

    /**
     * Create an instance of {@link ContextData }
     * 
     */
    public ContextData createContextData() {
        return new ContextData();
    }

    /**
     * Create an instance of {@link ReturnDetails }
     * 
     */
    public ReturnDetails createReturnDetails() {
        return new ReturnDetails();
    }

    /**
     * Create an instance of {@link Media }
     * 
     */
    public Media createMedia() {
        return new Media();
    }

    /**
     * Create an instance of {@link TimeInformations }
     * 
     */
    public TimeInformations createTimeInformations() {
        return new TimeInformations();
    }

    /**
     * Create an instance of {@link Telecoms }
     * 
     */
    public Telecoms createTelecoms() {
        return new Telecoms();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link ProfileInformation }
     * 
     */
    public ProfileInformation createProfileInformation() {
        return new ProfileInformation();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Signature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "SignatureElement")
    public JAXBElement<Signature> createSignatureElement(Signature value) {
        return new JAXBElement<Signature>(_SignatureElement_QNAME, Signature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificationData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "IdentificationDataElement")
    public JAXBElement<IdentificationData> createIdentificationDataElement(IdentificationData value) {
        return new JAXBElement<IdentificationData>(_IdentificationDataElement_QNAME, IdentificationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Telecoms }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "TelecomsElement")
    public JAXBElement<Telecoms> createTelecomsElement(Telecoms value) {
        return new JAXBElement<Telecoms>(_TelecomsElement_QNAME, Telecoms.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContextData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "ContextDataElement")
    public JAXBElement<ContextData> createContextDataElement(ContextData value) {
        return new JAXBElement<ContextData>(_ContextDataElement_QNAME, ContextData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeInformations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "TimeInformationsElement")
    public JAXBElement<TimeInformations> createTimeInformationsElement(TimeInformations value) {
        return new JAXBElement<TimeInformations>(_TimeInformationsElement_QNAME, TimeInformations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MarketLanguage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "MarketLanguageElement")
    public JAXBElement<MarketLanguage> createMarketLanguageElement(MarketLanguage value) {
        return new JAXBElement<MarketLanguage>(_MarketLanguageElement_QNAME, MarketLanguage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "ReturnDetailsElement")
    public JAXBElement<ReturnDetails> createReturnDetailsElement(ReturnDetails value) {
        return new JAXBElement<ReturnDetails>(_ReturnDetailsElement_QNAME, ReturnDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProfileInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "ProfileInformationElement")
    public JAXBElement<ProfileInformation> createProfileInformationElement(ProfileInformation value) {
        return new JAXBElement<ProfileInformation>(_ProfileInformationElement_QNAME, ProfileInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocalizationData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "LocalizationDataElement")
    public JAXBElement<LocalizationData> createLocalizationDataElement(LocalizationData value) {
        return new JAXBElement<LocalizationData>(_LocalizationDataElement_QNAME, LocalizationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Media }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "MediaElement")
    public JAXBElement<Media> createMediaElement(Media value) {
        return new JAXBElement<Media>(_MediaElement_QNAME, Media.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommunicationPreferences }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/ProspectType-v1/xsd", name = "CommunicationPreferencesElement")
    public JAXBElement<CommunicationPreferences> createCommunicationPreferencesElement(CommunicationPreferences value) {
        return new JAXBElement<CommunicationPreferences>(_CommunicationPreferencesElement_QNAME, CommunicationPreferences.class, null, value);
    }

}
