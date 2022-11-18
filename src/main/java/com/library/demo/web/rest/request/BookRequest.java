package com.library.demo.web.rest.request;


import com.library.demo.entity.Book;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequest {

    private String id;
    private String bookId;
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
