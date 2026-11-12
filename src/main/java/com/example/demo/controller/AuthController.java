package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import entity.Personne;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

   
private com.example.demo.services.AuthServices authServ;
	
public AuthController(com.example.demo.services.AuthServices authServices) {
    this.authServ = authServices;
}
	
	@PostMapping("/register")
	public Personne register(@RequestBody Personne utl) {
		return authServ.register(utl);
	}

	@PostMapping("/login")
	public Personne login(@RequestBody Personne utl){
		return authServ.login(utl);
	}
}