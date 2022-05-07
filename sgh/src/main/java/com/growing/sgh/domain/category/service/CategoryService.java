package com.growing.sgh.domain.category.service;

import com.growing.sgh.domain.category.dto.CategoryDto;
import com.growing.sgh.domain.category.entity.Category;
import com.growing.sgh.domain.category.repository.CategoryRepository;
import com.growing.sgh.domain.item.repository.ItemRepository;
import com.growing.sgh.exception.category.CategoryNotFoundException;
import com.growing.sgh.helper.ServiceFindHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public void categoryRegister(CategoryDto categoryDto) {
        Category category;
        if(!isParentCategory(categoryDto)){
            category = CategoryDto.toEntity(categoryDto, 1L);
        }else {
            Category parentCategory = ServiceFindHelper.findExistingCategory(categoryRepository, categoryDto.getParentId());
            category = CategoryDto.toEntity(categoryDto, parentCategory.getDepth()+1);
            category.changeParent(parentCategory);
        }
        categoryRepository.save(category);
    }

    public void categoryDelete(Long categoryId) {
        categoryRepository.delete(categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new));
    }

    public Category getCategory(Long categoryId){
        return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }

    public void categoryUpdate(Long categoryId, CategoryDto categoryDto) {
        Category category = ServiceFindHelper.findExistingCategory(categoryRepository, categoryId);
        category.updateCategory(categoryDto);
        if(isParentCategory(categoryDto) && !compareCategoryParent(categoryDto, category)){
            Category parentCategory = ServiceFindHelper.findExistingCategory(categoryRepository, categoryDto.getParentId());
            category.changeParent(parentCategory);
        }
    }

    private boolean compareCategoryParent(CategoryDto categoryDto, Category category) {
        return category.getParent().getId().equals(categoryDto.getParentId());
    }

    private boolean isParentCategory(CategoryDto categoryDto) {
        return !Objects.isNull(categoryDto.getParentId());
    }
}
