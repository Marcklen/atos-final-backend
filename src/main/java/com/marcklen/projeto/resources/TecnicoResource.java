package com.marcklen.projeto.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcklen.projeto.domain.Tecnico;
import com.marcklen.projeto.domain.dtos.TecnicoDTO;
import com.marcklen.projeto.services.TecnicoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Técnicos")
@RestController
@CrossOrigin
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	TecnicoService service;

	@ApiOperation(value = "Busca um tecnico por sua ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico tec = service.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(tec));
	}

	@ApiOperation(value = "Busca todos os tecnicos")
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<Tecnico> lista = service.findAll();
		List<TecnicoDTO> listaDTO = lista.stream().map(x -> new TecnicoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);
	}

	@ApiOperation(value = "Cria um tecnico - Somente ADMINs tem acesso")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO dto) {
		Tecnico tec = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tec.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Atualiza um tecnico - Somente ADMINs tem acesso")
	@PreAuthorize("hasAnyRole('ADMIN')") 
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO dto) {
		Tecnico tec = service.update(id, dto);
		return ResponseEntity.ok().body(new TecnicoDTO(tec));
	}
	
	@ApiOperation(value = "Deleta um tecnico - Somente ADMINs tem acesso")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}