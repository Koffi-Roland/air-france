package com.airfrance.repind.dao.preference;

import com.airfrance.repind.entity.preference.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
	
	@Query("select p from Preference p "
			+ "where p.preferenceId = :preferenceId "
			+ "and p.gin = :gin ")
    Preference findByIdAndGin(@Param("preferenceId") Long preferenceId, @Param("gin") String gin);

	@Query("select p from Preference p "
			+ "left outer join p.preferenceData data "
			+ "where p.gin = :gin "
			+ "and p.type = :type "
			+ "and data.key = 'number' "
			+ "and data.value = :number")
    List<Preference> findByGinAndTypeAndNumber(@Param("gin") String gin, @Param("type") String type, @Param("number") String number);

	@Query("select p from Preference p "
			+ "where p.gin = :gin "
			+ "and p.type = :type")
    List<Preference> findByGinAndType(@Param("gin") String gin, @Param("type") String type);

	@Query("select count(1) from Preference p "
			+ "where p.gin = :gin ")
	Long getPreferencesNumberByGin(@Param("gin") String gin);
	
	List<Preference> findByGin(@Param("gin") String gin);

	public Preference findByPreferenceId(Long preferenceId);
}
