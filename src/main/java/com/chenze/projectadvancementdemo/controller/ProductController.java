package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.pojo.Product;
import com.chenze.projectadvancementdemo.model.request.ProductListReq;
import com.chenze.projectadvancementdemo.model.vo.ProductVO;
import com.chenze.projectadvancementdemo.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("商品的详情")
    @PostMapping("/product/detail")
    public ApiRestResponse detail(@RequestParam("productId") Integer productId){
         Product detail = productService.detail(productId);
        return ApiRestResponse.success(detail);
    }
    @ApiOperation("前台商品列表")
    @GetMapping("/product/listOfUser")
    public ApiRestResponse listOfUser(ProductListReq productListReq){
         PageInfo<ProductVO> productVOPageInfo = productService.listOfUser(productListReq);
        return ApiRestResponse.success(productVOPageInfo);
    }

}
