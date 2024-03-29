package com.benchikh.demo.dao;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDao {
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(new User("anas@anas.com", "anas", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))));

    public UserDetails findUserByEmail(String email) {
        return APPLICATION_USERS.stream().filter(user -> user.equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
    }

}
