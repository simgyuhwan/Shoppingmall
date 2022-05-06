package com.growing.sgh.domain.item.service;

import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.repository.ItemRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks ItemService itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock ItemImgService itemImgService;
    @Mock CategoryRepository categoryRepository;




}