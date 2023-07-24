package com.example.shoppingmall.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDTO {
    Long id;
    Long cartMember;
    Long cartItem;

    public static CartDTO toCartDTO(Long cartMember, Long cartItem) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartMember(cartMember);
        cartDTO.setCartItem(cartItem);
        return cartDTO;
    }
}


