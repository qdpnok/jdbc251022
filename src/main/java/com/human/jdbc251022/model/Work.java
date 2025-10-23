package com.human.jdbc251022.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
    @NoArgsConstructor
    public class Work {
        private int workId;                // 작업 ID (PK)
        private int processNo;             // 공정 번호
        private String workOrder;          // 작업 지시 내용
        private String assignedTo;         // 작업 배정자
        private LocalDateTime startTime;   // 작업 시작 시간
        private LocalDateTime endTime;     // 작업 종료 시간
        private String result;             // 공정 실적
        private String batchInput;         // 생산 배치별 투입 내역
        private String materialInput;      // 원자재별 투입 내역
        private int inputQuantity;         // 투입 수량

    public void setWorkId(int workId) {
    }

    public void setProcessNo(int processNo) {
    }

    public void setWorkOrder(String workOrder) {
    }

    public void setAssignedTo(String assignedTo) {
    }

    public void setStartTime(LocalDateTime localDateTime) {
    }

    public void setEndTime(LocalDateTime localDateTime) {
    }

    public void setResult(String result) {
    }

    public void setBatchInput(String batchInput) {
    }

    public void setMaterialInput(String materialInput) {
    }

    public void setInputQuantity(int inputQuantity) {
    }

    public Object getProcessNo() {
        return null;
    }

    public Object getWorkOrder() {
        return null;
    }

    public Object getAssignedTo() {
        return null;
    }

    public Object getStartTime() {
        return null;
    }

    public Object getEndTime() {
        return null;
    }

    public Object getResult() {
        return null;
    }

    public Object getBatchInput() {
        return null;
    }

    public Object getMaterialInput() {
        return null;
    }

    public Object getInputQuantity() {
        return null;
    }

    public Object getWorkId() {
        return null;
    }
}