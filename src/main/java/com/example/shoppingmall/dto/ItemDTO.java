package com.example.shoppingmall.dto;

import com.example.shoppingmall.entity.ItemEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
//생성자 생성
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String itemName;
    private String itemDetail;
    private Long itemPrice;

    private MultipartFile itemFile;
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부1, 미첨부0)

    public static ItemDTO toItemDTO(ItemEntity itemEntity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(itemEntity.getId());
        itemDTO.setItemName(itemEntity.getItemName());
        itemDTO.setItemDetail(itemEntity.getItemDetail());
        itemDTO.setItemPrice(itemEntity.getItemPrice());

        if (itemEntity.getFileAttached() == 0) {
            itemDTO.setFileAttached(itemEntity.getFileAttached()); // 0
        } else {
            itemDTO.setFileAttached(itemEntity.getFileAttached()); // 1
            if (!itemEntity.getItemFileEntityList().isEmpty()) {
                //파일 이름을 가져가야 함.
                //originalFileName, storedFileName : item_file_table(ItemFileEntity) -> 하지만 itemEntity만 불러옴
                //join ! -> select * from item_table i, item_file_table if where i.id=if.item_id and where i.id=?
                itemDTO.setOriginalFileName(itemEntity.getItemFileEntityList().get(0).getOriginalFileName());
                itemDTO.setStoredFileName(itemEntity.getItemFileEntityList().get(0).getStoredFileName());
            }
        }

        return itemDTO;
    }
}
