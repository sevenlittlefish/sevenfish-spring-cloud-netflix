package com.cloud.controller;

import com.cloud.entity.Storage;
import com.cloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @RequestMapping("findByCommodityCode")
    public Storage findByCommodityCode(String commodityCode){
        Storage storage = storageService.findByCommodityCode(commodityCode);
        return storage;
    }

    @RequestMapping("changeCount")
    public void changeCount(Long id,Integer num){
        storageService.changeCount(id,num);
    }
}
