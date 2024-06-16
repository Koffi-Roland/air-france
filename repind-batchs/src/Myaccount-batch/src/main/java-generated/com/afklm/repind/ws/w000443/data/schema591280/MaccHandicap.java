
package com.afklm.repind.ws.w000443.data.schema591280;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MaccHandicap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaccHandicap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codeHCP1" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeHcpMatType" minOccurs="0"/>
 *         &lt;element name="codeHCP2" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeHcpMatType" minOccurs="0"/>
 *         &lt;element name="codeHCP3" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeHcpMatType" minOccurs="0"/>
 *         &lt;element name="medaCCFlag" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaCCDateStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaCCDateEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaCCAccomp" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaCCTxt" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}MedaTxtType" minOccurs="0"/>
 *         &lt;element name="medaMCFlag" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaMCDateStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaMCDateEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaMCAccomp" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaMCTxt" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}MedaTxtType" minOccurs="0"/>
 *         &lt;element name="medaLCFlag" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaLCDateStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaLCDateEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="medaLCAccomp" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="medaLCTxt" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}MedaTxtType" minOccurs="0"/>
 *         &lt;element name="codeMat1" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeHcpMatType" minOccurs="0"/>
 *         &lt;element name="length1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="width1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="height1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lengthPlie1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="widthPlie1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="heightPlie1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="weight1" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codeMat2" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}CodeHcpMatType" minOccurs="0"/>
 *         &lt;element name="length2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="width2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="height2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lengthPlie2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="widthPlie2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="heightPlie2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="weight2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="otherMat" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}OtherMatType" minOccurs="0"/>
 *         &lt;element name="dogGuideFlag" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="dogGuideBreed" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}DogBreedType" minOccurs="0"/>
 *         &lt;element name="dogGuideWeight" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="oxygFlag" type="{http://www.af-klm.com/services/passenger/SicMarketingType-v1/xsd}FlagType" minOccurs="0"/>
 *         &lt;element name="oxygOutput" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaccHandicap", propOrder = {
    "codeHCP1",
    "codeHCP2",
    "codeHCP3",
    "medaCCFlag",
    "medaCCDateStart",
    "medaCCDateEnd",
    "medaCCAccomp",
    "medaCCTxt",
    "medaMCFlag",
    "medaMCDateStart",
    "medaMCDateEnd",
    "medaMCAccomp",
    "medaMCTxt",
    "medaLCFlag",
    "medaLCDateStart",
    "medaLCDateEnd",
    "medaLCAccomp",
    "medaLCTxt",
    "codeMat1",
    "length1",
    "width1",
    "height1",
    "lengthPlie1",
    "widthPlie1",
    "heightPlie1",
    "weight1",
    "codeMat2",
    "length2",
    "width2",
    "height2",
    "lengthPlie2",
    "widthPlie2",
    "heightPlie2",
    "weight2",
    "otherMat",
    "dogGuideFlag",
    "dogGuideBreed",
    "dogGuideWeight",
    "oxygFlag",
    "oxygOutput"
})
public class MaccHandicap {

    protected String codeHCP1;
    protected String codeHCP2;
    protected String codeHCP3;
    protected String medaCCFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaCCDateStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaCCDateEnd;
    protected String medaCCAccomp;
    protected String medaCCTxt;
    protected String medaMCFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaMCDateStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaMCDateEnd;
    protected String medaMCAccomp;
    protected String medaMCTxt;
    protected String medaLCFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaLCDateStart;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar medaLCDateEnd;
    protected String medaLCAccomp;
    protected String medaLCTxt;
    protected String codeMat1;
    protected Integer length1;
    protected Integer width1;
    protected Integer height1;
    protected Integer lengthPlie1;
    protected Integer widthPlie1;
    protected Integer heightPlie1;
    protected Integer weight1;
    protected String codeMat2;
    protected Integer length2;
    protected Integer width2;
    protected Integer height2;
    protected Integer lengthPlie2;
    protected Integer widthPlie2;
    protected Integer heightPlie2;
    protected Integer weight2;
    protected String otherMat;
    protected String dogGuideFlag;
    protected String dogGuideBreed;
    protected Integer dogGuideWeight;
    protected String oxygFlag;
    protected Integer oxygOutput;

    /**
     * Gets the value of the codeHCP1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeHCP1() {
        return codeHCP1;
    }

    /**
     * Sets the value of the codeHCP1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeHCP1(String value) {
        this.codeHCP1 = value;
    }

    /**
     * Gets the value of the codeHCP2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeHCP2() {
        return codeHCP2;
    }

    /**
     * Sets the value of the codeHCP2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeHCP2(String value) {
        this.codeHCP2 = value;
    }

    /**
     * Gets the value of the codeHCP3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeHCP3() {
        return codeHCP3;
    }

    /**
     * Sets the value of the codeHCP3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeHCP3(String value) {
        this.codeHCP3 = value;
    }

    /**
     * Gets the value of the medaCCFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaCCFlag() {
        return medaCCFlag;
    }

    /**
     * Sets the value of the medaCCFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaCCFlag(String value) {
        this.medaCCFlag = value;
    }

    /**
     * Gets the value of the medaCCDateStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaCCDateStart() {
        return medaCCDateStart;
    }

    /**
     * Sets the value of the medaCCDateStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaCCDateStart(XMLGregorianCalendar value) {
        this.medaCCDateStart = value;
    }

    /**
     * Gets the value of the medaCCDateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaCCDateEnd() {
        return medaCCDateEnd;
    }

    /**
     * Sets the value of the medaCCDateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaCCDateEnd(XMLGregorianCalendar value) {
        this.medaCCDateEnd = value;
    }

    /**
     * Gets the value of the medaCCAccomp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaCCAccomp() {
        return medaCCAccomp;
    }

    /**
     * Sets the value of the medaCCAccomp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaCCAccomp(String value) {
        this.medaCCAccomp = value;
    }

    /**
     * Gets the value of the medaCCTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaCCTxt() {
        return medaCCTxt;
    }

    /**
     * Sets the value of the medaCCTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaCCTxt(String value) {
        this.medaCCTxt = value;
    }

    /**
     * Gets the value of the medaMCFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaMCFlag() {
        return medaMCFlag;
    }

    /**
     * Sets the value of the medaMCFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaMCFlag(String value) {
        this.medaMCFlag = value;
    }

    /**
     * Gets the value of the medaMCDateStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaMCDateStart() {
        return medaMCDateStart;
    }

    /**
     * Sets the value of the medaMCDateStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaMCDateStart(XMLGregorianCalendar value) {
        this.medaMCDateStart = value;
    }

    /**
     * Gets the value of the medaMCDateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaMCDateEnd() {
        return medaMCDateEnd;
    }

    /**
     * Sets the value of the medaMCDateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaMCDateEnd(XMLGregorianCalendar value) {
        this.medaMCDateEnd = value;
    }

    /**
     * Gets the value of the medaMCAccomp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaMCAccomp() {
        return medaMCAccomp;
    }

    /**
     * Sets the value of the medaMCAccomp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaMCAccomp(String value) {
        this.medaMCAccomp = value;
    }

    /**
     * Gets the value of the medaMCTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaMCTxt() {
        return medaMCTxt;
    }

    /**
     * Sets the value of the medaMCTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaMCTxt(String value) {
        this.medaMCTxt = value;
    }

    /**
     * Gets the value of the medaLCFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaLCFlag() {
        return medaLCFlag;
    }

    /**
     * Sets the value of the medaLCFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaLCFlag(String value) {
        this.medaLCFlag = value;
    }

    /**
     * Gets the value of the medaLCDateStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaLCDateStart() {
        return medaLCDateStart;
    }

    /**
     * Sets the value of the medaLCDateStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaLCDateStart(XMLGregorianCalendar value) {
        this.medaLCDateStart = value;
    }

    /**
     * Gets the value of the medaLCDateEnd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMedaLCDateEnd() {
        return medaLCDateEnd;
    }

    /**
     * Sets the value of the medaLCDateEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMedaLCDateEnd(XMLGregorianCalendar value) {
        this.medaLCDateEnd = value;
    }

    /**
     * Gets the value of the medaLCAccomp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaLCAccomp() {
        return medaLCAccomp;
    }

    /**
     * Sets the value of the medaLCAccomp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaLCAccomp(String value) {
        this.medaLCAccomp = value;
    }

    /**
     * Gets the value of the medaLCTxt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedaLCTxt() {
        return medaLCTxt;
    }

    /**
     * Sets the value of the medaLCTxt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedaLCTxt(String value) {
        this.medaLCTxt = value;
    }

    /**
     * Gets the value of the codeMat1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeMat1() {
        return codeMat1;
    }

    /**
     * Sets the value of the codeMat1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeMat1(String value) {
        this.codeMat1 = value;
    }

    /**
     * Gets the value of the length1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLength1() {
        return length1;
    }

    /**
     * Sets the value of the length1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLength1(Integer value) {
        this.length1 = value;
    }

    /**
     * Gets the value of the width1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth1() {
        return width1;
    }

    /**
     * Sets the value of the width1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth1(Integer value) {
        this.width1 = value;
    }

    /**
     * Gets the value of the height1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHeight1() {
        return height1;
    }

    /**
     * Sets the value of the height1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeight1(Integer value) {
        this.height1 = value;
    }

    /**
     * Gets the value of the lengthPlie1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLengthPlie1() {
        return lengthPlie1;
    }

    /**
     * Sets the value of the lengthPlie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLengthPlie1(Integer value) {
        this.lengthPlie1 = value;
    }

    /**
     * Gets the value of the widthPlie1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidthPlie1() {
        return widthPlie1;
    }

    /**
     * Sets the value of the widthPlie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidthPlie1(Integer value) {
        this.widthPlie1 = value;
    }

    /**
     * Gets the value of the heightPlie1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHeightPlie1() {
        return heightPlie1;
    }

    /**
     * Sets the value of the heightPlie1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeightPlie1(Integer value) {
        this.heightPlie1 = value;
    }

    /**
     * Gets the value of the weight1 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight1() {
        return weight1;
    }

    /**
     * Sets the value of the weight1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeight1(Integer value) {
        this.weight1 = value;
    }

    /**
     * Gets the value of the codeMat2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeMat2() {
        return codeMat2;
    }

    /**
     * Sets the value of the codeMat2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeMat2(String value) {
        this.codeMat2 = value;
    }

    /**
     * Gets the value of the length2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLength2() {
        return length2;
    }

    /**
     * Sets the value of the length2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLength2(Integer value) {
        this.length2 = value;
    }

    /**
     * Gets the value of the width2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidth2() {
        return width2;
    }

    /**
     * Sets the value of the width2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth2(Integer value) {
        this.width2 = value;
    }

    /**
     * Gets the value of the height2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHeight2() {
        return height2;
    }

    /**
     * Sets the value of the height2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeight2(Integer value) {
        this.height2 = value;
    }

    /**
     * Gets the value of the lengthPlie2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLengthPlie2() {
        return lengthPlie2;
    }

    /**
     * Sets the value of the lengthPlie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLengthPlie2(Integer value) {
        this.lengthPlie2 = value;
    }

    /**
     * Gets the value of the widthPlie2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWidthPlie2() {
        return widthPlie2;
    }

    /**
     * Sets the value of the widthPlie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidthPlie2(Integer value) {
        this.widthPlie2 = value;
    }

    /**
     * Gets the value of the heightPlie2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHeightPlie2() {
        return heightPlie2;
    }

    /**
     * Sets the value of the heightPlie2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHeightPlie2(Integer value) {
        this.heightPlie2 = value;
    }

    /**
     * Gets the value of the weight2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWeight2() {
        return weight2;
    }

    /**
     * Sets the value of the weight2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWeight2(Integer value) {
        this.weight2 = value;
    }

    /**
     * Gets the value of the otherMat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherMat() {
        return otherMat;
    }

    /**
     * Sets the value of the otherMat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherMat(String value) {
        this.otherMat = value;
    }

    /**
     * Gets the value of the dogGuideFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDogGuideFlag() {
        return dogGuideFlag;
    }

    /**
     * Sets the value of the dogGuideFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDogGuideFlag(String value) {
        this.dogGuideFlag = value;
    }

    /**
     * Gets the value of the dogGuideBreed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDogGuideBreed() {
        return dogGuideBreed;
    }

    /**
     * Sets the value of the dogGuideBreed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDogGuideBreed(String value) {
        this.dogGuideBreed = value;
    }

    /**
     * Gets the value of the dogGuideWeight property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDogGuideWeight() {
        return dogGuideWeight;
    }

    /**
     * Sets the value of the dogGuideWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDogGuideWeight(Integer value) {
        this.dogGuideWeight = value;
    }

    /**
     * Gets the value of the oxygFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOxygFlag() {
        return oxygFlag;
    }

    /**
     * Sets the value of the oxygFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOxygFlag(String value) {
        this.oxygFlag = value;
    }

    /**
     * Gets the value of the oxygOutput property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOxygOutput() {
        return oxygOutput;
    }

    /**
     * Sets the value of the oxygOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOxygOutput(Integer value) {
        this.oxygOutput = value;
    }

}
