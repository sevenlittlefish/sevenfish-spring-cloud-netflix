package com.cloud.service;

import com.cloud.entity.User;
import com.cloud.pojo.Product;

import java.util.List;

public interface DemoService {

    List<Product> listProducts();

    List<User> listUsers();

    void modifyUser(Long id,String name,Integer sex);
}
