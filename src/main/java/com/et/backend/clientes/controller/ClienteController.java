package com.et.backend.clientes.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.et.backend.clientes.entities.Cliente;
import com.et.backend.clientes.errores.RecordNotFoundException;
import com.et.backend.clientes.service.interfaces.IClienteInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
//import com.github.fge.jsonpatch.JsonPatch;
//import com.github.fge.jsonpatch.JsonPatchException;
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
	public ResponseEntity<?> index(){
		
		Map<String, Object> respuesta = new HashMap<>();
		try{
			// verificar si existen clientes en la bbdd
			List<Cliente> clientes = clienteInterface.findAll();
			if( clientes.size() == 0 ){
				respuesta.put( "mensaje", "SIN REGISTROS" );
				respuesta.put( "clientes", "no existen clientes" );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
			} else{
				respuesta.put( "mensaje", "OK" );
				respuesta.put( "clientes", clientes );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
			}

		} catch ( DataAccessException e ){
			respuesta.put( "mensaje", "ERROR" );
			respuesta.put( "log", e );
			return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@GetMapping( "/clientes/{idCliente}" )
	public ResponseEntity<?> findById( @PathVariable Long idCliente ) {
		
		Map<String, Object> respuesta = new HashMap<>();
		Cliente cliente = clienteInterface.findById( idCliente );
		try {
			if (cliente == null) {
				respuesta.put("mensaje", "SIN REGISTROS");
				respuesta.put("clientes", "no existe el cliente " + idCliente);
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
			} else {
				respuesta.put("mensaje", "OK" );
				respuesta.put("clientes", clienteInterface.findById(idCliente));
				return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
			}

		} catch (DataAccessException e) {
			respuesta.put("mensaje", "ERROR");
			respuesta.put( "log", e );
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping( "/clientes" )
	public ResponseEntity<?> save( @Valid @RequestBody Cliente cliente ) {
		Map<String, Object> respuesta = new HashMap<>();
		try{
			respuesta.put("mensaje", "OK");
			respuesta.put("cliente guardado satisfactoriamente", clienteInterface.save(cliente));
			return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
		} catch ( DataAccessException e ){
			respuesta.put( "mensaje", "ERROR" );
			respuesta.put( "log", e );
			return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@DeleteMapping( "/clientes/{id}" )
	public ResponseEntity<?> deleteById( @PathVariable Long id ) {
		Map<String, Object> respuesta = new HashMap<>();
		try{
			if( clienteInterface.findById(id) == null ) {
				respuesta.put( "mensaje", "SIN REGISTROS" );
				respuesta.put( "clientes", "no existe el cliente " + id );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
			} else {
				clienteInterface.deleteById( id );
				respuesta.put("mensaje", "OK");
				respuesta.put("cliente eliminado satisfactoriamente", id );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );	
			}

		} catch ( DataAccessException e ){
			respuesta.put( "mensaje", "ERROR" );
			respuesta.put( "log", e );
			return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
	
	@PutMapping( "/clientes" )
	public ResponseEntity<?> update(@RequestBody Cliente cliente ) {	
		Map<String, Object> respuesta = new HashMap<>();
		
		clienteInterface.actualizarCliente(cliente);
		respuesta.put("mensaje", "OK");
		respuesta.put("cliente actualizado", cliente );
		return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
		
		/*
		 * try{ if( clienteInterface.findById( cliente.getId() ) == null ) {
		 * respuesta.put( "mensaje", "SIN REGISTROS" ); respuesta.put( "clientes",
		 * "no existe el cliente " + cliente ); return new ResponseEntity<Map<String,
		 * Object>>( respuesta, HttpStatus.OK ); } else {
		 * clienteInterface.actualizarCliente(cliente); respuesta.put("mensaje", "OK");
		 * respuesta.put("cliente actualizado", cliente ); return new
		 * ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK ); }
		 * 
		 * } catch ( DataAccessException e ){ respuesta.put( "mensaje", "ERROR" ); respuesta.put(
		 * "log", e ); return new ResponseEntity<Map<String, Object>>( respuesta,
		 * HttpStatus.INTERNAL_SERVER_ERROR ); }
		 */
	}
		
	@PatchMapping( "/clientes/{id}" )
    public ResponseEntity<?> actualizarClientePatch( 
    		@PathVariable Long id, 
    		@RequestBody JsonPatch patch ) 
    				throws JsonProcessingException, JsonPatchException {		
		Map<String, Object> respuesta = new HashMap<>();

		try{
		
			if( clienteInterface.findById( id ) == null ) {
				respuesta.put( "mensaje", "SIN REGISTROS" );
				respuesta.put( "clientes", "no existe el cliente " + id );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
			} else {
				//obtener el cliente actual
	            Cliente clienteActual = clienteInterface.findById(id);
	            Cliente clientePatched = patchCliente( patch, clienteActual );
	            // actualizamos
	            clienteInterface.actualizarCliente( clientePatched );
				respuesta.put("mensaje", "OK");
				respuesta.put("cliente actualizado", clientePatched );
				return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.OK );
			}

		} catch ( DataAccessException e ){
			respuesta.put( "mensaje", "ERROR" );
			respuesta.put( "log", e );
			return new ResponseEntity<Map<String, Object>>( respuesta, HttpStatus.INTERNAL_SERVER_ERROR );
		}        
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
