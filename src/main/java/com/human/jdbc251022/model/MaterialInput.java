package com.human.jdbc251022.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class MaterialInput {
    private int id;
    private int batchId;
    private int materialId;
    private int workOrderId;
    private int inputQty;
    private LocalDateTime date;
}

