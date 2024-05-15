package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.model.dao.ProductMapper;
import com.chenze.projectadvancementdemo.model.pojo.Category;
import com.chenze.projectadvancementdemo.model.pojo.Product;
import com.chenze.projectadvancementdemo.model.query.ProductListQuery;
import com.chenze.projectadvancementdemo.model.request.ProductListReq;
import com.chenze.projectadvancementdemo.model.request.UpdateProductReq;
import com.chenze.projectadvancementdemo.model.vo.CategoryVO;
import com.chenze.projectadvancementdemo.model.vo.ProductVO;
import com.chenze.projectadvancementdemo.service.CategoryService;
import com.chenze.projectadvancementdemo.service.FileUploadService;
import com.chenze.projectadvancementdemo.service.ProductService;
import com.chenze.projectadvancementdemo.untils.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;

    @Autowired
    FileUploadService fileUploadService;

    @Override
    public Product detail(Integer productId) {
         Product product = productMapper.selectByPrimaryKey(productId);
         if (product==null){
             throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
         }
        return product;
    }

    @Override
    @Caching
    public PageInfo<ProductVO> listOfUser(ProductListReq productListReq) {
         ProductListQuery productListQuery = new ProductListQuery();
         //productListQuery的keyWord的处理
        if (productListReq.getKeyWord()!=null){
            String key = new StringBuilder().append("%").append(productListReq.getKeyWord()).append("%").toString();
            productListQuery.setKeyWord(key);
        }
        //进行递归查询,productListQuery的ids的处理
        if (productListReq.getCategoryId()!=null){
            List<CategoryVO> categoryVOS = categoryService.userList(productListReq.getCategoryId());
            List<Integer> listIds = new ArrayList<>();
            listIds.add(productListReq.getCategoryId());
            recursivelyFindCategoryIds(categoryVOS,listIds);
            productListQuery.setCategoryIds(listIds);
        }
        //排序处理
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(productListReq.getOrderBy())){
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize(), productListReq.getOrderBy());
        }else{
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize());
        }
        //返回一个商品列表
        List<Product> products = productMapper.selectList(productListQuery);
         List<ProductVO> productVOS = productListToProductVOList(products);
        PageInfo<ProductVO> productPageInfo = new PageInfo<>(productVOS);
        return productPageInfo;
    }

    @Override
    public void delete(Integer productId) {
         Product result = productMapper.selectByPrimaryKey(productId);
        if (result == null) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }
         int count = productMapper.deleteByPrimaryKey(productId);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAIL);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer selectStatus) {
         int count = productMapper.batchUpdate(ids, selectStatus);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public void update(Product product) {
         Product result = productMapper.selectByPrimaryKey(product.getId());
        if (result == null) {
            throw new MallException(MallExceptionEnum.PRODUCT_NOT_EXIST);
        }
        int count = productMapper.updateByPrimaryKeySelective(result);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    @Override
    public PageInfo<Product> list(Integer pageNum, Integer pageSize) {
        List<Product> products = productMapper.selectcqList();
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Product> productPageInfo = new PageInfo<>(products);
        return productPageInfo;
    }

    @Override
    public void addProductByExcel(File destFile) throws IOException {
        List<Product> products = readProductsFromExcel(destFile);
        for (Product product: products) {
             Product result = productMapper.selectByName(product.getName());
            if (result != null) {
                continue;
            }
             int count = productMapper.insertSelective(product);
            if (count ==0) {
                throw new MallException(MallExceptionEnum.UPDATE_FAIL);
            }
        }
    }

    @Override
    public void add(Product product) {
         Product result = productMapper.selectByName(product.getName());
        if (result != null) {
            throw new MallException(MallExceptionEnum.PRODUCT_EXIST);
        }
         int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
    }

    private List<ProductVO> productListToProductVOList(List<Product> products) {
        List<ProductVO> list = new ArrayList<>();
        for (Product product:products) {
             ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product,productVO);
            list.add(productVO);
        }
        return list;
    }

    private void recursivelyFindCategoryIds(List<CategoryVO> categoryVOS, List<Integer> listIds) {
        for (CategoryVO category :categoryVOS) {
            listIds.add(category.getId());
            recursivelyFindCategoryIds(category.getChildCategoryList(),listIds);
        }
    }

    private List<Product> readProductsFromExcel(File excelFile) throws IOException {
        List<Product> listProducts = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        while(iterator.hasNext()){
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Product aProduct = new Product();

            while(cellIterator.hasNext()){
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex){
                    case 0:
                        aProduct.setName((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 1:
                        aProduct.setImage((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 2:
                        aProduct.setDetail((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 3:
                        Double cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setCategoryId(cellValue.intValue());
                        break;
                    case 4:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setPrice(cellValue.intValue());
                        break;
                    case 5:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setStock(cellValue.intValue());
                        break;
                    case 6:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setStatus(cellValue.intValue());
                        break;
                    default:
                        break;
                }
            }
            listProducts.add(aProduct);
        }
        workbook.close();
        fileInputStream.close();
        return listProducts;
    }
}
