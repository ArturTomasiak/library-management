package com.library.service;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private User user;
    private Book book;
    private Loan loan;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);

        book = new Book();
        book.setBookId(1);
        book.setPopularity(0);

        loan = new Loan();
        loan.setLoanId(1L);
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
    }

    @Test
    void testLendBook() {
        when(loanRepository.findByUser_UserId(1)).thenReturn(Collections.emptyList());
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(bookRepository.save(book)).thenReturn(book);

        Loan lentLoan = loanService.lendBook(user, book);

        assertNotNull(lentLoan);
        assertEquals(1, book.getPopularity());
        verify(loanRepository, times(1)).save(any(Loan.class));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testReturnBook() {
        when(loanRepository.findById(1)).thenReturn(Optional.of(loan));

        loanService.returnBook(1);

        assertNotNull(loan.getReturnDate());
        verify(loanRepository, times(1)).save(loan);
    }
}
