package com.chenze.projectadvancementdemo.service;

import com.chenze.projectadvancementdemo.model.pojo.Product;
import com.chenze.projectadvancementdemo.model.request.AddProductReq;
import com.chenze.projectadvancementdemo.model.request.ProductListReq;
import com.chenze.projectadvancementdemo.model.request.UpdateProductReq;
import com.chenze.projectadvancementdemo.model.vo.ProductVO;
import com.github.pagehelper.PageInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product detail(Integer productId);

    PageInfo<ProductVO> listOfUser(ProductListReq productListReq);

    void delete(Integer productId);


    void batchUpdateSellStatus(Integer[] ids, Integer selectStatus);

    void update(Product product);

    PageInfo<Product> list(Integer pageNum, Integer pageSize);

    void addProductByExcel(File destFile) throws IOException;


    void add(Product product);
}
