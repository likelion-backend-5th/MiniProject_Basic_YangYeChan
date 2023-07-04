package com.example.miniproject_basic_yangyechan.service;

import com.example.miniproject_basic_yangyechan.dto.ItemDto;
import com.example.miniproject_basic_yangyechan.dto.ItemGetDto;
import com.example.miniproject_basic_yangyechan.dto.ItemGetListDto;
import com.example.miniproject_basic_yangyechan.dto.ItemVerificationDto;
import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import com.example.miniproject_basic_yangyechan.exceptions.UserNotFoundException;
import com.example.miniproject_basic_yangyechan.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    // CREATE
    public ItemDto createItem(ItemDto dto) {
        ItemEntity newItem = new ItemEntity();
        newItem.setTitle(dto.getTitle());
        newItem.setDescription(dto.getDescription());
        newItem.setMinPriceWanted(dto.getMinPriceWanted());
        newItem.setMinPriceWanted(dto.getMinPriceWanted());
        newItem.setWriter(dto.getWriter());
        newItem.setPassword(dto.getPassword());
        newItem.setStatus("판매중");
        return ItemDto
                .fromEntity(repository.save(newItem));
    }

    // READ Page
    public Page<ItemGetListDto> readItemPaged(
            Integer pageNumber, Integer pageSize
    ) {
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").ascending());
        Page<ItemEntity> articleEntityPage
                = repository.findAll(pageable);
        // map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Stream으로
        // Page.map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Page로
        Page<ItemGetListDto> articleDtoPage
                = articleEntityPage.map(ItemGetListDto::fromEntity);
        return articleDtoPage;
    }

    // READ
    public ItemGetDto readItemById(Long id) {
        Optional<ItemEntity> optionalEntity
                = repository.findById(id);
        if (optionalEntity.isPresent()) {
            return ItemGetDto
                    .fromEntity(optionalEntity.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // UPDATE
    public ItemDto updateItem(Long id, ItemDto dto) {
        Optional<ItemEntity> optionalEntity
                = repository.findById(id);
        if (optionalEntity.isPresent()) {
            ItemEntity item = optionalEntity.get();
            if (item.getWriter().equals(dto.getWriter())
                    && item.getPassword().equals(dto.getPassword())) {
                ItemEntity targetEntity = optionalEntity.get();
                targetEntity.setTitle(dto.getTitle());
                targetEntity.setDescription(dto.getDescription());
                targetEntity.setMinPriceWanted(dto.getMinPriceWanted());
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                targetEntity.setStatus(dto.getStatus());
                return ItemDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    // UPDATE Image
    public ItemDto updateUserAvatar(Long id, MultipartFile avatarImage, String writer, String password) {
        // 사용자가 프로필 이미지를 업로드 한다.

        // 1. 유저 존재 확인
        Optional<ItemEntity> optionalUser
                = repository.findById(id);
        if (optionalUser.isPresent()) {
            ItemEntity itemAvatar = optionalUser.get();
            if (itemAvatar.getWriter().equals(writer)
                    && itemAvatar.getPassword().equals(password)) {
                // media/filename.png
                // media/<업로드 시각>.png
                // 2. 파일을 어디에 업로드 할건지
                // media/{userId}/profile.{파일 확장자}

                // 2-1. 폴더만 만드는 과정
                String profileDir = String.format("media/%d/", id);
                try {
                    Files.createDirectories(Path.of(profileDir));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // 2-2. 확장자를 포함한 이미지 이름 만들기 (profile.{확장자})
                String originalFilename = avatarImage.getOriginalFilename();
                // queue.png => fileNameSplit = {"queue", "png"}
                String[] fileNameSplit = originalFilename.split("\\.");
                String extension = fileNameSplit[fileNameSplit.length - 1];
                String profileFilename = "profile." + extension;

                // 2-3. 폴더와 파일 경로를 포함한 이름 만들기
                String profilePath = profileDir + profileFilename;


                // 3. MultipartFile 을 저장하기
                try {
                    avatarImage.transferTo(Path.of(profilePath));
                } catch (IOException e) {

                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // 4. UserEntity 업데이트 (정적 프로필 이미지를 회수할 수 있는 URL)
                // http://localhost:8080/static/1/profile.png

                ItemEntity userEntity = optionalUser.get();
                userEntity.setImageUrl(String.format("/static/%d/%s", id, profileFilename));
                return ItemDto.fromEntity(repository.save(userEntity));
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }




    // DELETE
    public void deleteItem(Long id, ItemVerificationDto itemDto) {
        Optional<ItemEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            ItemEntity item = optionalEntity.get();

            if (item.getWriter().equals(itemDto.getWriter())
                    && item.getPassword().equals(itemDto.getPassword())) {
                repository.deleteById(id);
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
