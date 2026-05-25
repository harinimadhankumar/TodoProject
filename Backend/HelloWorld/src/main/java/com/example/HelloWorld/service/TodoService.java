package com.example.HelloWorld.service;

import java.util.List;
import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.HelloWorld.models.Todo;
import com.example.HelloWorld.Repository.TodoRepository;

@Service

public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);   // to create or update    
    }   
    public Todo getbyId(Long id) {
        return todoRepository.findById(id).orElse(null);   // to create or update    
    }  
    public List<Todo> getTodos() {
        return todoRepository.findAll();   // to create or update    
    }


    //pagging
     public Page<Todo> pagegetallTodos(int page,int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return todoRepository.findAll(pageable);   // to create or update    
    }
      

   


    public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);   // to create or update    
    } 
    public void deleteTodoByid(Long id) {
        todoRepository.delete(getbyId(id));   // to create or update    
    } 
    public void deleteTodo(Todo todo) {
        todoRepository.delete(todo);   // to create or update    
    }
}