package com.example.shoppingmall.config;

import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.service.MemberService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

//로그인 서비스
@AllArgsConstructor
@Component
public class MyUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        //insertedUserId 부분에는 SecurityConfig 에서 설정해놓은 usernameParameter("memberId")에 해당하는 정보가 넘어온다.
        Optional<MemberEntity> findOne = memberService.findOne(insertedUserId);
        MemberEntity member = findOne.orElseThrow(() ->
                new UsernameNotFoundException("없는 회원입니다."));
        //비밀번호가 동일한지 체크는 알아서 진행해주기 때문에 아이디만 가지고 DB 에서 유저 정보를 가져옴
        return User.builder()
                .username(member.getMemberId())
                .password(member.getMemberPw())
                .roles(String.valueOf(member.getRole()))
                .build();
        //유저 정보를 가져오면 UserDetails 로 담아서 리턴해야한다.
    }
    //실제 DB 에서 데이터를 가져와서 스프링부트한테 넣어주는 방식을 추가해서 임시 비밀번호가 뜨지 않음

}
