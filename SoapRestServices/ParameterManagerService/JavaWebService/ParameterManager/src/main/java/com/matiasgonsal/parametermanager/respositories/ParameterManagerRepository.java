package com.matiasgonsal.parametermanager.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.matiasgonsal.parametermanager.model.ParameterManager;

public interface ParameterManagerRepository extends JpaRepository<ParameterManager, Integer> {
	
	@Query("select p from ParameterManager p "
			+ "where p.parameter_category = :category and p.parameter_key = :key")
	public ParameterManager findValueByCategoryKey (@Param("category") String category, @Param("key") String key);

}
