package com.example.backendglobaldirectory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(
                        csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "register")
                        .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/approve")
                        .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/posts")
                        .hasAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/logout")
                        .authenticated()
                        .anyRequest().permitAll())
                .formLogin(form ->
                        form.usernameParameter("email")
                                .successForwardUrl("/loginSuccess")
                                .permitAll())
                .logout(logout ->
                        logout
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/logoutSuccess"))
                .build();
    }

}
