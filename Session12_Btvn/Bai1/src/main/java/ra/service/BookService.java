package ra.service;

import ra.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
    Optional<Book> update(Long id, Book book);
    boolean delete(Long id);
}