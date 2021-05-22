package com.cloud.service;

import com.cloud.dao.StorageDao;
import com.cloud.entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageService {

    @Autowired
    private StorageDao storageDao;

    public Storage findByCommodityCode(String commodityCode){
        return storageDao.findByCommodityCode(commodityCode);
    }

    @Transactional
    public void changeCount(Long id, int num){
        Storage storage = storageDao.findById(id).get();
        int remainNum = storage.getCount()-num;
        storage.setCount(remainNum);
        storageDao.save(storage);

        if (remainNum < 0)
            throw new RuntimeException("商品数量不足");
    }
}
