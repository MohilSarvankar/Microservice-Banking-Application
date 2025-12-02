package com.viper.auth.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.viper.auth.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(User user) {
		
		String token = Jwts.builder()
							.claims()
							.subject(user.getUsername())
							.issuedAt(new Date())
							.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
							.and()
							.signWith(getSigningKey(), Jwts.SIG.HS256)
							.compact();
			
		return token;
	}

	public Boolean validate(String token) {
		Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token);	
		
		return true;
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
}
