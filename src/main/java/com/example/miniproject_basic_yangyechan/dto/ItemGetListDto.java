package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import lombok.Data;

@Data
public class ItemGetListDto {
    private Long id;
    private String title;
    private String description;
    private String minPriceWanted;
    private String imageUrl;
    private String status;
    public static ItemGetListDto fromEntity(ItemEntity entity) {
        ItemGetListDto dto = new ItemGetListDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setImageUrl(entity.getImageUrl());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
