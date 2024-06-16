package com.airfrance.repind.dto.individu;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;



/**
 * <p>Title : WarningResponseDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2018</p>
 * <p>Company : AIRFRANCE</p>
 */
public class WarningResponseDTO  {
        
    /**
     * warning
     */
    protected List<WarningDTO> warning;
    
    /** 
     * default constructor 
     */
    public WarningResponseDTO() {
    	warning = new ArrayList<WarningDTO>();
    }
    
    
    /** 
     * full constructor
     * @param pWarning warning
     */
    public WarningResponseDTO(List<WarningDTO> pWarning) {
        this.warning = pWarning;
    }

    /**
     *
     * @return warning
     */
    public List<WarningDTO> getWarning() {
        return this.warning;
    }

    /**
     *
     * @param pWarning warning value
     */
    public void setWarning(List<WarningDTO> pWarning) {
        this.warning = pWarning;
    }

    /** 
     * add a list of warnings
     * @param warningToAdd
     */
    public void addAll(List<WarningDTO> warningToAdd) {
    	this.warning.addAll(warningToAdd);
    }
    
    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        result = toStringImpl();
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("warning", getWarning())
            .toString();
    }

}
