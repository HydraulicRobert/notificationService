package com.proxy.notifications.configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestResponseLogger extends OncePerRequestFilter {

	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		String ip = request.getRemoteAddr();
		DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		System.out.println(LocalDateTime.now().format(datetimeFormat)+";"+ip+";"+method+";"+query+";"+uri);
		filterChain.doFilter(request, response);
	}	

}
