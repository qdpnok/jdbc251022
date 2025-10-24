package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String job;
    private int deptId;
    private int sal;
    private LocalDateTime hireDate;
    @Override
    public String toString() {
        return "아이디: " + id + "\n" +
                "이름: " + name + "\n" +
                "직무: " + job + "\n" +
                "부서: " + deptId + "\n" +
                "연봉: " + sal + "\n" +
                "입사일: " + hireDate + "\n" +
                "-".repeat(10);
    }
}
