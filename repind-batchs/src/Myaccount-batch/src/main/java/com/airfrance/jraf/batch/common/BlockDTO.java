package com.airfrance.jraf.batch.common;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class BlockDTO {

    private Long id;

    private String name;
    
    private String identifier;
    
    private String notificationType;

    public BlockDTO()
    {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
        BlockDTO other = (BlockDTO) obj;
        Boolean test1 = name.equals(other.name);
        Boolean test2 = identifier.equals(other.identifier);
        Boolean test3 = notificationType.equals(other.notificationType);
        
        return test1 && test2 && test3;
    }
    
    // REPIND-260 : SONAR - Ajouter hashCode() ou Supprimer equals()
    public int hashCode() {
    	
    	HashCodeBuilder builder = new HashCodeBuilder(17, 31);
    	builder = builder.append(name);
    	builder = builder.append(identifier);
    	builder = builder.append(notificationType);
    	
    	return builder.toHashCode();
    }
    
}
