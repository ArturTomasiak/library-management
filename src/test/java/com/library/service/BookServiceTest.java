package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1, book2;

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setBookId(1);
        book1.setTitle("Book One");
        book1.setPopularity(5);

        book2 = new Book();
        book2.setBookId(2);
        book2.setTitle("Book Two");
        book2.setPopularity(10);
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBooksSortedByPopularity() {
        when(bookRepository.findAllByOrderByPopularityDesc()).thenReturn(Arrays.asList(book2, book1));

        List<Book> books = bookService.getBooksSortedByPopularity();

        assertEquals(10, books.get(0).getPopularity());
        verify(bookRepository, times(1)).findAllByOrderByPopularityDesc();
    }
}
