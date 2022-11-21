package com.library.demo.web.rest;


import com.library.demo.dto.response.PageResponse;
import com.library.demo.dto.response.ServiceResponse;
import com.library.demo.entity.CheckoutBook;
import com.library.demo.web.rest.request.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "Checkout Book Resource")
@RequestMapping("checkoutBook")
public interface CheckoutBookResource {

    @ApiOperation(value = "get all book or follow keyword")
    @GetMapping("getList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")})
    ServiceResponse<PageResponse<CheckoutBook>> getBookList(@RequestParam(required = false) String keyWord , @Valid @ApiIgnore PageCriteriaRequest pageCriteriaRequest);


    @ApiOperation(value = "add checkout book")
    @PostMapping("add")
    ServiceResponse<CheckoutBook> addBook( @RequestBody CheckoutBookRequest checkoutBookRequest);


    @ApiOperation(value = "update checkout book information")
    @PutMapping("update")
    ServiceResponse<CheckoutBook> updateBook(@Valid @RequestBody CheckoutBookUpdateRequest checkoutBookUpdateRequest) ;

    @ApiOperation(value = "delete checkout Book")
    @DeleteMapping("delete")
    ServiceResponse<HttpStatus> deleteBook (@RequestBody CheckoutBookListDeleteRequest ids);
}
