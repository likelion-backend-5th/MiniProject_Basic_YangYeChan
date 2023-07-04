package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import lombok.Data;

@Data
public class ItemGetDto {
    private String title;
    private String description;
    private String minPriceWanted;
    private String status;
    public static ItemGetDto fromEntity(ItemEntity entity) {
        ItemGetDto dto = new ItemGetDto();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
