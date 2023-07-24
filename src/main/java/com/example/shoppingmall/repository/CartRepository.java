package com.example.shoppingmall.repository;

import com.example.shoppingmall.entity.CartEntity;
import com.example.shoppingmall.entity.ItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity,Long > {
    @Query("SELECT cartItem FROM CartEntity c JOIN c.cartMember m WHERE m.id = :memberId")
    List<ItemEntity> findCartItemByMemberId(@Param("memberId") Long memberId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_table c WHERE c.member_id = :memberId AND c.item_id = :itemId", nativeQuery = true)
    void deleteByMemberIdAndItemId(@Param("memberId") Long memberId, @Param("itemId") Long itemId);
}
