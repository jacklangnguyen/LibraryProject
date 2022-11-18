package com.library.demo.utils;

import org.springframework.data.domain.Pageable;

public interface PageableMapper<T>{
    Pageable toPageable(T criteria);
}
