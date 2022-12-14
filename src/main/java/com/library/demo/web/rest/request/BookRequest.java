package com.library.demo.web.rest.request;


import com.library.demo.entity.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class BookRequest {

    private String id;
    @NotBlank
    private String bookId;
    @NotBlank
    private String bookName;
    private String bookTopic;
    private String bookPublisher;
    private String bookAuthor;

    public Book toBook(){
        return Book.builder()
                .id(this.id)
                .bookId(this.bookId)
                .bookName(this.bookName)
                .bookTopic(this.bookTopic)
                .bookPublisher(this.bookPublisher)
                .bookAuthor(this.bookAuthor).build();
    }

}
