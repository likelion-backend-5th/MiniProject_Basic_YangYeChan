package com.example.miniproject_basic_yangyechan.repository;

import com.example.miniproject_basic_yangyechan.entity.NegotiationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NegotiationRepository extends JpaRepository<NegotiationEntity, Long> {
    List<NegotiationEntity> findAllByItemid(Long itemid);
}
