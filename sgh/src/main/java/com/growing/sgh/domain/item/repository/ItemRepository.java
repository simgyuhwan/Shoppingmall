package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
