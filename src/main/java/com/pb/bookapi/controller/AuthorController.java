package com.pb.bookapi.controller;

import com.pb.bookapi.dto.AuthorNoId;
import com.pb.bookapi.dto.AuthorWithId;
import com.pb.bookapi.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("")
    public List<AuthorWithId> getAuthors(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "birthdate", required = false) LocalDate birthdate,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "sort-by", required = false) String sortBy,
            @RequestParam(name = "sort-order", required = false) String sortOrder){
        return authorService.getAllAuthors(name, birthdate, country, sortBy, sortOrder);
    }

    @GetMapping( "/{id}")
    public AuthorWithId getAuthor(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public AuthorWithId createAuthor(@RequestBody AuthorNoId dto) {
        return authorService.createAuthor(dto);
    }

    @PutMapping("/{id}")
    public AuthorWithId updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorNoId dto){
        return authorService.updateAuthor(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);
    }
}
