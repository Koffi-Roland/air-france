package com.airfrance.repind.dao.adresse;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IsNormalizedEnum;
import com.airfrance.repind.entity.adresse.Telecoms;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

public class TelecomsRepositoryImpl implements TelecomsRepositoryCustom {


	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
	
    /**
     * 
     * Update Phone Number with Batch rules.
     * 
     * Optimisation pour ne pas refaire un appel en BD pour des soucis de performance.
     * En effet le batch a effecuté l'appel et celui-ci n'est pas dans le cache Hibernate.
     * 
     * @throws JrafDomainException 
     */
    public int updatePhoneNumberOnly(Telecoms telecom) {
    	
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE SIC2.TELECOMS SET ");
		sql.append(" IVERSION = IVERSION + 1 , ");
		sql.append(" ISNORMALIZED = :is_normalized ");	
		
		if(IsNormalizedEnum.Y.getValue().equals(telecom.getIsnormalized())){			
			sql.append(" , SNORM_NAT_PHONE_NUMBER = :norm_nat_phone_number ");
			sql.append(" , SNORM_NAT_PHONE_NUMBER_CLEAN = :norm_nat_phone_number_clean ");
			sql.append(" , SNORM_INTER_COUNTRY_CODE = :norm_inter_country_code ");
			sql.append(" , SNORM_INTER_PHONE_NUMBER = :norm_inter_phone_number ");
			sql.append(" , SNORM_TERMINAL_TYPE_DETAIL = :norm_terminal_type_detail ");
			sql.append(" , SNUMERO = :numero ");
			sql.append(" , SINDICATIF = :indicatif ");
		}	
		
		if(IsNormalizedEnum.E.getValue().equals(telecom.getIsnormalized())){
			sql.append(" , SCODE_REGION = :code_region ");
			sql.append(" , SSITE_MODIFICATION = :site ");
			sql.append(" , DDATE_MODIFICATION = :date ");
			sql.append(" , SSIGNATURE_MODIFICATION = :signature ");	
		}

		sql.append(" WHERE SAIN = :sain ");
	
		Query query = entityManager.createNativeQuery(sql.toString());
		if(IsNormalizedEnum.Y.getValue().equals(telecom.getIsnormalized())){			
			query.setParameter("indicatif", telecom.getSindicatif());
			query.setParameter("numero", telecom.getSnumero());
			query.setParameter("norm_nat_phone_number", telecom.getSnorm_nat_phone_number());
			query.setParameter("norm_nat_phone_number_clean", telecom.getSnorm_nat_phone_number_clean());
			query.setParameter("norm_inter_country_code", telecom.getSnorm_inter_country_code());
			query.setParameter("norm_inter_phone_number", telecom.getSnorm_inter_phone_number());
			query.setParameter("norm_terminal_type_detail", telecom.getSnorm_terminal_type_detail());
		}
		if(IsNormalizedEnum.E.getValue().equals(telecom.getIsnormalized())){
			query.setParameter("signature", telecom.getSsignature_modification());
			query.setParameter("date", telecom.getDdate_modification(), TemporalType.DATE);
			query.setParameter("site", telecom.getSsite_modification());
			query.setParameter("code_region", telecom.getScode_region());
		}
		
		query.setParameter("is_normalized", telecom.getIsnormalized());
		query.setParameter("sain", telecom.getSain());		
		return query.executeUpdate();

    }

}
