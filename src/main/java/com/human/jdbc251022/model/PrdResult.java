package com.human.jdbc251022.model;
// 생산 번호, 실제 생산량, 목표 생산량,  생산 효율/달성률 ,공정 번호, 담당자,  생산일 조회
// RESULT_ID, PQTY, WQTY, PE,  PROCESS_ID, EMP_ID, PRODUCTION_DATE

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor

public class PrdResult {
    private int resultId;

    // 실제 생산량 (PQTY)
    private int PQTY;

    // 목표 생산량 (WQTY)
    private int WQTY;

    // 생산 효율/달성률 (PE)
    private double PE;

    // 공정 번호 (PROCESS_ID)
    private String processId;

    // 담당자 (EMP_ID)
    private String employeeId;

    // 생산일 조회 (PRODUCTION_DATE)
    private String productionDate;

}
