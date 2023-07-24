package com.example.shoppingmall.entity;

import com.example.shoppingmall.constant.Role;
import com.example.shoppingmall.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String memberId;

    @Column
    private String memberPw;

    @Column
    private String memberName;

    @Column
    private String memberPhone;

    @Enumerated (EnumType.STRING)
    //eunm 타입을 entity 속성으로 지정할 수 있음
    //enum 순서가 바뀌면 문제가 발생하므로 string으로 저장
    private Role role;

    @OneToMany(mappedBy = "cartMember", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();

    public static MemberEntity toMemberEntity(MemberDTO memberDTO, PasswordEncoder passwordEncoder){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberName(memberDTO.getMemberName());
        String password = passwordEncoder.encode(memberDTO.getMemberPw());
        memberEntity.setMemberPw(password);
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        return memberEntity;
    }
}
