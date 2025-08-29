package com.example.BookStore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private String category;
    private String description;
    private double price;

    public Book(String title,String author,String category,String description,double price){
        this.title=title;
        this.author=author;
        this.category=category;
        this.description=description;
        this.price=price;
    }
}
