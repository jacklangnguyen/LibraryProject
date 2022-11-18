package com.library.demo.persistence.mariadb.repository.impl;

import com.library.demo.entity.Book;
import com.library.demo.persistence.mariadb.entity.BookEntity;
import com.library.demo.persistence.mariadb.repository.custom.BookRepositoryCustom;
import com.library.demo.utils.PageCriteriaPageableMapper;
import com.library.demo.web.rest.request.PageCriteria;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    private PageCriteriaPageableMapper pageableMapper;

    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Book> listBook(String keyword, PageCriteria pageCriteria) {
        Pageable pageable = pageableMapper.toPageable(pageCriteria);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> subjectCriteriaQuery = cb.createQuery(BookEntity.class);
        Root<BookEntity> root = subjectCriteriaQuery.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if(Strings.isNotBlank(keyword)) {
            Predicate subjectName = cb.like(root.get("bookName"), String.format("%%%s%%", keyword));
            Predicate subjectId = cb.like(root.get("bookId"), String.format("%%%s%%", keyword));
            Predicate bookTopic = cb.like(root.get("bookTopic"), String.format("%%%s%%", keyword));
            Predicate bookPublisher = cb.like(root.get("bookPublisher"), String.format("%%%s%%", keyword));
            Predicate bookAuthor = cb.like(root.get("bookAuthor"), String.format("%%%s%%", keyword));
            predicates.add(cb.or(subjectName, subjectId, bookTopic, bookPublisher, bookAuthor));
        }

        Sort sort = pageable.getSort();
        if(sort.isSorted()) {
            try{
                subjectCriteriaQuery.orderBy(QueryUtils.toOrders(sort, root, cb));
            } catch (Exception ignored) {
                subjectCriteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
            }
        } else {
            subjectCriteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
        }

        CriteriaQuery<BookEntity> select = subjectCriteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        List<BookEntity> result = this.entityManager
                .createQuery(select)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return result.stream().map(BookEntity::toBook).collect(Collectors.toList());
    }


    @Override
    public long countBook(String keyword) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        Root<BookEntity> rootCount = countQuery.from(BookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (Strings.isNotBlank(keyword)) {
            Predicate subjectName = cb.like(rootCount.get("bookName"), String.format("%%%s%%", keyword));
            Predicate subjectId = cb.like(rootCount.get("bookId"), String.format("%%%s%%", keyword));
            Predicate bookTopic = cb.like(rootCount.get("bookTopic"), String.format("%%%s%%", keyword));
            Predicate bookPublisher = cb.like(rootCount.get("bookPublisher"), String.format("%%%s%%", keyword));
            Predicate bookAuthor = cb.like(rootCount.get("bookAuthor"), String.format("%%%s%%", keyword));
            predicates.add(cb.or(subjectName, subjectId, bookTopic, bookPublisher, bookAuthor));
        }

        countQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));

        return this.entityManager.createQuery(countQuery).getSingleResult();
    }
}
