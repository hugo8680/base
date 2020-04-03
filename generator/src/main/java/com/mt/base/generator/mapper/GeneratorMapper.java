package com.mt.base.generator.mapper;

import com.mt.base.generator.entity.TableInfo;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 代码生成器
 *
 * @author HUGO
 * @date 2019-02-24
 */
public interface GeneratorMapper extends BaseMapper<TableInfo> {

    List<TableInfo> selectTables(String tableName);

    Integer selectTotalCount(Map<String, Object> map);

    Map<String, String> selectTable(String tableName);

    List<Map<String, String>> selectColumns(String tableName);
}
