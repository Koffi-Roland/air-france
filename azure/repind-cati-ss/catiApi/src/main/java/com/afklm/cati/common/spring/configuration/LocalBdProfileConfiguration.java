package com.afklm.cati.common.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * A configuration for 'Local BD' profile.
 * 
 * @author DINB - TA
 */
@Configuration
@Profile("local_BD")
public class LocalBdProfileConfiguration {
//
//	/**
//	 * Builds a JNDI datasource.
//	 * @return the datasource.
//	 */
//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//		dataSource.setUrl("jdbc:postgresql://{host}:{port}/{sid}");
//		dataSource.setUsername("{user}");
//		dataSource.setPassword("{password}");
//		return dataSource;
//	}
//
//	/**
//	 * Returns the JPA dialect to use.
//	 * @return the JPA dialect to use.
//	 */
//	@Bean
//	public String jpaDialect() {
//		return org.hibernate.dialect.PostgreSQL9Dialect.class.getName();
//	}
//
//	/**
//	 * Returns the Generate DDL flag.
//	 * @return the Generate DDL flag.
//	 */
//	@Bean
//	public Boolean generateDdl() {
//		return Boolean.TRUE;
//	}
//
//	/**
//	 * Returns the Show SQL flag.
//	 * @return the Show SQL flag.
//	 */
//	@Bean
//	public Boolean showSql() {
//		return Boolean.TRUE;
//	}

}
