package com.chenze.projectadvancementdemo.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class AddCategoryReq {

    @NotNull(message = "目录名不能为空")
    private String name;

    @NotNull(message = "类型不能为空")
    @Max(3)
    private Integer type;


    @NotNull(message = "父id不能为空")
    private Integer parentId;

    @NotNull(message = "编号不能为空")
    private Integer orderNum;

    @Override
    public String toString() {
        return "AddCategoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
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
