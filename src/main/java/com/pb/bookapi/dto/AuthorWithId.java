package com.pb.bookapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorWithId {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private String country;

    @JsonProperty("book_ids")
    private List<Long> bookIds;
}
