package com.example.shoppingmall.entity;

import com.example.shoppingmall.dto.ItemDTO;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="item_table")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=20, nullable=false)
    private String itemName;
    @Column(length=300)
    private String itemDetail;
    @Column
    private Long itemPrice;

    @Column
    private int fileAttached; //1 or 0

    @OneToMany(mappedBy = "cartItem", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "itemEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemFileEntity> itemFileEntityList = new ArrayList<>();

    public static ItemEntity toSaveEntity(ItemDTO itemDTO){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemName(itemDTO.getItemName());
        itemEntity.setItemDetail(itemDTO.getItemDetail());
        itemEntity.setItemPrice(itemDTO.getItemPrice());
        itemEntity.setFileAttached(0); //파일 없음.
        return itemEntity;
    }

    public static ItemEntity toSaveFileEntity(ItemDTO itemDTO) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemName(itemDTO.getItemName());
        itemEntity.setItemDetail(itemDTO.getItemDetail());
        itemEntity.setItemPrice(itemDTO.getItemPrice());
        itemEntity.setFileAttached(1); //파일 있음.
        return itemEntity;
    }

    public static ItemEntity toUpdateFileEntity(ItemDTO itemDTO) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemDTO.getId());
        itemEntity.setItemName(itemDTO.getItemName());
        itemEntity.setItemDetail(itemDTO.getItemDetail());
        itemEntity.setItemPrice(itemDTO.getItemPrice());
        itemEntity.setFileAttached(1); //파일 있음.
        return itemEntity;
    }
}
