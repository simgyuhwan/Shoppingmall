package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    List<Item> findByItemNm(String itemNm);
    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceGreaterThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    List<Item> findByPriceGreaterThanOrderByPriceAsc(Integer price);
}
