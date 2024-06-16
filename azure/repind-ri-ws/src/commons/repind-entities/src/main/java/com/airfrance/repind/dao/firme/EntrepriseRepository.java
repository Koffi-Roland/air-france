package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*PROTECTED REGION END*/

/**
 * <p>Title : IEntrepriseDAO.java</p>
 * BO: Entreprise
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, String>, EntrepriseRepositoryCustom {

    
}
