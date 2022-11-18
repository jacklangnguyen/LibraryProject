package com.library.demo.persistence.mariadb.entity;


import com.library.demo.entity.UserAcount;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class UserAcountEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String userName;
    @Column(unique = true)
    private String userId;
    private String userAddress;
    private Long totalBookCheckout;
    private Boolean blockMember;

    public UserAcount toUserAcount(){
        return UserAcount.builder()
                .id(String.valueOf(this.id))
                .userId(this.userId)
                .userName(this.userName)
                .userAddress(this.userAddress)
                .totalBookCheckout(String.valueOf(this.totalBookCheckout))
                .blockMember(String.valueOf(this.blockMember)).build();
    }
}
