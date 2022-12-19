package com.library.demo.persistence.mariadb.entity;

import com.library.demo.entity.CheckoutBook;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "bookId"})
        })
public class CheckoutBookEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String bookId;
    private String bookName;
    private String userId;
    private String userName;
    private Boolean reserveBook;
    private Boolean renewBook;
    private Boolean processRenewBook;
    private Long totalrenew;
    private OffsetDateTime dayCheckoutBook;

    public CheckoutBook toCheckoutBook(){
        return CheckoutBook.builder()
                .id(String.valueOf(this.id))
                .bookId(this.bookId)
                .bookName(this.bookName)
                .userId(this.userId)
                .userName(this.userName)
                .reserveBook(String.valueOf(this.reserveBook))
                .renewBook(String.valueOf(this.renewBook))
                .processRenewBook(String.valueOf(this.processRenewBook))
                .totalrenew(String.valueOf(this.totalrenew))
                .dayCheckoutBook(this.dayCheckoutBook).build();
    }
}
