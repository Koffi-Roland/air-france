package com.afklm.rigui.mapper.address;

import com.afklm.rigui.client.dqe.RNVP1;
import com.afklm.rigui.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import org.apache.logging.log4j.util.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostalAddressMapper {

    static final int MAX_FIELD_LENGTH = 50;

    PostalAddressMapper INSTANCE = Mappers.getMapper(PostalAddressMapper.class);

    @Mappings({
            @Mapping(source = "codePostal", target = "postalAddressContent.zipCode"),
            @Mapping(source = "localite", target = "postalAddressContent.city"),
            @Mapping(source = "complement", target = "postalAddressContent.complementSends"),
            @Mapping(source = "adresse", target = "postalAddressContent.streetNumber"),
            @Mapping(source = "dqECodeDetail", target = "softComputingResponse.errorNumber"),
            @Mapping(source = "dqELibErreur", target = "softComputingResponse.errorLabel"),
            @Mapping(source = "pays", target = "postalAddressContent.countryCode"),
            @Mapping(source = "lieuDit", target = "postalAddressContent.saidPlace"),
            @Mapping(source = "complement", target = "softComputingResponse.adrMailingL1"),
            @Mapping(source = "adresse", target = "softComputingResponse.adrMailingL2"),
            @Mapping(expression = "java(mapAdrMailingL3(rNVP1.getListeNumero()))", target = "softComputingResponse.adrMailingL3"),
            @Mapping(source = "codePostal", target = "softComputingResponse.adrMailingL4"),
            @Mapping(source = "localite", target = "softComputingResponse.adrMailingL5"),
            @Mapping(source = "province", target = "softComputingResponse.adrMailingL6")
    })
    PostalAddressResponseDTO mapRNVP1ToPostalAddressResponseDTO(RNVP1 rnvp1);

    default String mapAdrMailingL3(String listNumero) {
        if (!Strings.isEmpty(listNumero)) {
            return (listNumero.length() < MAX_FIELD_LENGTH ? listNumero : listNumero.substring(0, MAX_FIELD_LENGTH));
        }
        return "";
    }
}
