package com.growing.sgh.domain.item.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.item.dto.ItemRegisterDto;
import com.growing.sgh.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.growing.sgh.common.response.Response.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Response register(@Validated @RequestBody ItemRegisterDto registerDto){
        itemService.register(registerDto);
        return success();
    }


}
