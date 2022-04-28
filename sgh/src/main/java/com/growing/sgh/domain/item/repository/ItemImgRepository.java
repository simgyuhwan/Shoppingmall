package com.growing.sgh.domain.item.repository;

import com.growing.sgh.domain.item.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    @Query("SELECT img FROM ItemImg img WHERE img.item.id = :itemId")
    List<ItemImg> findAllByItemId(@Param("itemId")Long itemId);

    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);

}
