package com.human.jdbc251022.model;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrder {
    private int workOrderId;
    private int processId;
    private int batchId;
    private int qty;
    private LocalDate startDate;
    private LocalDate endDate;
    private int worker;
}

