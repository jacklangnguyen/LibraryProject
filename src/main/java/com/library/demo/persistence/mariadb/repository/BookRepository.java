package com.library.demo.persistence.mariadb.repository;

import com.library.demo.persistence.mariadb.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
