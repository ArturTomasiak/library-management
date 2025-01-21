package com.library.rest;

import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRestControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    private Book book1, book2;

    @BeforeEach
    void setUp() {
        book1 = new Book();
        book1.setBookId(1);
        book1.setTitle("Book One");

        book2 = new Book();
        book2.setBookId(2);
        book2.setTitle("Book Two");
    }

    @Test
    void testGetAllBooks() {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        ResponseEntity<List<Book>> response = bookRestController.getBooks(null, null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetBooksSortedByPopularity() {
        when(bookService.getBooksSortedByPopularity()).thenReturn(Arrays.asList(book2, book1));

        ResponseEntity<List<Book>> response = bookRestController.getBooks("popularity", null);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Book Two", response.getBody().get(0).getTitle());
    }

    @Test
    void testGetBookById_Success() {
        when(bookService.getBookById(1)).thenReturn(book1);

        ResponseEntity<Book> response = bookRestController.getBookById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Book One", response.getBody().getTitle());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookService.getBookById(99)).thenReturn(null);

        ResponseEntity<Book> response = bookRestController.getBookById(99);

        assertEquals(404, response.getStatusCode().value());
    }
}
