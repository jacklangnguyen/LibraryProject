package com.library.demo.web.rest.request;

import com.library.demo.entity.CheckoutBook;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CheckoutBookRequest {

    private String id;
    private String bookId;
    private String bookName;
    private String userId;
    private String userName;
    private String reserveBook;
    private String renewBook;
    private String processRenewBook;
    private String totalrenew;
    private OffsetDateTime dayCheckoutBook;

    public CheckoutBook toCheckoutBook(){
        return CheckoutBook.builder()
                .bookId(this.bookId)
                .bookName(this.bookName)
                .userId(this.userId)
                .userName(this.userName)
                .reserveBook(this.reserveBook)
                .renewBook(this.renewBook)
                .processRenewBook(this.processRenewBook)
                .totalrenew(this.totalrenew)
                .dayCheckoutBook(this.dayCheckoutBook).build();
    }
}
