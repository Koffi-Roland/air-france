package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_Qmf-0LdgEeCrCZp8iGNNVw i) ENABLED START*/

// add not generated imports here
import com.airfrance.repind.util.comparators.DateComparatorEnum;
import com.airfrance.repind.service.internal.unitservice.zone.ZoneServiceHelper;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneVenteDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class ZoneVenteDTO extends ZoneDecoupDTO  implements Serializable {
        
    /**
	 * 
	 */
	private static final long serialVersionUID = -1714530097620939237L;


	/**
     * zv0
     */
    private Integer zv0;
        
        
    /**
     * zv1
     */
    private Integer zv1;
        
        
    /**
     * zv2
     */
    private Integer zv2;
        
        
    /**
     * zv3
     */
    private Integer zv3;
        
        
    /**
     * zvAlpha
     */
    private String zvAlpha;
        
        
    /**
     * libZvAlpha
     */
    private String libZvAlpha;
        
        
    /**
     * codeMonnaie
     */
    private String codeMonnaie;
        
        
    /**
     * liensZc
     */
    private Set<LienZvZcDTO> liensZc;
        

    /*PROTECTED REGION ID(_Qmf-0LdgEeCrCZp8iGNNVw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public ZoneVenteDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pZv0 zv0
     * @param pZv1 zv1
     * @param pZv2 zv2
     * @param pZv3 zv3
     * @param pZvAlpha zvAlpha
     * @param pLibZvAlpha libZvAlpha
     * @param pCodeMonnaie codeMonnaie
     */
    public ZoneVenteDTO(Integer pZv0, Integer pZv1, Integer pZv2, Integer pZv3, String pZvAlpha, String pLibZvAlpha, String pCodeMonnaie) {
        this.zv0 = pZv0;
        this.zv1 = pZv1;
        this.zv2 = pZv2;
        this.zv3 = pZv3;
        this.zvAlpha = pZvAlpha;
        this.libZvAlpha = pLibZvAlpha;
        this.codeMonnaie = pCodeMonnaie;
    }

    /**
     *
     * @return codeMonnaie
     */
    public String getCodeMonnaie() {
        return this.codeMonnaie;
    }

    /**
     *
     * @param pCodeMonnaie codeMonnaie value
     */
    public void setCodeMonnaie(String pCodeMonnaie) {
        this.codeMonnaie = pCodeMonnaie;
    }

    /**
     *
     * @return libZvAlpha
     */
    public String getLibZvAlpha() {
        return this.libZvAlpha;
    }

    /**
     *
     * @param pLibZvAlpha libZvAlpha value
     */
    public void setLibZvAlpha(String pLibZvAlpha) {
        this.libZvAlpha = pLibZvAlpha;
    }

    /**
     *
     * @return liensZc
     */
    public Set<LienZvZcDTO> getLiensZc() {
        return this.liensZc;
    }

    /**
     *
     * @param pLiensZc liensZc value
     */
    public void setLiensZc(Set<LienZvZcDTO> pLiensZc) {
        this.liensZc = pLiensZc;
    }

    /**
     *
     * @return zv0
     */
    public Integer getZv0() {
        return this.zv0;
    }

    /**
     *
     * @param pZv0 zv0 value
     */
    public void setZv0(Integer pZv0) {
        this.zv0 = pZv0;
    }

    /**
     *
     * @return zv1
     */
    public Integer getZv1() {
        return this.zv1;
    }

    /**
     *
     * @param pZv1 zv1 value
     */
    public void setZv1(Integer pZv1) {
        this.zv1 = pZv1;
    }

    /**
     *
     * @return zv2
     */
    public Integer getZv2() {
        return this.zv2;
    }

    /**
     *
     * @param pZv2 zv2 value
     */
    public void setZv2(Integer pZv2) {
        this.zv2 = pZv2;
    }

    /**
     *
     * @return zv3
     */
    public Integer getZv3() {
        return this.zv3;
    }

    /**
     *
     * @param pZv3 zv3 value
     */
    public void setZv3(Integer pZv3) {
        this.zv3 = pZv3;
    }

    /**
     *
     * @return zvAlpha
     */
    public String getZvAlpha() {
        return this.zvAlpha;
    }

    /**
     *
     * @param pZvAlpha zvAlpha value
     */
    public void setZvAlpha(String pZvAlpha) {
        this.zvAlpha = pZvAlpha;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_Qmf-0LdgEeCrCZp8iGNNVw) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("zv0=").append(getZv0());
        buffer.append(",");
        buffer.append("zv1=").append(getZv1());
        buffer.append(",");
        buffer.append("zv2=").append(getZv2());
        buffer.append(",");
        buffer.append("zv3=").append(getZv3());
        buffer.append(",");
        buffer.append("zvAlpha=").append(getZvAlpha());
        buffer.append(",");
        buffer.append("libZvAlpha=").append(getLibZvAlpha());
        buffer.append(",");
        buffer.append("codeMonnaie=").append(getCodeMonnaie());
        buffer.append("]");
        return buffer.toString();
    }

    public boolean isActive(Date controlDate) throws ParseException {
        return ZoneServiceHelper.comparePeriod(this.getDateOuverture(),controlDate,this.getDateFermeture(),controlDate).equals(DateComparatorEnum.INCLUSE);
    }

}
