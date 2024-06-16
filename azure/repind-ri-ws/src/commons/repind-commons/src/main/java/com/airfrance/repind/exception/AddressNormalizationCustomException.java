package com.airfrance.repind.exception;

import com.airfrance.ref.exception.AddressNormalizationException;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;

public class AddressNormalizationCustomException extends AddressNormalizationException {

    private PostalAddressResponseDTO postalAddressResponseDTO;

    public AddressNormalizationCustomException(String msg) {
        super(msg);
    }

    public AddressNormalizationCustomException(String msg, PostalAddressResponseDTO adr) {
        super(msg);
        postalAddressResponseDTO = adr;
    }

    public PostalAddressResponseDTO getPostalAddressResponseDTO() {
        return postalAddressResponseDTO;
    }

    public void setPostalAddressResponseDTO(PostalAddressResponseDTO postalAddressResponseDTO) {
        this.postalAddressResponseDTO = postalAddressResponseDTO;
    }
}
