package com.efooddeliveryapi.ctrl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Restaurante;
import com.efooddeliveryapi.domain.repository.RestauranteRepository;
import com.efooddeliveryapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	RestauranteRepository restauranteRepository;

	@Autowired
	RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();

	}

	@GetMapping("/{restauranteId}")
	public ResponseEntity<Object> buscar(@PathVariable Long restauranteId) {

		Restaurante restaurante = restauranteRepository.findById(restauranteId).get();

		if (restaurante != null) {
			return ResponseEntity.ok(restaurante);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtual = restauranteRepository.getById(restauranteId);

			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

				restauranteAtual = restauranteService.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> deletCozinha(@PathVariable Long restauranteId) {
		try {
			restauranteService.excluirRestaurante(restauranteId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		}

		catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}

	}

	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository.getById(restauranteId);

		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		Restaurante restauranteMergeado = merge(campos, restauranteAtual);

		return atualizar(restauranteId, restauranteMergeado);
	}

	private Restaurante merge(Map<String, Object> dadosOrigem, Restaurante restauranteAtual) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteDestino = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		String[] keys = dadosOrigem.keySet().toArray(new String[] {});
		BeanUtils.copyProperties(restauranteAtual, restauranteDestino, keys);
		return restauranteDestino;
	}
}