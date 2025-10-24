package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Department {
    private int id;
    private String name;
    private String job;
    private int deptId;
    private int sal;
    private LocalDateTime hireDate;
    private String type;
    private int factoryNO;
    private String manager;
    private int createDate;

    public Department(int deptId, String deptName, String type, int factoryNo,
                      String manager, LocalDateTime createDate) {
    }

    public Department(int id, String name) {
    }
}
