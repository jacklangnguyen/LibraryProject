package com.library.demo.web.rest;

import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.Book;
import com.library.demo.entity.UserAcount;
import com.library.demo.web.rest.request.BookRequest;
import com.library.demo.web.rest.request.UserAcountRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "User Resource")
@RequestMapping("userAcount")
public interface UserAcountResource {

    @ApiOperation(value = "add user acount")
    @PostMapping("add")
    ServiceResponse<UserAcount> addUserAcount(@RequestBody UserAcountRequest request);

    @ApiOperation(value = "update user acount information")
    @PutMapping("update")
    ServiceResponse<UserAcount> updateUserAcount(@RequestBody UserAcountRequest request) ;

}
