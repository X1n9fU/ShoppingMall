package com.example.shoppingmall.entity;

import com.example.shoppingmall.dto.CartDTO;
import com.example.shoppingmall.repository.ItemRepository;
import com.example.shoppingmall.repository.MemberRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;

@Getter
@Setter
@Entity
@Table(name="cart_table")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity cartMember; //member_id 값이지만 넘어가는 것은 MemberEntity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private ItemEntity cartItem; //item_id 값이지만 넘어가는 것은 ItemEntity


    public static CartEntity toCartEntity(MemberEntity member , ItemEntity item) {
        CartEntity cart = new CartEntity();
        cart.setCartMember(member);
        cart.setCartItem(item);
        return cart;
    }
}
