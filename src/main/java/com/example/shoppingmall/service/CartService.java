package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.CartDTO;
import com.example.shoppingmall.dto.ItemDTO;
import com.example.shoppingmall.entity.CartEntity;
import com.example.shoppingmall.entity.ItemEntity;
import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.repository.CartRepository;
import com.example.shoppingmall.repository.ItemRepository;
import com.example.shoppingmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public void add(CartDTO cartDTO) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(cartDTO.getCartMember());
        Optional<ItemEntity> optionalItem = itemRepository.findById(cartDTO.getCartItem());
        //해당 아이디에 맞는 member 와 item Entity 를 가져와서 Cart 에 저장
        if (optionalMember.isPresent() && optionalItem.isPresent()) {
            MemberEntity member = optionalMember.get();
            ItemEntity item = optionalItem.get();
            cartRepository.save(CartEntity.toCartEntity(member, item));
        }
    }

    @Transactional
    public List<ItemDTO> findAllByMemberId(Long id) {
        List<ItemEntity> itemEntityList =  cartRepository.findCartItemByMemberId(id);

        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (ItemEntity item : itemEntityList){
            itemDTOList.add(ItemDTO.toItemDTO(item));
            //회원이 구매한 장바구니 내역들을 모두 가져와서 DTO 로 변환한다.
            //이 값을 장바구니 목록에 출력할 예정
        }

        return itemDTOList;
    }

    public void delete(Long id, Long memberId) {
        cartRepository.deleteByMemberIdAndItemId(memberId, id);
    }
}
