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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@Tag(name = "Home", description = "Endpoints for retrieving books for the main page")
public class HomeRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    @Operation(summary = "Get books for homepage", description = "Fetches books sorted by popularity, genre, or all books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "sortBy", required = false) String sortBy) {
        if (sortBy != null && sortBy.equals("popularity")) {
            logger.info("Returned books sorted by popularity");
            return ResponseEntity.ok(bookService.getBooksSortedByPopularity());
        } else if (sortBy != null && sortBy.equals("genre")) {
            logger.info("Returned books sorted by genre");
            return ResponseEntity.ok(bookService.getBooksGroupedByGenre());
        } else {
            logger.info("Returned books");
            return ResponseEntity.ok(bookService.getAllBooks());
        }
    }
}
