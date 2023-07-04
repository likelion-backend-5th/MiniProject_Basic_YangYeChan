package com.example.miniproject_basic_yangyechan.controller;

import com.example.miniproject_basic_yangyechan.dto.*;
import com.example.miniproject_basic_yangyechan.service.ItemService;
import com.example.miniproject_basic_yangyechan.service.NegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class NegotiationController {
    private final NegotiationService service;

    @PostMapping("/{itemId}/proposal")
    public ResponseDto create(
            @RequestBody ProposalDto proposalDto,
            @PathVariable("itemId") Long item_id) {
        service.createProposal(proposalDto, item_id);
        ResponseDto response = new ResponseDto();
        response.setMessage("구매 제안이 등록되었습니다.");
        return response;
    }

    @GetMapping("/{itemId}/proposals")
    public Page<ProposalGetListDto> readAll(
            @PathVariable("itemId") Long item_id,
            @RequestParam(value = "writer") String writer,
            @RequestParam(value = "password") String password
    ) {
        Integer page = 0;
        Integer limit = 25;
        return service.readProposalPaged(page, limit, item_id, writer, password);
    }

    @PutMapping("/{itemId}/proposals/{proposalId}")
    public ResponseDto update(
            @PathVariable("itemId") Long item_id,
            @PathVariable("proposalId") Long proposalid,
            @RequestBody ProposalDto proposalDto
    ) {
        if (proposalDto.getSuggestedPrice() != null) {
            service.updateProposal(item_id, proposalid, proposalDto);

            ResponseDto response = new ResponseDto();
            response.setMessage("제안이 수정되었습니다.");
            return response;
        } else if (proposalDto.getStatus().equals("수락") || proposalDto.getStatus().equals("거절")) {
            service.updateProposalStatus(item_id, proposalid, proposalDto);

            ResponseDto response = new ResponseDto();
            response.setMessage("제안의 상태가 변경되었습니다.");
            return response;
        } else if (proposalDto.getStatus().equals("확정")) {
            service.updateProposalComplete(item_id, proposalid, proposalDto);

            ResponseDto response = new ResponseDto();
            response.setMessage("구매가 확정되었습니다.");
            return response;
        }else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping ("/{itemId}/proposals/{proposalId}")
    public ResponseDto delete(
            @PathVariable("itemId") Long item_id,
            @PathVariable("proposalId") Long proposalid,
            @RequestBody ProposalDto Dto) {
        service.deleteProposal(item_id, proposalid, Dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("제안을 삭제했습니다.");
        return response;
    }
}
