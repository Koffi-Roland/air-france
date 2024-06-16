package com.afklm.rigui.entity.environnement;

/*PROTECTED REGION ID(_Bi1wEDVcEeGby45oHEwUrg i) ENABLED START*/

// add not generated imports here

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*PROTECTED REGION END*/


/**
 * <p>Title : Variables.java</p>
 * BO Variables
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Entity
@Table(name="ENV_VAR")
public class Variables implements Serializable {

    /*PROTECTED REGION ID(serialUID _Bi1wEDVcEeGby45oHEwUrg) ENABLED START*/
    /**
     * Determines if a de-serialized file is compatible with this class.
     *
     * Maintainers must change this value if and only if the new version
     * of this class is not compatible with old versions. See Sun docs
     * for <a href=http://java.sun.com/j2se/1.5.0/docs/guide/serialization
     * /serialization/spec/class.html#4100> details. </a>
     *
     * Not necessary to include in first version of the class, but
     * included here as a reminder of its importance.
     */
    private static final long serialVersionUID = 1L;
    /*PROTECTED REGION END*/


    /**
     * envKey
     */
    @Id
    @Column(name="ENVKEY")
    private String envKey;


    /**
     * envValue
     */
    @Column(name="ENVVALUE")
    private String envValue;

    /*PROTECTED REGION ID(_Bi1wEDVcEeGby45oHEwUrg u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/


    /**
     * default constructor 
     */
    public Variables() {
    }


    /**
     * full constructor
     * @param pEnvKey envKey
     * @param pEnvValue envValue
     */
    public Variables(String pEnvKey, String pEnvValue) {
        this.envKey = pEnvKey;
        this.envValue = pEnvValue;
    }

    /**
     *
     * @return envKey
     */
    public String getEnvKey() {
        return this.envKey;
    }

    /**
     *
     * @param pEnvKey envKey value
     */
    public void setEnvKey(String pEnvKey) {
        this.envKey = pEnvKey;
    }

    /**
     *
     * @return envValue
     */
    public String getEnvValue() {
        return this.envValue;
    }

    /**
     *
     * @param pEnvValue envValue value
     */
    public void setEnvValue(String pEnvValue) {
        this.envValue = pEnvValue;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Bi1wEDVcEeGby45oHEwUrg) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
                .append("envKey", getEnvKey())
                .append("envValue", getEnvValue())
                .toString();
    }



    /*PROTECTED REGION ID(equals hash _Bi1wEDVcEeGby45oHEwUrg) ENABLED START*/

    /**
     * {@inheritDoc}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Variables other = (Variables) obj;

        // TODO: writes or generates equals method

        return super.equals(other);
    }

    /**
     * {@inheritDoc}
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();

        // TODO: writes or generates hashcode method

        return result;
    }

    /*PROTECTED REGION END*/

    /*PROTECTED REGION ID(_Bi1wEDVcEeGby45oHEwUrg u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
