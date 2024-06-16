package com.airfrance.repind.dto.reference;

import com.airfrance.repind.entity.reference.RefPermissionsQuestion;

public final class RefPermissionsQuestionTransform {

    /**
     * private constructor
     */
    private RefPermissionsQuestionTransform() {
    }
    
    public static RefPermissionsQuestion dto2Bo(RefPermissionsQuestionDTO refPermissionsQuestionDTO) {
    	
    	RefPermissionsQuestion refPermissionsQuestion = new RefPermissionsQuestion();
    	
    	refPermissionsQuestion.setId(refPermissionsQuestionDTO.getId());
    	refPermissionsQuestion.setName(refPermissionsQuestionDTO.getName());
    	refPermissionsQuestion.setQuestion(refPermissionsQuestionDTO.getQuestion());
    	refPermissionsQuestion.setQuestionEN(refPermissionsQuestionDTO.getQuestionEN());
    	refPermissionsQuestion.setDateCreation(refPermissionsQuestionDTO.getDateCreation());
    	refPermissionsQuestion.setSiteCreation(refPermissionsQuestionDTO.getSiteCreation());
    	refPermissionsQuestion.setSignatureCreation(refPermissionsQuestionDTO.getSignatureCreation());
    	refPermissionsQuestion.setDateModification(refPermissionsQuestionDTO.getDateModification());
    	refPermissionsQuestion.setSiteModification(refPermissionsQuestionDTO.getSiteModification());
    	refPermissionsQuestion.setSignatureModification(refPermissionsQuestionDTO.getSignatureModification());
    	
    	return refPermissionsQuestion;
    }
    
    public static RefPermissionsQuestionDTO bo2Dto(RefPermissionsQuestion refPermissionsQuestion) {
    	
    	RefPermissionsQuestionDTO refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
    	bo2Dto(refPermissionsQuestion, refPermissionsQuestionDTO);
    	
    	return refPermissionsQuestionDTO;
    }
    
    public static void bo2Dto(RefPermissionsQuestion refPermissionsQuestion, RefPermissionsQuestionDTO refPermissionsQuestionDTO) {
    	
    	if (refPermissionsQuestionDTO == null) {
    		refPermissionsQuestionDTO = new RefPermissionsQuestionDTO();
    	}

    	refPermissionsQuestionDTO.setId(refPermissionsQuestion.getId());
    	refPermissionsQuestionDTO.setName(refPermissionsQuestion.getName());
    	refPermissionsQuestionDTO.setQuestion(refPermissionsQuestion.getQuestion());
    	refPermissionsQuestionDTO.setQuestionEN(refPermissionsQuestion.getQuestionEN());
    	refPermissionsQuestionDTO.setDateCreation(refPermissionsQuestion.getDateCreation());
    	refPermissionsQuestionDTO.setSiteCreation(refPermissionsQuestion.getSiteCreation());
    	refPermissionsQuestionDTO.setSignatureCreation(refPermissionsQuestion.getSignatureCreation());
    	refPermissionsQuestionDTO.setDateModification(refPermissionsQuestion.getDateModification());
    	refPermissionsQuestionDTO.setSiteModification(refPermissionsQuestion.getSiteModification());
    	refPermissionsQuestionDTO.setSignatureModification(refPermissionsQuestion.getSignatureModification());
    	
    	//return refPermissionsQuestionDTO;    	
    }
    
    public static void updateDto(RefPermissionsQuestionDTO refPermissionsQuestionDTO, RefPermissionsQuestionDTO refPermissionsQuestionDTOFromDB) {
            	    	
    	refPermissionsQuestionDTOFromDB.setId(refPermissionsQuestionDTO.getId());
    	refPermissionsQuestionDTOFromDB.setName(refPermissionsQuestionDTO.getName());
    	refPermissionsQuestionDTOFromDB.setQuestion(refPermissionsQuestionDTO.getQuestion());
    	refPermissionsQuestionDTOFromDB.setQuestionEN(refPermissionsQuestionDTO.getQuestionEN());
    	refPermissionsQuestionDTOFromDB.setDateModification(refPermissionsQuestionDTO.getDateModification());
    	refPermissionsQuestionDTOFromDB.setSiteModification(refPermissionsQuestionDTO.getSiteModification());
    	refPermissionsQuestionDTOFromDB.setSignatureModification(refPermissionsQuestionDTO.getSignatureModification());
    }
}

