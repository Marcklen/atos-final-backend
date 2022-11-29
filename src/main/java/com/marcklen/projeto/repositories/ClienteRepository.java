package com.marcklen.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcklen.projeto.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
