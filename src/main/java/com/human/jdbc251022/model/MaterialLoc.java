package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MaterialLoc {
    private int id;
    private String name;
    private int qty;
    private String loc;

    @Override
    public String toString() {
        return "재고 번호: " + id + "\n" +
                "자재 이름: " + name + "\n" +
                "수량: " + qty + "\n" +
                "위치: " + loc + "\n" +
                "-".repeat(10);
    }
}
