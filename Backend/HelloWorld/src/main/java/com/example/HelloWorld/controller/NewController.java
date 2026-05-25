package com.example.HelloWorld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HelloWorld.service.NewService;

@RestController
@RequestMapping("/api/user")
public class NewController {

    //Dependency and Inversion of Control
    @Autowired
    private NewService newService;
    
    @GetMapping("/todos")
    String gettodos() {
        newService.printtodos();
        return "Fetched todos!";
    }

    //Simple GET
    @GetMapping("/todo")
    String todo() {
        return "This is a TODO endpoint!";
    }

    //Path Variable GET
    @GetMapping("/{id}")
    String info(@PathVariable long id) {
        return "This is a Spring Boot application.";
    }

    //Request Param
    @GetMapping("")
    String todo(@RequestParam("todoId") long id) {
        return "Dhinakaran.";
    }

    //Request Body
    @PostMapping("/create")
    String create(@RequestBody String user) {
        return user;
    }

    //Path Variable PUT
    @PutMapping("/{id}")
    String update(@PathVariable long id) {
        return "Update a Spring Boot application.";
    }

    //Path Variable DELETE
    @DeleteMapping("/{id}")
    String delete(@PathVariable long id) {
        return "Delete is a Spring Boot application.";
    }
}