package com.encore.ordering.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    private String name;
    private String category;
    private int price;
    private int stockQuantity; // 재고
    private String imagePath; // item 이미지 경로
    private String delYn; // item 삭제 유무

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public void deleteItem(){ // item 삭제 시 호출
        this.delYn = "Y";
    }

    public void updateStockQuantity(int newQuantity) { // update 시 stockQuantity를 newQuantity로 변경
        this.stockQuantity = newQuantity;
    }
}
