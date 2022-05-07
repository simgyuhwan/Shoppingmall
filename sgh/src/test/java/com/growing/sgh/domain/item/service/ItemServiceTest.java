package com.growing.sgh.domain.item.service;

import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.dto.ItemDto;
import com.growing.sgh.domain.item.dto.ItemImgDto;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.exception.category.CategoryNotFoundException;
import com.growing.sgh.factory.dto.FileFactory;
import com.growing.sgh.factory.dto.ItemDtoFactory;
import com.growing.sgh.factory.entity.CategoryFactory;
import com.growing.sgh.factory.entity.ItemFactory;
import com.growing.sgh.helper.ServiceFindHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks ItemService itemService;
    @Mock ItemRepository itemRepository;
    @Mock ItemImgService itemImgService;
    @Mock CategoryRepository categoryRepository;
    @Mock ServiceFindHelper serviceFindHelper;
    @Mock Item item;
    @Mock Category category;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 등록 테스트")
    void itemRegisterTest() throws IOException {
        //when
        itemService.itemRegister(createItemDto(), createMultipartFileList(), createCategory());

        //then
        verify(itemRepository).save(any());
        verify(itemImgService).itemImgRegister(any(), any());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void itemUpdateTest() throws IOException {
        //given
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(createItem()));

        //when
        itemService.itemUpdate(1L, createItemDto(), createCategory());

        //then
        verify(itemRepository).findById(anyLong());
    }

    private ItemDto createItemDto(){
        return ItemDto.builder()
                .itemNm("테스트")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(1000)
                .price(1000)
                .description("상품 설명")
                .itemImgDtoList(new ArrayList<>())
                .categoryId(1L)
                .build();
    }

    private MultipartFile createMultipartFile(){
        return FileFactory.createMultipartFile();
    }

    private List<MultipartFile> createMultipartFileList(){
        return FileFactory.createMultipartFileList();
    }


    private Category createCategory(){
        return CategoryFactory.createCategory();
    }
    private Item createItem() { return Item.builder()
            .id(1L)
            .itemNm("상품1")
            .category(CategoryFactory.createCategory())
            .itemSellStatus(ItemSellStatus.SELL)
            .description("설명")
            .price(1000)
            .stockNumber(1000)
            .category(createCategory())
            .build();
    }

    private void clear(){
        em.flush();
        em.clear();
    }

}