package com.et.backend.clientes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.et.backend.clientes.entities.Cliente;
import org.springframework.data.jpa.repository.Query;

//definir el objeto Cliente, Long id
public interface IClienteDao extends JpaRepository<Cliente, Long>{

    //JPQL
    public Cliente findByEmail( String email );

    @Query( "select c from Cliente c where c.email = ?1" )
    public Cliente buscarPorEmailQuery( String email );

    @Query( value = "select * from clientes c where c.email = ?1",
            nativeQuery = true )
    public Cliente buscarPorEmailQueryNativo( String email );

    public Cliente findByApellidoPaterno( String apPaterno );



}
