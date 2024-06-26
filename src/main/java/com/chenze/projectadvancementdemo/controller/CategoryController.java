package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.pojo.Category;
import com.chenze.projectadvancementdemo.model.request.AddCategoryReq;
import com.chenze.projectadvancementdemo.model.request.UpdateCategoryReq;
import com.chenze.projectadvancementdemo.model.vo.CategoryVO;
import com.chenze.projectadvancementdemo.service.CategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @ApiOperation("添加目录")
    @PostMapping("/admin/category/add")
    public ApiRestResponse add(@Valid @RequestBody AddCategoryReq addCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        categoryService.add(category);
        return ApiRestResponse.success();
    }
    @ApiOperation("删除目录")
    @PostMapping("/admin/category/delete")
    public ApiRestResponse delete(@RequestParam("CategoryId") Integer categoryId){
        categoryService.delete(categoryId);
        return ApiRestResponse.success();
    }
    @ApiOperation("更新目录")
    @PostMapping("/admin/category/update")
    public ApiRestResponse update(@Valid @RequestBody UpdateCategoryReq updateCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq,category);
        categoryService.update(category);
        return ApiRestResponse.success();
    }
    @ApiOperation("后台目录")
    @PostMapping("/admin/category/list")
    public ApiRestResponse list(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize){
        PageInfo<Category> pageInfo = categoryService.list(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
    @ApiOperation("前台目录")
    @GetMapping("/userList")
    public ApiRestResponse userList(){
         List<CategoryVO> categoryVOS = categoryService.userList(0);
        return ApiRestResponse.success(categoryVOS);
    }
}
