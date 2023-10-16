package com.sailotech.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sailotech.authservice.dto.UserDto;
import com.sailotech.authservice.service.AuthService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void register(@RequestBody UserDto userDto) {
		authService.registerUser(userDto);
	}
	
	@PostMapping("token")
	public ResponseEntity<?> getToken(@RequestBody UserDto userDto) {
		String token = authService.getToken(userDto);
		if(token == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		
		return new ResponseEntity(token, HttpStatus.OK);
	}
	
	/*
	@PostMapping("validate")
	public ResponseEntity<?> validateToken(@RequestHeader("AUTHORIZATION") String jwtToken) {
		
	}
	*/
}
