package com.library.demo.persistence.mariadb.repository.impl;

import com.library.demo.entity.CheckoutBook;
import com.library.demo.persistence.mariadb.entity.CheckoutBookEntity;
import com.library.demo.persistence.mariadb.repository.custom.CheckoutBookRepositoryCustom;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CheckoutBookRepositoryImpl implements CheckoutBookRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    private PageCriteriaPageableMapper pageableMapper;

    public CheckoutBookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CheckoutBook> listCheckoutBook(String keyword, PageCriteria pageCriteria) {
        Pageable pageable = pageableMapper.toPageable(pageCriteria);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CheckoutBookEntity> criteriaQuery = cb.createQuery(CheckoutBookEntity.class);
        Root<CheckoutBookEntity> root = criteriaQuery.from(CheckoutBookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if(Strings.isNotBlank(keyword)) {
            Predicate bookName = cb.like(root.get("bookName"), String.format("%%%s%%", keyword));
            Predicate bookId = cb.like(root.get("bookId"), String.format("%%%s%%", keyword));
            Predicate userId = cb.like(root.get("userId"), String.format("%%%s%%", keyword));
            Predicate userName = cb.like(root.get("userName"), String.format("%%%s%%", keyword));
//            Predicate dayCheckoutBook  = cb.like(root.get("dayCheckoutBook"), OffsetDateTime.parse(keyword));
            predicates.add(cb.or(bookName, bookId, userId, userName));
        }

        Sort sort = pageable.getSort();
        if(sort.isSorted()) {
            try{
                criteriaQuery.orderBy(QueryUtils.toOrders(sort, root, cb));
            } catch (Exception ignored) {
                criteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
            }
        } else {
            criteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
        }

        CriteriaQuery<CheckoutBookEntity> select = criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        List<CheckoutBookEntity> result = this.entityManager
                .createQuery(select)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return result.stream().map(CheckoutBookEntity::toCheckoutBook).collect(Collectors.toList());
    }


    @Override
    public long countCheckoutBook(String keyword) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        Root<CheckoutBookEntity> rootCount = countQuery.from(CheckoutBookEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (Strings.isNotBlank(keyword)) {
            Predicate bookName = cb.like(rootCount.get("bookName"), String.format("%%%s%%", keyword));
            Predicate bookId = cb.like(rootCount.get("bookId"), String.format("%%%s%%", keyword));
            Predicate userId = cb.like(rootCount.get("userId"), String.format("%%%s%%", keyword));
            Predicate userName = cb.like(rootCount.get("userName"), String.format("%%%s%%", keyword));
//            Predicate dayCheckoutBook  = cb.like(rootCount.get("dayCheckoutBook"), String.format("%%%s%%", keyword));
            predicates.add(cb.or(bookName, bookId, userId, userName));
        }

        countQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));

        return this.entityManager.createQuery(countQuery).getSingleResult();
    }
}
