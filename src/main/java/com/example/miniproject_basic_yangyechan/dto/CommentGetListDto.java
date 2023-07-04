package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import lombok.Data;

@Data
public class CommentGetListDto {
    private Long id;
    private String content;
    private String reply;

    public static CommentGetListDto fromEntity2(CommentEntity entity) {
        CommentGetListDto dto = new CommentGetListDto();
        dto.setId(entity.getItem_id());
        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }
}
