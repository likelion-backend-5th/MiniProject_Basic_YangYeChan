package com.example.miniproject_basic_yangyechan.controller;


import com.example.miniproject_basic_yangyechan.service.ItemService;
import com.example.miniproject_basic_yangyechan.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    // POST /items
    // 새 아이템 생성
    @PostMapping
    public ResponseDto create(@RequestBody ItemDto itemDto) {
        service.createItem(itemDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("등록이 완료되었습니다.");
        return response;
    }

    @GetMapping
    public Page<ItemGetListDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "25") Integer limit
    ) {
        return service.readItemPaged(page, limit);
    }

    // GET /items/{itemid}
    // 아이템 정보 조회
    @GetMapping("/{itemId}")
    public ItemGetDto read(@PathVariable("itemId") Long id) {
        return service.readItemById(id);
    }

    // PUT /items/{id}
    // 사용자 정보 수정
    @PutMapping("/{itemId}")
    public ResponseDto update(
            @PathVariable("itemId") Long id,
            @RequestBody ItemDto itemDto
    ) {
        service.updateItem(id, itemDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품이 수정되었습니다.");
        return response;
    }

    // PUT /users/{id}/avatar
    // 사용자 프로필 이미지 설정
    @PutMapping(
            value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseDto avatar(
            @PathVariable("id") Long id,
            @RequestParam("image") MultipartFile avatarImage,
            @RequestParam("writer") String writer,
            @RequestParam("password") String password
    ) {
        service.updateUserAvatar(id, avatarImage, writer, password);
        ResponseDto response = new ResponseDto();
        response.setMessage("이미지가 등록되었습니다.");
        return response;
    }

    @DeleteMapping ("/{itemId}")
    public ResponseDto delete(
            @PathVariable("itemId") Long id,
            @RequestBody ItemVerificationDto itemDto) {
        service.deleteItem(id, itemDto);
        ResponseDto response = new ResponseDto();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

}

