package com.cpms.cmmn.helper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReqPageDTO {

    private int pageNo;

    private int pageSize;

    private int limitStart;

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        calculateLimits();
    }

    private void calculateLimits() {
        this.limitStart = (pageNo - 1) * pageSize;
    }
}
