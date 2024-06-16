package com.airfrance.repind.service.individu.internal.ut;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.CivilityEnum;
import com.airfrance.ref.type.GenderEnum;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class IndividuDSTest {

    private IndividuDS individuDS = new IndividuDS();

    @Test
    void setGenderAndCivility_GenderAndCivilityProvided() throws InvalidParameterException {
        IndividuDTO individuDTO = new IndividuDTO();
        individuDTO.setSexe(GenderEnum.FEMALE.toString());
        individuDTO.setCivilite(CivilityEnum.MRS.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.FEMALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MRS.toString(), individuDTO.getCivilite());
    }

    @Test
    void setGenderAndCivility_GenderAndCivilityNotProvided() throws InvalidParameterException {
        IndividuDTO individuDTO = new IndividuDTO();
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.UNKNOWN.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.M_.toString(), individuDTO.getCivilite());
    }

    @Test
    void setGenderAndCivility_CivilityProvided() throws InvalidParameterException {
        IndividuDTO individuDTO = new IndividuDTO();
        // if CIVILITY = "MR", then GENDER = "M"
        individuDTO.setCivilite(CivilityEnum.MISTER.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.MALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MISTER.toString(), individuDTO.getCivilite());

        // if CIVILITY = "MRS", "MS", "MISS", then GENDER = "F"
        individuDTO = new IndividuDTO();
        individuDTO.setCivilite(CivilityEnum.MRS.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.FEMALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MRS.toString(), individuDTO.getCivilite());
        individuDTO = new IndividuDTO();
        individuDTO.setCivilite(CivilityEnum.MS.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.FEMALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MS.toString(), individuDTO.getCivilite());
        individuDTO = new IndividuDTO();
        individuDTO.setCivilite(CivilityEnum.MISS.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.FEMALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MISS.toString(), individuDTO.getCivilite());

        // if CIVILITY = "M.", then GENDER = "U"
        individuDTO = new IndividuDTO();
        individuDTO.setCivilite(CivilityEnum.M_.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.UNKNOWN.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.M_.toString(), individuDTO.getCivilite());
    }

    @Test
    void setGenderAndCivility_GenderProvided() throws InvalidParameterException {
        IndividuDTO individuDTO = new IndividuDTO();
        // if GENDER = "M"
        individuDTO.setSexe(GenderEnum.MALE.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.MALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MISTER.toString(), individuDTO.getCivilite());

        // if GENDER = "F"
        individuDTO = new IndividuDTO();
        individuDTO.setSexe(GenderEnum.FEMALE.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.FEMALE.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.MRS.toString(), individuDTO.getCivilite());

        // if GENDER = "U"
        individuDTO = new IndividuDTO();
        individuDTO.setSexe(GenderEnum.UNKNOWN.toString());
        individuDS.setGenderAndCivility(individuDTO);
        assertEquals(GenderEnum.UNKNOWN.toString(), individuDTO.getSexe());
        assertEquals(CivilityEnum.M_.toString(), individuDTO.getCivilite());
    }

    @Test
    void updateGenderAndCivility_GenderProvided() throws InvalidParameterException {
        IndividuDTO individuDTOFromWS = new IndividuDTO();
        individuDTOFromWS.setSexe(GenderEnum.MALE.toString());

        IndividuDTO individuDTOFromDB = new IndividuDTO();
        individuDTOFromDB.setSexe(GenderEnum.FEMALE.toString());
        individuDTOFromDB.setCivilite(CivilityEnum.MRS.toString());

        individuDS.updateGenderAndCivility(individuDTOFromWS, individuDTOFromDB);

        assertEquals(GenderEnum.MALE.toString(), individuDTOFromWS.getSexe());
        assertEquals(CivilityEnum.MISTER.toString(), individuDTOFromWS.getCivilite());
    }

    @Test
    void updateGenderAndCivility_CivilityProvided() throws InvalidParameterException {
        IndividuDTO individuDTOFromWS = new IndividuDTO();
        individuDTOFromWS.setCivilite(CivilityEnum.MRS.toString());

        IndividuDTO individuDTOFromDB = new IndividuDTO();
        individuDTOFromDB.setSexe(GenderEnum.MALE.toString());
        individuDTOFromDB.setCivilite(CivilityEnum.MISTER.toString());

        individuDS.updateGenderAndCivility(individuDTOFromWS, individuDTOFromDB);

        assertEquals(GenderEnum.FEMALE.toString(), individuDTOFromWS.getSexe());
        assertEquals(CivilityEnum.MRS.toString(), individuDTOFromWS.getCivilite());
    }

    @Test
    void updateGenderAndCivility_GenderAndCivilityNotProvided() throws InvalidParameterException {
        IndividuDTO individuDTOFromWS = new IndividuDTO();

        IndividuDTO individuDTOFromDB = new IndividuDTO();
        individuDTOFromDB.setSexe(GenderEnum.FEMALE.toString());
        individuDTOFromDB.setCivilite(CivilityEnum.MRS.toString());

        individuDS.updateGenderAndCivility(individuDTOFromWS, individuDTOFromDB);

        assertEquals(GenderEnum.FEMALE.toString(), individuDTOFromWS.getSexe());
        assertEquals(CivilityEnum.MRS.toString(), individuDTOFromWS.getCivilite());
    }

    @Test
    void updateGenderAndCivility_GenderAndCivilityProvided() throws InvalidParameterException {
        IndividuDTO individuDTOFromWS = new IndividuDTO();
        individuDTOFromWS.setSexe(GenderEnum.MALE.toString());
        individuDTOFromWS.setCivilite(CivilityEnum.MISTER.toString());

        IndividuDTO individuDTOFromDB = new IndividuDTO();
        individuDTOFromDB.setSexe(GenderEnum.FEMALE.toString());
        individuDTOFromDB.setCivilite(CivilityEnum.MRS.toString());

        individuDS.updateGenderAndCivility(individuDTOFromWS, individuDTOFromDB);

        assertEquals(GenderEnum.MALE.toString(), individuDTOFromWS.getSexe());
        assertEquals(CivilityEnum.MISTER.toString(), individuDTOFromWS.getCivilite());
    }

    @Test
    void setGenderSharepoint_CivilityProvided() throws InvalidParameterException {
        IndividuDTO individuDTOMister = new IndividuDTO();
        individuDTOMister.setCivilite(CivilityEnum.MISTER.toString());

        IndividuDTO individuDTOMs = new IndividuDTO();
        individuDTOMs.setCivilite(CivilityEnum.MS.toString());

        IndividuDTO individuDTOMiss = new IndividuDTO();
        individuDTOMiss.setCivilite(CivilityEnum.MISS.toString());

        IndividuDTO individuDTOMrs = new IndividuDTO();
        individuDTOMrs.setCivilite(CivilityEnum.MRS.toString());

        IndividuDTO individuDTOMx = new IndividuDTO();
        individuDTOMx.setCivilite(CivilityEnum.MX.toString());

        individuDS.setGenderSharepoint(individuDTOMister);
        individuDS.setGenderSharepoint(individuDTOMs);
        individuDS.setGenderSharepoint(individuDTOMiss);
        individuDS.setGenderSharepoint(individuDTOMrs);
        individuDS.setGenderSharepoint(individuDTOMx);

        assertEquals(GenderEnum.MALE.toString(), individuDTOMister.getSexe());
        assertEquals(GenderEnum.FEMALE.toString(), individuDTOMs.getSexe());
        assertEquals(GenderEnum.FEMALE.toString(), individuDTOMiss.getSexe());
        assertEquals(GenderEnum.FEMALE.toString(), individuDTOMrs.getSexe());
        assertEquals(GenderEnum.NONBINARY.toString(), individuDTOMx.getSexe());
    }

    @Test
    void setGenderSharepoint_CivilityNotProvided() throws InvalidParameterException {
        IndividuDTO individuDTO = new IndividuDTO();
        individuDTO.setCivilite(CivilityEnum.M_.toString());

        individuDS.setGenderSharepoint(individuDTO);

        assertEquals(null, individuDTO.getSexe());
    }
}
