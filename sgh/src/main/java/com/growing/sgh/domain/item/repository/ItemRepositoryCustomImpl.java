package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.domain.item.entity.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 상품 상태 기준
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    // 등록 기준
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null;
        else if(StringUtils.equals("1d", searchDateType)) dateTime = dateTime.minusDays(1);
        else if(StringUtils.equals("1w", searchDateType)) dateTime = dateTime.minusWeeks(1);
        else if(StringUtils.equals("1m", searchDateType)) dateTime = dateTime.minusMonths(1);
        else if(StringUtils.equals("6m", searchDateType)) dateTime = dateTime.minusMonths(6);

        return QItem.item.regTime.after(dateTime);
    }

    // 상품명
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("itemNm", searchBy)) return QItem.item.itemNm.like("%" + searchQuery + "%");
        return null;
    }

    // 최소 가격
    private BooleanExpression searchMinPrice(Integer minPrice){
        if(!minPrice.equals(null)) return QItem.item.price.goe(minPrice);
        return null;
    }

    // 최대 가격
    private BooleanExpression searchMaxPrice(Integer maxPrice){
        if(!maxPrice.equals(null)) return QItem.item.price.loe(maxPrice);
        return null;
    }

    @Override
    public Page<Item> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        List<Item> items = queryFactory
                .selectFrom(QItem.item)
                .where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()),
                        searchMaxPrice(itemSearchDto.getMaxPrice()),
                        searchMinPrice(itemSearchDto.getMinPrice()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = items.size();
        return new PageImpl<>(items, pageable, total);
    }
}
