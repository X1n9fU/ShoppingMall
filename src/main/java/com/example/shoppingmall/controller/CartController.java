package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.CartDTO;
import com.example.shoppingmall.dto.ItemDTO;
import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.service.CartService;
import com.example.shoppingmall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final MemberRepository memberRepository;
    private final ItemService itemService;

//    public String listForm(Model model){
//        List<ItemDTO> itemDTOList = itemService.findAll();
//        model.addAttribute("itemList", itemDTOList);
//        return "list";
//    }

    @GetMapping("/add/{id}")
    public String addCart(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        CartDTO cartDTO = new CartDTO();
        MemberEntity member = memberRepository.findByMemberId(user.getUsername());
        cartDTO.setCartItem(id);
        cartDTO.setCartMember(member.getId());
        //item_id와 member_id를 모두 DTO 에 넣어준다.
        cartService.add(cartDTO);
        model.addAttribute("message","장바구니에 추가하였습니다.");
        model.addAttribute("url", "/mall/list" );
        return "message";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        ItemDTO itemDTO = itemService.findById(id);
        model.addAttribute("item", itemDTO);
        return "cartDetail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal User user){
        MemberEntity member = memberRepository.findByMemberId(user.getUsername());
        Long memberId = member.getId();
        cartService.delete(id,memberId);
        return "redirect:/cart";

    }

}
