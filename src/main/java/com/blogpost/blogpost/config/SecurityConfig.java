package com.blogpost.blogpost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers("/v1/auth/**").permitAll()
//                        .requestMatchers("/v1/users/current").authenticated() // ✅ change here
//                        .requestMatchers(HttpMethod.GET, "/v1/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
//
//                        .requestMatchers(HttpMethod.GET, "/blogs/**", "/forums/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll() // Allow GET categories
//                        .requestMatchers(HttpMethod.GET, "/api/v1/blogs/**", "/api/v1/forums/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                );
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()


                    .requestMatchers("/v1/auth/**").permitAll()

                    .requestMatchers(HttpMethod.GET, "/v1/forums/**", "/v1/categories/**", "/v1/blogs/**").permitAll()

                    .requestMatchers(HttpMethod.POST, "/v1/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/v1/categories/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/v1/forums/**").hasRole("ADMIN")


                    .anyRequest().authenticated()
            )

            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            );

    return http.build();
}
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
