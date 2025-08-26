package com.proxy.notifications.configuration;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class websecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("filter");
		
		http
			.csrf(csrf -> csrf.disable())
	   		.authorizeHttpRequests(auth -> auth
	   				.anyRequest()
	   				.hasRole("USER")
//	   				.permitAll()
	   				)
	   		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	   		.authenticationProvider(authenticationProvider(users(), passwordEncoder()))
	   		.httpBasic(Customizer.withDefaults())
	   		;
	   		
//	   		
//	   		
//	   		.authenticationManager(authenticationManager(null));

		return http.build();

	}
	
	@Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }	
	
	@Bean
	public UserDetailsService users() {
		cfgInputOutput chkIOUser = new cfgInputOutput();
		String strCfgPath = global.getGstrcfgpath();
		String strFileName = global.getGstruserlist();
		List<String[]> userListString = cfgInputOutput.getUserList(strCfgPath,strFileName);
		UserBuilder users = User.builder();
		List<UserDetails> userList = new ArrayList<>();
		for(int i = 0;i<userListString.size();i++)
		{
			userList.add(users
					.username(userListString.get(i)[0])
					.password(userListString.get(i)[1])
					.roles("USER")
					.build());
		}
		/*UserDetails user = users
				.username("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		UserDetails admin = users
				.username("admin")
				.password(passwordEncoder().encode("password"))
				.roles("USER","ADMIN")
				.build();*/
		return new InMemoryUserDetailsManager(userList);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration
			) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider (
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder
	) {
		DaoAuthenticationProvider daoAuthenticationProvider =
		new DaoAuthenticationProvider(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
		manager.setAllowNullValues(false);
		manager.setCacheNames(Arrays.asList("sqlChkNotAllTop",
											"sqlChkNotAllBottom",
											"sqlChkNotTop1",
											"sqlChkSet",
											"sqlChk6Sec",
											"notificationList",
											"sixSecondTimestamp",
											"settingsTimestamp",
											"mostCurrentNotificationTimestamp",
											"mostCurrentNotificationTimestampAll",
											"userList"));
		return manager;
	}
}