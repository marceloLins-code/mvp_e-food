package com.efooddeliveryapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efooddeliveryapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

	@Query("from Restaurante r join r.cozinha")
	List<Restaurante> findAll();
	
}
