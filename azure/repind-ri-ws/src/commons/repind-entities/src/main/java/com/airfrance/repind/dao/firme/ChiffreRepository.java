package com.airfrance.repind.dao.firme;


import com.airfrance.repind.entity.firme.Chiffre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * <p>Title : IChiffreDAO.java</p>
 * BO: Chiffre
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Repository
public interface ChiffreRepository extends JpaRepository<Chiffre, Integer>, ChiffreRepositoryCustom {

    
}
