package com.merion.spring.book.controller;

import com.merion.spring.base.exeption.ResourceNotFoundExeption;
import com.merion.spring.book.entity.BookEntity;
import com.merion.spring.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookApiController {
    public final BookService bookService;

    public BookApiController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping("/")
/*    public String ok() {
        return "ok";
    }*/
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/api/v1/book")
    public List<BookEntity> all() {
        return bookService.all();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookEntity byId(@PathVariable Integer id) {
        //return bookService.byId(id).orElseThrow(RuntimeException::new);
        return bookService.byId(id).orElseThrow(ResourceNotFoundExeption::new);
    }
    @PostMapping("/api/v1/book")
    public BookEntity create(@RequestBody BookEntity request) {
        return bookService.create(request.getTitle(), request.getDescription());
    }

    @PutMapping("/api/v1/book/{id}")
    public BookEntity edit(@PathVariable Integer id, @RequestBody BookEntity request) {
        return bookService.edit(request).orElseThrow(ResourceNotFoundExeption::new);
    }
}
