package com.cloud.dao;

import com.cloud.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account,Long> {

    Account findByUserId(String userId);
}
