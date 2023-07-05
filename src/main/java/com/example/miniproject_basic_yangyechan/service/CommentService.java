package com.example.miniproject_basic_yangyechan.service;

import com.example.miniproject_basic_yangyechan.dto.*;
import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import com.example.miniproject_basic_yangyechan.repository.CommentRepository;
import com.example.miniproject_basic_yangyechan.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final ItemRepository repository2;

    // CREATE
    public CommentDto createComment(CommentDto dto, Long itemid) {
        if (!repository2.existsById(itemid))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity newItem = new CommentEntity();
        newItem.setItemid(itemid);
        newItem.setWriter(dto.getWriter());
        newItem.setPassword(dto.getPassword());
        newItem.setContent(dto.getContent());
        newItem.setReply(dto.getReply());
        return CommentDto
                .fromEntity(repository.save(newItem));
    }

    // page
    public Page<CommentGetListDto> readCommentPaged(Integer pageNumber, Integer pageSize, Long item_id) {
        if (!repository2.existsById(item_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").ascending());
        Page<CommentEntity> articleEntityPage
                = repository.findAll(pageable);
        // map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Stream으로
        // Page.map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Page로
        Page<CommentGetListDto> articleDtoPage
                = articleEntityPage.map(CommentGetListDto::fromEntity2);
        return articleDtoPage;
    }



    /*
    public Page<CommentGetListDto> readCommentPaged(
            Integer pageNumber, Integer pageSize, Long item_id
    ) {
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").ascending());
        Page<CommentEntity> articleEntityPage
                = repository.findAll(pageable);
        // map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Stream으로
        // Page.map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Page로
        Page<CommentGetListDto> articleDtoPage
                = articleEntityPage.map(CommentGetListDto::fromEntity2);
        return articleDtoPage;
    }
     */

    public CommentDto updateComment(Long itemid, Long commentid, CommentDto dto) {
        Optional<CommentEntity> optionalEntity2
                = repository.findById(commentid);
        if (optionalEntity2.isPresent()) {
            CommentEntity item = optionalEntity2.get();
            if (item.getWriter().equals(dto.getWriter())
                    && item.getPassword().equals(dto.getPassword())
                    && item.getItemid().equals(itemid)) {
                CommentEntity targetEntity = optionalEntity2.get();
                targetEntity.setItemid(itemid);
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                targetEntity.setContent(dto.getContent());
                targetEntity.setReply(dto.getReply());
                return CommentDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public CommentDto updateCommentReply(Long item_id, Long commentid, CommentDto dto) {
        Optional<CommentEntity> optionalEntity
                = repository.findById(commentid);
        Optional<ItemEntity> optionalEntity2
                = repository2.findById(item_id);
        if (optionalEntity.isPresent()) {
            ItemEntity item2 = optionalEntity2.get();
            if (item2.getWriter().equals(dto.getWriter())
                    && item2.getPassword().equals(dto.getPassword())
                    && item2.getId().equals(item_id)) {
                CommentEntity targetEntity = optionalEntity.get();
                targetEntity.setItemid(item_id);
                targetEntity.setReply(dto.getReply());
                return CommentDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void deleteComment(Long item_id, Long commentid, CommentDto Dto) {
        Optional<CommentEntity> optionalEntity = repository.findById(commentid);
        if (optionalEntity.isPresent()) {
            CommentEntity item = optionalEntity.get();

            if (item.getWriter().equals(Dto.getWriter())
                    && item.getPassword().equals(Dto.getPassword())
                    && item.getItemid().equals(item_id)) {
                repository.deleteById(commentid);
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
