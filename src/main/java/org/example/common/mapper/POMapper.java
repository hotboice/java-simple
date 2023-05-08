package org.example.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.common.config.MyMapper;
import org.example.common.entity.PO;

@Mapper
public interface POMapper extends MyMapper<PO> {
}