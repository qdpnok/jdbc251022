package com.human.jdbc251022.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int id;             // 부서 ID (DEPT_ID)
    private String name;        // 부서명 (DEPT_NAME)
    private String type;        // 부서 타입 (TYPE)
    private int factoryNO;      // 공장 번호 (FACTORY_NO)
    private String manager;     // 관리자 이름 (MANAGER)
    private LocalDateTime createDate; // 등록일 (CREATE_DATE)

    public Department(int id, String name) {
    }

}
