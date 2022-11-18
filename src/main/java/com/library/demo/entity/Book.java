package com.library.demo.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private String id;
    private String bookId;
    private String bookName;
    private String bookTopic;
    private String bookPublisher;
    private String bookAuthor;

}
