package com.efooddeliveryapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efooddeliveryapi.domain.model.FormaPagamento;

@Repository
public interface formaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
