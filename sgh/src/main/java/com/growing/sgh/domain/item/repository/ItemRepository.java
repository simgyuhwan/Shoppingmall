package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    Item findByItemNm(String itemNm);
}
