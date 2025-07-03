package com.transbnk.internPractise.service;

import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;


    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }


    public Page<User> getAllUsers(Pageable pageable, String field) {
        Pageable finalPageable = (field != null && !field.isEmpty())
                ? PageRequest.of(pageable.getPageNumber(), 10, Sort.by(Sort.Direction.DESC, field))
                : PageRequest.of(pageable.getPageNumber(), 10);
        return userRepo.findAll(finalPageable);
//        return userRepo.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC,field)));

    }


    public User findUserByUserName(String username){
        return userRepo.findByUsername(username);
    }

    public  Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }
    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }
}
