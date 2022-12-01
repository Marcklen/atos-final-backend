package com.marcklen.projeto.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcklen.projeto.domain.Pessoa;
import com.marcklen.projeto.domain.Cliente;
import com.marcklen.projeto.domain.dtos.ClienteDTO;
import com.marcklen.projeto.repositories.PessoaRepository;
import com.marcklen.projeto.repositories.ClienteRepository;
import com.marcklen.projeto.services.exceptions.DataIntegrityViolationException;
import com.marcklen.projeto.services.exceptions.ObjectNotFoundExceptions;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoa;

	public Cliente findById(Integer id) {
		Optional<Cliente> cli = repository.findById(id);
		return cli.orElseThrow(() -> new ObjectNotFoundExceptions("Objeto nao encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO dto) {
		dto.setId(null); // seguranca para que o ID venha nulo
		validacaoPorCPFeEmail(dto);
		Cliente cli = new Cliente(dto);
		return repository.save(cli);
	}

	public Cliente update(Integer id, @Valid ClienteDTO dto) {
		dto.setId(id);
		Cliente cliAntigo = findById(id);
		validacaoPorCPFeEmail(dto);
		cliAntigo = new Cliente(dto);
		return repository.save(cliAntigo);
	}

	public void delete(Integer id) {
		Cliente cli = findById(id);
		if (cli.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("O Cliente possui ordens de serviço e nao pode ser deletado!!!");
		}

		repository.deleteById(id);
	}

	private void validacaoPorCPFeEmail(ClienteDTO dto) {
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