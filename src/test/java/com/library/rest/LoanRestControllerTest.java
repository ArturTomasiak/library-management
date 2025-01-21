package com.library.rest;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanRestControllerTest {

    @Mock
    private LoanService loanService;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LoanRestController loanRestController;

    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setUsername("testUser");

        book = new Book();
        book.setBookId(1);
        book.setTitle("Book Title");

        loan = new Loan();
        loan.setLoanId(1L);
        loan.setUser(user);
        loan.setBook(book);
    }

    @Test
    void testLendBook_Success() {
        when(authentication.getName()).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(user);
        when(bookService.getBookById(1)).thenReturn(book);
        when(loanService.lendBook(user, book)).thenReturn(loan);

        ResponseEntity<?> response = loanRestController.lendBook(1, authentication);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(loanService, times(1)).lendBook(user, book);
    }

    @Test
    void testLendBook_InvalidUserOrBook() {
        when(authentication.getName()).thenReturn("testUser");
        when(userService.findUserByUsername("testUser")).thenReturn(null);

        ResponseEntity<?> response = loanRestController.lendBook(1, authentication);

        assertEquals(400, response.getStatusCode().value());
        verify(loanService, never()).lendBook(any(), any());
    }

    @Test
    void testReturnBook_Success() {
        doNothing().when(loanService).returnBook(1);

        ResponseEntity<String> response = loanRestController.returnBook(1);

        assertEquals(200, response.getStatusCode().value());
        verify(loanService, times(1)).returnBook(1);
    }
}
