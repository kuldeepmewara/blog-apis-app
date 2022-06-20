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
import com.kuldeep.demo.entities.MyUser;
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
			
//			MyUser myuser=new User();
//			myuser.setId(AppConstants.ADMIN_USER_ID);
//			myuser.setAbout("hello");
//			myuser.setEmail("kuldeep@gmail.com");
//			myuser.setName("kuldeep");
//			myuser.setPassword(this.passwordEncoder.encode("123"));
//			myuser.getRoles().add(role);
//			MyUser u=this.userRepo.save(myuser);
			//System.out.println(u.getRoles().toString());
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}

}
