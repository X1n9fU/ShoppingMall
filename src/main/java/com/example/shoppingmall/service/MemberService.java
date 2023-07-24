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

    public Optional<MemberEntity> findOne(String insertedUserId) {
        return Optional.ofNullable(memberRepository.findByMemberId(insertedUserId));
    }
}
