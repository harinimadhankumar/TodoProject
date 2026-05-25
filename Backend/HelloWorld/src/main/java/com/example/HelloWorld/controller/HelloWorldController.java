package com.example.HelloWorld.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    String sayHelloWorld() {
       return "Hello, World! from Spring Boot!";
    }
    @PostMapping("/echo")
    String echoMessage() {
       return "trying post method";
    }
    @PutMapping("/put")
    
        String putMessage() {
            return "trying put method";
        }   
    @DeleteMapping("/delete")
    
        String deleteMessage() {
            return "trying delete method";
        }   
    }

