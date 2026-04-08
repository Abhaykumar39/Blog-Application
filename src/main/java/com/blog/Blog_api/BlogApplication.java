package com.blog.Blog_api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.Blog_api.config.AppConstants;
import com.blog.Blog_api.entities.Role;
import com.blog.Blog_api.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void run(String... args) throws Exception {
		System.out.println("Password: " + this.passwordEncoder.encode("12345"));

		try {
			Role role = new Role();
			role.setId(AppConstants.Admin);
			role.setName("ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.Normal);
			role1.setName("NORMAL");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
