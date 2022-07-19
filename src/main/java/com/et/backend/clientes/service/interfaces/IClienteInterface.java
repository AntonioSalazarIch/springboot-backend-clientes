package com.et.backend.clientes.service.interfaces;

import java.util.List;

//import com.et.backend.clientes.dto.ClienteDto;
import com.et.backend.clientes.entities.Cliente;
import org.springframework.http.ResponseEntity;

public interface IClienteInterface {
	
	// estos metodos son definidos por nosotros
	// mostrar todos los clientes
	public List<Cliente> findAll();
	
	// buscar cliente por id
	public Cliente findById( Long id );
	
	// crear Cliente
	public Cliente save( Cliente cliente );
		
	// eliminar Cliente por id
	public void deleteById ( Long id );
	
	// actualizar todo el objeto 
	public Cliente actualizarCliente( Cliente clienteNuevo );

	public boolean existeClientePorId( Long id );



	public Cliente buscarPorEmail( String email );





	public Cliente buscarPorEmailQuery( String email );

	public Cliente buscarPorPaterno( String apPaterno );

}
