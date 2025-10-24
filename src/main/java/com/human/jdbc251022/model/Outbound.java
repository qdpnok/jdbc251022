package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Outbound {
    private int id;
    private int batchId;
    private int empId;
    private int qty;
    private LocalDateTime obDate;

    @Override
    public String toString() {
        return "출고 번호: " + id + "\n" +
                "배치 번호: " + batchId + "\n" +
                "담당자: " + empId + "\n" +
                "수량: " + qty + "\n" +
                "출고일: " + obDate + "\n" +
                "-".repeat(10);
    }
}
