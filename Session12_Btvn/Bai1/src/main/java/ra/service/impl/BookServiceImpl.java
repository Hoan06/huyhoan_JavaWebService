package ra.service.impl;

import org.springframework.stereotype.Service;
import ra.model.entity.Book;
import ra.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookServiceImpl implements BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookServiceImpl() {
        books.add(new Book(idGenerator.getAndIncrement(), "Lập trình Java", "Nguyễn Văn A", 150000.0));
        books.add(new Book(idGenerator.getAndIncrement(), "Spring Boot Cơ Bản", "Trần Thị B", 220000.0));
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    @Override
    public Book save(Book book) {
        book.setId(idGenerator.getAndIncrement());
        books.add(book);
        return book;
    }

    @Override
    public Optional<Book> update(Long id, Book updatedBook) {
        return findById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPrice(updatedBook.getPrice());
            return existingBook;
        });
    }

    @Override
    public boolean delete(Long id) {
        return books.removeIf(b -> b.getId().equals(id));
    }
}