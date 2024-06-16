package com.airfrance.ref.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class WebConfigRef {
	@Autowired
	Environment env;
	@Bean
	public Boolean generateDdl() {
		return Boolean.FALSE;
	}
	@Bean
	public HibernateJpaVendorAdapter jpaAdapter() {
		final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabasePlatform(jpaDialect());
		jpaAdapter.setGenerateDdl(generateDdl());
		jpaAdapter.setShowSql(showSql());
		return jpaAdapter;
	}

	@Bean
	public String jpaDialect() {
		return "org.hibernate.dialect.Oracle10gDialect";
	}
	@Bean
	public Boolean showSql() {
		return Boolean.FALSE;
	}
}