package com.et.backend.clientes.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.et.backend.clientes.errores.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.et.backend.clientes.entities.Cliente;
import com.et.backend.clientes.service.interfaces.IClienteInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import javax.validation.Valid;


@RestController
@RequestMapping( value = "/api" )
public class ClienteController {

	@Autowired
	IClienteInterface clienteInterface;
	 
	@GetMapping( "/backend-clientes" )
	public String backendUp() {
		return "El Backend CLIENTES esta levantado";
	}
	
	@GetMapping( "/clientes" )
	public ResponseEntity<?> findAll(){
		Map<String, Object> respuesta = new HashMap<>();
		List<Cliente> clientes = clienteInterface.findAll();
		try{
			if( !clientes.isEmpty() ){
				respuesta.put( "mensaje", "OK" );
				respuesta.put( "data", clientes );
			} else{
				respuesta.put( "mensaje", "SIN REGISTROS" );
				respuesta.put( "data", "no existen clientes" );
			}
		} catch ( DataAccessException e ){
			respuesta.put( "mensaje", "ERROR" );
			respuesta.put( "data", e );
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK);
	}
	
	@GetMapping( "/clientes/{idCliente}" )
	public ResponseEntity<?> findById( @PathVariable Long idCliente ) {
		Map<String, Object> respuesta = new HashMap<>();

		if( clienteInterface.existeClientePorId( idCliente ) ){
			Cliente cliente = clienteInterface.findById( idCliente );
			respuesta.put( "mensaje", "OK" );
			respuesta.put( "data", cliente );
		} else{
			throw new RecordNotFoundException( "El cliente con id: " + idCliente + " no existe!");
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK);
	}

	@GetMapping( "/clientes/email/{email}")
	public ResponseEntity<?> buscarPorEmail( @PathVariable String email ){
		Map<String, Object> respuesta = new HashMap<>();
		Cliente cliente = clienteInterface.buscarPorEmail( email );
		if( cliente != null ){
			respuesta.put( "mensaje", "OK" );
			respuesta.put( "data", cliente );
		} else {
			throw new RecordNotFoundException( "El cliente con email: " + email + " no existe!" );
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
	}

	@GetMapping( "/clientes/emailquery/{email}")
	public ResponseEntity<?> buscarPorEmailQuery( @PathVariable String email ){
		Map<String, Object> respuesta = new HashMap<>();
		Cliente cliente = clienteInterface.buscarPorEmailQuery( email );
		if( cliente != null ){
			respuesta.put( "mensaje", "OK" );
			respuesta.put( "data", cliente );
		} else {
			throw new RecordNotFoundException( "El cliente con email: " + email + " no existe!" );
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
	}



	@GetMapping( "/clientes/paterno/{apPaterno}")
	public ResponseEntity<?> buscarPorPaterno( @PathVariable String apPaterno ){
		Map<String, Object> respuesta = new HashMap<>();
		Cliente cliente = clienteInterface.buscarPorPaterno( apPaterno );
		if( cliente != null ){
			respuesta.put( "mensaje", "OK" );
			respuesta.put( "data", cliente );
		} else {
			throw new RecordNotFoundException( "El cliente con email: " + apPaterno + " no existe!" );
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
	}

	@PostMapping( "/clientes" )
	public Cliente save( @Valid @RequestBody Cliente cliente ) {
		return clienteInterface.save(cliente);
	}
	
	@DeleteMapping( "/clientes/{id}" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {

		Map<String, Object> respuesta = new HashMap<>();
		Cliente cliente = clienteInterface.findById( id );
		if( cliente != null ){
			clienteInterface.deleteById( id );
			respuesta.put( "mensaje", "OK" );
			respuesta.put( "data", "Cliente eliminado correctamente " + id );
		} else{
			throw new RecordNotFoundException( "El cliente con id: " + id + " no existe!");
		}
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK);
	}
	
	@PutMapping( "/clientes" )
	public Cliente update(@RequestBody Cliente cliente ) {
		Map<String, Object> respuesta = new HashMap<>();
		
		clienteInterface.actualizarCliente(cliente);
		respuesta.put("mensaje", "OK");
		respuesta.put("cliente actualizado", cliente );
		return clienteInterface.actualizarCliente(cliente);

	}
		
	@PatchMapping( "/clientes/{id}" )
    public Cliente actualizarClientePatch(
    		@PathVariable Long id, 
    		@RequestBody JsonPatch patch ) throws JsonProcessingException, JsonPatchException {
		//obtener el cliente actual
		Cliente clienteActual = clienteInterface.findById(id);
		Cliente clientePatched = patchCliente( patch, clienteActual );
		// actualizamos
		return clienteInterface.actualizarCliente( clientePatched );

    }
	
	

	
	
	
	
	
	//Parcha el objeto json segun la clase 
	private Cliente patchCliente( JsonPatch patch, Cliente clienteActual ) 
			throws JsonPatchException, JsonProcessingException
    {   
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply( 
        		objectMapper.convertValue(clienteActual, JsonNode.class ) );
        return objectMapper.treeToValue( patched, Cliente.class );
    }
}
