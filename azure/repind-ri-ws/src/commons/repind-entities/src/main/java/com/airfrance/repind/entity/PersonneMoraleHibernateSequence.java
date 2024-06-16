package com.airfrance.repind.entity;

import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.Entreprise;
import com.airfrance.repind.entity.firme.Etablissement;
import com.airfrance.repind.entity.firme.Groupe;
import com.airfrance.repind.entity.firme.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonneMoraleHibernateSequence implements IdentifierGenerator {
    
    /** logger */
    private static final Log LOG = LogFactory.getLog(PersonneMoraleHibernateSequence.class);

    @Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String sequenceName = null;
        if (Entreprise.class.equals(object.getClass())) {
            sequenceName = "ISEQ_ENTREPRISE";           // bientôt 800 000 entreprises
        } else if (Agence.class.equals(object.getClass())) {
            sequenceName = "ISEQ_AGENCE";               // bientôt 500 000 agences
        } else if (Etablissement.class.equals(object.getClass())) {
            sequenceName = "ISEQ_ETABLISSEMENT";        // bientôt 450 000 établissements
        } else if (Service.class.equals(object.getClass())) {
            sequenceName = "ISEQ_PERS_MORALE";          // bientôt 8 000 services
        } else if (Groupe.class.equals(object.getClass())) {
            sequenceName = "ISEQ_GROUPE";               // bientôt 150 groupes
        } else {
            throw new HibernateException("Class not supported : " + object.getClass());
        }

        Connection connection = session.connection();
        try(
                PreparedStatement ps = connection.prepareStatement("select " + sequenceName + ".nextval as nextval from dual");
                ResultSet rs = ps.executeQuery();

                ) {

            if (rs.next()) {
                
                Long id = rs.getLong("nextval");
                Long checkDigit = (id + 6) % 7;
                String code = id.toString() + checkDigit.toString();

                LOG.debug("Generated GIN : " + code);
                return code;
            }

        } catch (SQLException e) {
            
            LOG.error(e);
            throw new HibernateException("Unable to generate GIN");
        }
        return null;
	}
}
