package com.library.demo.web.rest.impl;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.Book;
import com.library.demo.resolver.PageCriteriaValidator;
import com.library.demo.service.BookService;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.BookResource;
import com.library.demo.web.rest.request.BookRequest;
import com.library.demo.web.rest.request.DeleteBookListRequest;
import com.library.demo.web.rest.request.PageCriteriaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class BookResourceImpl implements BookResource {

    private final BookService bookService;

    public BookResourceImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @PageCriteriaValidator
    @Override
    public ServiceResponse<PageResponse<Book>> getBookList(String keyWord, @Valid PageCriteriaRequest pageCriteriaRequest) {
        log.debug("Rest get Book List");
        PageResponse<Book> response = bookService.getBookList(keyWord, pageCriteriaRequest.getPageCriteria());
        return ServiceResponse.succed(HttpStatus.OK, response);

    }

    @Override
    public ServiceResponse<Book> addBook(BookRequest bookRequest) {
        log.debug("Rest add Book");
        try {
            Book response = bookService.addBook(bookRequest.toBook());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<Book> updateBook(BookRequest bookRequest) {
        log.debug("Rest update Book");
        try {
            Book response = bookService.updateBook(bookRequest.toBook());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }

    }

    @Override
    public ServiceResponse<HttpStatus> deleteBook(DeleteBookListRequest ids) {
        log.debug("Rest delete Id List");
        try {
            this.bookService.deleteBook(ids.getIds());
            return ServiceResponse.succed(HttpStatus.OK, null);

        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "Data Not Found");// (HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        }

    }
}
