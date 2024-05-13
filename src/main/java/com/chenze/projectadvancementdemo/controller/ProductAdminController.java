package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.pojo.Product;
import com.chenze.projectadvancementdemo.model.request.AddProductReq;
import com.chenze.projectadvancementdemo.model.request.UpdateProductReq;
import com.chenze.projectadvancementdemo.service.FileUploadService;
import com.chenze.projectadvancementdemo.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductAdminController {
    @Autowired
    ProductService productService;
    @Autowired
    FileUploadService fileUploadService;

    //删除商品
    @ApiOperation("删除商品")
    @PostMapping("/admin/product/delete")
    public ApiRestResponse delete(@RequestParam("ProductId") Integer productId){
        productService.delete(productId);
        return ApiRestResponse.success();
    }

    //批量上下架
    @ApiOperation("批量上下架")
    @PostMapping("/admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam("ids") Integer[] ids,
                                        @RequestParam("selectStatus") Integer selectStatus){
        productService.batchUpdateSellStatus(ids,selectStatus);
        return ApiRestResponse.success();
    }

    //更新
    @ApiOperation("更新")
    @PostMapping("/admin/product/update")
    public ApiRestResponse update(@Valid @RequestBody UpdateProductReq updateProductReq){
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq,product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    //批量更新商品
    @ApiOperation("批量更新商品")
    @PostMapping("/admin/product/updateList")
    public ApiRestResponse updateList(@Validated @RequestBody List<UpdateProductReq> updateProductReqList){
        for (UpdateProductReq updateProductReq : updateProductReqList) {
             Product product = new Product();
            BeanUtils.copyProperties(updateProductReq,product);
            productService.update(product);
        }
        return ApiRestResponse.success();
    }

    //后台商品表
    @ApiOperation("后台商品表")
    @PostMapping("/admin/product/list")
    public ApiRestResponse list(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize){
         PageInfo<Product> list = productService.list(pageNum, pageSize);
        return ApiRestResponse.success(list);
    }

    //后台上传文件
    @ApiOperation("后台上传文件")
    @PostMapping("/admin/upload/file")
        public ApiRestResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
         String result = fileUploadService.getResult(file);
        return ApiRestResponse.success(result);
    }

    //后台上传照片
    @ApiOperation("后台上传照片")
    @PostMapping("/admin/upload/imageFile")
    public ApiRestResponse imageFile(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String result = fileUploadService.getString(imageFile);
        return ApiRestResponse.success(result);
    }

    //后台上传Excel商品列表
    @ApiOperation("后台上传Excel商品列表")
    @PostMapping("/admin/upload/fileUploadOfExcel")
    public ApiRestResponse excelFile(@RequestParam("fileUploadOfExcel") MultipartFile fileUploadOfExcel) throws IOException {
      fileUploadService.fileUploadOfExcel(fileUploadOfExcel);
        return ApiRestResponse.success();
    }

    //商品添加
    @ApiOperation("商品添加")
    @PostMapping("/admin/product/add")
    public ApiRestResponse add(@Valid @RequestBody AddProductReq addProductReq){
         Product product = new Product();
         BeanUtils.copyProperties(addProductReq,product);
        productService.add(product);
        return ApiRestResponse.success();
    }
}
