package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Member {
    private String email;
    private String pwd;
    private String name;
    private LocalDateTime regDate; // 회원가입 시간
}
