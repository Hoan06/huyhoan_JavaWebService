package ra.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ra.demo.exception.BookNotFound;
import ra.demo.model.entity.Book;
import ra.demo.repository.BookResporitory;
import ra.demo.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookResporitory bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getAllBooks_returnList() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.getBooks();

        assertEquals(2, result.size());
    }

    @Test
    void getBookById_found() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Book 1", result.getTitle());
    }

    @Test
    void getBookById_notFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.getBookById(1L));
    }
}