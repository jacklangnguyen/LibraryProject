package com.library.demo.web.rest.request;

import com.library.demo.resolver.ParamName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PageCriteria {

    public static final String ASC_SYMBOL = "+";
    public static final String DESC_SYBOL = "-";

    @Min(value = 1, message = "page must be greater than 0")
    @Max(value =  1000000, message = "page must be less than 1000000")
    @ParamName("page")
    private Integer page = 1;

    @Min(value = 1, message = "limit must be greater than 0")
    @Max(value = 100, message = "limit must be less than or equal to 100")
    @ParamName("limit")
    private Integer limit = 50;

    @ParamName("sort")
    private List<String> sort = new ArrayList<>();

    public void setLimit(Integer limit) {
        if (limit != null) {
            this.limit = limit;
        }
    }

    public void setPage(Integer page) {
        if (page != null) {
            this.page = page;
        }
    }
}
