package com.afklm.repind.msv.handicap.services.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.afklm.repind.msv.handicap.entity.RefHandicapType;
import com.afklm.repind.msv.handicap.entity.RefHandicapTypeCodeData;
import com.afklm.repind.msv.handicap.entity.Handicap;
import com.afklm.repind.msv.handicap.entity.HandicapData;
import com.afklm.repind.msv.handicap.model.CreateHandicapModel;
import com.afklm.repind.msv.handicap.model.HandicapDataCreateModel;
import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.services.exception.ServiceException;

@Component
public class HandicapHelper {

    /**
     * For each data, check if the key is allowed for the type
     * If yes and if the value is not blank, add data to the handicap Object
     *
     * @return response
     */
    public List<HandicapData> getHandicapData(final Handicap handicap, final List<HandicapDataCreateModel> listHandicapDataModel, final List<RefHandicapTypeCodeData> keysAllowed, String application, Date now, final boolean isUpdate) throws ServiceException {

        List<HandicapData> listHandicapData = new ArrayList<>();

        List<String> keysAdded = new ArrayList<>();

        if (!isUpdate) {
            controlMandatoryValues(listHandicapDataModel, keysAllowed);
        }

        if (listHandicapDataModel != null) {

            for (HandicapDataCreateModel handicapDataModel : listHandicapDataModel) {

                String value = handicapDataModel.getValue();

                // Check if the key or the value is blank
                if (!StringUtils.isBlank(handicapDataModel.getKey()) && !StringUtils.isBlank(value)) {

                    String key = StringUtils.upperCase(handicapDataModel.getKey());

                    RefHandicapTypeCodeData refKey = controlIfKeyIsAllowed(key, keysAllowed);

                    if (keysAdded.contains(key)) {

                        // If the key is already set, doublon !
                        throw new ServiceException(BusinessErrorList.API_PRESENT_MORE_THAN_ONCE.getError()
                                .setDescription("Key : " + handicapDataModel.getKey()),
                                HttpStatus.PRECONDITION_FAILED);

                    }

                    controlValueType(refKey, key, value);

                    HandicapData handicapData = new HandicapData();
                    handicapData.setKey(refKey.getRefHandicapTypeCodeDataID().getKey());
                    handicapData.setValue(value);
                    handicapData.setDateCreation(now);
                    handicapData.setDateModification(now);
                    handicapData.setSiteCreation("REPINDMSV");
                    handicapData.setSiteModification("REPINDMSV");
                    handicapData.setSignatureCreation(application);
                    handicapData.setSignatureModification(application);
                    handicapData.setHandicap(handicap);

                    keysAdded.add(key);

                    listHandicapData.add(handicapData);

                } else {
                    // If the key or the value is blank, throw an exception Invalid Parameter
                    throw new ServiceException(BusinessErrorList.API_INVALID_HANDICAP.getError(),
                            HttpStatus.PRECONDITION_FAILED);
                }
            }

        }

        return listHandicapData;


    }

    private RefHandicapTypeCodeData controlIfKeyIsAllowed(final String key, final List<RefHandicapTypeCodeData> keysAllowed) throws ServiceException {

        return keysAllowed.stream()
                .filter(attr -> key.equalsIgnoreCase(attr.getRefHandicapTypeCodeDataID().getKey()))
                .findFirst()
                .orElseThrow(() -> new ServiceException(BusinessErrorList.API_INVALID_KEY.getError()
                        .setDescription("Key : " + key),
                        HttpStatus.PRECONDITION_FAILED));

    }

    private void controlMandatoryValues(final List<HandicapDataCreateModel> listHandicapDataModel, final List<RefHandicapTypeCodeData> keysAllowed) throws ServiceException {

        // Control mandatory values
        Iterator<RefHandicapTypeCodeData> mandatoryKeys = keysAllowed
                .stream().filter(attr -> "M".equalsIgnoreCase(attr.getCondition())).iterator();

        while (mandatoryKeys.hasNext()) {
            RefHandicapTypeCodeData ref = mandatoryKeys.next();
            if (listHandicapDataModel.stream().noneMatch(attr -> ref.getRefHandicapTypeCodeDataID().getKey().equalsIgnoreCase(attr.getKey()))) {
                throw new ServiceException(BusinessErrorList.API_KEY_IS_MISSING.getError()
                        .setDescription("Key : " + ref.getRefHandicapTypeCodeDataID().getKey()),
                        HttpStatus.PRECONDITION_FAILED);
            }
        }

    }

    private void controlValueType(final RefHandicapTypeCodeData refKey, final String key, final String value) throws ServiceException {

        // Check value length for String
        if ("String".equalsIgnoreCase(refKey.getDataType())) {

            if (value.length() > refKey.getMaxLength()) {
                // Value max length
                throw new ServiceException(BusinessErrorList.API_VALUE_TOO_LONG.getError()
                        .setDescription("Key : " + key),
                        HttpStatus.PRECONDITION_FAILED);
            }

            if (value.length() < refKey.getMinLength()) {
                // Value max length
                throw new ServiceException(BusinessErrorList.API_VALUE_TOO_SHORT.getError()
                        .setDescription("Key : " + key),
                        HttpStatus.PRECONDITION_FAILED);
            }

        }

        // Check value format
        if ("Date".equalsIgnoreCase(refKey.getDataType()) && !isValidDate(value)) {
            throw new ServiceException(BusinessErrorList.API_INVALID_TYPE_DATE.getError()
                    .setDescription("Key : " + key + ". Expected format is 'dd/MM/yyyy'"),
                    HttpStatus.PRECONDITION_FAILED);
        }

        if ("Boolean".equalsIgnoreCase(refKey.getDataType()) && !isBoolean(value)) {
            throw new ServiceException(BusinessErrorList.API_INVALID_TYPE_BOOLEAN.getError()
                    .setDescription("Key : " + key),
                    HttpStatus.PRECONDITION_FAILED);
        }

    }

    public void controlTypeAndCode(final List<CreateHandicapModel> listHandicapRequest, final List<Handicap> listHandicapDB, final List<RefHandicapType> listType) throws ServiceException {

        for (CreateHandicapModel handicapFromRequest : listHandicapRequest) {

            handicapFromRequest.setType(getType(handicapFromRequest.getType()));

            handicapFromRequest.setCode(getCode(handicapFromRequest.getCode()));

            if (listHandicapRequest.stream()
                    .filter(attr -> handicapFromRequest.getType().equalsIgnoreCase(attr.getType())
                            && handicapFromRequest.getCode().equalsIgnoreCase(attr.getCode())).count() > 1) {

                throw new ServiceException(BusinessErrorList.API_DUPLICATE_TYPE_CODE.getError()
                        .setDescription("Type : " + handicapFromRequest.getType() + " and code : " + handicapFromRequest.getCode()),
                        HttpStatus.PRECONDITION_FAILED);
            }

            checkHandicapNumberByType(listHandicapRequest, listHandicapDB, listType, handicapFromRequest);

        }


    }

    public void checkHandicapNumberByType(final List<CreateHandicapModel> listHandicapRequest, final List<Handicap> listHandicapDB,
                                          final List<RefHandicapType> listType, final CreateHandicapModel handicapFromRequest) throws ServiceException {

        long nbHandicapFromRequest = listHandicapRequest.stream()
                .filter(attr -> handicapFromRequest.getType().equalsIgnoreCase(attr.getType())).count();
        long nbHandicapFromDB = listHandicapDB.stream()
                .filter(attr -> handicapFromRequest.getType().equalsIgnoreCase(attr.getType())).count();

        Optional<RefHandicapType> ref = listType.stream()
                .filter(attr -> handicapFromRequest.getType().equalsIgnoreCase(attr.getCode()))
                .findFirst();

        if (!ref.isPresent()) {
            throw new ServiceException(BusinessErrorList.API_INVALID_TYPE.getError()
                    .setDescription("Type : " + handicapFromRequest.getType()),
                    HttpStatus.PRECONDITION_FAILED);
        }

        if (ref.get().getCode().equalsIgnoreCase(handicapFromRequest.getType()) && nbHandicapFromRequest + nbHandicapFromDB > ref.get().getNbMax()) {
            throw new ServiceException(BusinessErrorList.API_TOO_MUCH_TYPE.getError()
                    .setDescription("Type : " + handicapFromRequest.getType()),
                    HttpStatus.PRECONDITION_FAILED);
        }

    }

    public String getType(final String type) throws ServiceException {

        if (StringUtils.isBlank(type)) {
            throw new ServiceException(BusinessErrorList.API_TYPE_IS_MISSING.getError()
                    .setDescription("Type : " + type), HttpStatus.BAD_REQUEST);
        }
        if (type.length() > 10) {
            throw new ServiceException(BusinessErrorList.API_INVALID_TYPE.getError()
                    .setDescription("Type : " + type), HttpStatus.PRECONDITION_FAILED);
        }
        return StringUtils.upperCase(type);
    }

    public String getCode(final String code) throws ServiceException {

        if (StringUtils.isBlank(code)) {
            throw new ServiceException(BusinessErrorList.API_CODE_IS_MISSING.getError(), HttpStatus.BAD_REQUEST);
        }
        if (code.length() > 10) {
            throw new ServiceException(BusinessErrorList.API_INVALID_CODE.getError(), HttpStatus.PRECONDITION_FAILED);
        }
        return StringUtils.upperCase(code);

    }

    public boolean isValidDate(final String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }


    public boolean isBoolean(String value) {
        return ("O".equalsIgnoreCase(value) || "Y".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value)
                || "N".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value));
    }


}
