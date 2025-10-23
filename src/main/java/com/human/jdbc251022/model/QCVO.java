package com.human.jdbc251022.model;

import lombok.*;

import java.util.Date;
// java.sql.Date가 아닌 java.util.Date를 주로 사용합니다.

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor

public class QCVO {
    private String qcId;
    private int sampleId;
    private String testItem;
    private String status;
    private String passFail;
    private Date qcDate;
    private int tester;

    public QCVO(String qcId, String testItem, String status, Date qcDate, int tester){

    }
}