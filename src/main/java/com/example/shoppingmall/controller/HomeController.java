package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.ItemDTO;
import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.repository.MemberRepository;
import com.example.shoppingmall.service.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class HomeController {
    private final MemberRepository memberRepository;
    private final CartService cartService;

    @GetMapping("/")
    public String index() {
        return "home";
    }

//    @GetMapping("/loginHome")
//    public String loginHome(){
//        return "loginHome";
//    }
    @GetMapping("/loginHome")
    public String loginHome(@AuthenticationPrincipal User user, Model model){
        //AuthenticationPrincipal 은 UserDetailsService 에서 Return 한 객체를 가져올 수 있다.
        //해당 User 은 MyUserDetailsService 에서 받아온 User 이다.
        MemberEntity member = memberRepository.findByMemberId(user.getUsername());
        model.addAttribute("name", member.getMemberName());
        return "loginHome";
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal User user, Model model){
        MemberEntity member = memberRepository.findByMemberId(user.getUsername());
        model.addAttribute("name", member.getMemberName());
        //회원의 이름 넘겨주기

        List<ItemDTO> itemDTOList = cartService.findAllByMemberId(member.getId());
        model.addAttribute("cartList", itemDTOList);

        int price = 0;
        for (ItemDTO itemDTO : itemDTOList){
            price += itemDTO.getItemPrice();
        }
        model.addAttribute("price", price);
        System.out.println("itemDTOList" + itemDTOList);
        return "cart";
    }

}
