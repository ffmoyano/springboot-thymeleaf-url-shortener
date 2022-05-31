package com.ffmoyano.jird.configuration;

import com.ffmoyano.jird.service.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] ALLOWED_RESOURCES = {"/login", "/resources/**", "/static/**", "/css/**", "/icon/**", "/js/**","/logo/**",  "/favicon.ico"};

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder encoder;


    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers(ALLOWED_RESOURCES).permitAll()
                                .antMatchers("/user/**").hasRole("USER")
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/user/index")
                                .failureUrl("/login?error")
                                .permitAll())
                .logout( logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .deleteCookies());


    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//
//        http.csrf().disable()
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers("/", "/login").permitAll()
//                                .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                                .antMatchers("/user/**").hasAnyRole("USER")
//                                .anyRequest().authenticated()
//                )
//                .passwordManagement(passwordManagement ->
//                        passwordManagement
//                                .changePasswordPage("/forgotPassword"))
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/login")
//                                .defaultSuccessUrl("/login", true)
//                                .failureUrl("/loginError")
//                                .permitAll()
//                )
//                .rememberMe();
//    }


}
