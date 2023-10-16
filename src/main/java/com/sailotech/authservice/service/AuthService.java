package com.sailotech.authservice.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sailotech.authservice.dto.UserDto;
import com.sailotech.authservice.persistence.entity.User;
import com.sailotech.authservice.persistence.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	
	private String signingKey = "SFDFS834KSKDF@RWELJLJDSFSA)S78787454SFSFDSSLSCADSFDSU8734398#$$";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserDto registerUser(UserDto userDto) {
		User newUser = modelMapper.map(userDto, User.class);
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser = userRepository.save(newUser);
		
		return modelMapper.map(newUser, UserDto.class);
	}

	public String getToken(UserDto userDto) {
		Optional<User> user = userRepository.findUserByUserName(userDto.getUserName());
		
		if(!user.isPresent()) {
			return null;
		}
		if(!passwordEncoder.matches(userDto.getPassword(), user.get().getPassword()))
		{
			return null;
		}
		
		Instant issuedAt = Instant.now();
		Date issued = Date.from(issuedAt);
		Date expiration = Date.from(issuedAt.plus(24, ChronoUnit.HOURS));
		
		var roles = new HashMap<String, Object>();
		roles.put("role", "ADMIN");
		
		return Jwts.builder()
		.subject(userDto.getUserName())
		.expiration(expiration)
		.issuedAt(issued)
		.claims(roles)
		.signWith(Keys.hmacShaKeyFor(signingKey.getBytes()))
		.compact();
		
	}
 

}
