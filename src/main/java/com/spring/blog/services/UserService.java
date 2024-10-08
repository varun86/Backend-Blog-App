package com.spring.blog.services;

import java.util.List;

import com.spring.blog.payloads.UserDto;

public interface UserService {
    
    UserDto registerNewUser(UserDto user);
    
    UserDto createUser(UserDto user);
    
    UserDto updateUser(UserDto user, Integer userid);
    
    UserDto getUserById(Integer userId);
    
    List<UserDto> getAllUsers();
    
    void deleteUser(Integer userId);
}
