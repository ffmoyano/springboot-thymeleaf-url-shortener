package com.ffmoyano.jird.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final String[] ALLOWED_RESOURCES = {"/login", "/resources/**", "/static/**", "/css/**", "/icon/**", "/js/**", "/logo/**", "/favicon.ico"};

    // UserDetails: UserService which implements UserDetailsService, overrided method loadUserByUsername
    // PasswordEncoder: BCryptPasswordEncoder, whose bean is declared in BeanConfiguration, retrieved by
    //      internal AuthenticationConfiguration method getPasswordEncoder()
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {

        return http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers(ALLOWED_RESOURCES).permitAll()
                                .antMatchers("/*").permitAll()  //we dont want to allow subfolders, only shorturl param
                                .antMatchers("/user/**").hasRole("USER")
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/user/links")
                                .failureUrl("/login?error")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies())
                .build();

    }

}
