package com.example.shoppingmall.service;

import com.example.shoppingmall.dto.MemberDTO;
import com.example.shoppingmall.entity.MemberEntity;
import com.example.shoppingmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO,passwordEncoder);
        validateDuplicateMember(memberEntity);
        memberRepository.save(memberEntity);
    }

    private void validateDuplicateMember(MemberEntity member){
        MemberEntity findMember = memberRepository.findByMemberId(member.getMemberId());
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

//    public String login(MemberDTO memberDTO) {
//        MemberEntity findMemberId = memberRepository.findByMemberId(memberDTO.getMemberId());
//        if (findMemberId == null) {
//            memberDTO.setLoginError(1);
//            return "아이디를 찾을 수 없습니다.";
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String createdPw = memberDTO.getMemberPw(); //작성된 비밀번호
//        String realPw = findMemberId.getMemberPw(); //db에 저장된 비밀번호
//
//        if (!encoder.matches(createdPw, realPw)) {
//            memberDTO.setLoginError(1);
//            return "비밀번호가 일치하지 않습니다";
//        }
//
//        return findMemberId.getMemberName();
//    }

    public Optional<MemberEntity> findOne(String insertedUserId) {
        return Optional.ofNullable(memberRepository.findByMemberId(insertedUserId));
    }
}
