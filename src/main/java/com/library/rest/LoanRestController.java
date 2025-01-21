package com.library.rest;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan Management", description = "Endpoints for managing book loans")
public class LoanRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    public LoanRestController(LoanService loanService, BookService bookService, UserService userService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping("/lend")
    @Operation(summary = "Lend a book", description = "Lends a book to a user for two weeks")
    public ResponseEntity<?> lendBook(@RequestParam Integer bookId, Authentication authentication) {
        logger.info("Received request to lend book ID: {}", bookId);
        User user = userService.findUserByUsername(authentication.getName());
        Book book = bookService.getBookById(bookId);

        if (user == null || book == null) {
            logger.warn("Invalid user or book");
            return ResponseEntity.badRequest().body("Invalid user or book.");
        }

        try {
            Loan loan = loanService.lendBook(user, book);
            logger.info("Book lent successfully: {}", loan);
            return ResponseEntity.ok(loan);
        } catch (IllegalStateException e) {
            logger.error("Error lending book: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    @Operation(summary = "Return a book", description = "Marks a book loan as returned")
    public ResponseEntity<String> returnBook(@RequestParam Integer loanId) {
        try {
            loanService.returnBook(loanId);
            logger.info("Book returned succesfully");
            return ResponseEntity.ok("Book returned successfully");
        } catch (IllegalStateException e) {
            logger.error("Failed to return book: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to return book: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    @Operation(summary = "Get user's loan history", description = "Retrieves all books loaned by the authenticated user")
    public ResponseEntity<List<Loan>> getUserLoans(Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());
        return ResponseEntity.ok(loanService.getLoansByUserId(user.getUserId()));
    }
}
