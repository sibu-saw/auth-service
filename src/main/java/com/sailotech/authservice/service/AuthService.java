package com.sailotech.authservice.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

		if (!user.isPresent()) {
			return null;
		}
		if (!passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
			return null;
		}
		
		//Ideally, get it from the database
		Map<String, Object> roles = new HashMap<>();
		roles.put("roles", "ADMIN");

		return JwtService.getToken(user.get().getUserName(), roles);

	}
	
	public boolean validateToken(String token) {
		return JwtService.validateToken(token);
	}

}
