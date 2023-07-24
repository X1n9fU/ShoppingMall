package com.example.shoppingmall.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

    private Long id;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberPhone;

    private int loginError=0;
}
