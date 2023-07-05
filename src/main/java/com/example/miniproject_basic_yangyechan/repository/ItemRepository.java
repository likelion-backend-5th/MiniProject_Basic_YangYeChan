package com.example.miniproject_basic_yangyechan.repository;


import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    // 제목에 title이 들어가는 article 검사
    Page<ItemEntity> findAllByTitleContains(String title, Pageable pageable);
}
