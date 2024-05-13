package com.chenze.projectadvancementdemo.service;



import com.chenze.projectadvancementdemo.model.pojo.Order;
import com.chenze.projectadvancementdemo.model.vo.OrderStatisticsVO;
import com.chenze.projectadvancementdemo.model.vo.OrderVO;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface OrderService {
    PageInfo<OrderVO> list(Integer pageNum, Integer pageSize);

    void cancel(Integer orderNo);

    void finish(Integer orderNo);

    String createOrder(Order order);

    void pay(Integer orderNo);

    OrderVO detail(Integer orderNo);

    void deliver(Integer orderNo);

    String qrCode(Integer orderNo) throws IOException, WriterException;

    List<OrderStatisticsVO> statistics(Date start, Date end);
}
