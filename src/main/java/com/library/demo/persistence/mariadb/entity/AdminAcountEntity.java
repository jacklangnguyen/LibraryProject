package com.library.demo.persistence.mariadb.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class AdminAcountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String adminId;
    private String adminName;
    private String adminAddress;

}
