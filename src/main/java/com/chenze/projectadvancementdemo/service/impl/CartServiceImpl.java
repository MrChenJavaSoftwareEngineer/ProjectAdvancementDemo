package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.filter.UserFilter;
import com.chenze.projectadvancementdemo.model.dao.CartMapper;
import com.chenze.projectadvancementdemo.model.dao.ProductMapper;
import com.chenze.projectadvancementdemo.model.pojo.Cart;
import com.chenze.projectadvancementdemo.model.pojo.Product;
import com.chenze.projectadvancementdemo.model.vo.CartVO;
import com.chenze.projectadvancementdemo.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<CartVO> list() {
        List<Cart> carts = cartMapper.selectList(UserFilter.currentUser.getId());
        List<CartVO> cartVOs = listCartVOs(carts);
        return cartVOs;
    }

    @Override
    public List<CartVO> delete(Integer cartId) {
         Cart result = cartMapper.selectByPrimaryKey(cartId);
        if (result == null) {
            throw new MallException(MallExceptionEnum.CART_NOT_EXISTED);
        }
         int count = cartMapper.deleteByPrimaryKey(cartId);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELETE_FAIL);
        }
        return list();
    }

    @Override
    public List<CartVO> update(Cart cart) {
         Cart result = cartMapper.selectByPrimaryKey(cart.getId());
        if (result == null) {
            throw new MallException(MallExceptionEnum.CART_NOT_EXISTED);
        }
        BeanUtils.copyProperties(cart,result);
         int count = cartMapper.updateByPrimaryKeySelective(result);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
        return list();
    }

    @Override
    public List<CartVO> select(Integer cartId, Integer select) {
         Cart cart = cartMapper.selectByPrimaryKey(cartId);
        if (cart == null) {
            throw new MallException(MallExceptionEnum.CART_NOT_EXISTED);
        }
        cart.setSelected(select);
        int count = cartMapper.updateByPrimaryKeySelective(cart);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
        return list();
    }

    @Override
    public List<CartVO> selectList(Integer select) {
        for (Cart cart : cartMapper.selectList(UserFilter.currentUser.getId())) {
            cart.setSelected(select);
            int count = cartMapper.updateByPrimaryKeySelective(cart);
            if (count == 0) {
                throw new MallException(MallExceptionEnum.UPDATE_FAIL);
            }
        }
        return list();
    }

    @Override
    public List<CartVO> add(Cart cart) {
        Product product = productMapper.selectByPrimaryKey(cart.getProductId());
        validProduct(product, cart.getQuantity());
        Cart result = cartMapper.selectByPrimaryKey(cart.getId());
        if (result != null) {
            throw new MallException(MallExceptionEnum.CART_EXISTED);
        }
        int count = cartMapper.insertSelective(cart);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.UPDATE_FAIL);
        }
        return list();
    }

    private List<CartVO> listCartVOs(List<Cart> carts) {
        List<CartVO> cartVOs = new ArrayList<>();
        for (Cart cart: carts) {
             Product product = productMapper.selectByPrimaryKey(cart.getProductId());
             validProduct(product,cart.getQuantity());
             CartVO cartVO = new CartVO();
             BeanUtils.copyProperties(cart,cartVO);
             cartVO.setPrice(product.getPrice());
             cartVO.setTotalPrice(cart.getQuantity()*product.getPrice());
             cartVO.setProductImage(product.getImage());
             cartVO.setProductName(product.getName());
             cartVOs.add(cartVO);
        }
        return cartVOs;
    }

    private void validProduct(Product product, Integer quantity) {
        if (product==null||product.getStatus().equals(Constant.ProductStatus.NOT_SALE)){
            throw new MallException(MallExceptionEnum.NOT_SALE);
        }
        if (product.getStock()<quantity){
            throw new MallException(MallExceptionEnum.LOWS_STOCK);
        }
    }
}
