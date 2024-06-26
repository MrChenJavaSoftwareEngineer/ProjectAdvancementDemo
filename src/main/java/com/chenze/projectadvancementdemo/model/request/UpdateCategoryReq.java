package com.chenze.projectadvancementdemo.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class UpdateCategoryReq {
    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message = "目录名不能为空")
    private String name;

    @NotNull(message = "类型不能为空")
    @Max(3)
    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    @Override
    public String toString() {
        return "UpdateCategoryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
