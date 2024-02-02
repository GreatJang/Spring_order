package com.encore.ordering.item.service;

import com.encore.ordering.item.domain.Item;
import com.encore.ordering.item.dto.ItemReqDto;
import com.encore.ordering.item.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(ItemReqDto itemReqDto) {
        MultipartFile multipartFile = itemReqDto.getItemImage();
        String fileName = multipartFile.getOriginalFilename(); // fileName 가져오기
        Item new_item = Item.builder()
                .name(itemReqDto.getName())
                .category(itemReqDto.getCategory())
                .price(itemReqDto.getPrice())
                .stockQuantity(itemReqDto.getStockQuantity())
                .build();
        Item item = itemRepository.save(new_item); // DB저장
        Path path = Paths.get("C:/Users/Playdata/Desktop/tmp/", item.getId()+"_"+fileName); // 사진 파일은 해당 경로에 넣겠다.
//        Id를 넣으므로서 중복제거
        item.setImagePath(path.toString());

        try { // 파일 넣을때 에러처리 중요하다.
            byte[] bytes = multipartFile.getBytes();
//            있으면 덮어쓰기, 없으면 create
            Files.write(path, bytes, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new IllegalArgumentException("image not available");
        }

        return item;
    }

    public Item delete(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(()->new EntityNotFoundException("not found item"));
        item.deleteItem();
        return item;


    }
}
