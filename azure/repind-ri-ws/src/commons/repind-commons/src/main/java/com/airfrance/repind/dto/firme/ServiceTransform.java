package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Service;
import com.airfrance.repind.entity.firme.ServiceLight;

/*PROTECTED REGION END*/

/**
 * <p>Title : ServiceTransform.java</p>
 * transformation bo <-> dto pour un(e) Service
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ServiceTransform {

    /*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ServiceTransform() {
    }
    /**
     * dto -> bo for a Service
     * @param serviceDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Service dto2BoLight(ServiceDTO serviceDTO) throws JrafDomainException {
        return (Service)PersonneMoraleTransform.dto2BoLight(serviceDTO);
    }

    /**
     * dto -> bo for a service
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param serviceDTO dto
     * @param service bo
     */
    public static void dto2BoLight(ServiceDTO serviceDTO, Service service) {
    
        /*PROTECTED REGION ID(dto2BoLight_tHH6kC02EeSfSooroMx0yQ) ENABLED START*/
        
        dto2BoLightImpl(serviceDTO,service);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a service
     * @param serviceDTO dto
     * @param service bo
     */
    private static void dto2BoLightImpl(ServiceDTO serviceDTO, Service service){
    
        // superclass property
        PersonneMoraleTransform.dto2BoLight(serviceDTO, service);
        // property of ServiceDTO
    
    }

    /**
     * bo -> dto for a service
     * @param service bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ServiceDTO bo2DtoLight(Service service) throws JrafDomainException {
        return (ServiceDTO)PersonneMoraleTransform.bo2DtoLight(service);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param service bo
     * @param serviceDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Service service,
        ServiceDTO serviceDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_tHH6kC02EeSfSooroMx0yQ) ENABLED START*/

        bo2DtoLightImpl(service, serviceDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param service bo
     * @param serviceDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Service service,
        ServiceDTO serviceDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLight(service, serviceDTO);

        // simple properties
    
    }
    
    
    /**
     * Transforms a BO to a DTO.
     * 
     * @param personneMoraleLightToConvert
     * @param personneMoraleDTO
     */
	public static void bo2DtoLight(ServiceLight serviceLight, ServiceDTO serviceDTO) {

		if (serviceLight != null) {
			// Make the conversion
	        PersonneMoraleTransform.bo2DtoLight(serviceLight, serviceDTO);
		}
	}
    
    /*PROTECTED REGION ID(_tHH6kC02EeSfSooroMx0yQ u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

