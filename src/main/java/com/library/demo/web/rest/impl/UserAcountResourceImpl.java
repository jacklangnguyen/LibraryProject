package com.library.demo.web.rest.impl;

import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.Book;
import com.library.demo.entity.UserAcount;
import com.library.demo.service.UserAcountService;
import com.library.demo.utils.exception.DataConflictException;
import com.library.demo.utils.exception.DataNotFoundException;
import com.library.demo.web.rest.UserAcountResource;
import com.library.demo.web.rest.request.UserAcountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserAcountResourceImpl implements UserAcountResource {

   private final UserAcountService  userAcountService;

    public UserAcountResourceImpl(UserAcountService userAcountService) {
        this.userAcountService = userAcountService;
    }

    @Override
    public ServiceResponse<UserAcount> addUserAcount(UserAcountRequest request) {
        log.debug("Rest add User Acount");
        try {
            UserAcount response = userAcountService.addUserAcount(request.toUserAcount());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<UserAcount> updateUserAcount(UserAcountRequest request) {
        log.debug("Rest update User Acount");
        try {
            UserAcount response = userAcountService.updateUserAcount(request.toUserAcount());
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }
}
