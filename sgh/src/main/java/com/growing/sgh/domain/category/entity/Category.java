package com.growing.sgh.domain.category.entity;

import com.growing.sgh.domain.category.dto.CategoryDto;
import com.growing.sgh.domain.member.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    @Column(length = 30, nullable = false, name = "category_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category parent;

    @Column(nullable = false)
    private Long depth = 1L;

    @Builder
    public Category(String name, Category parent, Long depth) {
        this.name = name;
        this.parent = parent;
        this.depth = depth;
    }


    public void changeParent(Category parent){
        this.parent = parent;
        this.depth = parent.depth+1;
    }

    public void updateCategory(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
    }

    public boolean compareCategoryId(Long categoryId){
        return this.id.equals(categoryId);
    }
}
