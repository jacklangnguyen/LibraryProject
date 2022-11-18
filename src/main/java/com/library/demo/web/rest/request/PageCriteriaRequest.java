package com.library.demo.web.rest.request;



import com.library.demo.resolver.ParamName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCriteriaRequest {

    @Pattern(regexp = "[\\s]*[0-9]*[\\s]*", message = "Invalid input : '${validatedValue}'")
    @ParamName("page")
    private String page;

    @Pattern(regexp = "[\\s]*[0-9]*[\\s]*", message = "Invalid input : '${validatedValue}'")
    @ParamName("limit")
    private String limit;

    @ParamName("sort")
    private List<String> sort = new ArrayList<>();

    public PageCriteria getPageCriteria() {
        PageCriteria pageCriteria = new PageCriteria();
        pageCriteria.setPage(StringUtils.isNotBlank(this.page) ? Integer.parseInt(this.page.trim()) : null);
        pageCriteria.setLimit(StringUtils.isNotBlank(this.limit) ? Integer.parseInt(this.limit.trim()) : null);
        pageCriteria.setSort(this.sort);
        return pageCriteria;
    }


}
