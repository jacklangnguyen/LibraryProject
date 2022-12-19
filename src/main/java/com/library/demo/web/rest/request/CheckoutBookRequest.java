package com.library.demo.web.rest.request;

import com.library.demo.entity.CheckoutBook;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Data
public class CheckoutBookRequest {

    @NotBlank
    private String bookId;
    @NotBlank
    private String bookName;
    @NotBlank
    private String userId;
    @NotBlank
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
