package com.pb.bookapi.repository;

import com.pb.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByAuthorId(Long authorId);
}
