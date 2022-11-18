package com.library.demo.web.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DeleteBookListRequest {

    private List<String> ids = new ArrayList<>();
}
