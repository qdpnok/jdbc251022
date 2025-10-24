package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;
@Getter @Setter @ToString
@AllArgsConstructor
    @NoArgsConstructor
    public class Work {
        private int workId;                // 작업 ID (PK)
        private int processNo;             // 공정 번호
        private int batchId;               // 배치번호
        private int qty;                   // 생산량
        private int assignedTo;         // 작업 배정자
        private LocalDateTime startTime;   // 작업 시작 시간
        private LocalDateTime endTime;     // 작업 종료 시간


}