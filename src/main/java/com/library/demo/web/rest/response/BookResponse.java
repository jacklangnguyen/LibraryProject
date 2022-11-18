package com.library.demo.web.rest.response;

import com.library.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookResponse {
    private List<Book> bookList;
    private long total;
}
