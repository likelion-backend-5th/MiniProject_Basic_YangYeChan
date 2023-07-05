package com.example.miniproject_basic_yangyechan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long itemid;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String password;

    private String content;

    private String reply;

}
