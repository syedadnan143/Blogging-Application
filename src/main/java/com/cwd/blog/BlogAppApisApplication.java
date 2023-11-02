package com.cwd.blog;

import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cwd.blog.config.AppConstants;
import com.cwd.blog.entities.Role;
import com.cwd.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.passwordEncoder.encode("ABC"));
		
		try {
		    // Create the first role
		    Role role = new Role();
		    role.setId(AppConstants.ADMIN_USER);
		    role.setName("ROLE_ADMIN");

		    // Create the second role
		    Role role1 = new Role();
		    role1.setId(AppConstants.NORMAL_USER);
		    role1.setName("ROLE_NORMAL");

		    // Create a list of roles and add the roles to it
		    List<Role> roles = Arrays.asList(role, role1);

		    // Save the roles using the roleRepo.saveAll() method
		    List<Role> result = this.roleRepo.saveAll(roles);

		    // Loop through the saved roles and print their names
		    result.forEach(r -> {
		        System.out.println(r.getName());
		    });
		} catch (Exception e) {
		    // Handle any exceptions that may occur during the database operation
		    e.printStackTrace();
		}

	}
     
}
