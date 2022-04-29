package com.growing.sgh.domain.category.dto;

import com.growing.sgh.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    private String name;

    @Nullable
    private Long parentId;

    public static Category toEntity(CategoryDto categoryDto, Long depth){
        return Category.builder()
                .name(categoryDto.getName())
                .depth(depth)
                .build();
    }

}
