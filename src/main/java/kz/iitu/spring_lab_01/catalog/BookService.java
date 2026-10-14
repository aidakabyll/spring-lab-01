package kz.iitu.spring_lab_01.catalog;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        if (author == null || author.isBlank()) {
            return repository.findAll();
        }
        return repository.findAll().stream()
                .filter(b -> b.author().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }

    public int deleteByAuthor(String author) {
        int count = 0;
        for (Book book : repository.findAll()) {
            if (author.equalsIgnoreCase(book.author())) {
                if (repository.deleteById(book.id())) {
                    count++;
                }
            }
        }
        return count;
    }
}