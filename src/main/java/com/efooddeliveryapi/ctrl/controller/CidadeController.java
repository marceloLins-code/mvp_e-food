package com.efooddeliveryapi.ctrl.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Cidade;
import com.efooddeliveryapi.domain.repository.CidadeRepository;
import com.efooddeliveryapi.domain.service.CidadeService;

@Controller
public class CidadeController {
	
	@Autowired
	CidadeService cidadeService;

	@Autowired
	CidadeRepository cidadeRepository;
	
	  @PostMapping
		public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
			try {
				cidade = cidadeService.salvar(cidade);
				
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(cidade);
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.badRequest()
						.body(e.getMessage());
			}
		}
	  
	  @GetMapping("/{cidadeId}")
		public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
			Cidade cidade = cidadeRepository.getById(cidadeId);
			
			if (cidade != null) {
				return ResponseEntity.ok(cidade);
			}
			
			return ResponseEntity.notFound().build();
		}
	  
	  
	  
	  @PutMapping("/{cidadeAId}")
		public ResponseEntity<Cidade> atualizar(@PathVariable Long cidadeId,
				@RequestBody Cidade cidade) {
			Cidade cidadeAtual = cidadeRepository.getById(cidadeId);
			
			if (cidadeAtual != null) {
				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
				
				cidadeAtual = cidadeService.salvar(cidadeAtual);
				return ResponseEntity.ok(cidadeAtual);
			}
			
			return ResponseEntity.notFound().build();
		}
	  
	  @DeleteMapping("/{cidadeAId}")
		public ResponseEntity<?> remover(@PathVariable Long cidadeAId) {
			try {
				cidadeService.excluir(cidadeAId);	
				return ResponseEntity.noContent().build();
				
			} catch (EntidadeNaoEncontradaException e) {
				return ResponseEntity.notFound().build();
				
			} catch (EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(e.getMessage());
			}
		}
	  
	  
	
	
}
