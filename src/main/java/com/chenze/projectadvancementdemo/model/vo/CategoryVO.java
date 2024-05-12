package com.chenze.projectadvancementdemo.model.vo;

import java.util.ArrayList;
import java.util.List;

public class CategoryVO {
    private Integer id;

    private String name;

    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    private List<CategoryVO> childCategoryList=new ArrayList<>();

    @Override
    public String toString() {
        return "CategoryVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                ", childCategoryList=" + childCategoryList +
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

    public List<CategoryVO> getChildCategoryList() {
        return childCategoryList;
    }

    public void setChildCategoryList(List<CategoryVO> childCategoryList) {
        this.childCategoryList = childCategoryList;
    }
}
