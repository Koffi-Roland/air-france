package com.afklm.cati.common.controllers.it;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.Filter;

import lombok.Getter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.excilys.ebi.spring.dbunit.config.DBType;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.afklm.cati.common.spring.rest.exceptions.CatiResponseEntityExceptionHandler;

/**
 * Abstract controller test
 * 
 * Convenient class to test each controller in a standalone way
 * Just inherit from this class and provide the controller to test
 * 
 */
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestExecutionListeners({ 
		DataSetTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		ServletTestExecutionListener.class,
		WithSecurityContextTestExecutionListener.class 
		})
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
		"classpath:dataSet/refPcsFactorDataSet.xml",
		"classpath:dataSet/refPcsScoreDataSet.xml"

})

public abstract class AbstractControllerTest {
    @Resource(name = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME)
    private Filter securityFilter;

	@Autowired
	private WebApplicationContext webApplicationContext;
	/**
	 * -- GETTER --
	 *  Get MockMvc
	 */
	@Getter
	private MockMvc mockMvc;

	@Before
	public void setup() {
		// Customize exceptionHandler
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
                    Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(CatiResponseEntityExceptionHandler.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new CatiResponseEntityExceptionHandler(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(jacksonConverter);
        exceptionResolver.afterPropertiesSet();
        
        // Argument resolvers
        HateoasPageableHandlerMethodArgumentResolver hateoasPageableHandlerMethodArgumentResolver = new HateoasPageableHandlerMethodArgumentResolver();

        PagedResourcesAssemblerArgumentResolver resolver_0 = new PagedResourcesAssemblerArgumentResolver(
        		hateoasPageableHandlerMethodArgumentResolver, null);
        PageableHandlerMethodArgumentResolver resolver_1 = new PageableHandlerMethodArgumentResolver();

        AuthenticationPrincipalArgumentResolver resolver = new AuthenticationPrincipalArgumentResolver();

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	/**
	 * Get controller
	 */
    abstract protected Object getController();

}
