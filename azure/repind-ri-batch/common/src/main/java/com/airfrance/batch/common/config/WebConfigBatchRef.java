package com.airfrance.batch.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class WebConfigBatchRef {

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
		return "org.hibernate.dialect.Oracle12cDialect";
	}
	@Bean
	public Boolean showSql() {
		return Boolean.FALSE;
	}
}
