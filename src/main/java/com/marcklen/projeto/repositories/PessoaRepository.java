package com.marcklen.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcklen.projeto.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
