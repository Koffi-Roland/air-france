package com.afklm.rigui.dao.adresse;

import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.entity.adresse.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

	@Query("select ema from Individu ind "
			+ "right outer join ind.email ema "
			+ "where ind.sgin = :gin "
			+ "and (ema.statutMedium = 'V' "
			+ "or ema.statutMedium = 'I')")
	List<Email> findEmail(@Param("gin") String gin);

	@Transactional(readOnly = true)
	@Query("select ema from Email ema where ema.email = :email")
	List<Email> findByEmail(@Param("email") String email);

	@Query("select ema from Email ema where ema.sgin = :gin and ema.codeMedium = :code")
	List<Email> findByGinAndCodeMedium(@Param("gin") String gin, @Param("code") String code);

	List<Email> findBySgin(String gin);

	@Query("select count(1) from Email e "
			+ "where e.sgin = :gin "
			+ "and e.codeMedium = :codeMedium "
			+ "and e.statutMedium = 'V'")
	Long getNumberProOrPersoEmailByGinAndCodeMedium(@Param("gin") String gin, @Param("codeMedium") String codeMedium);

	public Email findBySain(String sain);

	@Transactional
	@Modifying
	@Query("Delete from Email e where e.sgin = :gin and e.email = :email")
	public void deleteByEmail(@Param("gin") String gin, @Param("email") String email);

}
