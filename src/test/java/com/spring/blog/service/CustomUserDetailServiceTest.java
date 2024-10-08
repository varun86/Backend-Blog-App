package com.spring.blog.service;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.spring.blog.entities.User;
import com.spring.blog.repositories.UserRepo;
import com.spring.blog.security.CustomUserDetailService;


public class CustomUserDetailServiceTest {


    @InjectMocks
    private CustomUserDetailService customUserDetailService;


    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void loadUserByUsernameTest() {
        User user = User.builder().name("kumar").password("avdchd").roles(new HashSet<>()).build();

        when(userRepo.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
        UserDetails userDetails = customUserDetailService.loadUserByUsername("Kumar");
        Assertions.assertNotNull(userDetails);

    }
}
