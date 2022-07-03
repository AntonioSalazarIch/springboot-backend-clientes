package com.et.backend.clientes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.et.backend.clientes.entities.Cliente;

//definir el objeto Cliente, Long id
public interface IClienteDao extends JpaRepository<Cliente, Long>{

}
