package com.library.controller;

import com.library.entity.Book;
import com.library.entity.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    public BookController(BookService bookService, UserService userService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping("/{bookId}")
    public String getBookPage(@PathVariable Integer bookId, Authentication authentication, Model model) {
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            return "redirect:/";
        }

        boolean alreadyLent = false;
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findUserByUsername(authentication.getName());
            alreadyLent = loanService.getLoansByUserId(user.getUserId()).stream()
                    .anyMatch(loan -> loan.getBook().getBookId().equals(bookId) && loan.getReturnDate() == null);
        }

        model.addAttribute("book", book);
        model.addAttribute("alreadyLent", alreadyLent);
        return "book";
    }

}
