package com.example.HelloWorld.Repository;

import org.springframework.stereotype.Component;

@Component

public class NewRespository {
    public String getAlltodos() {
        return "This is a TODO from Repository!";
    }   
}