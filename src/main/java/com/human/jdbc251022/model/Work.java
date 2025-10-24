package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;
@Getter @Setter @ToString
@AllArgsConstructor
    @NoArgsConstructor
    public class Work {
        private int workId;                // 작업 ID (PK)
        private int processNo;             // 공정 번호
        private String workOrder;      // WORK_ORDER (작업지시 내용)
        private int assignedTo;     // ASSIGNED_TO (담당자)
        private LocalDateTime startTime;   // 작업 시작 시간
        private LocalDateTime endTime;     // 작업 종료 시간
        private String result;         // RESULT (작업결과)
        private int batchId;               // 배치번호
        private String materialInput;  // MATERIAL_INPUT (자재명)
        private int qty;                   // 생산량


    public void setBatchInput(String batchInput) {
    }

    public void setInputQuantity(int inputQuantity) {
    }

}
