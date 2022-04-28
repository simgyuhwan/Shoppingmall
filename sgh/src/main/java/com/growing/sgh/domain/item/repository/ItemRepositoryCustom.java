package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
