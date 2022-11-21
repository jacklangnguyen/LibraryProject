package com.library.demo.persistence.mariadb.repository.custom;

import com.library.demo.entity.CheckoutBook;
import com.library.demo.web.rest.request.PageCriteria;

import java.util.List;

public interface CheckoutBookRepositoryCustom {
    List<CheckoutBook> listCheckoutBook(String keyword, PageCriteria pageCriteria);

    long countCheckoutBook(String keyword);
}
