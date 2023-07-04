package com.example.miniproject_basic_yangyechan.repository;


import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    // ID가 큰 순서대로 최상위 20개
    List<ItemEntity> findTop20ByOrderByIdDesc();
    // ID가 특정 값보다 작은 데이터 중 큰 순서대로 최상위 20개
    List<ItemEntity> findTop20ByIdLessThanOrderByIdDesc(Long id);

    // 제목에 title이 들어가는 article 검사
    Page<ItemEntity> findAllByTitleContains(
            String title, Pageable pageable);
}
