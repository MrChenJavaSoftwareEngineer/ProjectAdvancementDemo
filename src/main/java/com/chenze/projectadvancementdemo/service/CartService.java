package com.chenze.projectadvancementdemo.service;

import com.chenze.projectadvancementdemo.model.pojo.Cart;
import com.chenze.projectadvancementdemo.model.vo.CartVO;

import java.util.List;

public interface CartService{
    List<CartVO> list();

    List<CartVO> delete(Integer cartId);

    List<CartVO> update(Cart cart);

    List<CartVO> select(Integer cartId, Integer select);

    List<CartVO> selectList(Integer select);

    List<CartVO> add(Cart cart);
}
