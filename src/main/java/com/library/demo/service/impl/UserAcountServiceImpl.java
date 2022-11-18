package com.library.demo.service.impl;

import com.library.demo.entity.UserAcount;
import com.library.demo.persistence.mariadb.entity.BookEntity;
import com.library.demo.persistence.mariadb.entity.UserAcountEntity;
import com.library.demo.service.UserAcountService;
import com.library.demo.utils.PersistenceHelper;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;


@Slf4j
@Service
public class UserAcountServiceImpl implements UserAcountService {

    private final EntityManager entityManager;

    public UserAcountServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAcount addUserAcount(UserAcount userAcount) throws DataConflictException {
        log.debug("Rest request add User Acount");

        UserAcountEntity userAcountEntity = new UserAcountEntity();

        userAcountEntity.setUserId(userAcount.getUserId());
        userAcountEntity.setUserName(userAcount.getUserName());
        userAcountEntity.setUserAddress(userAcount.getUserAddress());
        try {
            entityManager.persist(userAcountEntity);
            entityManager.flush();
            return userAcountEntity.toUserAcount();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(userAcountEntity.getUserId());
            }
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserAcount updateUserAcount(UserAcount userAcount) throws DataConflictException, DataNotFoundException {
        log.debug("Rest request update User Acount");
        try {
            UserAcountEntity userAcountEntity = this.entityManager.getReference(UserAcountEntity.class, Long.parseLong(userAcount.getId()));
            userAcountEntity.setUserAddress(userAcount.getUserAddress());
            userAcountEntity.setUserName(userAcount.getUserName());
            userAcountEntity.setBlockMember(Boolean.valueOf(userAcount.getBlockMember()));
            userAcountEntity.setUserId (userAcount.getUserId());
            entityManager.flush();
            return userAcountEntity.toUserAcount();
        }catch (EntityNotFoundException exn){
            throw new DataNotFoundException(userAcount.getId());
        }catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(userAcount.getUserId());
            }
            throw e;
        }

    }
}
