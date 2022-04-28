package com.growing.sgh.domain.order.repository;

import com.growing.sgh.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
