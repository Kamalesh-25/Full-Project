package com.example.BookStore.controller;

import com.example.BookStore.model.Book;
import com.example.BookStore.model.Orders;
import com.example.BookStore.model.User;
import com.example.BookStore.repository.UserRepository;
import com.example.BookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping("books/add")
    public String addBook(@RequestParam("userId") int userId,
                          @RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam("category") String category,
                          @RequestParam("description") String description,
                          @RequestParam("price") double price) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            bookService.addBook(title, author, category, description, price);
            return "Book is added successfully";
        } else {
            return "Access denied. Only ADMIN can add books.";
        }
    }

    @GetMapping("books/search/{id}")
    public Book getbookbyid(@PathVariable("id") int id){
        return bookService.getbookbyid(id);
    }

    @GetMapping("books/title/{title}")
    public Book getbookbytitle(@PathVariable("title")String title){
        return bookService.getbookbytitle(title);
    }

    @GetMapping("books/author/{author}")
    public Book getbookbyauthor(@PathVariable("author")String author){
        return bookService.getbookbyauthor(author);
    }

    @GetMapping("books/category/{category}")
    public List<Book> getbookbycategory(@PathVariable("category")String category){
        return bookService.getbookbycategory(category);
    }

    @PutMapping("/books/update")
    public String updateBook(@RequestParam("userId") int userId,
                             @RequestParam("id") int bookId,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("category") String category,
                             @RequestParam("description") String description,
                             @RequestParam("price") double price) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            Book book = new Book(title, author, category, description, price);
            bookService.updatebook(book);
            return "Updated successfully";
        } else {
            return "Access denied. Only ADMIN can update books.";
        }
    }

    @DeleteMapping("/books/delete/{id}")
    public String deleteById(@RequestParam("userId") int userId, @PathVariable("id") int id){
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "ADMIN".equalsIgnoreCase(user.getRole())) {
            bookService.deleteById(id);
            return "Book is deleted successfully";
        } else {
            return "Access denied. Only ADMIN can delete books.";
        }
    }

    @GetMapping("/users")
    public List<User> getusers(){
        return bookService.getusers();
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> adduser(@RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("password") String password,
                                     @RequestParam("role") String role) {
        return bookService.adduser(username, email, password, role);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestParam("username") String username,
                                   @RequestParam("password") String password){
        return bookService.login(username,password);
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestParam("userId") int userId,
                             @RequestParam("booktitle") String booktitle,
                             @RequestParam("total") double total) {

        System.out.println("Received Order - userId: " + userId + ", title: " + booktitle + ", total: " + total);

        User user = userRepository.findById(userId).orElse(null);
        if (user != null && "CLIENT".equalsIgnoreCase(user.getRole())) {
            return bookService.placeOrder(userId, booktitle, total);
        } else {
            return "Only CLIENT users can place orders.";
        }
    }




    @GetMapping("/orders/{userId}")
    public List<Orders> getOrders(@PathVariable int userId) {
        return bookService.getOrders(userId);
    }

}
