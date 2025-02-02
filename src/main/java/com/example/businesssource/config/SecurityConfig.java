package com.example.businesssource.config;

import com.example.businesssource.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/quiz/**", "/results", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/business-plan/create/**").hasRole("USER") // Only users can access dashboard
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only admins can access admin panel
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/business-plan/create")
                        .failureUrl("/login?error=true") // Redirect to login page with an error on failed login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Endpoint for logging out
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login after logout
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Limit to one active session
                        .maxSessionsPreventsLogin(false) // Allow new sessions to kick out old ones
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // Allows frames from the same origin
                )
                .headers(headers -> headers
                        .addHeaderWriter((request, response) -> {
                            response.setHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; Path=/; HttpOnly; SameSite=Lax");
                        })
                );
        http.authenticationProvider(authenticationProvider()); // Ensure provider is set

        System.out.println("Security configuration loaded correctly");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // Use the password encoder bean
        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
