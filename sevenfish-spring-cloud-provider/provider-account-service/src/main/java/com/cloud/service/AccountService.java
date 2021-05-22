package com.cloud.service;

import com.cloud.dao.AccountDAO;
import com.cloud.entity.Account;
import com.cloud.entity.Storage;
import com.cloud.feign.FeignStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private FeignStorageClient storageClient;

    @Transactional
    public void changeMoney(String userId,Integer money){
        Account account = accountDAO.findByUserId(userId);
        int remainMoney = account.getMoney()-money;
        account.setMoney(remainMoney);
        accountDAO.save(account);

        Storage storage = storageClient.findByCommodityCode("code1");
        System.out.println(storage);

        if (remainMoney < 0)
            throw new RuntimeException("余额不足");
    }
}
