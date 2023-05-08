package org.example.system.common;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class PageParamsInfo implements Serializable {

    private Integer pageSize = 10;
    private Integer pageNum = 1;
}
