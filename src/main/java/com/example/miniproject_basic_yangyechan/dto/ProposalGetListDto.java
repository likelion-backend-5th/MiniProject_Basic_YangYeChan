package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import com.example.miniproject_basic_yangyechan.entity.NegotiationEntity;
import lombok.Data;

@Data
public class ProposalGetListDto {
    private Long id;
    private Long suggestedPrice;
    private String status;

    public static ProposalGetListDto fromEntity2(NegotiationEntity entity) {
        ProposalGetListDto dto = new ProposalGetListDto();
        dto.setId(entity.getItemid());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
