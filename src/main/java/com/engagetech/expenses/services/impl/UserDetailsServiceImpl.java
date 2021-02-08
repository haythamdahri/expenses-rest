package com.engagetech.expenses.services.impl;

import com.engagetech.expenses.dao.UserRepository;
import com.engagetech.expenses.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Haytham DAHRI
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Load user from database using his username
        Optional<User> optionalUser = this.userRepository.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (optionalUser.isPresent()) {
            optionalUser.get().getRoles().forEach(role ->
                    grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName().name()))
            );
            return new org.springframework.security.core.userdetails.User(optionalUser.get().getUsername(),
                    optionalUser.get().getPassword(), grantedAuthorities);
        }
        throw new UsernameNotFoundException("No user found with " + username);
    }
}
