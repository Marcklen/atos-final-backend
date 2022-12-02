package com.marcklen.projeto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcklen.projeto.domain.Chamado;
import com.marcklen.projeto.repositories.ChamadoRepository;
import com.marcklen.projeto.services.exceptions.ObjectNotFoundExceptions;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExceptions("Chamado de Numero " + id+ " não encontrado "));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
}
