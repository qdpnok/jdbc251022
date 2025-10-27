package com.human.jdbc251022.model;

import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor

public class TestQc {
    private String testItem;
    private String status;
    private String passFail;
    private Date qcDate;
    private int tester;

}
