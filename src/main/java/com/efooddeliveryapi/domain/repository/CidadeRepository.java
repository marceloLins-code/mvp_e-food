package com.efooddeliveryapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efooddeliveryapi.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
