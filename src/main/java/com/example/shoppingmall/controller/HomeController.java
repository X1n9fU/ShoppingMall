package com.example.shoppingmall.controller;

import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class HomeController {
    private final MemberRepository memberRepository;

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
}
