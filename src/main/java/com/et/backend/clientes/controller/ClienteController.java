package com.et.backend.clientes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/api" )
public class ClienteController {

	@GetMapping( "/backend-clientes" )
	public String backendUp() {
		
		return "El Backend CLIENTES esta levantado";
	}
}
