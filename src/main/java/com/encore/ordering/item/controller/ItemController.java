package com.encore.ordering.item.controller;

import com.encore.ordering.common.CommonResponse;
import com.encore.ordering.item.dto.ItemReqDto;
import com.encore.ordering.item.dto.ItemResDto;
import com.encore.ordering.item.dto.ItemSearchDto;
import com.encore.ordering.item.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/item/create") // 관리자만 가능
    public ResponseEntity<CommonResponse> itemCreate(ItemReqDto itemReqDto) { // image를 받아야 하기때문에 json말고 form 데이터 multipart사용

        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED, "item succesfully create", null), HttpStatus.CREATED);
        // CommonResponse 공통문구 출력
    }

    @GetMapping("/items") // items는 로그인안해도 조회 가능하게 security config에서 풀어줌
    public List<ItemResDto> items(ItemSearchDto itemSearchDto, Pageable pageable) {

        return null;
    }


}
