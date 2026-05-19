package com.tw.step.readit.config;

import com.tw.step.readit.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSecurity
public class Security {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/is-logged-in", "/login-user", "/api/auth/register", "/login").permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().authenticated())

                .formLogin(login -> login.loginProcessingUrl("/login"));

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://localhost:9000", "http://localhost:5173"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "PATCH"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public AppUserDetailsService appUserDetailsService(PasswordEncoder passwordEncoder) {
        ConcurrentHashMap<String, UserDetails> users = new ConcurrentHashMap<>();
        UserDetails dinesh = User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username("dinesh")
                .password("Dinesh@2026")
                .roles("USER")
                .build();

        users.put(dinesh.getUsername(), dinesh);
        return new AppUserDetailsService(users, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
