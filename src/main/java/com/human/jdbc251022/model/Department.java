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

}
