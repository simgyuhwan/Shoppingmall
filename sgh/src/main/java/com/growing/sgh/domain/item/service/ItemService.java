package com.growing.sgh.domain.item.service;

import com.growing.sgh.domain.item.dto.ItemRegisterDto;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.entity.ItemSellStatus;
import com.growing.sgh.domain.item.repository.ItemImgRepository;
import com.growing.sgh.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public void registerItem(ItemRegisterDto registerDto, List<MultipartFile> itemImgList) throws IOException {

        Item item = ItemRegisterDto.toEntity(registerDto);
        itemRepository.save(item);

        itemImgService.itemImgRegister(item, itemImgList);

    }

}
