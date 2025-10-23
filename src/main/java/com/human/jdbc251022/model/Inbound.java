package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Inbound {
    private int id;
    private int matId;
    private int empId;
    private int qty;
    private LocalDateTime regDate;
}
