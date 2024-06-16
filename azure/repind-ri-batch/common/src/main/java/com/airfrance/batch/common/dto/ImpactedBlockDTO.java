package com.airfrance.batch.common.dto;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class ImpactedBlockDTO {

    
    private String blockName;
    
    private String blockIdentifier;
    
    private String site;
    
    private String signature;
    
    private String notificationType;

    public ImpactedBlockDTO()
    {
        
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockIdentifier() {
        return blockIdentifier;
    }

    public void setBlockIdentifier(String blockIdentifierd) {
        this.blockIdentifier = blockIdentifierd;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ImpactedBlockDTO other = (ImpactedBlockDTO) obj;
        Boolean test1 = blockName.equals(other.blockName);
        Boolean test2 = blockIdentifier.equals(other.blockIdentifier);
        Boolean test3 = (signature != null && other.signature != null) ? signature.equals(other.signature) : true;
        Boolean test4 = (site != null && other.site != null) ? site.equals(other.site) : true;
        Boolean test5 = notificationType.equals(other.notificationType);
        
        return test1 && test2 && test3 && test4 && test5;
    }
    
    // REPIND-260 : SONAR - Ajouter hashCode() ou Supprimer equals()
    public int hashCode() {
    	
    	HashCodeBuilder builder = new HashCodeBuilder(17, 31);
    	builder = builder.append(blockName);
    	builder = builder.append(blockIdentifier);
    	builder = builder.append(site);
    	builder = builder.append(signature);
    	builder = builder.append(notificationType);
    	
    	return builder.toHashCode();
    }
    
}
