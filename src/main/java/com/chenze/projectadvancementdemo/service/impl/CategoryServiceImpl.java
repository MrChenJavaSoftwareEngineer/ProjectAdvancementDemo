package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.model.dao.CategoryMapper;
import com.chenze.projectadvancementdemo.model.pojo.Category;
import com.chenze.projectadvancementdemo.model.vo.CategoryVO;
import com.chenze.projectadvancementdemo.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
         Category result = categoryMapper.selectByPrimaryKey(category.getId());
        if (result != null) {
            throw new MallException(MallExceptionEnum.CATEGORY_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public void delete(Integer categoryId) {
         Category result = categoryMapper.selectByPrimaryKey(categoryId);
         if (result==null){
             throw new MallException(MallExceptionEnum.CATEGORY_NOT_EXISTED);
         }
         int count = categoryMapper.deleteByPrimaryKey(categoryId);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public void update(Category category) {
         Category result = categoryMapper.selectByPrimaryKey(category.getId());
        if (result == null) {
            throw new MallException(MallExceptionEnum.CATEGORY_NOT_EXISTED);
        }
        BeanUtils.copyProperties(category,result);
        int count = categoryMapper.updateByPrimaryKeySelective(result);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public PageInfo<Category> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categories = categoryMapper.selectList();
         PageInfo<Category> categoryPageInfo = new PageInfo<>(categories);
         return categoryPageInfo;
    }

    @Override
    public List<CategoryVO> userList(Integer parentId) {
        List<CategoryVO> categoryVOList = new ArrayList<>();
        List<CategoryVO> categoryVOS = recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOS;

    }

    private List<CategoryVO> recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        for (Category category: categoryMapper.selectByParentId(parentId)) {
             CategoryVO categoryVO = new CategoryVO();
             BeanUtils.copyProperties(category,categoryVO);
             categoryVOList.add(categoryVO);
             recursivelyFindCategories(categoryVO.getChildCategoryList(),categoryVO.getId());
        }
        return categoryVOList;
    }
}
