package com.example.HelloWorld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HelloWorld.Repository.NewRespository;

@Service

public class NewService {
    @Autowired
    private NewRespository newRespository;

    public void printtodos() {
        System.out.println(newRespository.getAlltodos());
    }   
}