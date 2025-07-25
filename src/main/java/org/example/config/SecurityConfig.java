package org.example.config;

import org.example.security.CustomLoginSuccessHandler;
import org.example.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final UserService userService;
    private final AppConfig appConfig;
    private CustomLoginSuccessHandler customLoginSuccessHandler;


    public SecurityConfig(UserService userService, AppConfig appConfig) {
        this.userService = userService;
        this.appConfig = appConfig;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomLoginSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/register", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/workspaces/**").hasAnyRole("ADMIN")
                        .requestMatchers("/book/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}


