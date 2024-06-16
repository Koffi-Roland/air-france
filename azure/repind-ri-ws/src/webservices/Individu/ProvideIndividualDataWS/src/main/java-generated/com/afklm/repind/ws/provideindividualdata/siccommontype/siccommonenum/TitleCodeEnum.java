
package com.afklm.repind.ws.provideindividualdata.siccommontype.siccommonenum;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TitleCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TitleCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ADM"/>
 *     &lt;enumeration value="AMB"/>
 *     &lt;enumeration value="ARC"/>
 *     &lt;enumeration value="AVO"/>
 *     &lt;enumeration value="BAR"/>
 *     &lt;enumeration value="BRE"/>
 *     &lt;enumeration value="CAP"/>
 *     &lt;enumeration value="CAV"/>
 *     &lt;enumeration value="CHA"/>
 *     &lt;enumeration value="CJU"/>
 *     &lt;enumeration value="CNT"/>
 *     &lt;enumeration value="COL"/>
 *     &lt;enumeration value="COM"/>
 *     &lt;enumeration value="CON"/>
 *     &lt;enumeration value="COT"/>
 *     &lt;enumeration value="COW"/>
 *     &lt;enumeration value="DAM"/>
 *     &lt;enumeration value="DEA"/>
 *     &lt;enumeration value="DIP"/>
 *     &lt;enumeration value="DIR"/>
 *     &lt;enumeration value="DOC"/>
 *     &lt;enumeration value="DOE"/>
 *     &lt;enumeration value="DOT"/>
 *     &lt;enumeration value="DUC"/>
 *     &lt;enumeration value="EVE"/>
 *     &lt;enumeration value="FRE"/>
 *     &lt;enumeration value="GEN"/>
 *     &lt;enumeration value="GOU"/>
 *     &lt;enumeration value="HAD"/>
 *     &lt;enumeration value="HIN"/>
 *     &lt;enumeration value="HJR"/>
 *     &lt;enumeration value="HIH"/>
 *     &lt;enumeration value="HKR"/>
 *     &lt;enumeration value="HNR"/>
 *     &lt;enumeration value="HON"/>
 *     &lt;enumeration value="HRH"/>
 *     &lt;enumeration value="ING"/>
 *     &lt;enumeration value="JUG"/>
 *     &lt;enumeration value="LAD"/>
 *     &lt;enumeration value="LIE"/>
 *     &lt;enumeration value="LOR"/>
 *     &lt;enumeration value="MAI"/>
 *     &lt;enumeration value="MAJ"/>
 *     &lt;enumeration value="MAQ"/>
 *     &lt;enumeration value="MAR"/>
 *     &lt;enumeration value="MIN"/>
 *     &lt;enumeration value="MIS"/>
 *     &lt;enumeration value="MMM"/>
 *     &lt;enumeration value="MOG"/>
 *     &lt;enumeration value="MRR"/>
 *     &lt;enumeration value="MRS"/>
 *     &lt;enumeration value="MSS"/>
 *     &lt;enumeration value="NDA"/>
 *     &lt;enumeration value="NUO"/>
 *     &lt;enumeration value="PDG"/>
 *     &lt;enumeration value="PER"/>
 *     &lt;enumeration value="PRE"/>
 *     &lt;enumeration value="PRI"/>
 *     &lt;enumeration value="PRL"/>
 *     &lt;enumeration value="PRO"/>
 *     &lt;enumeration value="PRS"/>
 *     &lt;enumeration value="PRX"/>
 *     &lt;enumeration value="RAB"/>
 *     &lt;enumeration value="RAG"/>
 *     &lt;enumeration value="REM"/>
 *     &lt;enumeration value="REP"/>
 *     &lt;enumeration value="REV"/>
 *     &lt;enumeration value="SAE"/>
 *     &lt;enumeration value="SAL"/>
 *     &lt;enumeration value="SAP"/>
 *     &lt;enumeration value="SEN"/>
 *     &lt;enumeration value="SEX"/>
 *     &lt;enumeration value="SHE"/>
 *     &lt;enumeration value="SID"/>
 *     &lt;enumeration value="SIR"/>
 *     &lt;enumeration value="SOE"/>
 *     &lt;enumeration value="VCP"/>
 *     &lt;enumeration value="VIC"/>
 *     &lt;enumeration value="VIT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TitleCodeEnum", namespace = "http://www.af-klm.com/services/passenger/SicCommonEnum-v1/xsd")
@XmlEnum
public enum TitleCodeEnum {

    ADM,
    AMB,
    ARC,
    AVO,
    BAR,
    BRE,
    CAP,
    CAV,
    CHA,
    CJU,
    CNT,
    COL,
    COM,
    CON,
    COT,
    COW,
    DAM,
    DEA,
    DIP,
    DIR,
    DOC,
    DOE,
    DOT,
    DUC,
    EVE,
    FRE,
    GEN,
    GOU,
    HAD,
    HIN,
    HJR,
    HIH,
    HKR,
    HNR,
    HON,
    HRH,
    ING,
    JUG,
    LAD,
    LIE,
    LOR,
    MAI,
    MAJ,
    MAQ,
    MAR,
    MIN,
    MIS,
    MMM,
    MOG,
    MRR,
    MRS,
    MSS,
    NDA,
    NUO,
    PDG,
    PER,
    PRE,
    PRI,
    PRL,
    PRO,
    PRS,
    PRX,
    RAB,
    RAG,
    REM,
    REP,
    REV,
    SAE,
    SAL,
    SAP,
    SEN,
    SEX,
    SHE,
    SID,
    SIR,
    SOE,
    VCP,
    VIC,
    VIT;

    public String value() {
        return name();
    }

    public static TitleCodeEnum fromValue(String v) {
        return valueOf(v);
    }

}
