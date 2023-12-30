package com.efooddeliveryapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Restaurante;
import com.efooddeliveryapi.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	RestauranteRepository restauranteRepository;

	public Restaurante salvar(Restaurante restaurante) {
		
		

		return restauranteRepository.save(restaurante);

	}

	public void excluirRestaurante(Long restauranteId) {

		try {
			restauranteRepository.deleteById(restauranteId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Restaurante de codigo %d não pode ser encontrada.");
		}

		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Restaurante de codigo %d não pode ser removida, pois esta em uso.");
		}

	}
}
	
	