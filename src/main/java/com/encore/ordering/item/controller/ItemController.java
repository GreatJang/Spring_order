package com.encore.ordering.item.controller;

import com.encore.ordering.common.CommonResponse;
import com.encore.ordering.item.domain.Item;
import com.encore.ordering.item.dto.ItemReqDto;
import com.encore.ordering.item.dto.ItemResDto;
import com.encore.ordering.item.dto.ItemSearchDto;
import com.encore.ordering.item.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        Item item = itemService.create(itemReqDto);
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.CREATED, "item succesfully create", item.getId())
                , HttpStatus.CREATED);
        // CommonResponse 공통문구 출력
    }

    @GetMapping("/items") // items는 로그인안해도 조회 가능하게 security config에서 풀어줌
    public List<ItemResDto> items(ItemSearchDto itemSearchDto, Pageable pageable) {

        return null;
    }

    @GetMapping("/item/{id}/image") //get 했을때 postman에서 이미지가 바로 보인다.
    public Resource getImage(@PathVariable Long id){
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/item/{id}/update")
    public ResponseEntity<CommonResponse> itemUpdate(@PathVariable Long id, ItemReqDto itemReqDto) {
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<CommonResponse> itemDelete(@PathVariable Long id) {
        Item item = itemService.delete(id);
        return new ResponseEntity<>(
                new CommonResponse(HttpStatus.OK, "item succesfully deleted", item.getId())
                , HttpStatus.OK);
    }


}
