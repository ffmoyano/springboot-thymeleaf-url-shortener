package com.ffmoyano.urlshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers(ALLOWED_RESOURCES).permitAll()
                                .antMatchers("/*").permitAll()  //we dont want to allow subfolders, only shorturl param so only one asterisk
                                .antMatchers("/auth/**").permitAll()
                                .antMatchers("/link/**", "/user/**").hasRole("USER")
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/auth/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/link/")
                                .failureUrl("/auth/login?error")
                                .permitAll())
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "GET"))
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/auth/login"))
                .build();

    }

}
