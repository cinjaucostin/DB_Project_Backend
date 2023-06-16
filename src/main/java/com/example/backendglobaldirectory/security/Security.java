package com.example.backendglobaldirectory.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(customAuthenticationProvider);

        return new ProviderManager(authenticationProviders);
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
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
                .addFilterAt(new CustomUsernamePasswordAuthenticationFilter(authenticationManagerBean()),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(form ->
                        form.usernameParameter("email")
                                .successForwardUrl("/loginSuccess")
                                .permitAll())
                .logout(logout ->
                        logout
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/logoutSuccess"));

        http.authenticationManager(authenticationManagerBean());

        return http.build();
    }

}
