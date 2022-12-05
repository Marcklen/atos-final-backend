package com.marcklen.projeto.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tec = repository.findById(id);
		return tec.orElseThrow(() -> new ObjectNotFoundExceptions("Objeto nao encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO dto) {
		dto.setId(null); // seguranca para que o ID venha nulo
		dto.setSenha(encoder.encode(dto.getSenha()));
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

	public void delete(Integer id) {
		Tecnico tec = findById(id);
		if (tec.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O Tecnico possui ordens de serviço e nao pode ser deletado!!!");
		}

		repository.deleteById(id);
	}

	private void validacaoPorCPFeEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
		}
	}
}