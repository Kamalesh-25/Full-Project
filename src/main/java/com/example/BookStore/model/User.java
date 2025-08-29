package com.example.BookStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;

    public User(String username,String email,String password,String role){
        this.username=username;
        this.email=email;
        this.password=password;
        this.role=role;
    }

    public Object getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
