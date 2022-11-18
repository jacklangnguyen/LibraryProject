package com.library.demo.persistence.mariadb.entity;


import com.library.demo.entity.Book;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class BookEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String bookId;
    private String bookName;
    private String bookTopic;
    private String bookPublisher;
    private String bookAuthor;

    public Book toBook(){
        return Book.builder()
                .id(String.valueOf(this.id))
                .bookId(this.bookId)
                .bookName(this.bookName)
                .bookTopic(this.bookTopic)
                .bookPublisher(this.bookPublisher)
                .bookAuthor(this.bookAuthor).build();
    }
}
