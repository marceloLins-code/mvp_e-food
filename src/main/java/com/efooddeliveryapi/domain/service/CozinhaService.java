package com.efooddeliveryapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Cozinha;
import com.efooddeliveryapi.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	CozinhaRepository cozinhaRepository;
	
		
	public Cozinha salvar(Cozinha cozinha) {
		
		return cozinhaRepository.save(cozinha);		
		
	}
	
	public void excluirCozinha(Long cozinhaId) {
		
		try {			
			cozinhaRepository.deleteById(cozinhaId);
			
		} 
		catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Cozinha de codigo %d não pode ser encontrada.");
		}
		
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cozinha de codigo %d não pode ser removida, pois esta em uso.");
		}
		
	}
	
	
	
	
	
	
	
}
