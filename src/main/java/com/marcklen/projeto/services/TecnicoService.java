package com.marcklen.projeto.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcklen.projeto.domain.Tecnico;
import com.marcklen.projeto.repositories.TecnicoRepository;
import com.marcklen.projeto.services.exceptions.ObjectNotFoundExceptions;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> tec = repository.findById(id);
		return tec.orElseThrow(() -> new ObjectNotFoundExceptions("Objeto nao encontrado! Id: " + id));
	}
}
