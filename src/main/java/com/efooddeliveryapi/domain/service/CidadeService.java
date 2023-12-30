package com.efooddeliveryapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.efooddeliveryapi.domain.exception.EntidadeEmUsoException;
import com.efooddeliveryapi.domain.exception.EntidadeNaoEncontradaException;
import com.efooddeliveryapi.domain.model.Cidade;
import com.efooddeliveryapi.domain.model.Estado;
import com.efooddeliveryapi.domain.repository.CidadeRepository;
import com.efooddeliveryapi.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;	
	
	
	
	public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.getById(estadoId);
        
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de estado com código %d", estadoId));
        }
        
        cidade.setEstado(estado);
        
        return cidadeRepository.save(cidade);
    }
	
	
	
	  public void excluir(Long cidadeId) {
          try {
              cidadeRepository.deleteById(cidadeId);
              
          } catch (EmptyResultDataAccessException e) {
              throw new EntidadeNaoEncontradaException(
                  String.format("Não existe um cadastro de cidade com código %d", cidadeId));
          
          } catch (DataIntegrityViolationException e) {
              throw new EntidadeEmUsoException(
                  String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
          }
      }
      
  }


