package com.spring.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	
	Optional<User> findByEmail(String email);
}
