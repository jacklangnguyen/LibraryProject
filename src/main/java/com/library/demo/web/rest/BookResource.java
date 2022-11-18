package com.library.demo.web.rest;


import com.library.demo.dto.response.PageResponse;
import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.Book;
import com.library.demo.web.rest.request.BookRequest;
import com.library.demo.web.rest.request.DeleteBookListRequest;
import com.library.demo.web.rest.request.PageCriteriaRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@Api(tags = "Book Resource")
@RequestMapping("book")
public interface BookResource {

    @ApiOperation(value = "get all book or follow keyword")
    @GetMapping("getList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")})
    ServiceResponse<PageResponse<Book>> getBookList(@RequestParam(required = false) String keyWord , @Valid @ApiIgnore PageCriteriaRequest pageCriteriaRequest);


    @ApiOperation(value = "add book")
    @PostMapping("add")
    ServiceResponse<Book> addBook( @RequestBody BookRequest bookRequest);


    @ApiOperation(value = "update book information")
    @PutMapping("update")
    ServiceResponse<Book> updateBook(@RequestBody BookRequest bookRequest) ;

    @ApiOperation(value = "delete User")
    @DeleteMapping("delete")
    ServiceResponse<HttpStatus> deleteBook ( @RequestBody DeleteBookListRequest ids);
}

