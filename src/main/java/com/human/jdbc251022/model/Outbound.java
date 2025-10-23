package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Outbound {
    private int id;
    private int batchId;
    private int empId;
    private int qty;
    private LocalDateTime obDate;
}
