package com.mt.base.service.authentication.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Table(name = "auth_role")
public class Role implements Serializable, GrantedAuthority {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String remark;
    private String level;
    private Date createTime;
    private Date updateTime;
    private Long createId;
    private Long updateId;
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
