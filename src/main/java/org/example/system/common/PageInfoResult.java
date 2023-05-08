package org.example.system.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PageInfoResult implements Serializable {
    private static final long serialVersionUID = 3648413853092244951L;

    private Long total = 0L;
    private Integer pageNum;
    private Integer pageSize;
    private List<?> data = Collections.emptyList();

    public static PageInfoResult init(Integer pageNum, Integer pageSize) {
        return new PageInfoResult().setPageNum(pageNum).setPageSize(pageSize);
    }
}
