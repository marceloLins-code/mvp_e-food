package com.efooddeliveryapi.ctrl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Cozinha;
import com.efooddeliveryapi.domain.model.Restaurante;
import com.efooddeliveryapi.domain.repository.CozinhaRepository;
import com.efooddeliveryapi.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	CozinhaService cozinhaService;

	@GetMapping
	public List<Cozinha> listar() {

		List<Cozinha> cozinha = listar();

		return cozinhaRepository.findAll();

	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Object> buscar(@PathVariable Long cozinhaId) {

		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

		if (cozinha.isPresent()) {

			System.out.println(cozinha.get().getRestaurante());

			return ResponseEntity.ok(cozinha.get());

		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = cozinhaRepository.getById(cozinhaId);

		if (cozinhaAtual != null) {
//			cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

			cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> deletCozinha(@PathVariable Long cozinhaId) {
		try {
			cozinhaService.excluirCozinha(cozinhaId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		}

		catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		}

	}

}
