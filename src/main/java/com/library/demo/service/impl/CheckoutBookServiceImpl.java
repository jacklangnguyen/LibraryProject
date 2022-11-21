package com.library.demo.service.impl;

import com.library.demo.dto.response.PageResponse;
import com.library.demo.entity.Book;
import com.library.demo.entity.CheckoutBook;
import com.library.demo.persistence.mariadb.entity.BookEntity;
import com.library.demo.persistence.mariadb.entity.CheckoutBookEntity;
import com.library.demo.persistence.mariadb.repository.CheckoutBookRepository;
import com.library.demo.persistence.mariadb.repository.custom.CheckoutBookRepositoryCustom;
import com.library.demo.service.CheckoutBookService;
import com.library.demo.utils.PersistenceHelper;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.request.PageCriteria;
import com.library.demo.web.rest.response.BookResponse;
import com.library.demo.web.rest.response.CheckoutBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CheckoutBookServiceImpl implements CheckoutBookService {

    private final CheckoutBookRepositoryCustom checkoutBookRepositoryCustom;
    private final CheckoutBookRepository checkoutBookRepository;
    private final EntityManager entityManager;

    public CheckoutBookServiceImpl(CheckoutBookRepositoryCustom checkoutBookRepositoryCustom, CheckoutBookRepository checkoutBookRepository, EntityManager entityManager) {
        this.checkoutBookRepositoryCustom = checkoutBookRepositoryCustom;
        this.checkoutBookRepository = checkoutBookRepository;
        this.entityManager = entityManager;
    }

    @Override
    public PageResponse<CheckoutBook> getCheckoutBookList(String keyword, PageCriteria pageCriteria) {
        log.debug("Rest request get Book List");
//        Page<Book> response = null;
//        if(CollectionUtils.isEmpty(pageCriteria.getSort())) {
//            pageCriteria.setSort(List.of("-updateAt"));
////        }
//        response = subjectRepositoryCustom.getSubjectList(keyword, pageCriteria);
//        return new PageResponse<>(response.getTotalElements(), response.getContent(),
//                pageCriteria.getPage(), pageCriteria.getLimit());

        CheckoutBookResponse checkoutBookResponse = new CheckoutBookResponse(this.checkoutBookRepositoryCustom.listCheckoutBook(keyword, pageCriteria),
                this.checkoutBookRepositoryCustom.countCheckoutBook(keyword));

        return new PageResponse<>(checkoutBookResponse.getTotal(), checkoutBookResponse.getCheckoutBookList(),
                pageCriteria.getPage(), pageCriteria.getLimit());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CheckoutBook addCheckoutBook(CheckoutBook checkoutBook) throws DataConflictException {
        log.debug("Rest request add Book");

        CheckoutBookEntity checkoutBookEntity = new CheckoutBookEntity();

        checkoutBookEntity.setBookId(checkoutBook.getBookId());
        checkoutBookEntity.setBookName(checkoutBook.getBookName());
        checkoutBookEntity.setDayCheckoutBook(OffsetDateTime.now());
        checkoutBookEntity.setUserId(checkoutBook.getUserId());
        checkoutBookEntity.setUserName(checkoutBook.getUserName());
        if(Strings.isNotBlank(checkoutBook.getReserveBook())) {
            checkoutBookEntity.setReserveBook(Boolean.valueOf(checkoutBook.getReserveBook()));
        }

        try {
            entityManager.persist(checkoutBookEntity);
            entityManager.flush();
            return checkoutBookEntity.toCheckoutBook();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(checkoutBook.getBookId());
            }
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CheckoutBook updateCheckoutBook(CheckoutBook checkoutBook) throws DataNotFoundException, DataConflictException {
        log.debug("Rest request update Checkout Book");
        try {
            CheckoutBookEntity checkoutBookEntity = this.entityManager.getReference(CheckoutBookEntity.class, Long.parseLong(checkoutBook.getId()));

            checkoutBookEntity.setRenewBook(Boolean.valueOf(checkoutBook.getRenewBook()));

            entityManager.flush();
            return checkoutBookEntity.toCheckoutBook();
        }catch (EntityNotFoundException exn){
            throw new DataNotFoundException(checkoutBook.getId());
        }catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(checkoutBook.getBookId());
            }
            throw e;
        }
    }

    @Transactional
    @Override
    public void deleteBook(List<String> ids) throws DataNotFoundException {

        for( String id: ids) {
            Optional<CheckoutBookEntity> userEntity = this.checkoutBookRepository.findById(Long.parseLong(id));
            if(!userEntity.isPresent()){
                throw new DataNotFoundException("Not found user");
            }
        }
        this.checkoutBookRepository.deleteAllById(ids.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
    }
}
