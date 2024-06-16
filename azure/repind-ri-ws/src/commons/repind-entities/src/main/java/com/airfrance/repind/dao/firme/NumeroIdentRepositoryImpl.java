package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.NumeroIdent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class NumeroIdentRepositoryImpl implements NumeroIdentRepositoryCustom{

	private static final Log log = LogFactory.getLog(NumeroIdentRepositoryImpl.class);

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
	@Override
	public List<NumeroIdent> findAllByKeyNumberTypeDate(Long key, String numero, String type, String dateOuverture,
			String dateFermeture) {
		
		StringBuilder hql = new StringBuilder(" SELECT numId FROM NumeroIdent numId where numId.key = :key and numId.numero = :numero and numId.type = :types and ");
		
		if (StringUtils.isEmpty(dateFermeture)) {
			hql.append("(numId.dateFermeture is null)");
			hql.append(" or ");
			hql.append("(numId.dateFermeture is not null and TO_DATE(':dateOuverture','DDMMYYYY') <= numId.dateFermeture)");
			hql.append(" or ");
			hql.append("(numId.dateFermeture is not null and TO_DATE(':dateOuverture','DDMMYYYY') <= numId.dateOuverture)");
			hql.append(")");

		}
		else {
		    // Si intervalle dedans alors non
			hql.append("(TO_DATE(':dateOuverture','DDMMYYYY')>=numId.dateOuverture and TO_DATE(':dateFermeture','DDMMYYYY')<=numId.dateFermeture)");
			hql.append(" or ");
		    // Si chevauchement "par la droite" alors non plus
			hql.append("(TO_DATE(':dateOuverture','DDMMYYYY')<=numId.dateOuverture and TO_DATE(':dateFermeture','DDMMYYYY')<=numId.dateFermeture) and TO_DATE(':dateFermeture','DDMMYYYY')>=numId.dateOuverture)");
			hql.append(" or ");
		    // Si chevauchement "par la gauche" alors non plus
			hql.append("(TO_DATE(':dateOuverture','DDMMYYYY')<=numId.dateFermeture and TO_DATE(':dateFermeture','DDMMYYYY')>=numId.dateFermeture))");
			hql.append(" or ");
		    // Cas avec date de fin non renseignee
			hql.append("(TO_DATE(':dateOuverture','DDMMYYYY')>=numId.dateOuverture and TO_DATE(':dateFermeture','DDMMYYYY')>=numId.dateOuverture) and numId.dateFermeture is null)");
			hql.append(")");
		}
		
		// query
		Query myquery = entityManager.createQuery(hql.toString());
		
		// parameters
		myquery.setParameter("key", key);
		myquery.setParameter("numero", numero);
		myquery.setParameter("type", type);
		myquery.setParameter("dateOuverture", dateOuverture);
		myquery.setParameter("dateFermeture", dateFermeture);

        @SuppressWarnings("unchecked")
		List<NumeroIdent> listResults = myquery.getResultList();

        return listResults;
	}

}
