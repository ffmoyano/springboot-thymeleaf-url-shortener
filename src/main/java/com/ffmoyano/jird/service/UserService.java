package com.ffmoyano.jird.service;

import com.ffmoyano.jird.entity.AppUser;
import com.ffmoyano.jird.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User principal = (User)authentication.getPrincipal();
            AppUser user = userRepository.findByEmail(principal.getUsername());
            return user;
        } else {
            return null;
        }
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);
        if ( user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
