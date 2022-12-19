package com.library.demo.service;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.entity.Book;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.request.PageCriteria;

import java.util.List;

public interface BookService {
    PageResponse<Book> getBookList(String keyWord, PageCriteria pageCriteria);

    Book addBook(Book book) throws DataConflictException;

    Book updateBook(Book book) throws DataNotFoundException, DataConflictException;

    void deleteBook(List<String> ids) throws DataNotFoundException;

    Book getBook(String bookId) throws  DataNotFoundException;
}
