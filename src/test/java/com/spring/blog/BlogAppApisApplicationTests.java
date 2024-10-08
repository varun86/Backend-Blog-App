package com.spring.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepo repo;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {
		String name = this.repo.getClass().getName();
		
		Package package1 = this.repo.getClass().getPackage();
		System.out.println(name);
		System.out.println(package1);
	}
}
