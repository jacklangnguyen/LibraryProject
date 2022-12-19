package com.library.demo.web.rest.impl;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.Book;
import com.library.demo.entity.CheckoutBook;
import com.library.demo.resolver.PageCriteriaValidator;
import com.library.demo.service.CheckoutBookService;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.CheckoutBookResource;
import com.library.demo.web.rest.request.CheckoutBookListDeleteRequest;
import com.library.demo.web.rest.request.CheckoutBookRequest;
import com.library.demo.web.rest.request.CheckoutBookUpdateRequest;
import com.library.demo.web.rest.request.PageCriteriaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
public class CheckoutBookResourceImpl implements CheckoutBookResource {

    private final CheckoutBookService checkoutBookService;

    public CheckoutBookResourceImpl(CheckoutBookService checkoutBookService) {
        this.checkoutBookService = checkoutBookService;
    }

    @PageCriteriaValidator
    @Override
    public ServiceResponse<PageResponse<CheckoutBook>> getBookList(String keyWord, @Valid PageCriteriaRequest pageCriteriaRequest) {
        log.debug("Rest get Checkout Book List");
        PageResponse<CheckoutBook> response = checkoutBookService.getCheckoutBookList(keyWord, pageCriteriaRequest.getPageCriteria());
        return ServiceResponse.succed(HttpStatus.OK, response);
    }

    @Override
    public ServiceResponse<CheckoutBook> getCheckoutBook(String checkoutBookId) {
        log.debug("Rest get Book");
        try {
            CheckoutBook response = checkoutBookService.getCheckoutBook(checkoutBookId);
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");

        }

    }

    @Override
    public ServiceResponse<CheckoutBook> addBook(CheckoutBookRequest checkoutBookRequest) {
        log.debug("Rest add Book");
        try {
            CheckoutBook response = checkoutBookService.addCheckoutBook(checkoutBookRequest.toCheckoutBook());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }

    }

    @Override
    public ServiceResponse<CheckoutBook> updateBook(@Valid CheckoutBookUpdateRequest checkoutBookUpdateRequest) {
        log.debug("Rest update Book");
        try {
            CheckoutBook response = checkoutBookService.updateCheckoutBook(checkoutBookUpdateRequest.toCheckoutBook());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<HttpStatus> deleteBook(CheckoutBookListDeleteRequest ids) {
        log.debug("Rest delete Id List");
        try {
            this.checkoutBookService.deleteBook(ids.getIds());
            return ServiceResponse.succed(HttpStatus.OK, null);

        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "Data Not Found");// (HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        }
    }
}
