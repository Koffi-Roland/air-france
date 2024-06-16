package com.airfrance.repind.service.agency.searchagencyonmulticriteria.dto;

/**
 * TelecomBloc DTO
 * @author t950700
 *
 */
public class TelecomBlocDTO {

	/*===============================================*/
	/*             INSTANCE VARIABLES                */
	/*===============================================*/
	private TelecomDTO telecomDTO;
    private TelecomStandardizationDTO telecomStandardizationDTO;

    /*===============================================*/
	/*                CONSTRUCTORS                   */
	/*===============================================*/
    
    public TelecomBlocDTO() {
		super();
	}
    
    public TelecomBlocDTO(TelecomDTO telecomDTO,
			TelecomStandardizationDTO telecomStandardizationDTO) {
		super();
		this.telecomDTO = telecomDTO;
		this.telecomStandardizationDTO = telecomStandardizationDTO;
	}

    /*===============================================*/
	/*                ACCESSORS                      */
	/*===============================================*/
    public TelecomDTO getTelecomDTO() {
        return telecomDTO;
    }

	public void setTelecomDTO(TelecomDTO value) {
        this.telecomDTO = value;
    }

    public TelecomStandardizationDTO getTelecomStandardizationDTO() {
        return telecomStandardizationDTO;
    }

    public void setTelecomStandardizationDTO(TelecomStandardizationDTO value) {
        this.telecomStandardizationDTO = value;
    }
}
