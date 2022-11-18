package com.library.demo.entity;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CheckoutBook {

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

}
