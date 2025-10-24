package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Inventory {
    private int id;
    private int matId;
    private int ibId;
    private LocalDateTime expDate;
    private int qty;
    private String loc;

    @Override
    public String toString() {
        return "재고 번호: " + id + "\n" +
                "원자재 번호: " + matId + "\n" +
                "입고 번호: " + ibId + "\n" +
                "유효기간: " + expDate + "\n" +
                "수량: " + qty + "\n" +
                "위치: " + loc + "\n" +
                "-".repeat(10);
    }
}
