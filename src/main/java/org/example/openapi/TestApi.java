package org.example.openapi;

import com.alibaba.fastjson.JSON;
import org.example.common.entity.PO;
import org.example.common.mapper.POMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TestApi {
    @Autowired
    private POMapper poMapper;

    @RequestMapping(value = "/")
    public void home() {
        List<PO> pos = poMapper.selectAll();
        System.out.println(JSON.toJSONString(pos));
    }
}