package com.afklm.rigui.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public abstract class AbstractDBConfiguration {

	protected static ResourceBundle riguiResourceBundle = PropertyResourceBundle.getBundle("rigui-config");

	/**
	 * The dialect provided by other configurations.
	 */
	@Bean(name = "jpaDialect")
	public String jpaDialect() {
		return "org.hibernate.dialect.Oracle10gDialect";
	}

	@Bean(name = "generateDdl")
	public Boolean generateDdl() {
		return Boolean.FALSE;
	}

	@Bean(name = "showSql")
	public Boolean showSql() {
		return Boolean.valueOf(riguiResourceBundle.getString("hibernate.showSql"));
	}

	@Bean
	public HibernateJpaVendorAdapter jpaAdapter() {
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform(jpaDialect());
		jpaAdapter.setGenerateDdl(generateDdl());
		jpaAdapter.setShowSql(showSql());
		return jpaAdapter;
	}
}
