package com.viper.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viper.auth.model.User;

public interface AuthRepo extends JpaRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);
}
