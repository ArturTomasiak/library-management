package com.library.service;

import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    public Loan lendBook(User user, Book book) {

        List<Loan> activeLoans = loanRepository.findByUser_UserId(user.getUserId());
        boolean alreadyLent = activeLoans.stream()
                .anyMatch(loan -> loan.getBook().getBookId().equals(book.getBookId()) && loan.getReturnDate() == null);

        if (alreadyLent) {
            throw new IllegalStateException("You have already borrowed this book and haven't returned it yet.");
        }

        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setBook(book);
        newLoan.setLoanDate(LocalDate.now());
        newLoan.setReturnDate(null);

        book.setPopularity(book.getPopularity() + 1);
        bookRepository.save(book);

        return loanRepository.save(newLoan);
    }

    public void returnBook(Integer loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalStateException("Loan not found"));

        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("This book has already been returned.");
        }

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public List<Loan> getLoansByUserId(Integer userId) {
        return loanRepository.findByUser_UserId(userId);
    }

    public boolean isBookAlreadyLoanedByUser(Integer userId, Integer bookId) {
        return loanRepository.findByUser_UserId(userId).stream()
                .anyMatch(loan -> loan.getBook().getBookId().equals(bookId) && loan.getReturnDate() == null);
    }

}