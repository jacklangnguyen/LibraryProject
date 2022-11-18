package com.library.demo.persistence.mariadb.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditEntity implements Serializable {

    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private Instant createAt;

    @LastModifiedDate
    @Column(name="update_at")
    private Instant updatedAt;

    @CreatedBy
    @Column(name = "create_by", updatable = false, length = 50)
    private String createBy;

    @LastModifiedBy
    @Column(name= "update_by", length = 50)
    private String updatedBy;

}
