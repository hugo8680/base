package com.mt.base.generator.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mt.base.common.utils.StringUtils;
import com.mt.base.generator.entity.GenerateParam;
import com.mt.base.generator.entity.GenerateRecord;
import com.mt.base.generator.entity.TableInfo;
import com.mt.base.generator.mapper.GeneratorMapper;
import com.mt.base.generator.mapper.GeneratorRecordMapper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 代码生成器
 *
 * @author HUGO
 * @date 2019-02-24
 */
@Service
public class GeneratorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorService.class);

    private GeneratorMapper generatorMapper;
    private GeneratorRecordMapper generatorRecordMapper;

    public GeneratorService(GeneratorMapper mapper, GeneratorRecordMapper recordMapper) {
        this.generatorRecordMapper = recordMapper;
        this.generatorMapper = mapper;
    }

    public PageInfo<TableInfo> selectTables(String tableName, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<TableInfo> tableInfos = this.generatorMapper.selectTables(tableName);
        return new PageInfo<>(tableInfos);
    }

    public Integer getTotalCount(Map<String, Object> map) {
        return generatorMapper.selectTotalCount(map);
    }

    public Map<String, String> getTable(String tableName) {
        return generatorMapper.selectTable(tableName);
    }

    public List<Map<String, String>> getColumns(String tableName) {
        return generatorMapper.selectColumns(tableName);
    }

    public PageInfo<GenerateRecord> selectRecords(String tableName, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<GenerateRecord> records = this.generatorRecordMapper.selectRecords(tableName);
        return new PageInfo<>(records);
    }

    public void clearRecord(String tableName) {
        this.generatorRecordMapper.deleteByTableName(tableName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void generateCode(GenerateParam generateParam) throws IOException, NullPointerException, XMLParserException, InvalidConfigurationException, InterruptedException, SQLException, ConfigurationException {
        String propertiesFileName = "application.properties";
        //MBG 执行过程中的警告信息
        List<String> warnings = new ArrayList<>();
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        configuration.setEncoding("UTF-8");
        configuration.load(propertiesFileName);
        String baseDir = generateParam.getBaseDir();
        String basePackage = generateParam.getBasePackage();
        String separater = File.separator;
        configuration.setProperty("generator.entity.package", basePackage + ".entity");
        configuration.setProperty("generator.sql.mapper.package", basePackage + ".mapper");
        configuration.setProperty("generator.client.package", basePackage + ".mapper");
        configuration.setProperty("generator.entity.directory", baseDir + separater + "java");
        configuration.setProperty("generator.sql.mapper.directory", baseDir + separater + "resources");
        configuration.setProperty("generator.client.directory", baseDir + separater + "java");
        configuration.save(propertiesFileName);
        boolean overwrite = configuration.getBoolean("generator.overwrite");

        //读取我们的 MBG 配置文件
        InputStream is = GeneratorService.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        Context context = config.getContext("MySqlContext");
        context.getTableConfigurations().clear();
        GeneratedKey commonGeneratedKey = new GeneratedKey("id", "MySql", true, "");
        for (String table : generateParam.getTables()) {
            TableConfiguration tc = new TableConfiguration(context);
            tc.setTableName(table);
            tc.setGeneratedKey(commonGeneratedKey);
            context.addTableConfiguration(tc);
        }
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建 MBG
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行生成代码
        myBatisGenerator.generate(null);
        //输出警告信息
        for (String warning : warnings) {
            LOGGER.warn(warning);
        }
        for (String i : generateParam.getTables()) {
            GenerateRecord generatorRecord = new GenerateRecord();
            generatorRecord.setBaseDir(generateParam.getBaseDir());
            generatorRecord.setBasePackage(generateParam.getBasePackage());
            generatorRecord.setCreateTime(new Date());
            generatorRecord.setUpdateTime(new Date());
            generatorRecord.setStatus(Boolean.TRUE);
            generatorRecord.setTableName(i);
            generatorRecord.setEntityName(StringUtils.toCamel(i) + ".java");
            generatorRecord.setExampleName(StringUtils.toCamel(i) + "Example.java");
            generatorRecord.setClientName(StringUtils.toCamel(i) + "Mapper.java");
            generatorRecord.setSqlMapperName(StringUtils.toCamel(i) + "Mapper.xml");
            this.generatorRecordMapper.insert(generatorRecord);
        }
    }
}
