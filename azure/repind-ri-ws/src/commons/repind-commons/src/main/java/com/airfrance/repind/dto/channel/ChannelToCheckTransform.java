package com.airfrance.repind.dto.channel;

/*PROTECTED REGION ID(_dtMF_cTcOjPby13oHEwUrg i - Tr) ENABLED START*/

// add not generated imports here
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.channel.ChannelToCheck;

/*PROTECTED REGION END*/

/**
 * <p>Title : ChannelToCheckTransform.java</p>
 * transformation bo <-> dto pour un(e) ChannelToCheck
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ChannelToCheckTransform {

    /*PROTECTED REGION ID(_dtMF_cTcOjPby13oHEwUrg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private ChannelToCheckTransform() {
    }
    /**
     * dto -> bo pour un ChannelToCheck
     * @param channelToCheckDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ChannelToCheck dto2BoLight(ChannelToCheckDTO channelToCheckDTO) throws JrafDomainException {
        // instanciation du BO
    	ChannelToCheck channelToCheck = new ChannelToCheck();
        dto2BoLight(channelToCheckDTO, channelToCheck);

        // on retourne le BO
        return channelToCheck;
    }

    /**
     * dto -> bo pour un channelToCheck
     * @param channelToCheckDTO dto
     * @param channelToCheck bo
     */
    public static void dto2BoLight(ChannelToCheckDTO channelToCheckDTO, ChannelToCheck channelToCheck) {
        // property of ChannelToCheckDTO
    	channelToCheck.setId(channelToCheckDTO.getId());
    	channelToCheck.setChannel(channelToCheckDTO.getChannel());
    }

    /**
     * bo -> dto pour un channelToCheck
     * @param pChannelToCheck bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ChannelToCheckDTO bo2DtoLight(ChannelToCheck pChannelToCheck) throws JrafDomainException {
        // instanciation du DTO
    	ChannelToCheckDTO channelToCheckDTO = new ChannelToCheckDTO();
        bo2DtoLight(pChannelToCheck, channelToCheckDTO);
        // on retourne le dto
        return channelToCheckDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param channelToCheck bo
     * @param channelToCheckDTO dto
     */
    public static void bo2DtoLight(
    	ChannelToCheck channelToCheck,
        ChannelToCheckDTO channelToCheckDTO) {


        // simple properties
    	channelToCheckDTO.setId(channelToCheck.getId());
    	channelToCheckDTO.setChannel(channelToCheck.getChannel());


    }
    
    /*PROTECTED REGION ID(_dtMF_cTcOjPby13oHEwUrg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

