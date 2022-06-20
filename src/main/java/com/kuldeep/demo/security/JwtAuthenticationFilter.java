package com.kuldeep.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// get token
		
		String requestToken=request.getHeader("Authorization");
		//bearer 234554
		String username=null;
		String token=null;
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			token=requestToken.substring(7);
			try {
				username=jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Unable to get jwt token");
			}
			catch(ExpiredJwtException ex)
			{
				System.out.println("jwt token expired");
			}
			catch(MalformedJwtException ex)
			{
				System.out.println("Invalid jwt");
			}
		}
		else
		{
			System.out.println("Jwt token does not starts with Bearer");
		}
		
		//once we get the token then validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetail=this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetail))
			{
				UsernamePasswordAuthenticationToken  authentication= new UsernamePasswordAuthenticationToken(userDetail, null,userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else
			{
				
				System.out.println("Invalid jwt token");
			}
		}
		else
		{
			System.out.println("Username is null pr context is not null");
		}
		filterChain.doFilter(request, response);
		
		
		
	}

}
