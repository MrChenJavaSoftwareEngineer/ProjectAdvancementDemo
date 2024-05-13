package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.service.FileUploadService;
import com.chenze.projectadvancementdemo.service.ProductService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.poi.hpsf.Thumbnail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Service
public class FileUploadServiceImpl implements FileUploadService {
    String ui="127.0.0.1:8081";
    @Autowired
    ProductService productService;

    @Override
    public void createFile(File fileDir, File file){
        if (!fileDir.exists()){
            if (!fileDir.mkdir()){
                throw new MallException(MallExceptionEnum.MAKE_FILE_FAIL);
            }
        }

         if (!file.exists()){
            if (!file.mkdir()){
                throw new MallException(MallExceptionEnum.MAKE_FILE_FAIL);
            }
        }
    }

    @Override
    public String getResult(MultipartFile file) throws IOException {
        //获取文件名
         String originalFilename = file.getOriginalFilename();
         //截取文件后缀
         String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
         //uuid生成文件名，避免重复文件名
         UUID uuid = UUID.randomUUID();
        String newFile = uuid + substring;
         File fileDir = new File(Constant.FILE_UPLOAD_DIR);
         File destFile = new File(Constant.FILE_UPLOAD_DIR + newFile);
         //判断是否创建成功
         createFile(fileDir,destFile);
         file.transferTo(destFile);
         String address = ui;
         String result = "http://" + address+"/images/"+newFile;
         return result;
    }

    @Override
    public String getString(MultipartFile imageFile) throws IOException {
        //获取文件名
        String originalFilename = imageFile.getOriginalFilename();
        //截取文件后缀
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //uuid生成文件名，避免重复文件名
        UUID uuid = UUID.randomUUID();
        String newFile = uuid + substring;
        File fileDir = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFile);
        //判断是否创建成功
        createFile(fileDir,destFile);
        imageFile.transferTo(destFile);
        //对图片的处理
        Thumbnails.of(destFile).size(Constant.IMAGE_LENGTH,Constant.IMAGE_HEIGHT)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read
                        (new File(Constant.FILE_UPLOAD_DIR+Constant.WATER_MARK_JPG)
                        ),Constant.IMAGE_OPACITY).toFile(new File(Constant.FILE_UPLOAD_DIR
                        +newFile));

        String address = ui;
        String result = "http://" + address+"/images/"+newFile;
        return result;
    }

    @Override
    public void fileUploadOfExcel(MultipartFile fileUploadOfExcel) throws IOException {
        //获取文件的类型后缀
        String originalFilename = fileUploadOfExcel.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成uuid，避免文件名重复
        UUID uuid = UUID.randomUUID();
        String newFile = uuid + substring;
        //创建文件夹和文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile= new File(Constant.FILE_UPLOAD_DIR + newFile);
        createFile(fileDirectory, destFile);
        //进行文件的转移，必须是文件，不是字符串。
        fileUploadOfExcel.transferTo(destFile);
        productService.addProductByExcel(destFile);
    }
}
