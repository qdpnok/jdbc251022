package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Batch {
    private int id;
    private int prodId;
    private int unit;
    private String status;
    private String note;

    @Override
    public String toString() {
        return "배치 번호: " + id + "\n" +
                "제품 번호: " + prodId + "\n" +
                "단위: " + unit + "\n" +
                "상태: " + status + "\n" +
                "특이사항: " + note + "\n" +
                "-".repeat(10);
    }
}
