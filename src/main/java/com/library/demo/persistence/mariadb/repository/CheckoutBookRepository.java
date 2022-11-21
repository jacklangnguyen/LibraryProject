package com.library.demo.persistence.mariadb.repository;

import com.library.demo.persistence.mariadb.entity.CheckoutBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutBookRepository extends JpaRepository<CheckoutBookEntity, Long> {
}
