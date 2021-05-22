package com.cloud.controller;

import com.cloud.entity.User;
import com.cloud.pojo.Product;
import com.cloud.service.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoProviderControllerTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void products() {
        List<Product> list = demoService.listProducts();
        list.forEach(System.out::println);
    }

    @Test
    public void users() {
        List<User> list = demoService.listUsers();
        list.forEach(System.out::println);
        Assert.assertSame("数量有误",1,list.size());
    }

    @Test
    public void modifyUser() {
        demoService.modifyUser(2L,"sanji",1);
    }
}