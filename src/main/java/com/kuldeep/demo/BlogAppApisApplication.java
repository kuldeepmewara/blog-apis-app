package com.kuldeep.demo;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kuldeep.demo.config.AppConstants;
import com.kuldeep.demo.entities.Role;
import com.kuldeep.demo.entities.User;
import com.kuldeep.demo.repositories.RoleRepo;
import com.kuldeep.demo.repositories.UserRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	

	@Autowired
	private UserRepo userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("123"));
		
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles=new ArrayList<Role>();
			roles.add(role);
			roles.add(role1);
			
			List<Role> result=this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
//			User user=new User();
//			user.setId(AppConstants.ADMIN_USER_ID);
//			user.setAbout("hello");
//			user.setEmail("kuldeep@gmail.com");
//			user.setName("kuldeep");
//			user.setPassword(this.passwordEncoder.encode("123"));
//			user.getRoles().add(role);
//			User u=this.userRepo.save(user);
			//System.out.println(u.getRoles().toString());
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}

}
