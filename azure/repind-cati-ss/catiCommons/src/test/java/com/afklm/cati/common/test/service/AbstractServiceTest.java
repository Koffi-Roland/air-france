package com.afklm.cati.common.test.service;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;

/**
 * Abstract controller test
 * 
 * Convenient class to test each controller in a standalone way
 * Just inherit from this class and provide the controller to test
 * 
 */

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class, TransactionalTestExecutionListener.class })
@DataSet(dbType = DBType.H2, locations = {
		"classpath:dataSet/refComPrefDomainDataSet.xml",
		"classpath:dataSet/refComPrefGTypeDataSet.xml",
		"classpath:dataSet/refComPrefTypeDataSet.xml",
		"classpath:dataSet/refComPrefMediaDataSet.xml",
		"classpath:dataSet/refComPrefCountryMarketDataSet.xml",
		"classpath:dataSet/refComPrefDataSet.xml",
		"classpath:dataSet/refComPrefDgtDataSet.xml",
		"classpath:dataSet/refComPrefMlDataSet.xml",
		"classpath:dataSet/refPermissionsQuestionDataSet.xml",
		"classpath:dataSet/refPermissionsDataSet.xml",
		"classpath:dataSet/refTrackingPermissionDataSet.xml",
		"classpath:dataSet/refPreferenceDataKeyDataSet.xml",
		"classpath:dataSet/refPreferenceKeyTypeDataSet.xml",
		"classpath:dataSet/refPreferenceTypeDataSet.xml",
		"classpath:dataSet/variablesDataSet.xml",
		"classpath:dataSet/paysDataSet.xml",
		"classpath:dataSet/refPcsFactorDataSet.xml",
		"classpath:dataSet/refPcsScoreDataSet.xml"})
public abstract class AbstractServiceTest {

}
