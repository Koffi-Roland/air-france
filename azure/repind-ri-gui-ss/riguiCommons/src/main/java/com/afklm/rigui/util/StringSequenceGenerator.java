package com.afklm.rigui.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class StringSequenceGenerator extends Configuration implements IdentifierGenerator {
	private String sequenceCallSyntax;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
		final Dialect dialect = jdbcEnvironment.getDialect();

		final String sequencePerEntitySuffix = ConfigurationHelper.getString(SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, params, SequenceStyleGenerator.DEF_SEQUENCE_SUFFIX);

		final String defaultSequenceName = ConfigurationHelper.getBoolean(SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, params, false)
				? params.getProperty(JPA_ENTITY_NAME) + sequencePerEntitySuffix
				: SequenceStyleGenerator.DEF_SEQUENCE_NAME;

		sequenceCallSyntax = dialect.getSequenceNextValString(ConfigurationHelper.getString(SequenceStyleGenerator.SEQUENCE_PARAM, params, defaultSequenceName));

	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if (object instanceof Identifiable) {
			Identifiable identifiable = (Identifiable) object;
			String id = identifiable.getId();
			if (StringUtils.isNotBlank(id)) {
				return id;
			}
		}
		return ((Number)session.createNativeQuery(sequenceCallSyntax).uniqueResult()).toString();
	}

}
