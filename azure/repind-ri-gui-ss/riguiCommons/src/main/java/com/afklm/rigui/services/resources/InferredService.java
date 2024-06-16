package com.afklm.rigui.services.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InferredService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InferredService.class);
	
	/*
	 * @Autowired private InferredRepository inferredRepository;
	 * 
	 * @Autowired private RefInfrdTypeRepository refInfrdTypeRepository;
	 * 
	 * @Autowired private InferredHelper inferredHelper;
	 * 
	 * @Transactional public WrapperIndividualInferred
	 * getInferredForIndividual(String gin) throws ServiceException {
	 * WrapperIndividualInferred wrapperIndividualInferred = new
	 * WrapperIndividualInferred(); try { // Types from the document
	 * SSD_Provide_Individual_data List<RefInfrdType> inferredTypes =
	 * refInfrdTypeRepository.findAll(); HashSet<Inferred> inferreds = new
	 * HashSet<>(); for (RefInfrdType refInfrdType : inferredTypes) { String
	 * type = refInfrdType.getCode();
	 * inferreds.addAll(inferredRepository.findByGinAndType(gin, type)); }
	 * 
	 * if (inferreds == null || inferreds.size() < 1) throw new
	 * ServiceException(BusinessErrorList.API_CUSTOMER_NOT_FOUND.getError(),
	 * HttpStatus.OK);
	 * 
	 * wrapperIndividualInferred =
	 * inferredHelper.sortInferreds(wrapperIndividualInferred, inferreds); }
	 * catch (DataAccessException e) { String msg =
	 * "Can't count asked individual from DB"; LOGGER.error(msg, e); throw new
	 * ServiceException(BusinessErrorList.API_CANT_ACCESS_DB.getError(),
	 * HttpStatus.INTERNAL_SERVER_ERROR); } return wrapperIndividualInferred; }
	 */

}
