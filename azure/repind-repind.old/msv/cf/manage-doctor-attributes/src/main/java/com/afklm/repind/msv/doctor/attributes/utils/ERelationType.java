package com.afklm.repind.msv.doctor.attributes.utils;

import com.afklm.repind.msv.doctor.attributes.model.RelationModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum ERelationType implements IAttributesEnum {
    ROLE_LANGUE_RELATIONSHIP("SPEAK" , ELanguage::contains);

    private static final int MAX_LANG_VALUE = 3;
    private final String value;
    private final Function<String , IAttributesEnum> function;

    public static ERelationType contains(String iValue){
        if(iValue != null){
            for (ERelationType value : ERelationType.values()) {
                if(value.getValue().equals(iValue.toUpperCase())){
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Method to know if every ERelationType are contained inside the given collection
     * @param iValues
     * @return
     */
    public static boolean containAll(Collection<String> iValues){
        boolean contained = true;
        if(iValues == null){
            contained = false;
        }else{
            if(!iValues.stream().anyMatch(s -> s.equalsIgnoreCase(ROLE_LANGUE_RELATIONSHIP.getValue()))){
                contained = false;
            }
        }
        return contained;
    }

    /**
     * Method to know if every ERelationType has the right number of values
     * @param iRelationModel
     * @return
     */
    public static boolean checkSizeOfRelationType(RelationModel iRelationModel){
        boolean response = true;
        if(iRelationModel == null){
            response = false;
        }else{
            if(ROLE_LANGUE_RELATIONSHIP.getValue().equals(iRelationModel.getType()) && iRelationModel.getValues().size() > MAX_LANG_VALUE){
                response = false;

            }
        }
        return response;
    }

}
