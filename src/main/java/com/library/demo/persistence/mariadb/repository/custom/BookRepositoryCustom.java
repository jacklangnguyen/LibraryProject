package com.library.demo.persistence.mariadb.repository.custom;

import com.library.demo.entity.Book;
import com.library.demo.web.rest.request.PageCriteria;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> listBook(String keyword, PageCriteria pageCriteria);

    long countBook(String keyword);

}
