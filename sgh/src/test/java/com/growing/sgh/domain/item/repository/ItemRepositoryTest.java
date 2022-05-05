package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.exception.category.CategoryNotFoundException;
import com.growing.sgh.exception.item.ItemNotFoundException;
import com.growing.sgh.factory.entity.CategoryFactory;
import com.growing.sgh.factory.entity.ItemFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @PersistenceContext
    EntityManager em;

    private Category category;

    @BeforeEach
    void beforeEach(){
        Category category = categoryRepository.save(createCategory());
        clear();
    }

    @Test
    @DisplayName("상품 생성 및 조회 테스트")
    void findItemTest(){
        // given
        Item item = createItem();

        // when
        itemRepository.save(item);
        clear();

        // then
        Item findItem = itemRepository.findById(item.getId()).orElseThrow(ItemNotFoundException::new);
        assertThat(findItem.getId()).isEqualTo(item.getId());
    }
    @Test
    @DisplayName("상품 Date 테스트")
    void validateMemberInfoTest(){
        // given
        Item item = createItem();

        //when
        itemRepository.save(item);
        clear();

        //then
         Item findItem = itemRepository.findById(item.getId()).orElseThrow(ItemNotFoundException::new);
        assertThat(findItem.getRegTime()).isNotNull();
        assertThat(findItem.getUpdateTime()).isNotNull();
        assertThat(findItem.getRegTime()).isEqualTo(findItem.getUpdateTime());
    }

    @Test
    @DisplayName("ItemNotFoundException 테스트")
    void ItemNotFoundExceptionTest(){
        // given
        Item item = createItem();

        // when
        itemRepository.save(item);
        clear();

        // then
        assertThrows(ItemNotFoundException.class, ()->{
            itemRepository.findById(item.getId()+1).orElseThrow(ItemNotFoundException::new);
        });
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest(){
        // given
        Item item = itemRepository.save(createItem());
        clear();

        // when
        itemRepository.delete(item);

        // then
        assertThatThrownBy(()-> itemRepository.findById(item.getId()).orElseThrow(ItemNotFoundException::new))
                .isInstanceOf(ItemNotFoundException.class);
    }

    @Test
    @DisplayName("판매 상태 조회 테스트")
    void searchOfItemOnSELLTest(){
        // given
        itemRepository.save(createItem());
        clear();

        // when
        Page<Item> itemPage = itemRepository.getItemPage(ItemSearchDto.builder()
                .searchSellStatus(ItemSellStatus.SELL).maxPrice(10000).minPrice(0).build(), PageRequest.of(0, 10));
        Stream<Item> itemStream = itemPage.get();
        List<Item> collect = itemStream.collect(Collectors.toList());
        Item item = collect.get(0);

        // then
        assertThat(itemPage.getTotalElements()).isEqualTo(1);
        assertThat(item.getItemSellStatus()).isEqualTo(ItemSellStatus.SELL);
    }

    @Test
    @DisplayName("품절 상태 조회 테스트")
    void searchOfItemOnSOLDOUTTest(){
        // given
        itemRepository.save(Item.builder()
                .itemNm("품절상품").category(category).itemSellStatus(ItemSellStatus.SOLD_OUT)
                .description("테스트").price(10000).stockNumber(1000).build());
        clear();

        // when
        Page<Item> itemPage = itemRepository.getItemPage(ItemSearchDto.builder()
                        .searchSellStatus(ItemSellStatus.SOLD_OUT).minPrice(0).maxPrice(10000).build()
                , PageRequest.of(0, 10));
        Stream<Item> itemStream = itemPage.get();
        List<Item> collect = itemStream.collect(Collectors.toList());
        Item item = collect.get(0);

        // then
        assertThat(itemPage.getTotalElements()).isEqualTo(1);
        assertThat(item.getItemSellStatus()).isEqualTo(ItemSellStatus.SOLD_OUT);
    }

    @Test
    @DisplayName("최소 금액 상품 조회 테스트")
    void minPriceSearchTest(){
        // given
        injectSearchItems();

        // when
        Page<Item> itemPage = itemRepository.getItemPage(ItemSearchDto.builder()
                        .searchSellStatus(ItemSellStatus.SELL).minPrice(20).maxPrice(10000).build()
                , PageRequest.of(0, 10));

        // then
        assertThat(itemPage.getTotalElements()).isEqualTo(4);
    }

    @Test
    @DisplayName("최대 금액 상품 조회 테스트")
    void maxPriceSearchTest(){
        // given
        injectSearchItems();

        Page<Item> itemPage = itemRepository.getItemPage(ItemSearchDto.builder()
                        .searchSellStatus(ItemSellStatus.SELL).minPrice(0).maxPrice(30).build()
                , PageRequest.of(0, 10));

        // then
        assertThat(itemPage.getTotalElements()).isEqualTo(3);
    }

    private void injectSearchItems(){
        for(int i=0; i<5; i++){
            itemRepository.save(Item.builder()
                    .itemNm("상품"+ i+1).category(category).itemSellStatus(ItemSellStatus.SELL)
                    .description("상품 설명"+ i+1)
                    // price 10, 20, 30, 40, 50
                    .price(10 * (i+1))
                    .stockNumber(10 * (i+1))
                    .build());
        }
        clear();
    }

    private Item createItem(){
        return Item.builder()
                .itemNm("상품1").stockNumber(1000).price(1000).itemSellStatus(ItemSellStatus.SELL)
                .description("상품 설명").category(category).build();
    }

    private Category createCategory(){
        return CategoryFactory.createCategory();
    }

    private void clear(){
        em.flush();
        em.clear();
    }

}