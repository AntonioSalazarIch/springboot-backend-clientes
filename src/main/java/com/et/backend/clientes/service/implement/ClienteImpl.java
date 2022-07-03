package com.et.backend.clientes.service.implement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.et.backend.clientes.dao.IClienteDao;
import com.et.backend.clientes.entities.Cliente;
import com.et.backend.clientes.service.interfaces.IClienteInterface;

@Service
public class ClienteImpl implements IClienteInterface {

	// Inyeccion de dependencias con @Autowired
	@Autowired
	IClienteDao clienteDao;
	
	@Transactional( readOnly = true )
	@Override
	public List<Cliente> findAll() {
		
		return clienteDao.findAll();
	}

	@Transactional( readOnly = true )
	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);		
	}

	
	@Transactional
	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save( cliente );
	}

	
	@Transactional
	@Override
	public void deleteById(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public Cliente actualizarCliente( Cliente clienteNuevo ) {
		
		//crear un objeto Cliente actual
		Cliente clienteActual = new Cliente();
		
		//obtener el cliente por id
		clienteActual = findById( clienteNuevo.getId() );
		
		//actualizamos el cliente actual con los campos del cliente nuevo
		clienteActual.setApellidoPaterno(clienteNuevo.getApellidoPaterno());
		clienteActual.setApellidoMaterno(clienteNuevo.getApellidoMaterno());
		clienteActual.setNombre(clienteNuevo.getNombre());
		clienteActual.setCi(clienteNuevo.getCi());
		
		clienteDao.save(clienteActual);
		return clienteActual;
		
	}

	
}
