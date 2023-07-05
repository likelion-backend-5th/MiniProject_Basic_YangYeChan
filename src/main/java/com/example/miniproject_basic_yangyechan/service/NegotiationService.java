package com.example.miniproject_basic_yangyechan.service;

import com.example.miniproject_basic_yangyechan.dto.*;
import com.example.miniproject_basic_yangyechan.entity.CommentEntity;
import com.example.miniproject_basic_yangyechan.entity.ItemEntity;
import com.example.miniproject_basic_yangyechan.entity.NegotiationEntity;
import com.example.miniproject_basic_yangyechan.repository.CommentRepository;
import com.example.miniproject_basic_yangyechan.repository.ItemRepository;
import com.example.miniproject_basic_yangyechan.repository.NegotiationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NegotiationService {
    private final NegotiationRepository repository;
    private final ItemRepository repository2;

    public ProposalDto createProposal(ProposalDto dto, Long itemid) {
        if (!repository2.existsById(itemid))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        NegotiationEntity newItem = new NegotiationEntity();
        newItem.setItemid(itemid);
        newItem.setSuggestedPrice(dto.getSuggestedPrice());
        newItem.setStatus("제안");
        newItem.setWriter(dto.getWriter());
        newItem.setPassword(dto.getPassword());
        return ProposalDto
                .fromEntity(repository.save(newItem));
    }

    public Page<ProposalGetListDto> readProposalPaged(Integer pageNumber, Integer pageSize, Long item_id, String writer, String password) {
        if (!repository2.existsById(item_id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌때 0번 페이지를 달라고 요청하는 Pageable
        Pageable pageable = PageRequest.of(
                pageNumber, pageSize, Sort.by("id").ascending());
        Page<NegotiationEntity> articleEntityPage
                = repository.findAll(pageable);
        // map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Stream으로
        // Page.map: 전달받은 함수를 각 원소에 인자로 전달한 결과를
        // 다시 모아서 Page로
        Page<ProposalGetListDto> articleDtoPage
                = articleEntityPage.map(ProposalGetListDto::fromEntity2);
        return articleDtoPage;
    }

    public ProposalDto updateProposal(Long itemid, Long proposalid, ProposalDto dto) {
        Optional<NegotiationEntity> optionalEntity2
                = repository.findById(proposalid);
        if (optionalEntity2.isPresent()) {
            NegotiationEntity item = optionalEntity2.get();
            if (item.getWriter().equals(dto.getWriter())
                    && item.getPassword().equals(dto.getPassword())
                    && item.getItemid().equals(itemid)) {
                NegotiationEntity targetEntity = optionalEntity2.get();
                targetEntity.setItemid(itemid);
                targetEntity.setSuggestedPrice(dto.getSuggestedPrice());
                targetEntity.setStatus("제안");
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                return ProposalDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void deleteProposal(Long itemid, Long proposalid, ProposalDto Dto) {
        Optional<NegotiationEntity> optionalEntity = repository.findById(proposalid);
        if (optionalEntity.isPresent()) {
            NegotiationEntity item = optionalEntity.get();

            if (item.getWriter().equals(Dto.getWriter())
                    && item.getPassword().equals(Dto.getPassword())
                    && item.getItemid().equals(itemid)) {
                repository.deleteById(proposalid);
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public ProposalDto updateProposalStatus(Long itemid, Long proposalid, ProposalDto dto) {
        Optional<NegotiationEntity> optionalEntity
                = repository.findById(proposalid);
        Optional<ItemEntity> optionalEntity2
                = repository2.findById(itemid);
        if (optionalEntity.isPresent()) {
            ItemEntity item2 = optionalEntity2.get();
            if (item2.getWriter().equals(dto.getWriter())
                    && item2.getPassword().equals(dto.getPassword())
                    && item2.getId().equals(itemid)) {
                NegotiationEntity targetEntity = optionalEntity.get();
                targetEntity.setItemid(itemid);
                targetEntity.setStatus(dto.getStatus());
                return ProposalDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public ProposalDto updateProposalComplete(Long itemid, Long proposalid, ProposalDto dto) {
        Optional<NegotiationEntity> optionalEntity
                = repository.findById(proposalid);
        Optional<ItemEntity> optionalEntity2
                = repository2.findById(itemid);
        if (optionalEntity.isPresent()) {
            NegotiationEntity item = optionalEntity.get();
            if (item.getWriter().equals(dto.getWriter())
                    && item.getPassword().equals(dto.getPassword())
                    && item.getItemid().equals(itemid)
                    && item.getStatus().equals("수락")) {
                NegotiationEntity targetEntity = optionalEntity.get();
                targetEntity.setItemid(itemid);
                targetEntity.setStatus("확정");
                targetEntity.setWriter(dto.getWriter());
                targetEntity.setPassword(dto.getPassword());
                ItemEntity targetitem = optionalEntity2.get();
                targetitem.setStatus("판매 완료");
                repository2.save(targetitem);
                for(NegotiationEntity target : repository.findAllByItemid(itemid)){
                    if (target.getItemid().equals(itemid)) {
                        if (target.getStatus().equals("확정")) {
                            target.setStatus("확정");
                            repository.save(target);
                        }else {
                            target.setStatus("거절");
                            repository.save(target);
                        }
                    }
                }
                return ProposalDto.fromEntity(repository.save(targetEntity));
            } else {
                // 작성자와 비밀번호가 일치하지 않으면 예외 발생
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
