package com.efooddeliveryapi.ctrl.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.efooddeliveryapi.domain.model.Estado;
import com.efooddeliveryapi.domain.repository.EstadoRepository;
import com.efooddeliveryapi.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    EstadoService estadoService;
    
    @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return estadoService.salvar(estado);
				
	}
    
    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }
    
    @PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,
			@RequestBody Estado estado) {
		Estado estadoAtual = estadoRepository.getById(estadoId);
		
		if (estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			
			estadoAtual = estadoService.salvar(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
    
    
    @DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId) {
		try {
			estadoService.excluir(estadoId);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
    
    
    
}