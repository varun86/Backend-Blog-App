package com.spring.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
    
    
}
