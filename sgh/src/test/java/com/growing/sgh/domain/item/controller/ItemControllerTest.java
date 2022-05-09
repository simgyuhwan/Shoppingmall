package com.growing.sgh.domain.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growing.sgh.domain.category.service.CategoryService;
import com.growing.sgh.domain.item.dto.ItemDto;
import com.growing.sgh.domain.item.dto.ItemImgDto;
import com.growing.sgh.domain.item.entity.ItemImg;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.domain.item.service.ItemImgService;
import com.growing.sgh.domain.item.service.ItemService;
import com.growing.sgh.exception.item.ItemNotFoundException;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.factory.dto.FileFactory;
import com.growing.sgh.factory.dto.ItemDtoFactory;
import com.growing.sgh.factory.entity.ItemFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @InjectMocks ItemController itemController;
    @Mock ItemService itemService;
    @Mock ItemImgService itemImgService;
    @Mock CategoryService categoryService;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    @DisplayName("상품 등록 api 테스트")
    void itemRegisterTest() throws Exception {
        //given
        ItemDto itemDto = createItemDto();
        List<MultipartFile> imgFiles
                = createMultipartFileList();
        //when, then
        mockMvc.perform(multipart("/items/new")
                .file("itemImgFile", imgFiles.get(0).getBytes())
                .file("itemImgFile", imgFiles.get(1).getBytes())
                        .param("itemNm",itemDto.getItemNm())
                        .param("description",itemDto.getDescription())
                        .param("stockNumber", itemDto.getStockNumber().toString())
                        .param("price", itemDto.getPrice().toString())
                        .param("categoryId", itemDto.getCategoryId().toString())
                .contentType(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isCreated());

        verify(itemService).itemRegister(any(),any(), any());
    }

    @Test
    @DisplayName("상품 삭제 api 테스트")
    void deleteItemTest() throws Exception {
        //when
        mockMvc.perform(
                delete("/items/{itemId}", 1L)
        ).andExpect(status().isOk());

        //then
        verify(itemService).itemDelete(1L);
    }

    @Test
    @DisplayName("상품 조회 api 테스트")
    void getItemTest() throws Exception {
        // given
        given(itemService.getItemDtl(anyLong())).willReturn(ItemFactory.createItem());

        //when, then
        mockMvc.perform(get("/items/{itemId}", 1L))
                .andExpect(status().isOk());

        verify(itemService).getItemDtl(any());
    }

    private ItemDto createItemDto(){
        return ItemDtoFactory.createItemDto();
    }

    private List<MultipartFile> createMultipartFileList(){
        return FileFactory.createMultipartFileList();
    }
}