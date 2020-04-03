package com.mt.base.generator.entity;

import javax.validation.constraints.NotEmpty;
import java.util.List;


public class GenerateParam {
    @NotEmpty
    private List<String> tables;
    @NotEmpty
    private String baseDir;
    @NotEmpty
    private String basePackage;
    private String entityPackage;
    private String entityDirectory;
    private String sqlMapperPackage;
    private String sqlMapperDirectory;
    private String clientPackage;
    private String clientDirectory;

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getEntityDirectory() {
        return entityDirectory;
    }

    public void setEntityDirectory(String entityDirectory) {
        this.entityDirectory = entityDirectory;
    }

    public String getSqlMapperPackage() {
        return sqlMapperPackage;
    }

    public void setSqlMapperPackage(String sqlMapperPackage) {
        this.sqlMapperPackage = sqlMapperPackage;
    }

    public String getSqlMapperDirectory() {
        return sqlMapperDirectory;
    }

    public void setSqlMapperDirectory(String sqlMapperDirectory) {
        this.sqlMapperDirectory = sqlMapperDirectory;
    }

    public String getClientPackage() {
        return clientPackage;
    }

    public void setClientPackage(String clientPackage) {
        this.clientPackage = clientPackage;
    }

    public String getClientDirectory() {
        return clientDirectory;
    }

    public void setClientDirectory(String clientDirectory) {
        this.clientDirectory = clientDirectory;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
