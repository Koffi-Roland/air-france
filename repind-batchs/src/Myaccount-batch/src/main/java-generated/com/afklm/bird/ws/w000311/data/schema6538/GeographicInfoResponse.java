
package com.afklm.bird.ws.w000311.data.schema6538;

import com.afklm.bird.ws.w000311.data.schema5299.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GeographicInfoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeographicInfoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="currentTimeVariation" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}DT_TimeZoneVariation" minOccurs="0"/>
 *         &lt;element name="foundStations" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}StationWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundCities" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CityWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundCountries" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}CountryWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundIATAAreas" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}IATAAreaWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundTimeZoneExceptions" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}TimeZoneExceptionWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundStateProvinces" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}StateProvinceWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundTimeZones" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}TimeZoneWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="foundAFAreas" type="{http://www.af-klm.com/services/passenger/geographyBusinessData-v1/xsd}AFAreaWSDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeographicInfoResponse", propOrder = {
    "currentTimeVariation",
    "foundStations",
    "foundCities",
    "foundCountries",
    "foundIATAAreas",
    "foundTimeZoneExceptions",
    "foundStateProvinces",
    "foundTimeZones",
    "foundAFAreas"
})
public class GeographicInfoResponse {

    protected String currentTimeVariation;
    protected List<StationWSDTO> foundStations;
    protected List<CityWSDTO> foundCities;
    protected List<CountryWSDTO> foundCountries;
    protected List<IATAAreaWSDTO> foundIATAAreas;
    protected List<TimeZoneExceptionWSDTO> foundTimeZoneExceptions;
    protected List<StateProvinceWSDTO> foundStateProvinces;
    protected List<TimeZoneWSDTO> foundTimeZones;
    protected List<AFAreaWSDTO> foundAFAreas;

    /**
     * Gets the value of the currentTimeVariation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentTimeVariation() {
        return currentTimeVariation;
    }

    /**
     * Sets the value of the currentTimeVariation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentTimeVariation(String value) {
        this.currentTimeVariation = value;
    }

    /**
     * Gets the value of the foundStations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundStations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundStations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StationWSDTO }
     * 
     * 
     */
    public List<StationWSDTO> getFoundStations() {
        if (foundStations == null) {
            foundStations = new ArrayList<StationWSDTO>();
        }
        return this.foundStations;
    }

    /**
     * Gets the value of the foundCities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundCities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundCities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CityWSDTO }
     * 
     * 
     */
    public List<CityWSDTO> getFoundCities() {
        if (foundCities == null) {
            foundCities = new ArrayList<CityWSDTO>();
        }
        return this.foundCities;
    }

    /**
     * Gets the value of the foundCountries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundCountries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundCountries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CountryWSDTO }
     * 
     * 
     */
    public List<CountryWSDTO> getFoundCountries() {
        if (foundCountries == null) {
            foundCountries = new ArrayList<CountryWSDTO>();
        }
        return this.foundCountries;
    }

    /**
     * Gets the value of the foundIATAAreas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundIATAAreas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundIATAAreas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IATAAreaWSDTO }
     * 
     * 
     */
    public List<IATAAreaWSDTO> getFoundIATAAreas() {
        if (foundIATAAreas == null) {
            foundIATAAreas = new ArrayList<IATAAreaWSDTO>();
        }
        return this.foundIATAAreas;
    }

    /**
     * Gets the value of the foundTimeZoneExceptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundTimeZoneExceptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundTimeZoneExceptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeZoneExceptionWSDTO }
     * 
     * 
     */
    public List<TimeZoneExceptionWSDTO> getFoundTimeZoneExceptions() {
        if (foundTimeZoneExceptions == null) {
            foundTimeZoneExceptions = new ArrayList<TimeZoneExceptionWSDTO>();
        }
        return this.foundTimeZoneExceptions;
    }

    /**
     * Gets the value of the foundStateProvinces property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundStateProvinces property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundStateProvinces().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StateProvinceWSDTO }
     * 
     * 
     */
    public List<StateProvinceWSDTO> getFoundStateProvinces() {
        if (foundStateProvinces == null) {
            foundStateProvinces = new ArrayList<StateProvinceWSDTO>();
        }
        return this.foundStateProvinces;
    }

    /**
     * Gets the value of the foundTimeZones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundTimeZones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundTimeZones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimeZoneWSDTO }
     * 
     * 
     */
    public List<TimeZoneWSDTO> getFoundTimeZones() {
        if (foundTimeZones == null) {
            foundTimeZones = new ArrayList<TimeZoneWSDTO>();
        }
        return this.foundTimeZones;
    }

    /**
     * Gets the value of the foundAFAreas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the foundAFAreas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFoundAFAreas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AFAreaWSDTO }
     * 
     * 
     */
    public List<AFAreaWSDTO> getFoundAFAreas() {
        if (foundAFAreas == null) {
            foundAFAreas = new ArrayList<AFAreaWSDTO>();
        }
        return this.foundAFAreas;
    }

}
