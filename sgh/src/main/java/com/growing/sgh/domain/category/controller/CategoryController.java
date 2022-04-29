package com.growing.sgh.domain.category.controller;

import com.growing.sgh.common.response.Response;
import com.growing.sgh.domain.category.dto.CategoryDto;
import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Response register(@Validated @RequestBody CategoryDto categoryDto){
        categoryService.categoryRegister(categoryDto);
        return Response.success();
    }

}
