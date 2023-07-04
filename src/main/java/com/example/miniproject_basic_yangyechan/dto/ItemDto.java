package com.example.miniproject_basic_yangyechan.dto;


import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

@Data
public class ItemDto {
    private Long id;
    private String title;
    private String description;
    private String minPriceWanted;
    private String writer;
    private String password;
    private String status;
    private String imageUrl;
    public static ItemDto fromEntity(ItemEntity entity) {
        ItemDto dto = new ItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }
}
