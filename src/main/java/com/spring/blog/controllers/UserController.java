package com.spring.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.spring.blog.payloads.ApiResponse;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@Tag(name="UserController",description = "APIs for User Operations")
public class UserController {

	@Autowired
	private UserService userService;

	// create user
	@PostMapping("/")
	@Operation(summary = "create new user !!")
	@ApiResponses(value= {
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Success |OK"),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401",description = "no authorised !!"),
	    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201",description = "new user created !!")                
	})
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto createUserDto = this.userService.createUser(userDto);

		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	// PUT -update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {

		UserDto updateUser = this.userService.updateUser(userDto, uid);

		return ResponseEntity.ok(updateUser);
	}

	//ADMIN
	// DELETE-delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);

		// without ApiResponse
		// return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		// return ResponseEntity.ok(Map.of("message","user deleted successfully")

		// with ApiResponse
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

	// Get-All users
	@GetMapping("/")
	@Operation(summary = "Get all users")
	public ResponseEntity<List<UserDto>> getAllUsers() {

		List<UserDto> allUsers = this.userService.getAllUsers();

		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
	}

	// GET-get user
	@GetMapping("/{userId}")
	@Operation(summary = "Get Single user byh User Id !!")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		UserDto userDto = this.userService.getUserById(userId);
//		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);

		// or can be
		return ResponseEntity.ok(userDto);
	}

}
