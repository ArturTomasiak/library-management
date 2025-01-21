package com.library.service;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByCategory(String categoryName) {
        return bookRepository.findByCategories_NameIgnoreCase(categoryName);
    }

    public List<Book> getBooksSortedByPopularity() {
        return bookRepository.findAllByOrderByPopularityDesc();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooksGroupedByGenre() {
        List<Book> books = bookRepository.findAll();
        Map<String, List<Book>> genreMap = new HashMap<>();

        for (Book book : books) {
            for (Category category : book.getCategories()) {
                genreMap.computeIfAbsent(category.getName(), k -> new ArrayList<>()).add(book);
            }
        }

        List<Book> sortedBooks = new ArrayList<>();
        for (List<Book> genreBooks : genreMap.values()) {
            sortedBooks.addAll(genreBooks);
        }

        return sortedBooks;
    }

    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }
}
