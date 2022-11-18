package com.library.demo.utils;


import com.library.demo.web.rest.request.PageCriteria;

import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

public class SetupPage {

    public static void setParamsWithPageable(@NotNull Query query, Map<String, Object> params, @NotNull PageCriteria pageable, @NotNull Number total){

        if(params != null && !params.isEmpty()) {
            Set<Map.Entry<String, Object>> set = params.entrySet();
            for (Map.Entry<String, Object> obj : set) {
                query.setParameter(obj.getKey(), obj.getValue());
            }
        }

        query.setFirstResult((pageable.getPage() - 1) * pageable.getLimit());
        query.setMaxResults(pageable.getLimit());
    }

    public static void setParams(@NotNull Query query, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            Set<Map.Entry<String, Object>> set = params.entrySet();
            for (Map.Entry<String, Object> obj : set) {
                query.setParameter(obj.getKey(), obj.getValue());
            }
        }

    }

}
