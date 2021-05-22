package com.cloud.service;

import com.cloud.client.FeignAccountClient;
import com.cloud.client.FeignOrderClient;
import com.cloud.client.FeignStorageClient;
import com.cloud.common.Result;
import com.cloud.entity.Order;
import com.cloud.entity.Storage;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BusinessService {

    @Autowired
    private FeignAccountClient accountClient;

    @Autowired
    private FeignOrderClient orderClient;

    @Autowired
    private FeignStorageClient storageClient;

    @GlobalTransactional
    public Result purchase(String userId, String commodityCode, Integer num){
        Storage storage = storageClient.findByCommodityCode(commodityCode);

        Integer price = storage.getPrice();
        int money = price*num;

        accountClient.changeMoney(userId,money);
        storageClient.changeCount(storage.getId(),num);

        Order order = new Order();
        order.setCommodityCode(commodityCode);
        order.setCount(num);
        order.setMoney(money);
        order.setUserId(userId);
        orderClient.create(order);

        //模拟异常
        int randomNum = new Random().nextInt(2);
        if (randomNum == 1){
            int r = 1/0;
        }

        return Result.success("下单成功");
    }
}
