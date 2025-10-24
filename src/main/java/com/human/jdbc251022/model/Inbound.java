package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Inbound {
    private int id;
    private int matId;
    private int empId;
    private int qty;
    private LocalDateTime ibDate;

    @Override
    public String toString() {
        return "입고 번호: " + id + "\n" +
                "원자재 번호: " + matId + "\n" +
                "담당자: " + empId + "\n" +
                "수량: " + qty + "\n" +
                "입고일: " + ibDate + "\n" +
                "-".repeat(10);
    }
}
