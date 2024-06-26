package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import com.example.miniproject_basic_yangyechan.entity.NegotiationEntity;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProposalDto {
    private Long id;
    private Long itemid;
    private Long suggestedPrice;
    private String status;
    private String writer;
    private String password;
    public static ProposalDto fromEntity(NegotiationEntity entity) {
        ProposalDto dto = new ProposalDto();
        dto.setId(entity.getId());
        dto.setItemid(entity.getItemid());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
