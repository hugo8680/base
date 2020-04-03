package com.mt.base.generator.entity;


public class TableInfo {
    private String tableName;
    private String engine;
    private String tableComment;
    private String createTime;
    private Boolean hasGenerated;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Boolean getHasGenerated() {
        return this.hasGenerated == null ? false : this.hasGenerated;
    }

    public void setHasGenerated(Boolean hasGenerated) {
        this.hasGenerated = hasGenerated;
    }
}
