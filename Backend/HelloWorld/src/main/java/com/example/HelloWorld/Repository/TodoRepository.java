package com.example.HelloWorld.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HelloWorld.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}