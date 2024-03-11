package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.auth.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final MyUserDetailsService myUserDetailsService;

	@Bean
	BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(myUserDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req
						.requestMatchers("/")
						.authenticated()
						.anyRequest()
						.permitAll())
				.formLogin(form -> form
						.loginPage("/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.loginProcessingUrl("/login-request")
						.defaultSuccessUrl("/success")
						.failureUrl("/failure"))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login"))
				.exceptionHandling(e -> e
						.accessDeniedPage("/accessDenied"))
				.build();
	}
}
