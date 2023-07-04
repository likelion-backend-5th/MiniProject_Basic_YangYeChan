package com.example.miniproject_basic_yangyechan.controller;

import com.example.miniproject_basic_yangyechan.service.CommentService;
import com.example.miniproject_basic_yangyechan.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class CommentController {
    private final CommentService service;

    @PostMapping("/{itemId}/comments")
    public ResponseDto create(
            @RequestBody CommentDto commentDto,
            @PathVariable("itemId") Long item_id) {
        service.createComment(commentDto, item_id);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 등록되었습니다.");
        return response;
    }

    @GetMapping("/{itemId}/comments")
    public Page<CommentGetListDto> readAll(
            @PathVariable("itemId") Long item_id
    ) {
        Integer page = 0;
        Integer limit = 25;
        return service.readCommentPaged(page, limit, item_id);
    }

    @PutMapping("/{itemId}/comments/{commentId}")
    public ResponseDto update(
            @PathVariable("itemId") Long item_id,
            @PathVariable("commentId") Long commentid,
            @RequestBody CommentDto commentDto
    ) {
        service.updateComment(item_id, commentid, commentDto);

        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

    @PutMapping("/{itemId}/comments/{commentId}/reply")
    public ResponseDto updateReply(
            @PathVariable("itemId") Long item_id,
            @PathVariable("commentId") Long commentid,
            @RequestBody CommentDto commentDto
    ) {
        service.updateCommentReply(item_id, commentid, commentDto);

        ResponseDto response = new ResponseDto();
        response.setMessage("댓글에 답변이 추가되었습니다.");
        return response;
    }

    @DeleteMapping ("/{itemId}/comments/{commentId}")
    public ResponseDto delete(
            @PathVariable("itemId") Long item_id,
            @PathVariable("commentId") Long commentid,
            @RequestBody CommentDto Dto) {
        service.deleteComment(item_id, commentid, Dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글을 삭제했습니다.");
        return response;
    }
}
