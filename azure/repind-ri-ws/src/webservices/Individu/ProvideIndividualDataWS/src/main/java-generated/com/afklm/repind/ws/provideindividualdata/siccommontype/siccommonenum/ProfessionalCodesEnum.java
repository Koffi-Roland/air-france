
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfessionalCodesEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProfessionalCodesEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADC"/>
 *     &lt;enumeration value="ADM"/>
 *     &lt;enumeration value="ART"/>
 *     &lt;enumeration value="ASC"/>
 *     &lt;enumeration value="CAF"/>
 *     &lt;enumeration value="CAV"/>
 *     &lt;enumeration value="CDC"/>
 *     &lt;enumeration value="CDV"/>
 *     &lt;enumeration value="CIS"/>
 *     &lt;enumeration value="CSL"/>
 *     &lt;enumeration value="DAC"/>
 *     &lt;enumeration value="DAV"/>
 *     &lt;enumeration value="DCN"/>
 *     &lt;enumeration value="DCO"/>
 *     &lt;enumeration value="DEX"/>
 *     &lt;enumeration value="DFI"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="DMA"/>
 *     &lt;enumeration value="DVE"/>
 *     &lt;enumeration value="DZO"/>
 *     &lt;enumeration value="GER"/>
 *     &lt;enumeration value="ING"/>
 *     &lt;enumeration value="PDH"/>
 *     &lt;enumeration value="PDR"/>
 *     &lt;enumeration value="RDT"/>
 *     &lt;enumeration value="SEC"/>
 *     &lt;enumeration value="GAP"/>
 *     &lt;enumeration value="SVR"/>
 *     &lt;enumeration value="SIG"/>
 *     &lt;enumeration value="VPR"/>
 *     &lt;enumeration value="CPA"/>
 *     &lt;enumeration value="TRA"/>
 *     &lt;enumeration value="SAL"/>
 *     &lt;enumeration value="KAM"/>
 *     &lt;enumeration value="AMR"/>
 *     &lt;enumeration value="RAM"/>
 *     &lt;enumeration value="TAM"/>
 *     &lt;enumeration value="AMT"/>
 *     &lt;enumeration value="ADS"/>
 *     &lt;enumeration value="CRC"/>
 *     &lt;enumeration value="HOR"/>
 *     &lt;enumeration value="AIR"/>
 *     &lt;enumeration value="IMP"/>
 *     &lt;enumeration value="IAE"/>
 *     &lt;enumeration value="OFM"/>
 *     &lt;enumeration value="NGV"/>
 *     &lt;enumeration value="FIC"/>
 *     &lt;enumeration value="PDG"/>
 *     &lt;enumeration value="PDT"/>
 *     &lt;enumeration value="MAS"/>
 *     &lt;enumeration value="BUY"/>
 *     &lt;enumeration value="PCO"/>
 *     &lt;enumeration value="CRE"/>
 *     &lt;enumeration value="REX"/>
 *     &lt;enumeration value="SGL"/>
 *     &lt;enumeration value="VRP"/>
 *     &lt;enumeration value="CVD"/>
 *     &lt;enumeration value="DRH"/>
 *     &lt;enumeration value="LIB"/>
 *     &lt;enumeration value="MED"/>
 *     &lt;enumeration value="OWN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProfessionalCodesEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum ProfessionalCodesEnum {

    ADC,
    ADM,
    ART,
    ASC,
    CAF,
    CAV,
    CDC,
    CDV,
    CIS,
    CSL,
    DAC,
    DAV,
    DCN,
    DCO,
    DEX,
    DFI,
    DIR,
    DMA,
    DVE,
    DZO,
    GER,
    ING,
    PDH,
    PDR,
    RDT,
    SEC,
    GAP,
    SVR,
    SIG,
    VPR,
    CPA,
    TRA,
    SAL,
    KAM,
    AMR,
    RAM,
    TAM,
    AMT,
    ADS,
    CRC,
    HOR,
    AIR,
    IMP,
    IAE,
    OFM,
    NGV,
    FIC,
    PDG,
    PDT,
    MAS,
    BUY,
    PCO,
    CRE,
    REX,
    SGL,
    VRP,
    CVD,
    DRH,
    LIB,
    MED,
    OWN;

    public String value() {
        return name();
    }

    public static ProfessionalCodesEnum fromValue(String v) {
        return valueOf(v);
    }

}
