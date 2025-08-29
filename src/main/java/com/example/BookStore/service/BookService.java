package com.example.BookStore.service;

import com.example.BookStore.model.Book;
import com.example.BookStore.model.Orders;
import com.example.BookStore.model.User;
import com.example.BookStore.repository.BookRepository;
import com.example.BookStore.repository.OrderRepository;
import com.example.BookStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(String title, String author, String category, String description, double price) {
        Book book = new Book(title,author,category,description,price);
        bookRepository.save(book);
    }

    public Book getbookbyid(int id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found in id "+id));
    }

    public Book getbookbytitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book getbookbyauthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> getbookbycategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public void updatebook(Book book) {
        bookRepository.save(book);
    }

    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public List<User> getusers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> adduser(String username, String email, String password,String role) {
        if(userRepository.findByEmail(email) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user already exist");
        }
        else {
            User user = new User(username,email,password,role);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User Added.");
        }
    }


    public ResponseEntity<?> login(String username, String password) {
        User validUser = userRepository.findByUsername(username);
        if (validUser == null || !validUser.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok(validUser);
    }

    public String placeOrder(int userId, String booktitle, double total) {
        Orders order = new Orders(userId,booktitle,total,new Date());
        orderRepository.save(order);
        return "Order palced.";
    }

    public List<Orders> getOrders(int userId) {
        return orderRepository.findAllById(userId);
    }
}
