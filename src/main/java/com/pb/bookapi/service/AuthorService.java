package com.pb.bookapi.service;

import com.pb.bookapi.dto.AuthorNoId;
import com.pb.bookapi.dto.AuthorWithId;
import com.pb.bookapi.entity.Author;
import com.pb.bookapi.entity.Book;
import com.pb.bookapi.repository.AuthorRepository;
import com.pb.bookapi.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public AuthorWithId getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The author was not found"));

        return toDto(author);
    }

    private AuthorWithId toDto(Author author) {
        List<Long> bookIds = author.getBooks() != null
                ? author.getBooks().stream().map(Book::getId).toList()
                : List.of();
        return new AuthorWithId(author.getId(), author.getName(), author.getBirthdate(), author.getCountry(), bookIds);
    }

    public AuthorWithId createAuthor(AuthorNoId dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setBirthdate(dto.getBirthdate());
        author.setCountry(dto.getCountry());

        Author savedAuthor = authorRepository.save(author);

        return toDto(savedAuthor);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The author was not found");
        }
        ;
        if (bookRepository.existsByAuthorId(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The author is linked to a book and can't be deleted");
        }

        authorRepository.deleteById(id);
    }

    @Transactional
    public AuthorWithId updateAuthor(Long id, AuthorNoId dto) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The author was not found"));
        author.setName(dto.getName());
        author.setBirthdate(dto.getBirthdate());
        author.setCountry(dto.getCountry());

        Author newAuthor = authorRepository.save(author);

        return toDto(newAuthor);
    }

    @Transactional(readOnly = true)
    public List<AuthorWithId> getAllAuthors(String name, LocalDate birthdate, String country, String sortBy, String sortOrder) {
        Comparator<Author> comparator = switch (sortBy != null ? sortBy.toLowerCase() : "name") {
            case "birthdate" -> Comparator.comparing(Author::getBirthdate);
            case "country" -> Comparator.comparing(Author::getCountry);
            default -> Comparator.comparing(Author::getName);
        };

        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        List<AuthorWithId> authors = authorRepository.findAll().stream()
                .filter(author -> name == null || name.equalsIgnoreCase(author.getName()))
                .filter(author -> birthdate == null || birthdate.equals(author.getBirthdate()))
                .filter(author -> country == null || country.equalsIgnoreCase(author.getCountry()))
                .sorted(comparator)
                .map(this::toDto)
                .toList();
        return authors;
    }
}
