package com.example.HelloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HelloWorld.models.User;
import com.example.HelloWorld.Repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
     // Create Todo
    public User createUser(User user) {
        return userRepository.save(user);       
    }   

    //Get Todo by ID
    public User getTodoById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}