package com.human.jdbc251022.model;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Batch {
    private int id;
    private int prodId;
    private int unit;
    private String status;
    private String note;
}
