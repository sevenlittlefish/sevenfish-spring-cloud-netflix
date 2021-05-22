package com.cloud.dao;

import com.cloud.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageDao extends JpaRepository<Storage,Long> {

    Storage findByCommodityCode(String commodityCode);
}
