package com.airfrance.repind.dao.firme;

import com.airfrance.repind.entity.firme.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*PROTECTED REGION END*/

/**
 * <p>Title : IGroupeDAO.java</p>
 * BO: Groupe
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, String>, GroupeRepositoryCustom {

   
}
