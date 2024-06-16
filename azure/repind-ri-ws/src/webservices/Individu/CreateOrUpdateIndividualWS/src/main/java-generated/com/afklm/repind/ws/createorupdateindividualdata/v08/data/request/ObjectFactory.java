
package com.afklm.repind.ws.createorupdateindividualdata.v08.data.request;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.sic.ws.createorupdateindividualdata.v08.data.request package. 
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

    private final static QName _UtfRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "UtfRequestElement");
    private final static QName _IndividualRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "IndividualRequestElement");
    private final static QName _ComunicationPreferencesRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "ComunicationPreferencesRequestElement");
    private final static QName _TelecomRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "TelecomRequestElement");
    private final static QName _DelegationDataElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "DelegationDataElement");
    private final static QName _PrefilledNumbersRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "PrefilledNumbersRequestElement");
    private final static QName _DelegatorElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "DelegatorElement");
    private final static QName _PostalAddressRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "PostalAddressRequestElement");
    private final static QName _ExternalIdentifierRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "ExternalIdentifierRequestElement");
    private final static QName _AccountDelegationDataRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "AccountDelegationDataRequestElement");
    private final static QName _DelegateElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "DelegateElement");
    private final static QName _AlertRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "AlertRequestElement");
    private final static QName _PreferenceRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "PreferenceRequestElement");
    private final static QName _EmailRequestElement_QNAME = new QName("http://www.af-klm.com/services/passenger/request-v8/xsd", "EmailRequestElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.sic.ws.createorupdateindividualdata.v08.data.request
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExternalIdentifierRequest }
     * 
     */
    public ExternalIdentifierRequest createExternalIdentifierRequest() {
        return new ExternalIdentifierRequest();
    }

    /**
     * Create an instance of {@link UtfRequest }
     * 
     */
    public UtfRequest createUtfRequest() {
        return new UtfRequest();
    }

    /**
     * Create an instance of {@link DelegationData }
     * 
     */
    public DelegationData createDelegationData() {
        return new DelegationData();
    }

    /**
     * Create an instance of {@link AlertRequest }
     * 
     */
    public AlertRequest createAlertRequest() {
        return new AlertRequest();
    }

    /**
     * Create an instance of {@link AccountDelegationDataRequest }
     * 
     */
    public AccountDelegationDataRequest createAccountDelegationDataRequest() {
        return new AccountDelegationDataRequest();
    }

    /**
     * Create an instance of {@link PrefilledNumbersRequest }
     * 
     */
    public PrefilledNumbersRequest createPrefilledNumbersRequest() {
        return new PrefilledNumbersRequest();
    }

    /**
     * Create an instance of {@link Delegator }
     * 
     */
    public Delegator createDelegator() {
        return new Delegator();
    }

    /**
     * Create an instance of {@link PreferenceRequest }
     * 
     */
    public PreferenceRequest createPreferenceRequest() {
        return new PreferenceRequest();
    }

    /**
     * Create an instance of {@link ComunicationPreferencesRequest }
     * 
     */
    public ComunicationPreferencesRequest createComunicationPreferencesRequest() {
        return new ComunicationPreferencesRequest();
    }

    /**
     * Create an instance of {@link PostalAddressRequest }
     * 
     */
    public PostalAddressRequest createPostalAddressRequest() {
        return new PostalAddressRequest();
    }

    /**
     * Create an instance of {@link TelecomRequest }
     * 
     */
    public TelecomRequest createTelecomRequest() {
        return new TelecomRequest();
    }

    /**
     * Create an instance of {@link EmailRequest }
     * 
     */
    public EmailRequest createEmailRequest() {
        return new EmailRequest();
    }

    /**
     * Create an instance of {@link Delegate }
     * 
     */
    public Delegate createDelegate() {
        return new Delegate();
    }

    /**
     * Create an instance of {@link IndividualRequest }
     * 
     */
    public IndividualRequest createIndividualRequest() {
        return new IndividualRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UtfRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "UtfRequestElement")
    public JAXBElement<UtfRequest> createUtfRequestElement(UtfRequest value) {
        return new JAXBElement<UtfRequest>(_UtfRequestElement_QNAME, UtfRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IndividualRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "IndividualRequestElement")
    public JAXBElement<IndividualRequest> createIndividualRequestElement(IndividualRequest value) {
        return new JAXBElement<IndividualRequest>(_IndividualRequestElement_QNAME, IndividualRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComunicationPreferencesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "ComunicationPreferencesRequestElement")
    public JAXBElement<ComunicationPreferencesRequest> createComunicationPreferencesRequestElement(ComunicationPreferencesRequest value) {
        return new JAXBElement<ComunicationPreferencesRequest>(_ComunicationPreferencesRequestElement_QNAME, ComunicationPreferencesRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TelecomRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "TelecomRequestElement")
    public JAXBElement<TelecomRequest> createTelecomRequestElement(TelecomRequest value) {
        return new JAXBElement<TelecomRequest>(_TelecomRequestElement_QNAME, TelecomRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DelegationData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "DelegationDataElement")
    public JAXBElement<DelegationData> createDelegationDataElement(DelegationData value) {
        return new JAXBElement<DelegationData>(_DelegationDataElement_QNAME, DelegationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrefilledNumbersRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "PrefilledNumbersRequestElement")
    public JAXBElement<PrefilledNumbersRequest> createPrefilledNumbersRequestElement(PrefilledNumbersRequest value) {
        return new JAXBElement<PrefilledNumbersRequest>(_PrefilledNumbersRequestElement_QNAME, PrefilledNumbersRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delegator }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "DelegatorElement")
    public JAXBElement<Delegator> createDelegatorElement(Delegator value) {
        return new JAXBElement<Delegator>(_DelegatorElement_QNAME, Delegator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PostalAddressRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "PostalAddressRequestElement")
    public JAXBElement<PostalAddressRequest> createPostalAddressRequestElement(PostalAddressRequest value) {
        return new JAXBElement<PostalAddressRequest>(_PostalAddressRequestElement_QNAME, PostalAddressRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExternalIdentifierRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "ExternalIdentifierRequestElement")
    public JAXBElement<ExternalIdentifierRequest> createExternalIdentifierRequestElement(ExternalIdentifierRequest value) {
        return new JAXBElement<ExternalIdentifierRequest>(_ExternalIdentifierRequestElement_QNAME, ExternalIdentifierRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountDelegationDataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "AccountDelegationDataRequestElement")
    public JAXBElement<AccountDelegationDataRequest> createAccountDelegationDataRequestElement(AccountDelegationDataRequest value) {
        return new JAXBElement<AccountDelegationDataRequest>(_AccountDelegationDataRequestElement_QNAME, AccountDelegationDataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delegate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "DelegateElement")
    public JAXBElement<Delegate> createDelegateElement(Delegate value) {
        return new JAXBElement<Delegate>(_DelegateElement_QNAME, Delegate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AlertRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "AlertRequestElement")
    public JAXBElement<AlertRequest> createAlertRequestElement(AlertRequest value) {
        return new JAXBElement<AlertRequest>(_AlertRequestElement_QNAME, AlertRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PreferenceRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "PreferenceRequestElement")
    public JAXBElement<PreferenceRequest> createPreferenceRequestElement(PreferenceRequest value) {
        return new JAXBElement<PreferenceRequest>(_PreferenceRequestElement_QNAME, PreferenceRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/request-v8/xsd", name = "EmailRequestElement")
    public JAXBElement<EmailRequest> createEmailRequestElement(EmailRequest value) {
        return new JAXBElement<EmailRequest>(_EmailRequestElement_QNAME, EmailRequest.class, null, value);
    }

}
