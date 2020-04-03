package com.mt.base.generator.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * 注释生成器
 * Created by HUGO on 2019-03-03
 */
public class CommentGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;
    private boolean suppressAllComments = false;

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
    }

    /**
     * 给字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if (this.addRemarkComments && StringUtility.stringHasValue(remarks)) {
            //文档注释开始
            field.addJavaDocLine("/**");
            //获取数据库字段的备注信息
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine(" * " + remarkLine);
            }
            addJavadocTag(field, false);
            field.addJavaDocLine(" */");
            List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
            for (IntrospectedColumn col : primaryKeyColumns) {
                if (col.getActualColumnName().equals(introspectedColumn.getActualColumnName())) {
                    field.addAnnotation("@Id");
                }
            }
            field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("javax.persistence.Column");
        topLevelClass.addImportedType("javax.persistence.Id");
        topLevelClass.addImportedType("javax.persistence.Table");

        Properties systemProperties = System.getProperties();
        String author = systemProperties.getProperty("user.name");
        try {
            PropertiesConfiguration generatorProperties = new PropertiesConfiguration();
            generatorProperties.setEncoding("UTF-8");
            generatorProperties.load("application.properties");
            author = generatorProperties.getString("generator.author");
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
        }
        String currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

        if (this.suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        topLevelClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(author);
        sb.append(" ");
        sb.append(currentDateStr);
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.addAnnotation("@Table(name = \"" + introspectedTable.getFullyQualifiedTable() + "\")");
    }
}
