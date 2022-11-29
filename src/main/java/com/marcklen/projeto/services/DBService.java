package com.marcklen.projeto.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcklen.projeto.domain.Chamado;
import com.marcklen.projeto.domain.Cliente;
import com.marcklen.projeto.domain.Tecnico;
import com.marcklen.projeto.domain.enums.Perfil;
import com.marcklen.projeto.domain.enums.Prioridade;
import com.marcklen.projeto.domain.enums.Status;
import com.marcklen.projeto.repositories.ChamadoRepository;
import com.marcklen.projeto.repositories.ClienteRepository;
import com.marcklen.projeto.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciarDB() {
		Tecnico t1 = new Tecnico(null, "Marcklen Guimaraes", "01724375393", "marcklen@gmail.com", "123");
		t1.addPerfil(Perfil.ADMIN);

		Cliente c1 = new Cliente(null, "Danielle Dourado", "01898528365", "danielle@email.com", "246");
		// c1.addPerfil(Perfil.CLIENTE);

		Chamado chama = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado01", "Primeiro Chamado", t1, c1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		chamadoRepository.saveAll(Arrays.asList(chama));
	}

}