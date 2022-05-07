package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemImg;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.exception.item.ItemImgNotFoundException;
import com.growing.sgh.factory.entity.CategoryFactory;
import com.growing.sgh.factory.entity.ItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class ItemImgRepositoryTest {
    @Autowired private ItemImgRepository itemImgRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private
    CategoryRepository categoryRepository;
    @PersistenceContext
    EntityManager em;

    private Category category;
    private Item item;

    @BeforeEach
    void beforeEach(){
        category = categoryRepository.save(createCategory());
        item = itemRepository.save(createItem());
        clear();
    }

    @Test
    @DisplayName("상품 이미지 생성 및 조회 테스트")
    void findItemTest(){
        //given
        ItemImg itemImg = createItemImg();

        //when
        itemImgRepository.save(itemImg);
        clear();

        //then
        ItemImg findItemImg = itemImgRepository.findById(itemImg.getId()).orElseThrow(ItemImgNotFoundException::new);
        assertThat(findItemImg.getId()).isEqualTo(itemImg.getId());
    }

    @Test
    @DisplayName("ItemImgNotFoundException 테스트")
    void ItemImgNotFoundException(){
        //given
        ItemImg itemImg = createItemImg();

        //when
        itemImgRepository.save(itemImg);
        clear();

        //then
        assertThrows(ItemImgNotFoundException.class, () ->
                itemImgRepository.findById(itemImg.getId()+1).orElseThrow(ItemImgNotFoundException::new));
    }

    @Test
    @DisplayName("상품 이미지 Date 테스트")
    void ItemImgDateTest(){
        //given
        ItemImg itemImg = createItemImg();

        //when
        itemImgRepository.save(itemImg);
        clear();

        //then
        ItemImg findItemImg = itemImgRepository.findById(itemImg.getId()).orElseThrow(ItemImgNotFoundException::new);
        assertThat(findItemImg.getRegTime()).isNotNull();
        assertThat(findItemImg.getUpdateTime()).isNotNull();
        assertThat(findItemImg.getRegTime()).isEqualTo(findItemImg.getUpdateTime());
    }

    @Test
    @DisplayName("상품 이미지 삭제 테스트")
    void deleteItemTest(){
        // given
        ItemImg itemImg = itemImgRepository.save(createItemImg());
        clear();

        // when
        itemImgRepository.delete(itemImg);

        // then
        assertThatThrownBy(()-> itemImgRepository.findById(itemImg.getId()).orElseThrow(ItemImgNotFoundException::new))
                .isInstanceOf(ItemImgNotFoundException.class);
    }

    @Test
    @DisplayName("상품 id 조회 테스트")
    void findAllByItemIdTest(){
        //given
        ItemImg itemImg = itemImgRepository.save(createItemImg());
        clear();

        //when
        List<ItemImg> itemImgList = itemImgRepository.findAllByItemId(item.getId());

        //then
        assertThat(itemImgList.size()).isEqualTo(1);
        assertThat(itemImgList.get(0).getImgName()).isEqualTo(itemImg.getImgName());
    }

    @Test
    @DisplayName("상품 id 와 대표 이미지 여부 조회")
    void findByItemIdAndRepImgYnTest(){
        //given
        ItemImg itemImg = itemImgRepository.save(createItemImg());
        clear();

        //when
        ItemImg findItemImg = itemImgRepository.findByItemIdAndRepImgYn(item.getId(), "Y");

        //then
        assertThat(findItemImg.getRepImgYn()).isEqualTo("Y");
        assertThat(itemImg.getImgName()).isEqualTo(findItemImg.getImgName());
    }




    private ItemImg createItemImg(){
        return new ItemImg("imageName", "oriImgName",
                "imgUrl","Y", item);
    }

    private Item createItem(){
        return Item.builder()
                .id(1L)
                .itemNm("상품1")
                .category(CategoryFactory.createCategory())
                .itemSellStatus(ItemSellStatus.SELL)
                .description("설명")
                .price(1000)
                .stockNumber(1000)
                .category(category)
                .build();
    }
    private Category createCategory(){
        return CategoryFactory.createCategory();
    }

    private void clear(){
        em.flush();
        em.clear();
    }
}