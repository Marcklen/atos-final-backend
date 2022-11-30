package com.marcklen.projeto.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcklen.projeto.domain.Tecnico;
import com.marcklen.projeto.domain.dtos.TecnicoDTO;
import com.marcklen.projeto.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	TecnicoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		Tecnico tec = service.findById(id);
		return ResponseEntity.ok().body(new TecnicoDTO(tec));
	}
}