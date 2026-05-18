package ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Book> library = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookController() {
        library.add(new Book(idGenerator.getAndIncrement(), "Tat Den", "Ngo Tat To", 1937, true));
        library.add(new Book(idGenerator.getAndIncrement(), "Chi Pheo", "Nam Cao", 1941, false));
        library.add(new Book(idGenerator.getAndIncrement(), "So Do", "Vu Trong Phung", 1936, true));
    }

    @GetMapping(produces = { "application/json", "application/xml" })
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "author", required = false) String author) {
        if (author != null && !author.trim().isEmpty()) {
            List<Book> filteredBooks = library.stream()
                    .filter(b -> b.getAuthor().equalsIgnoreCase(author.trim()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
        }
        return new ResponseEntity<>(library, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<Book> foundBook = library.stream().filter(b -> b.getId().equals(id)).findFirst();

        if (foundBook.isPresent()) {
            return new ResponseEntity<>(foundBook.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy cuốn sách nào với ID: " + id, HttpStatus.NOT_FOUND); // Lỗi 404
        }
    }

    @PostMapping(
            consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<?> createBook(@RequestBody Book newBook) {
        if (newBook.getTitle() == null || newBook.getTitle().trim().isEmpty() ||
                newBook.getAuthor() == null || newBook.getAuthor().trim().isEmpty()) {
            return new ResponseEntity<>("Tiêu đề và Tác giả không được để trống!", HttpStatus.BAD_REQUEST);
        }

        newBook.setId(idGenerator.getAndIncrement());
        library.add(newBook);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = { "application/json", "application/xml" },
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> existingBookOpt = library.stream().filter(b -> b.getId().equals(id)).findFirst();

        if (existingBookOpt.isPresent()) {
            if (updatedBook.getYear() > 2026) {
                return new ResponseEntity<>("Năm xuất bản không hợp lệ!", HttpStatus.BAD_REQUEST);
            }

            Book existingBook = existingBookOpt.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setYear(updatedBook.getYear());
            existingBook.setAvailable(updatedBook.isAvailable());
            return new ResponseEntity<>(existingBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không thể cập nhật vì sách không tồn tại!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Optional<Book> bookToDelete = library.stream().filter(b -> b.getId().equals(id)).findFirst();

        if (bookToDelete.isPresent()) {
            library.remove(bookToDelete.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Không thể xóa vì sách không tồn tại!", HttpStatus.NOT_FOUND);
        }
    }
}