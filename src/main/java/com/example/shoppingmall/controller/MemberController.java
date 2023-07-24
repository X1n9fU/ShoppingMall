package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.MemberDTO;
import com.example.shoppingmall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDTO){
        System.out.println("memberDTO" + memberDTO);
        memberService.save(memberDTO);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(){return "login";}

//    @PostMapping("/login")
//    public String login(@ModelAttribute MemberDTO memberDTO, Model model){
//        String result = memberService.login(memberDTO);
//        if (memberDTO.getLoginError() == 1) {
//            model.addAttribute("result", result);
//            return "redirect:/login";
//        }else {
//            model.addAttribute("name", result);
//            return "redirect:/loginHome";
//        }
//    }

}
