package com.example.miniproject_basic_yangyechan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "negotiation")
public class NegotiationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long item_id;

    private Long suggestedPrice;

    private String status;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String password;
}
