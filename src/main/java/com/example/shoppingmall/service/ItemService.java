package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.ItemDTO;
import com.example.shoppingmall.entity.ItemEntity;
import com.example.shoppingmall.entity.ItemFileEntity;
import com.example.shoppingmall.repository.ItemFileRepository;
import com.example.shoppingmall.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemFileRepository itemFileRepository;
    public void add(ItemDTO itemDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (itemDTO.getItemFile().isEmpty()) {
            // 첨부 파일 없음
            itemRepository.save(ItemEntity.toSaveEntity(itemDTO));
        }else {
            //첨부 파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듦
                //내 사진.jpg => 1341353412313_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. item_table에 해당 데이터 save 처리
                7. item_file_table에 해당 데이터   save 처리
            */
            MultipartFile itemFile = itemDTO.getItemFile(); //1.
            String originalFileName = itemFile.getOriginalFilename(); //2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName; //3.
            String savePath = "C:/Images/" + storedFileName; //4. C:/springboot_img/2349104134_내사진.jpg
            itemFile.transferTo(new File(savePath)); //5.
            //아이디 값이 있기 전의 itemEntity
            ItemEntity itemEntity = ItemEntity.toSaveFileEntity(itemDTO);
            Long savedId = itemRepository.save(itemEntity).getId();
            //현재 저장된 entity의 id값까지 가져온 item
            ItemEntity item = itemRepository.findById(savedId).get();

            ItemFileEntity itemFileEntity = ItemFileEntity.toItemFileEntity(item, originalFileName, storedFileName);
            itemFileRepository.save(itemFileEntity);
        }
    }

    @Transactional
    public List<ItemDTO> findAll(){
        List<ItemEntity> itemEntityList = itemRepository.findAll();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (ItemEntity itemEntity: itemEntityList){
            itemDTOList.add(ItemDTO.toItemDTO(itemEntity));
        }
        return itemDTOList;
    }

    @Transactional
    public ItemDTO findById(Long id){
        Optional<ItemEntity> optionalItemEntity = itemRepository.findById(id);
        if (optionalItemEntity.isPresent()){
            ItemEntity itemEntity = optionalItemEntity.get();
            ItemDTO itemDTO = ItemDTO.toItemDTO(itemEntity);
            return itemDTO;
        }
        else{
            return null;
        }
    }

    @Transactional
    public ItemDTO update(ItemDTO itemDTO) throws IOException {
        if (itemDTO.getItemFile().isEmpty()) {
            ItemEntity itemEntity = ItemEntity.toUpdateFileEntity(itemDTO);
            itemRepository.save(itemEntity);
            //findById 함수에서 Optional을 통한 과정이 있음
        }
        else { //사진을 업데이트 하는 과정에서
            Optional<ItemEntity> optionalItemEntity = itemRepository.findById(itemDTO.getId());
            if (optionalItemEntity.isPresent()){
                ItemEntity itemEntity = optionalItemEntity.get();
                if (!itemEntity.getItemFileEntityList().isEmpty()) {
                    Long entityId = itemEntity.getItemFileEntityList().get(0).getId();
                    String savedPath = "C:/Images/" + itemEntity.getItemFileEntityList().get(0).getStoredFileName();
                    File f = new File(savedPath);
                    if (f.exists()) f.delete(); //저장공간을 위해 기존의 사진을 지운다.
                    itemFileRepository.deleteById(entityId);
                }
            } //넘어온 DTO에서 itemFileEntityList를 통해 File 데이터베이스의 Id를 가져오고 해당 데이터를 지운다.
            MultipartFile itemFile = itemDTO.getItemFile(); //1.
            String originalFileName = itemFile.getOriginalFilename(); //2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName; //3.
            String savePath = "C:/Images/" + storedFileName; //4. C:/springboot_img/2349104134_내사진.jpg
            itemFile.transferTo(new File(savePath)); //5.
            ItemEntity itemEntity = ItemEntity.toUpdateFileEntity(itemDTO);
            Long savedId = itemRepository.save(itemEntity).getId();
            //현재 저장된 entity의 id값까지 가져온 item
            ItemEntity item = itemRepository.findById(savedId).get();

            ItemFileEntity itemFileEntity = ItemFileEntity.toItemFileEntity(item, originalFileName, storedFileName);
            itemFileRepository.save(itemFileEntity);

            //새롭게 수정되는 파일을 가져와서 새롭게 이름을 정의해주고 저장한다.
        }
        return findById(itemDTO.getId());

    }


    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
