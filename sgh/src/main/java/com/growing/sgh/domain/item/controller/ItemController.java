package com.growing.sgh.domain.item.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.item.dto.ItemDto;
import com.growing.sgh.domain.item.dto.ItemImgDto;
import com.growing.sgh.domain.item.dto.ItemSearchDto;
import com.growing.sgh.domain.item.entity.Item;
import com.growing.sgh.domain.item.service.ItemImgService;
import com.growing.sgh.domain.item.service.ItemService;
import com.growing.sgh.exception.RegisterImgNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.growing.sgh.common.response.Response.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Response register(@Validated @RequestBody ItemDto itemDto,
                             @RequestPart("itemImgFile")List<MultipartFile> itemImgList) throws IOException {
        validateImgFile(itemImgList);
        itemService.itemRegister(itemDto, itemImgList);
        return success();
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@PathVariable Long itemId){
        itemService.itemDelete(itemId);
        return success();
    }

    @PutMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Response update(@PathVariable Long itemId, @Validated @RequestBody ItemDto itemDto,
                           @RequestPart("itemImgFile") List<MultipartFile> itemImgList) throws IOException {
        validateImgFile(itemImgList);
        Item item = itemService.itemUpdate(itemId, itemDto);
        return success(ItemDto.of(item,itemImgService.itemImgUpdate(item,
                itemImgList).stream().map(itemImg -> ItemImgDto.of(itemImg)).collect(Collectors.toList())));
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Response itemDtl(@PathVariable Long itemId){
        return success(ItemDto.of(itemService.getItemDtl(itemId),
                itemImgService.getItemImgs(itemId).stream().map(ItemImg -> ItemImgDto.of(ItemImg)).collect(Collectors.toList())));
    }

//    @GetMapping("/{page}")
//    @ResponseStatus(HttpStatus.OK)
//    public Response itemsDtl(@PathVariable("page")Optional<Integer> page, @RequestBody ItemSearchDto itemSearchDto){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
//        Page<Item> items = itemService.getItemPage(itemSearchDto, pageable);
//
//        return success();
//    }

    private void validateImgFile(List<MultipartFile> itemImgList) {
        if(itemImgList.get(0).isEmpty()) throw new RegisterImgNotExistsException();
    }


}
