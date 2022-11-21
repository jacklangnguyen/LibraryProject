package com.library.demo.web.rest.response;

import com.library.demo.entity.Book;
import com.library.demo.entity.CheckoutBook;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CheckoutBookResponse {
    private List<CheckoutBook> checkoutBookList;
    private long total;
}
