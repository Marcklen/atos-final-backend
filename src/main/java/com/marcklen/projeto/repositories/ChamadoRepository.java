package com.marcklen.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcklen.projeto.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
