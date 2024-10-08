package com.spring.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.blog.config.AppConstants;
import com.spring.blog.entities.Role;
import com.spring.blog.entities.User;
import com.spring.blog.exceptions.ResourceNotFoundException;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.repositories.RoleRepo;
import com.spring.blog.repositories.UserRepo;
import com.spring.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {

		User savedUser = this.userRepo.save(dtoToUser(userDto));

		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);

		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();

		List<UserDto> list = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return list;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}

	// we will try to ModelMapper modelMapper = new ModelMapper(); to avoid
	// boilerplate code
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setName(userDto.getName());
//		user.setAbout(userDto.getAbout());

		return user;

	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setAbout(user.getAbout());
//		userDto.setEmail(user.getEmail());
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());

		return userDto;
	}

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        
        User user = this.modelMapper.map(userDto,User.class);
        
        //encoded the password
        
        String encode = this.bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        
        //roles get
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        
        user.getRoles().add(role);
        
        User newUser = this.userRepo.save(user);
        
        
        return this.modelMapper.map(newUser,UserDto.class);
    }
}
