package com.ffmoyano.urlshortener.service;

import com.ffmoyano.urlshortener.dto.UserDto;
import com.ffmoyano.urlshortener.entity.AppUser;
import com.ffmoyano.urlshortener.entity.Role;
import com.ffmoyano.urlshortener.repository.RoleRepository;
import com.ffmoyano.urlshortener.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public AppUser getUserFromSession(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User principal = (User)authentication.getPrincipal();
            return userRepository.findByEmail(principal.getUsername());
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

    public boolean userExists(String email) {
        return userRepository.existsAppUserByEmail("email");
    }

    public void signup(UserDto userDto) {
        String encodedPassword = encoder.encode(userDto.password());
        AppUser user = new AppUser();
        user.setActive(false);
        user.setEmail(userDto.email());
        user.setPassword(encodedPassword);
        Role role = roleRepository.findByName("ROLE_USER");
        var roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmailAndActive(email, true);
        if ( user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }
        var authorities = new ArrayList<SimpleGrantedAuthority>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
