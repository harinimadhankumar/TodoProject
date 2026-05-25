package com.example.HelloWorld.models;




import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_table")
public class User{
    @Id
    @GeneratedValue
    private Long id;
    @Email
    String email;
    String password;

}
