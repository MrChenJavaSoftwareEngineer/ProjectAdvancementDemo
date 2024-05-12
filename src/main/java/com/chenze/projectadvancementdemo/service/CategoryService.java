package com.chenze.projectadvancementdemo.service;

import com.chenze.projectadvancementdemo.model.pojo.Category;
import com.chenze.projectadvancementdemo.model.vo.CategoryVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    void delete(Integer categoryId);

    void update(Category category);

    PageInfo<Category> list(Integer pageNum, Integer pageSize);

    List<CategoryVO> userList(Integer parentId);
}
