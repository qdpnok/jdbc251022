package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String job;
    private int deptId;
    private int sal;
    private LocalDateTime hireDate;
}
