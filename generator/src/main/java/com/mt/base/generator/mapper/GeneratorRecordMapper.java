package com.mt.base.generator.mapper;

import com.mt.base.generator.entity.GenerateRecord;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


public interface GeneratorRecordMapper extends BaseMapper<GenerateRecord> {
    void deleteByTableName(String tableName);
    GenerateRecord selectByName(String tableName);
    List<GenerateRecord> selectRecords(String tableName);
}
