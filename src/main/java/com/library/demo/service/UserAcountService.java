package com.library.demo.service;

import com.library.demo.entity.UserAcount;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;

public interface UserAcountService {

    public UserAcount addUserAcount(UserAcount userAcount) throws DataConflictException;

    public UserAcount updateUserAcount(UserAcount userAcount) throws DataConflictException, DataNotFoundException ;
}
