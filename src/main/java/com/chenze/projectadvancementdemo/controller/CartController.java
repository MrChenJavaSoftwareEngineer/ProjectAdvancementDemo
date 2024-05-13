package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.pojo.Cart;
import com.chenze.projectadvancementdemo.model.request.AddCartReq;
import com.chenze.projectadvancementdemo.model.request.UpdateCartReq;
import com.chenze.projectadvancementdemo.model.vo.CartVO;
import com.chenze.projectadvancementdemo.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/list")
    public ApiRestResponse list(){
         List<CartVO> list = cartService.list();
        return ApiRestResponse.success(list);
    }

    @PostMapping("/delete")
    public ApiRestResponse delete(@RequestParam("cartId") Integer cartId ){
        List<CartVO> delete = cartService.delete(cartId);
        return ApiRestResponse.success(delete);
    }

    @PostMapping("/update")
    public ApiRestResponse update(@RequestBody UpdateCartReq updateCartReq){
         Cart cart = new Cart();
        BeanUtils.copyProperties(updateCartReq,cart);
        List<CartVO> update = cartService.update(cart);
        return ApiRestResponse.success(update);
    }

    @PostMapping("/select")
    public ApiRestResponse select(@RequestParam("cartId") Integer cartId,
                                  @RequestParam("select") Integer select){
         List<CartVO> select1 = cartService.select(cartId, select);
        return ApiRestResponse.success(select1);
    }

    @PostMapping("/selectList")
    public ApiRestResponse selectList(@RequestParam("select") Integer select){
        List<CartVO> cartVOS = cartService.selectList(select);
        return ApiRestResponse.success(cartVOS);
    }

    @PostMapping("/add")
    public ApiRestResponse add(@RequestBody AddCartReq addCartReq){
         Cart cart = new Cart();
         BeanUtils.copyProperties(addCartReq,cart);
         List<CartVO> add = cartService.add(cart);
        return ApiRestResponse.success(add);
    }
}
