package com.kuldeep.demo.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="users")
public class Users implements UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="user_name", nullable=false,length=100)
	private String name;
	private String email;
	private String password;
	private String about;

	//user is here variable name of post
	//fetch lazy parent ask then child wont be searched
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL,fetch =FetchType.LAZY)
	private List<Post> posts =new ArrayList();
	
	@ManyToMany(cascade=CascadeType.ALL,fetch =FetchType.EAGER)
	@JoinTable(name="user_role",joinColumns=@JoinColumn(name="users",referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="role",referencedColumnName="Id"))
	private Set<Role> roles=new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities= this.roles.stream()
				.map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
		
}

/*
 * user entity
 * user dto
 * user repo interface extends jpa
 * user service interface declare function
 * user service implementation class  define function
 * user controler define mapping for th0se fucntion
 * add validation in dto
 * 
 * 
 * */
