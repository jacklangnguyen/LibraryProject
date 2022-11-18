package com.library.demo.utils;


import com.library.demo.web.rest.request.PageCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageCriteriaPageableMapper implements PageableMapper<PageCriteria> {

    public PageCriteriaPageableMapper() {
    }

    public Pageable toPageable(PageCriteria criteria) {

        List<Sort.Order> orders = new ArrayList();
        if (!CollectionUtils.isEmpty(criteria.getSort())) {
            String sortStr = (String)criteria.getSort().get(criteria.getSort().size() - 1);
            if (!StringUtils.isEmpty(sortStr)) {
                if (sortStr.startsWith("-") && sortStr.length() > 1 ) {
                    orders.add(Sort.Order.desc(sortStr.substring(1)));
                } else if (sortStr.startsWith("+") && sortStr.length() >1 ) {
                    orders.add(Sort.Order.asc(sortStr.substring(1)));
                } else {
                    orders.add(Sort.Order.asc(sortStr));
                }

            }
        }

        return PageRequest.of(criteria.getPage() - 1 , criteria.getLimit(), Sort.by(orders));

    }

}
