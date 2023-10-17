package com.sailotech.authservice.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.WeakKeyException;

@Service
public class JwtService {

	//@Value("${jwt.key}")
	private static String signingKey = "SFDFS834KSKDF@RWELJLJDSFSA)S78787454SFSFDSSLSCADSFDSU8734398#$$";

	public static String getToken(String userName, Map<String, Object> roles)
			throws WeakKeyException, InvalidKeyException {
		Instant issuedAt = Instant.now();
		Date issued = Date.from(issuedAt);
		Date expiration = Date.from(issuedAt.plus(24, ChronoUnit.HOURS));

		return Jwts.builder()
				.subject(userName)
				.issuedAt(issued)
				.expiration(expiration)
				.claims(roles)
				.signWith(Keys.hmacShaKeyFor(signingKey.getBytes()))
				.compact();
	}

	public static boolean validateToken(String token) {
		
		try {
			Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(signingKey.getBytes()))
			.build()
			.parse(token);
		}
		catch (ExpiredJwtException | MalformedJwtException | SecurityException| IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	
	}
}