package com.cloud.dao;

import com.cloud.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order,Long> {
}
