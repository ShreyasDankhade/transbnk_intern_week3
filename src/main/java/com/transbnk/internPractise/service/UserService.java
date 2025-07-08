package com.transbnk.internPractise.service;

import com.transbnk.internPractise.dto.LoginRequestDto;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable, String field) {
        Pageable finalPageable = (field != null && !field.isEmpty())
                ? PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.DESC, field))
                : PageRequest.of(pageable.getPageNumber(), 10);
        return userRepo.findAll(finalPageable);
//        return userRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC,field)));
    }

    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    public String verify(LoginRequestDto loginRequestDto) {
        Objects.requireNonNull(loginRequestDto, "Login request cannot be null");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()
                    )
            );

            if (!authentication.isAuthenticated()) {
                throw new AuthenticationServiceException("Authentication failed for user: " + loginRequestDto.getUsername());
            }

            // Consider using the authenticated principal (UserDetails) instead of DTO
            return jwtService.generateToken(loginRequestDto);

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid username or password", ex);
        } catch (AuthenticationException ex) {
            throw new AuthenticationServiceException("Authentication failed", ex);
        }
    }
}
