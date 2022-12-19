package com.library.demo.service;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.entity.Book;
import com.library.demo.entity.CheckoutBook;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.request.PageCriteria;

import java.util.List;

public interface CheckoutBookService {

    PageResponse<CheckoutBook> getCheckoutBookList(String keyWord, PageCriteria pageCriteria);

    CheckoutBook getCheckoutBook(String checkoutBookId) throws  DataNotFoundException;

    CheckoutBook addCheckoutBook(CheckoutBook checkoutBook) throws DataConflictException;

    CheckoutBook updateCheckoutBook(CheckoutBook checkoutBook) throws DataNotFoundException, DataConflictException;

    void deleteBook(List<String> ids) throws DataNotFoundException;

}
