package com.example.BookStore.repository;

import com.example.BookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Book findByTitle(String title);
    Book findByAuthor(String author);
    List<Book> findByCategory(String category);
}
