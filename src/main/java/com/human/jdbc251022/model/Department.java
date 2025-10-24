package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Department {
    private int id;
    private String deptname;
    private String job;
    private int deptId;
    private int sal;
    private LocalDateTime hireDate;
    private String type;
    private int factoryNO;
    private String manager;
    private int createDate;

    public Department(int deptId, String deptName, String type, int factoryNo,
                      String manager, int createDate) {
        this.deptId = deptId;
        this.deptname = deptName;
        this.type = type;
        this.factoryNO = factoryNo;
        this.manager = manager;
        this.createDate = createDate;
    }

    public Department(int id, String name) {
    }

    public Department(int deptId, String deptName, String type, int factoryNo, String manager, LocalDateTime createDate) {
    }

    public Object getName() {
        return null;
    }
}
