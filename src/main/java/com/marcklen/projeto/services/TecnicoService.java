package com.marcklen.projeto.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcklen.projeto.domain.Pessoa;
import com.marcklen.projeto.domain.Tecnico;
import com.marcklen.projeto.domain.dtos.TecnicoDTO;
import com.marcklen.projeto.repositories.PessoaRepository;
import com.marcklen.projeto.repositories.TecnicoRepository;
import com.marcklen.projeto.services.exceptions.DataIntegrityViolationException;
import com.marcklen.projeto.services.exceptions.ObjectNotFoundExceptions;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoa;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> tec = repository.findById(id);
		return tec.orElseThrow(() -> new ObjectNotFoundExceptions("Objeto nao encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO dto) {
		dto.setId(null); // seguranca para que o ID venha nulo
		validacaoPorCPFeEmail(dto);
		Tecnico tec = new Tecnico(dto);
		return repository.save(tec);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO dto) {
		dto.setId(id);
		Tecnico tecAntigo = findById(id);
		validacaoPorCPFeEmail(dto);
		tecAntigo = new Tecnico(dto);
		return repository.save(tecAntigo);
	}
	
	private void validacaoPorCPFeEmail(TecnicoDTO dto) {
		Optional<Pessoa> obj = pessoa.findByCpf(dto.getCpf());
		if (obj.isPresent() && obj.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoa.findByEmail(dto.getEmail());
		if (obj.isPresent() && obj.get().getId() != dto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}

}
