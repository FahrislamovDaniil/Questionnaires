package com.practice.questionnaires.security;

import com.practice.questionnaires.models.QueUser;
import com.practice.questionnaires.repos.QueUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final QueUserRepo queUserRepo;

    @Autowired
    public UserDetailsServiceImpl(QueUserRepo queUserRepo) {
        this.queUserRepo = queUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueUser queUser = queUserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User by username " + username + " was not found"));
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(queUser.getRole().toString()));
        return new User(queUser.getUsername(), queUser.getPassword(), authorities);
    }
}