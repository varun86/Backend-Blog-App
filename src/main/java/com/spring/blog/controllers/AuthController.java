package com.spring.blog.controllers;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.blog.entities.User;
import com.spring.blog.exceptions.ApiException;
import com.spring.blog.payloads.JwtAuthRequest;
import com.spring.blog.payloads.JwtAuthResponse;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.repositories.UserRepo;
import com.spring.blog.security.JwtTokenHelper;
import com.spring.blog.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth/")
@Tag(name = "Auth Controller", description = "APIs for Authentication !!")
public class AuthController {
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ModelMapper mapper;
    
//    @Operation(summary = "My endpoint")
//    @SecurityRequirement(name = "")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authrequest) throws Exception {
        
        this.authenticate(authrequest.getUsername(), authrequest.getPassword());
        
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authrequest.getUsername());
        
        String token = this.jwtTokenHelper.generateToken(userDetails);
        
        JwtAuthResponse response = new JwtAuthResponse();
        
        response.setToken(token);
        response.setUser(this.mapper.map((User) userDetails, UserDto.class));
        
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }
    
    
    
    @GetMapping("/current-user/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
    }
    
    
    
    private void authenticate(String username, String password) throws Exception {
        
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username, password
        );
        
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e) {
            
            throw new ApiException(" Invalid Username or Password  !!");
            
        }
        
    }
    
    // register new user API
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
        
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
        
    }
    
}
