package com.afklm.repind.msv.doctor.attributes.service;

import com.afklm.repind.msv.doctor.attributes.criteria.attributes.UpsertDoctorAttributesCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.CreateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.criteria.role.UpdateDoctorRoleCriteria;
import com.afklm.repind.msv.doctor.attributes.entity.node.AirLineCode;
import com.afklm.repind.msv.doctor.attributes.entity.node.Language;
import com.afklm.repind.msv.doctor.attributes.entity.node.Role;
import com.afklm.repind.msv.doctor.attributes.entity.node.Speciality;
import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import com.afklm.repind.msv.doctor.attributes.repository.IAirLineCodeRepository;
import com.afklm.repind.msv.doctor.attributes.repository.ILanguageRepository;
import com.afklm.repind.msv.doctor.attributes.repository.IRoleRepository;
import com.afklm.repind.msv.doctor.attributes.repository.ISpecialityRepository;
import com.afklm.repind.msv.doctor.attributes.utils.ELanguage;
import com.afklm.repind.msv.doctor.attributes.utils.ERelationType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UpdateAttributesService {

    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private ISpecialityRepository specialityRepository;

    @Autowired
    private IAirLineCodeRepository airLineCodeRepository;

    @Autowired
    private IRoleRepository roleRepository;



    public void upsertRole(Role ioRole , CreateDoctorRoleCriteria iCreateDoctorRoleCriteria){

        UpsertDoctorAttributesCriteria upsertDoctorAttributesCriteria = new UpsertDoctorAttributesCriteria()
                .withRoleId(iCreateDoctorRoleCriteria.getRoleId())
                .withAirLineCode(iCreateDoctorRoleCriteria.getAirLineCode())
                .withSpeciality(iCreateDoctorRoleCriteria.getSpeciality())
                .withRelationsList(iCreateDoctorRoleCriteria.getRelationsList());

        upsertRole(ioRole,upsertDoctorAttributesCriteria);
    }


    public void upsertRole(Role ioRole , UpdateDoctorRoleCriteria updateDoctorRoleCriteria){
        log.info("upsertRole doctor attribute service  : {}", updateDoctorRoleCriteria.getRoleId());

        UpsertDoctorAttributesCriteria upsertDoctorAttributesCriteria = new UpsertDoctorAttributesCriteria()
                .withRoleId(updateDoctorRoleCriteria.getRoleId())
                .withAirLineCode(updateDoctorRoleCriteria.getAirLineCode())
                .withSpeciality(updateDoctorRoleCriteria.getSpeciality())
                .withRelationsList(updateDoctorRoleCriteria.getRelationsList());

        upsertRole(ioRole,upsertDoctorAttributesCriteria);
    }


    public void upsertRole(Role ioRole, UpsertDoctorAttributesCriteria updateValues) {
        log.info("upsertRole doctor attribute service  : {}", updateValues.getRoleId());
        ioRole.setLastUpdate(new Date());

        detachSpeciality(ioRole);
        Optional<Speciality> specialityOpt = specialityRepository.findByValue(updateValues.getSpeciality());
        if (!specialityOpt.isPresent()) {
            Speciality speciality = new Speciality();
            speciality.setValue(updateValues.getSpeciality());
            specialityRepository.save(speciality);
            ioRole.setSpeciality(speciality);
        } else {
            ioRole.setSpeciality(specialityOpt.get());
        }

        detachAirLineCode(ioRole);
        Optional<AirLineCode> airLineCodeOpt = airLineCodeRepository.findByValue(updateValues.getAirLineCode());
        if (!airLineCodeOpt.isPresent()) {
            AirLineCode airLineCode = new AirLineCode();
            airLineCode.setValue(updateValues.getAirLineCode());
            airLineCodeRepository.save(airLineCode);
            ioRole.setAirLineCode(airLineCode);
        } else {
            ioRole.setAirLineCode(airLineCodeOpt.get());
        }

        upsertLanguage(ioRole, updateValues);


    }


    private void upsertLanguage(Role ioRole, UpsertDoctorAttributesCriteria updateValues) {
        log.info("upsertLanguage doctor attribute service  : {}", updateValues.getRoleId());
        for (RelationModel relationModel : updateValues.getRelationsList()) {
            if (ERelationType.ROLE_LANGUE_RELATIONSHIP.getValue().equals(relationModel.getType())) {

                if(ioRole.getLanguages() != null){
                    ioRole.getLanguages().forEach(l ->
                        roleRepository.detachRelationSpeak(ioRole.getRoleId(), l.getValue())
                    );
                    ioRole.getLanguages().clear();
                }
                Collection<Language> languages = languageRepository.findAllByValueIn(relationModel.getValues());
                if(languages.size() != relationModel.getValues().size()) {
                    List<ELanguage> eLanguagesList = relationModel.getValues().stream().map(ELanguage::contains).collect(Collectors.toList());
                    eLanguagesList.forEach(l -> {
                        Optional<Language> langOpt = languageRepository.findByAcronymeAndValue(l.getAcronyme(),l.getValue());
                        if(!langOpt.isPresent()){
                            Language language = new Language();
                            language.setAcronyme(l.getAcronyme());
                            language.setValue(l.getValue());
                            languageRepository.save(language);
                            languages.add(language);
                        }
                    });
                }

                ioRole.addLanguages(languages);
            }
        }
    }

    private void detachSpeciality(Role role) {
        if(role.getSpeciality() != null) {
            roleRepository.detachRelationExpert(role.getRoleId(), role.getSpeciality().getValue());
        }
    }

    private void detachAirLineCode(Role role) {
        if(role.getAirLineCode() != null){
            roleRepository.detachRelationApproval(role.getRoleId(), role.getAirLineCode().getValue());
        }
    }

}
