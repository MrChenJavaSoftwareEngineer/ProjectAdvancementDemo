package com.chenze.projectadvancementdemo.service.impl;

import com.chenze.projectadvancementdemo.common.Constant;
import com.chenze.projectadvancementdemo.exception.MallException;
import com.chenze.projectadvancementdemo.exception.MallExceptionEnum;
import com.chenze.projectadvancementdemo.filter.UserFilter;
import com.chenze.projectadvancementdemo.model.dao.CartMapper;
import com.chenze.projectadvancementdemo.model.dao.OrderItemMapper;
import com.chenze.projectadvancementdemo.model.dao.OrderMapper;
import com.chenze.projectadvancementdemo.model.dao.ProductMapper;
import com.chenze.projectadvancementdemo.model.pojo.*;
import com.chenze.projectadvancementdemo.model.query.OrderStatisticsQuery;
import com.chenze.projectadvancementdemo.model.vo.OrderItemVO;
import com.chenze.projectadvancementdemo.model.vo.OrderStatisticsVO;
import com.chenze.projectadvancementdemo.model.vo.OrderVO;
import com.chenze.projectadvancementdemo.service.CartService;
import com.chenze.projectadvancementdemo.service.OrderService;
import com.chenze.projectadvancementdemo.service.UserService;
import com.chenze.projectadvancementdemo.untils.OrderCodeFactory;
import com.chenze.projectadvancementdemo.untils.QRCodeGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;

    String ui="127.0.0.1:8081";

    @Override
    public PageInfo<OrderVO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
         User currentUser = UserFilter.currentUser;
        if (userService.checkAdmin(currentUser)) {
             List<Order> orders = orderMapper.selectAll();
             if (orders==null){
                 throw new MallException(MallExceptionEnum.NOT_ORDER);
             }
            List<OrderVO> orderVOS = orderToOrderVO(orders);
             return new PageInfo<>(orderVOS);
        }
        List<Order> orders = orderMapper.selectByUserId(currentUser.getId());
        if (orders==null){
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        List<OrderVO> orderVOS = orderToOrderVO(orders);
        return new PageInfo<>(orderVOS);
    }

    private List<OrderVO> orderToOrderVO(List<Order> orders) {
        List<OrderVO> orderVOs = new ArrayList<>();
        for (Order order:orders) {
             OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order,orderVO);
            orderVO.setOrderStatusName(Constant.orderStatusName(order.getOrderStatus()));
            orderVOs.add(orderVO);
        }
        return orderVOs;
    }

    @Override
    public void cancel(Integer orderNo) {
        User currentUser = UserFilter.currentUser;
         Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        if (!userService.checkAdmin(currentUser)&&
        !currentUser.getId().equals(order.getUserId()) ||
        order.getOrderStatus().equals(Constant.OrderStatus.DELIVER.getStatusCode())) {
            throw new MallException(MallExceptionEnum.CANCEL_FAIL);
        }
        order.setOrderStatus(Constant.OrderStatus.CANCEL.getStatusCode());
         int count = orderMapper.updateByPrimaryKeySelective(order);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.CANCEL_FAIL);
        }
    }

    @Override
    public void finish(Integer orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        if (!order.getOrderStatus().equals(Constant.OrderStatus.FINISH.getStatusCode())) {
            throw new MallException(MallExceptionEnum.FINISH_ERROR);
        }
        order.setOrderStatus(Constant.OrderStatus.FINISH.getStatusCode());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.FINISH_FAIL);
        }
    }

    @Override
    public String createOrder(Order order) {
         String orderNo= OrderCodeFactory.getOrderCode(UserFilter.currentUser.getId().longValue());
         Integer totalPrice=0;
         Integer price=0;
         List<Cart> cartList = new ArrayList<>();
        for (Cart cart:cartMapper.selectList(UserFilter.currentUser.getId())) {
            if (cart.getSelected().equals(Constant.CartSelected.CART_SELECTED.getSelectedCode())){
                cartList.add(cart);
            }
        }

        for (Cart carts:cartList) {
             Product product = productMapper.selectByPrimaryKey(carts.getProductId());
             cartService.validProduct(product,carts.getQuantity());
             totalPrice+=product.getPrice()*carts.getQuantity();
            price= carts.getQuantity() * product.getPrice();
            totalPrice+=price;
            int count = product.getStock() - carts.getQuantity();
            product.setStock(count);
            int i = productMapper.updateByPrimaryKeySelective(product);
            if (i == 0) {
                throw new MallException(MallExceptionEnum.INSERT_FAILED);
            }
            int in = cartMapper.deleteByPrimaryKey(carts.getId());
            if (in == 0) {
                throw new MallException(MallExceptionEnum.DELETE_FAIL);
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderNo(orderNo);
            orderItem.setProductId(carts.getProductId());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setProductImg(product.getImage());
            orderItem.setProductName(product.getName());
            orderItem.setTotalPrice(price);
            orderItem.setQuantity(carts.getQuantity());
            int i1 = orderItemMapper.insertSelective(orderItem);
            if (i1 == 0) {
                throw new MallException(MallExceptionEnum.INSERT_FAILED);
            }

        }
        //生成订单和写入order表
        order.setOrderNo(orderNo);
        order.setUserId(UserFilter.currentUser.getId());
        order.setTotalPrice(totalPrice);
        order.setOrderStatus(Constant.OrderStatus.NOT_PAY.getStatusCode());
        int i = orderMapper.insertSelective(order);
        if (i == 0) {
            throw new MallException(MallExceptionEnum.INSERT_FAILED);
        }
        return orderNo;
    }

    @Override
    public void pay(Integer orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        if (!order.getOrderStatus().equals(Constant.OrderStatus.NOT_PAY.getStatusCode())) {
            throw new MallException(MallExceptionEnum.NEED_NOT_PAY);
        }
        order.setOrderStatus(Constant.OrderStatus.PAY.getStatusCode());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.PAY_FAIL);
        }
    }

    @Override
    public OrderVO detail(Integer orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
         OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        List<OrderItemVO> itemVOs = new ArrayList<>();
        for (OrderItem orderItem: orderItemMapper.selectByOrderNo(order.getId())) {
             OrderItemVO orderItemVO = new OrderItemVO();
             BeanUtils.copyProperties(orderItem,orderItemVO);
             itemVOs.add(orderItemVO);
        }
        orderVO.setOrderItemVOList(itemVOs);
        return orderVO;
    }

    @Override
    public void deliver(Integer orderNo) {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        if (!order.getOrderStatus().equals(Constant.OrderStatus.DELIVER.getStatusCode())) {
            throw new MallException(MallExceptionEnum.NOT_DELIVER);
        }
        order.setOrderStatus(Constant.OrderStatus.DELIVER.getStatusCode());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        if (count == 0) {
            throw new MallException(MallExceptionEnum.DELIVER_FAIL);
        }
    }

    @Override
    public String qrCode(Integer orderNo) throws IOException, WriterException {
        Order order = orderMapper.selectByPrimaryKey(orderNo);
        if (order == null) {
            throw new MallException(MallExceptionEnum.NOT_ORDER);
        }
        if (!order.getOrderStatus().equals(Constant.OrderStatus.NOT_PAY.getStatusCode())){
            throw new MallException(MallExceptionEnum.NEED_NOT_PAY);
        }
        String address=ui;
        String payUrl="http://"+address+"/order/pay?orderNo="+order.getOrderNo();
        QRCodeGenerator.generateQRCodeImage(payUrl,400,400,
                Constant.FILE_UPLOAD_DIR+order.getOrderNo()+".png");
        String qrImages="http://" + address+"/images/"+order.getOrderNo()+".png";
        return qrImages;
    }

    @Override
    public List<OrderStatisticsVO> statistics(Date start, Date end) {
        //进行Query的属性的赋值
        OrderStatisticsQuery orderStatisticsQuery = new OrderStatisticsQuery();
        orderStatisticsQuery.setStart(start);
        orderStatisticsQuery.setEnd(end);
        //进行数据库的查询
        List<OrderStatisticsVO> orderStatisticsVOS = orderMapper.selectOrderStatistics(orderStatisticsQuery);
        return orderStatisticsVOS;
    }
}
