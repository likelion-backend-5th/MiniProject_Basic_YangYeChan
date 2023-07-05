package com.example.miniproject_basic_yangyechan.dto;

import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long itemid;
    private String writer;
    private String password;
    private String content;
    private String reply;

    public static CommentDto fromEntity(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setItemid(entity.getItemid());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        dto.setContent(entity.getContent());
        dto.setReply(entity.getReply());
        return dto;
    }
}
