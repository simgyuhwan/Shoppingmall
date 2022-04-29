package com.growing.sgh.domain.category.repository;

import com.growing.sgh.domain.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
