package com.merion.spring.book.service;

import com.merion.spring.book.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    static List<BookEntity> bookStorage = new ArrayList<>();

    public BookService() {
        fillStorage();
    }

    public void fillStorage() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            book.setTitle("Book № " + random.nextInt(100, 999));
            book.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            bookStorage.add(book);
        }
    }

    public List<BookEntity> all() {
        return bookStorage;
    }

    public Optional<BookEntity> byId(Integer id) {
        return bookStorage.stream().filter((book -> book.getId().equals(id))).findFirst();
    }

    public BookEntity create(String title, String description) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookStorage.size());
        bookEntity.setTitle(title);
        bookEntity.setDescription(description);
        bookStorage.add(bookEntity);
        return bookEntity;
    }

    public Optional<BookEntity> edit(BookEntity book) {
        // PreFIX // BookEntity oldBook = byId(book.getId()).orElseThrow();
        // Add by FIX
        Optional<BookEntity> oldBookOptional = byId(book.getId());
        if (oldBookOptional.isEmpty()) {
            return Optional.empty();
        }

        BookEntity oldBook = oldBookOptional.get(); // Add by FIX
        oldBook.setTitle(book.getTitle());
        oldBook.setDescription(book.getDescription());
        return Optional.of(oldBook);
    }

    public Boolean delete(Integer id) {
        Optional<BookEntity> book = byId(id);
        if (book.isEmpty()) {
            return false;
        }
        bookStorage.remove(book.get());
        return true;
    }

    public Optional<BookEntity> editPart(Integer id, Map<String, String> fields) {
        Optional<BookEntity> optionalBookEntity = byId(id);
        if (optionalBookEntity.isEmpty()) {
            return Optional.empty();
        }

        BookEntity book = optionalBookEntity.get();

        for (String key : fields.keySet()) {
            switch (key) {
                case "title" -> book.setTitle(fields.get(key));
                case "description" -> book.setDescription(fields.get(key));
            }
        }
        return Optional.of(book);
    }
}
