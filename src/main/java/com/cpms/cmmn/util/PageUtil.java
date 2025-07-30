package com.cpms.cmmn.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static Pageable createPageable(int pageNo, int pageSize) {
        int page = pageNo > 0 ? pageNo - 1 : 0; // Spring Data JPA는 페이지 번호가 0부터 시작
        return PageRequest.of(page, pageSize);
    }
}
