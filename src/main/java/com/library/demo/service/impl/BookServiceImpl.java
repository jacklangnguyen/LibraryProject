package com.library.demo.service.impl;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.entity.Book;
import com.library.demo.persistence.mariadb.entity.BookEntity;
import com.library.demo.persistence.mariadb.repository.BookRepository;
import com.library.demo.persistence.mariadb.repository.custom.BookRepositoryCustom;
import com.library.demo.service.BookService;
import com.library.demo.utils.PersistenceHelper;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.request.PageCriteria;
import com.library.demo.web.rest.response.BookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepositoryCustom bookRepositoryCustom;
    private final EntityManager entityManager;
    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepositoryCustom bookRepositoryCustom, EntityManager entityManager, BookRepository bookRepository) {
        this.bookRepositoryCustom = bookRepositoryCustom;
        this.entityManager = entityManager;
        this.bookRepository = bookRepository;
    }


    @Override
    public PageResponse<Book> getBookList(String keyword, PageCriteria pageCriteria) {
        log.debug("Rest request get Book List");
        Page<Book> response = null;
//        if(CollectionUtils.isEmpty(pageCriteria.getSort())) {
//            pageCriteria.setSort(List.of("-updateAt"));
////        }
//        response = subjectRepositoryCustom.getSubjectList(keyword, pageCriteria);
//        return new PageResponse<>(response.getTotalElements(), response.getContent(),
//                pageCriteria.getPage(), pageCriteria.getLimit());

        BookResponse bookResponse = new BookResponse(this.bookRepositoryCustom.listBook(keyword, pageCriteria),
                this.bookRepositoryCustom.countBook(keyword));

        return new PageResponse<>(bookResponse.getTotal(), bookResponse.getBookList(),
                pageCriteria.getPage(), pageCriteria.getLimit());
    }

    @Override
    public Book getBook(String bookId)  throws  DataNotFoundException{
        log.debug("Rest request get book");
        try {
            BookEntity bookEntity = this.entityManager.getReference(BookEntity.class, Long.parseLong(bookId));
        return bookEntity.toBook();
        }catch (EntityNotFoundException exn){
                throw new DataNotFoundException(bookId);
            }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Book addBook(Book book) throws DataConflictException {
        log.debug("Rest request add Book");

        BookEntity bookEntity = new BookEntity();

        bookEntity.setBookId(book.getBookId());
        bookEntity.setBookName(book.getBookName());
        bookEntity.setBookAuthor(book.getBookAuthor());
        bookEntity.setBookTopic(book.getBookTopic());
        bookEntity.setBookPublisher(book.getBookPublisher());
        try {
            entityManager.persist(bookEntity);
            entityManager.flush();
            return bookEntity.toBook();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(book.getBookId());
            }
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Book updateBook(Book book) throws DataNotFoundException, DataConflictException {
        log.debug("Rest request update Book");
        try {
            BookEntity bookEntity = this.entityManager.getReference(BookEntity.class, Long.parseLong(book.getId()));
            bookEntity.setBookId(book.getBookId());
            bookEntity.setBookName(book.getBookName());
            bookEntity.setBookAuthor(book.getBookAuthor());
            bookEntity.setBookTopic(book.getBookTopic());
            bookEntity.setBookPublisher(book.getBookPublisher());
            entityManager.flush();
            return bookEntity.toBook();
        }catch (EntityNotFoundException exn){
                throw new DataNotFoundException(book.getId());
        }catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(book.getBookId());
            }
            throw e;
        }

    }

    @Transactional
    @Override
    public void deleteBook(List<String> ids) throws DataNotFoundException {
        for( String id: ids) {
            Optional<BookEntity> userEntity = this.bookRepository.findById(Long.parseLong(id));
            if(!userEntity.isPresent()){
                throw new DataNotFoundException("Not found user");
            }
        }
        this.bookRepository.deleteAllById(ids.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
    }
}
