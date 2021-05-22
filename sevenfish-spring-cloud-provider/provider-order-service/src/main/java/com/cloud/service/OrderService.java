package com.cloud.service;

import com.cloud.dao.OrderDao;
import com.cloud.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void save(Order order){
        orderDao.save(order);
    }
}
