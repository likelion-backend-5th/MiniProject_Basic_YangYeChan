package com.example.miniproject_basic_yangyechan.repository;

import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
}
