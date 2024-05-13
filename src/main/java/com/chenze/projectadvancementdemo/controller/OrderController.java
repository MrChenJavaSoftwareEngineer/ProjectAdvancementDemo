package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.pojo.Order;
import com.chenze.projectadvancementdemo.model.request.CreateOrderReq;
import com.chenze.projectadvancementdemo.model.vo.OrderVO;
import com.chenze.projectadvancementdemo.service.OrderService;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class OrderController {

    @Autowired
    OrderService orderService;

    @ApiOperation("返回前台订单列表")
    @PostMapping("/order/list")
    public ApiRestResponse list(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize) {
         PageInfo<OrderVO> list = orderService.list(pageNum, pageSize);
        return ApiRestResponse.success(list);
    }

    @ApiOperation("生成支付二维码")
    @PostMapping("/order/qrCode")
    public ApiRestResponse qrCode(@RequestParam("orderNo") Integer orderNo) throws IOException, WriterException {
        orderService.qrCode(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("完成订单")
    @PostMapping("/order/finish")
    public ApiRestResponse finish(@RequestParam("orderNo") Integer orderNo) {
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("取消订单")
    @PostMapping("/order/cancel")
    public ApiRestResponse cancel(@RequestParam("orderNo") Integer orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("创建订单")
    @PostMapping("/order/createOrder")
    public ApiRestResponse createOrder(@Valid @RequestBody CreateOrderReq createOrderReq) {
         Order order = new Order();
        BeanUtils.copyProperties(createOrderReq,order);
        orderService.createOrder(order);
        return ApiRestResponse.success();
    }
    @ApiOperation("支付")
    @PostMapping("pay")
    public ApiRestResponse pay(@RequestParam("orderNo") Integer orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("订单详情")
    @PostMapping("/order/detail")
    public ApiRestResponse detail(@RequestParam("orderNo") Integer orderNo) {
        orderService.detail(orderNo);
        return ApiRestResponse.success();
    }
}
