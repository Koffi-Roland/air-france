
package com.afklm.bird.ws.w000311.data.schema6538;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GreatCircleDistanceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GreatCircleDistanceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="distanceKM" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="distanceSM" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="distanceNM" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GreatCircleDistanceResponse", propOrder = {
    "distanceKM",
    "distanceSM",
    "distanceNM"
})
public class GreatCircleDistanceResponse {

    protected int distanceKM;
    protected int distanceSM;
    protected int distanceNM;

    /**
     * Gets the value of the distanceKM property.
     * 
     */
    public int getDistanceKM() {
        return distanceKM;
    }

    /**
     * Sets the value of the distanceKM property.
     * 
     */
    public void setDistanceKM(int value) {
        this.distanceKM = value;
    }

    /**
     * Gets the value of the distanceSM property.
     * 
     */
    public int getDistanceSM() {
        return distanceSM;
    }

    /**
     * Sets the value of the distanceSM property.
     * 
     */
    public void setDistanceSM(int value) {
        this.distanceSM = value;
    }

    /**
     * Gets the value of the distanceNM property.
     * 
     */
    public int getDistanceNM() {
        return distanceNM;
    }

    /**
     * Sets the value of the distanceNM property.
     * 
     */
    public void setDistanceNM(int value) {
        this.distanceNM = value;
    }

}
