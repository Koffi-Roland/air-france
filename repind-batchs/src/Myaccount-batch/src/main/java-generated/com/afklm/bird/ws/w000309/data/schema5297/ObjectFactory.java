
package com.afklm.bird.ws.w000309.data.schema5297;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.afklm.bird.ws.w000309.data.schema5297 package. 
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

    private final static QName _CommonsBIRDEntityWSDTOElement_QNAME = new QName("http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd", "CommonsBIRDEntityWSDTOElement");
    private final static QName _CommonsBIRDEntityCriteriaWSDTOElement_QNAME = new QName("http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd", "CommonsBIRDEntityCriteriaWSDTOElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.afklm.bird.ws.w000309.data.schema5297
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommonsBIRDEntityCriteriaWSDTO }
     * 
     */
    public CommonsBIRDEntityCriteriaWSDTO createCommonsBIRDEntityCriteriaWSDTO() {
        return new CommonsBIRDEntityCriteriaWSDTO();
    }

    /**
     * Create an instance of {@link CommonsBIRDEntityWSDTO }
     * 
     */
    public CommonsBIRDEntityWSDTO createCommonsBIRDEntityWSDTO() {
        return new CommonsBIRDEntityWSDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommonsBIRDEntityWSDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd", name = "CommonsBIRDEntityWSDTOElement")
    public JAXBElement<CommonsBIRDEntityWSDTO> createCommonsBIRDEntityWSDTOElement(CommonsBIRDEntityWSDTO value) {
        return new JAXBElement<CommonsBIRDEntityWSDTO>(_CommonsBIRDEntityWSDTOElement_QNAME, CommonsBIRDEntityWSDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommonsBIRDEntityCriteriaWSDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.af-klm.com/services/passenger/commonsBusinessData-v1/xsd", name = "CommonsBIRDEntityCriteriaWSDTOElement")
    public JAXBElement<CommonsBIRDEntityCriteriaWSDTO> createCommonsBIRDEntityCriteriaWSDTOElement(CommonsBIRDEntityCriteriaWSDTO value) {
        return new JAXBElement<CommonsBIRDEntityCriteriaWSDTO>(_CommonsBIRDEntityCriteriaWSDTOElement_QNAME, CommonsBIRDEntityCriteriaWSDTO.class, null, value);
    }

}
