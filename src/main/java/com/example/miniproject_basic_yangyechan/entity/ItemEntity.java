package com.example.miniproject_basic_yangyechan.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "sale_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"writer", "password"})})
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String minPriceWanted;

    @Column(nullable = false, unique = true)
    private String writer;

    @Column(nullable = false)
    private String password;

    private String status;
    private String imageUrl;
}