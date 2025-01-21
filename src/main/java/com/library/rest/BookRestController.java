package com.library.rest;

import com.library.entity.Book;
import com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "Endpoints for managing books")
public class BookRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieves all available books, with optional sorting by popularity or category")
    public ResponseEntity<List<Book>> getBooks(
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "category", required = false) String category) {

        if (category != null && !category.isEmpty()) {
            logger.info("Getting books by category: " + category);
            return ResponseEntity.ok(bookService.getBooksByCategory(category));
        } else if ("popularity".equalsIgnoreCase(sortBy)) {
            logger.info("Getting books by popularity");
            return ResponseEntity.ok(bookService.getBooksSortedByPopularity());
        } else {
            logger.info("Getting books");
            return ResponseEntity.ok(bookService.getAllBooks());
        }
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get book by ID", description = "Fetches details of a specific book by its ID")
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            logger.info("Getting book: " + book);
            return ResponseEntity.ok(book);
        }
        logger.info("Getting book with id: " + bookId);
        return ResponseEntity.notFound().build();
    }
}