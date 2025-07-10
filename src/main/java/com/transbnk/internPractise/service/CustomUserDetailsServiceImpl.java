package com.transbnk.internPractise.service;

import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

   private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepo.findByUsername(username);
        if (Objects.isNull(user)){
            System.out.println("User not present in Database.");
            throw new UsernameNotFoundException("No user Found.");
        }
        return new CustomUserDetails(user);
    }
}
