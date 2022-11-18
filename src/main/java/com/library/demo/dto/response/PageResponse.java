package com.library.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T>{

    private long count;
    private List<T> rows;
    private int page;
    private int limit;

    public long getTotalPage() {
        long mod = count % (long) limit;
        long totalPage = count / limit;
        totalPage += mod > 0 ? 1 : 0;
        return totalPage;
    }

}
