package com.chenze.projectadvancementdemo.controller;

import com.chenze.projectadvancementdemo.common.ApiRestResponse;
import com.chenze.projectadvancementdemo.model.vo.OrderStatisticsVO;
import com.chenze.projectadvancementdemo.model.vo.OrderVO;
import com.chenze.projectadvancementdemo.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class OrderAdminController {
    @Autowired
    OrderService orderService;

    @ApiOperation("返回后台订单列表")
    @PostMapping("/order/list")
    public ApiRestResponse list(@RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize){
         PageInfo<OrderVO> list = orderService.list(pageNum, pageSize);
        return ApiRestResponse.success(list);
    }
    @ApiOperation("完成订单")
    @PostMapping("/order/finish")
    public ApiRestResponse finish(@RequestParam("orderNo") Integer orderNo){
        orderService.finish(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("订单进行发送")
    @PostMapping("/order/deliver")
    public ApiRestResponse deliver(@RequestParam("orderNo") Integer orderNo){
        orderService.deliver(orderNo);
        return ApiRestResponse.success();
    }
    @ApiOperation("取消订单")
    @PostMapping("/order/cancel")
    public ApiRestResponse cancel(@RequestParam("orderNo") Integer orderNo) {
        orderService.cancel(orderNo);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台进行统计一段时间内的订单数")
    @PostMapping("/order/statistics")
    public ApiRestResponse statistics(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date end){
        List<OrderStatisticsVO> statistics = orderService.statistics(start, end);
        return ApiRestResponse.success(statistics);
    }
}
