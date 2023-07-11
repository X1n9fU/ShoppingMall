package com.example.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//itemEntity와 부모 자식 관계
@Entity
@Getter
@Setter
@Table(name="item_file_table")
public class ItemFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private ItemEntity itemEntity; //결국 item_id 값이지만 넘어가는 것은 entity

    public static ItemFileEntity toItemFileEntity(ItemEntity itemEntity, String originalFileName, String storedFileName){
        ItemFileEntity itemFileEntity = new ItemFileEntity();
        itemFileEntity.setOriginalFileName(originalFileName);
        itemFileEntity.setStoredFileName(storedFileName);
        itemFileEntity.setItemEntity(itemEntity);
        return itemFileEntity;
    }
}
