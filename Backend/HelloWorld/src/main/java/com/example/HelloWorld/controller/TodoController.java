package com.example.HelloWorld.controller;

import com.example.HelloWorld.models.Todo;
import com.example.HelloWorld.service.TodoService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
@Slf4j
public class TodoController {
    @Autowired
    private TodoService todoService;

    // Creating a new todo item
    @PostMapping("/create")
    ResponseEntity<Todo> createUsr(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    // Getting a todo item by id
    @GetMapping("/get/{id}")
    ResponseEntity<Todo> gettinguser(@PathVariable Long id) {
        try {
            Todo newtodo = todoService.getbyId(id);
            return new ResponseEntity<>(newtodo, HttpStatus.OK);
        } catch (RuntimeException exception) {
            log.info("Error");

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Getting all todo items
    @GetMapping("/path")
    ResponseEntity<List<Todo>> getTodos() {
        return new ResponseEntity<List<Todo>>(todoService.getTodos(), HttpStatus.OK);
    }

    // pagging of todo items
    @GetMapping("/todos/page")
    public Page<Todo> getTodosPaged(@RequestParam int page, @RequestParam int size) {
        return todoService.pagegetallTodos(page, size);
    }

    // Updating a todo item
    @PutMapping
    ResponseEntity<Todo> updatingnew(@RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    void deletingtodoByid(@PathVariable Long id) {
        todoService.deleteTodoByid(id);
    }

    @DeleteMapping
    void deletingtodo(@RequestBody Todo todo) {
        todoService.deleteTodo(todo);
    }
}
